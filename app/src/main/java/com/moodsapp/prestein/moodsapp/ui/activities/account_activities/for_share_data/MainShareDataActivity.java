package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_share_data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.MyProfileDB;
import com.moodsapp.prestein.moodsapp.data.Database.RecentChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.model.RecentChatRoom;
import com.skyfishjy.library.RippleBackground;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainShareDataActivity extends AppCompatActivity {
    private String UserIdentifier;
    private RecyclerView mSharedRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    public static final int SHARE_MY_STATUS_ITEM_TYPE=1;
    public static final int SHARE_RECENT_CONTACTS_ITEM_TYPE=2;
    public static final int SHARE_MORE_CONTACTS_ITEM_TYPE=3;
    private shareDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_share_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_share_activity_toolbar);
        setSupportActionBar(toolbar);
        if ( ExtractedStrings.UID.equals("")){
            UserIdentifier= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(0,getApplicationContext());
            ExtractedStrings.UID=UserIdentifier;
            Toast.makeText(this, UserIdentifier, Toast.LENGTH_SHORT).show();
        }
        if ( ExtractedStrings.NAME.equals("")){
            ExtractedStrings.NAME= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(1,getApplicationContext());
        }
        if ( ExtractedStrings.MY_COUNTRY.equals("")){
            ExtractedStrings.MY_COUNTRY= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(3,getApplicationContext());
        }
        if ( ExtractedStrings.MY_STATUS.equals("")){
            ExtractedStrings.MY_STATUS= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(2,getApplicationContext());
        }
        if (ExtractedStrings.MY_PROFILE_PICTURE_PATH.equals("")){
            ExtractedStrings.MY_PROFILE_PICTURE_PATH= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(5,getApplicationContext());
        }
        if (ExtractedStrings.mProfileImage==null){
            new ConvertingImageTask().execute();
        }
        mSharedRecyclerView=(RecyclerView)findViewById(R.id.share_data_recycler_view);
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Mystatus");
        arrayList.add("RecentChat");
        arrayList.add("MoreContacts");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        adapter = new shareDataAdapter(getApplicationContext(),arrayList);
        mSharedRecyclerView.setLayoutManager(linearLayoutManager);
        mSharedRecyclerView.setAdapter(adapter);
    }
    private class ConvertingImageTask extends AsyncTask<String,Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                try {
                    String MyProfileImageBase= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(4,getApplicationContext());
                    if (MyProfileImageBase.equals("default")) {
                        ExtractedStrings.mProfileImage = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_default);
                    } else {
                        byte[] imageBytes = Base64.decode(MyProfileImageBase, Base64.DEFAULT);
                        ExtractedStrings.mProfileImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    }
                } catch (Exception e) {
                }
            }finally {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Toast.makeText(getApplicationContext(), "Error in loading profile picture: "+s, Toast.LENGTH_SHORT).show();
            }else {
                if (ExtractedStrings.mProfileImage!=null){
                    Bitmap bitmap= ExtractedStrings.mProfileImage;
                    //mCircleImageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
class shareDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<String> arrayList;
    Context context;
    private RecentChatRoom recentChatRoom;

    public shareDataAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList=arrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case MainShareDataActivity.SHARE_MY_STATUS_ITEM_TYPE:{
                View view=LayoutInflater.from(context).inflate(R.layout.row_share_data_connected_friends_my_status,parent,false);
                return new shareMyStatus(view);
            }
            case MainShareDataActivity.SHARE_RECENT_CONTACTS_ITEM_TYPE:{
                View viewRecent=LayoutInflater.from(context).inflate(R.layout.row_share_data_connected_recent_chat_recycler,parent,false);
                return new shareRecentContactList(viewRecent);
            }
            default:{
                View viewmore=LayoutInflater.from(context).inflate(R.layout.row_share_data_connected_more_contacts_recycler,parent,false);
                return new shareMoreContactsList(viewmore);
            }
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
       if (holder instanceof shareMyStatus) {
            Toast.makeText(context, "status started", Toast.LENGTH_SHORT).show();
            ((shareMyStatus) holder).mRippleBackgourImageMyStatus.startRippleAnimation();
            if (ExtractedStrings.mProfileImage!=null) {
                ((shareMyStatus) holder).mImageViewMyStatus.setImageBitmap(ExtractedStrings.mProfileImage);
            }
        }else if (holder instanceof  shareRecentContactList){
             LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false);
            recentChatRoom = RecentChatsDB.getInstance(context).getListRecentChats(context);
            RecentContactAdapter adpter = new RecentContactAdapter(context, recentChatRoom);
            ((shareRecentContactList) holder).mRecentChatList.setLayoutManager(linearLayoutManager);
            ((shareRecentContactList) holder).mRecentChatList.setAdapter(adpter);
        }
        else if (holder instanceof shareMoreContactsList){
           ArrayList<Friend> MoreContactsList=FriendDB.getInstance(context).getListFriendArray();
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false);
            MoreContactAdapter adapter = new MoreContactAdapter(context,MoreContactsList,recentChatRoom );
            ((shareMoreContactsList) holder).mMoreContactList.setLayoutManager(linearLayoutManager);
            ((shareMoreContactsList) holder).mMoreContactList.setAdapter(adapter);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return MainShareDataActivity.SHARE_MY_STATUS_ITEM_TYPE;
        }else if (position==1){
            return MainShareDataActivity.SHARE_RECENT_CONTACTS_ITEM_TYPE;
        }else {
            return MainShareDataActivity.SHARE_MORE_CONTACTS_ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}

class RecentContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    RecentChatRoom listChatsHistory;
    private Bitmap src;

    public RecentContactAdapter(Context context, RecentChatRoom listChatsHistory) {
        this.context = context;
        this.listChatsHistory = listChatsHistory;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_share_data_connected_friends,parent,false);
        return new shareFriendContactItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            final String imageDb=listChatsHistory.getListRecentChatDataData().get(position).small_profile_image;
            final String name = listChatsHistory.getListRecentChatDataData().get(position).name;
            final String idSender = listChatsHistory.getListRecentChatDataData().get(position).id;
            final String status = FriendDB.getInstance(context).getInfoByIdUser(2,idSender,context);
            if (imageDb.equals("default")) {
                src = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default);
            } else {
                byte[] imageBytes = Base64.decode(imageDb, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
            BitmapDrawable Image_drawable = new BitmapDrawable(context.getResources(), src);
            ((shareFriendContactItem) holder).mImageView.setImageDrawable(Image_drawable);
            ((shareFriendContactItem) holder).mTextUserName.setText(name);
            ((shareFriendContactItem) holder).mTextStatus.setText(status);
            ((shareFriendContactItem) holder).mRippleBackgourImage.startRippleAnimation();
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return listChatsHistory.getListRecentChatDataData().size()>3?3:listChatsHistory.getListRecentChatDataData().size();
    }
}
class MoreContactAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    RecentChatRoom recentChat;
    Context context;
    ArrayList<Friend> listFriend;
    private Bitmap src;

    public MoreContactAdapter(Context context, ArrayList<Friend> listFriend,RecentChatRoom recentChatRoom) {
        this.context = context;
        this.listFriend = listFriend;
        this.recentChat=recentChatRoom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_share_data_connected_friends,parent,false);
        return new shareFriendContactItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String name = listFriend.get(position).name;
        final String id = listFriend.get(position).id;
        final String status=listFriend.get(position).status;
        final String avata = listFriend.get(position).image;
       /* for (ResentChats recentChatRoom:recentChat.getListRecentChatDataData()){
            if (id.equals(recentChatRoom.id)){
                ((shareFriendContactItem) holder).itemView.setVisibility(View.GONE);
            }
        }*/
        try {
            if (avata.equals("default")) {
                src = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default);
            } else {
                byte[] imageBytes = Base64.decode(avata, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
            BitmapDrawable Image_drawable = new BitmapDrawable(context.getResources(), src);
            ((shareFriendContactItem) holder).mImageView.setImageDrawable(Image_drawable);
            ((shareFriendContactItem) holder).mTextUserName.setText(name);
            ((shareFriendContactItem) holder).mTextStatus.setText(status);
            ((shareFriendContactItem) holder).mRippleBackgourImage.startRippleAnimation();
        }catch (Exception e){
        }
    }
    @Override
    public int getItemCount() {
        return listFriend.size();
    }
}
class shareMoreContactsList extends RecyclerView.ViewHolder{
    public RecyclerView mMoreContactList;
    public shareMoreContactsList(View itemView) {
        super(itemView);
        mMoreContactList=(RecyclerView)itemView.findViewById(R.id.share_Recycler_view_more_connected_contacts);
    }
}
class shareRecentContactList extends RecyclerView.ViewHolder{
    public RecyclerView mRecentChatList;
    public shareRecentContactList(View itemView) {
        super(itemView);
        mRecentChatList=(RecyclerView)itemView.findViewById(R.id.share_Recycler_view_recent_chat);
    }
}
class shareMyStatus extends RecyclerView.ViewHolder{
    public ImageView mOpenStatusSetting;
    public RippleBackground mRippleBackgourImageMyStatus;
    public CircleImageView mImageViewMyStatus;
    public Button mButtonSendMyStatus;

    public shareMyStatus(View itemView) {
        super(itemView);
        mRippleBackgourImageMyStatus=(RippleBackground)itemView.findViewById(R.id.shared_content_background_on_profile_picture_my_status);
        mImageViewMyStatus=(CircleImageView)itemView.findViewById(R.id.shared_image_view_profile_picture_my_status);
        mButtonSendMyStatus=(Button)itemView.findViewById(R.id.send_shared_data_button_my_status);
        mOpenStatusSetting=(ImageView)itemView.findViewById(R.id.shared_open_status_setting);
    }
}
class shareFriendContactItem extends RecyclerView.ViewHolder{
    public TextView mTextUserName;
    public EmojiconTextView mTextStatus;
    public CircleImageView mImageView;
    public Button mButtonSend;
    public RippleBackground mRippleBackgourImage;

    public shareFriendContactItem(View itemView) {
        super(itemView);
        mRippleBackgourImage=(RippleBackground)itemView.findViewById(R.id.shared_content_background_on_profile_picture);
        mImageView=(CircleImageView)itemView.findViewById(R.id.shared_image_view_profile_picture);
        mButtonSend=(Button)itemView.findViewById(R.id.send_shared_data_button);
        mTextUserName=(TextView)itemView.findViewById(R.id.shared_text_view_username);
        mTextStatus=(EmojiconTextView)itemView.findViewById(R.id.shared_text_view_status);
    }
}