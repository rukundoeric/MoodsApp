package com.moodsapp.prestein.moodsapp.ui.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.GroupDB;
import com.moodsapp.prestein.moodsapp.data.Database.RecentChatsDB;
import com.moodsapp.prestein.moodsapp.data.Database.RecentNotificationDb;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.model.Group;
import com.moodsapp.prestein.moodsapp.model.OnlineUsersStatus;
import com.moodsapp.prestein.moodsapp.model.RecentChatRoom;
import com.moodsapp.prestein.moodsapp.model.ResentChats;
import com.moodsapp.prestein.moodsapp.service.BackgroundProcess.ContactWatchService;
import com.moodsapp.prestein.moodsapp.service.NotificationMessage.SaveNewMessage;
import com.moodsapp.prestein.moodsapp.service.OnineAndOfflineUsersStatus.UsersStatus;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui.PhotoGalleryActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home.CustomProfilePictureView;
import com.moodsapp.prestein.moodsapp.util.ColorUtils.ColorCreation;

import com.moodsapp.prestein.moodsapp.util.InterfaceUtils.SoltByTimeRecentChats;
import com.skyfishjy.library.RippleBackground;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import freemarker.template.utility.NullArgumentException;

public class FragmentHome extends android.app.Fragment{
    private View home;
    private FragmentActivity myContext;
    private RecyclerView mAllChatsHistory;
    private Point ptSize=new Point();
    public static Resources res;

    public static ListChatHistoryAdapter FriendWithMoodsAppAdapter;

    private FloatingActionMenu mHomeFloatingMenuButton;
    private FloatingActionButton mSelfieAlenaMenu;
    private FloatingActionButton mMusicMenu;
    private FloatingActionButton mBusinessMenu;
    private FloatingActionButton mMarketMenu;

    private static final long  TIME_TO_DETECT_ONLINE= 20*1000;
    private RecentChatRoom recentChatList;
    private RecyclerView mList;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        home = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        mAllChatsHistory=(RecyclerView)home.findViewById(R.id.Chats_history_in_home_adapter);
         mList=(RecyclerView) home.findViewById(R.id.row_connected_contacts_in_home_recycler_view);
        floating_menu(home);
        res=getResources();
        int columnsCount = 5;
       // topItemWidth = (MoodsApp.displaySize.x - ((columnsCount + 1) * MoodsApp.dp(3))) / columnsCount;
            updateAdapterRecentChat();
            LocalBroadcastManager.getInstance(myContext).registerReceiver(
                    new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            updateAdapterRecentChat();
                        }
                    }, new IntentFilter(SaveNewMessage.ACTION_NEW_MESSAGE_RECEIVED)
            );
       // detectOnlineFriend();
        return home;
    }
    private void detectOnlineFriend(){
        Handler mHandler=new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(System.currentTimeMillis(), TIME_TO_DETECT_ONLINE) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        ArrayList<Friend> friendList= FriendDB.getInstance(myContext).getListFriendArray();
                        for (Friend friend:friendList){
                            final String id=friend.id;
                            FirebaseDatabase.getInstance().getReference().child("Users/Profiles/"+id+"/onlineStatus").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        HashMap mapStatus = (HashMap) dataSnapshot.getValue();
                                        ExtractedStrings.OnlineUserStatusList=new ArrayList<>();
                                        boolean isOnline=(boolean) mapStatus.get("isOnline");
                                        if (isOnline){
                                            boolean isAlreadExist=false;
                                            OnlineUsersStatus onlineUsersStatus=new OnlineUsersStatus();
                                            onlineUsersStatus.UserId=id;
                                            onlineUsersStatus.isOnline=true;
                                            onlineUsersStatus.lastSeen=(long) mapStatus.get("lastSeen");
                                            for (int i=0;i<ExtractedStrings.OnlineUserStatusList.size();i++){
                                                if (ExtractedStrings.OnlineUserStatusList.get(i).UserId.equals(id)){
                                                    ExtractedStrings.OnlineUserStatusList.set(i,onlineUsersStatus);
                                                    isAlreadExist=true;
                                                }
                                            }
                                            if (!isAlreadExist){
                                                ExtractedStrings.OnlineUserStatusList.add(onlineUsersStatus);
                                            }
                                        }else{
                                            boolean isAlreadExist=false;
                                            OnlineUsersStatus onlineUsersStatus=new OnlineUsersStatus();
                                            onlineUsersStatus.UserId=id;
                                            onlineUsersStatus.isOnline=false;
                                            onlineUsersStatus.lastSeen=(long) mapStatus.get("lastSeen");
                                            for (int i=0;i<ExtractedStrings.OnlineUserStatusList.size();i++){
                                                if (ExtractedStrings.OnlineUserStatusList.get(i).UserId.equals(id)){
                                                    ExtractedStrings.OnlineUserStatusList.set(i,onlineUsersStatus);
                                                    isAlreadExist=true;
                                                }
                                            }
                                            if (!isAlreadExist){
                                                ExtractedStrings.OnlineUserStatusList.add(onlineUsersStatus);
                                            }
                                        }
                                    }else {
                                        HashMap mapStatus = (HashMap) dataSnapshot.getValue();
                                        boolean isAlreadExist=false;
                                        OnlineUsersStatus onlineUsersStatus=new OnlineUsersStatus();
                                        onlineUsersStatus.UserId=id;
                                        onlineUsersStatus.isOnline=false;
                                        ExtractedStrings.OnlineUserStatusList=new ArrayList<>();
                                        //onlineUsersStatus.lastSeen=0;
                                        for (int i=0;i<ExtractedStrings.OnlineUserStatusList.size();i++){
                                            if (ExtractedStrings.OnlineUserStatusList.get(i).UserId.equals(id)){
                                                ExtractedStrings.OnlineUserStatusList.set(i,onlineUsersStatus);
                                                isAlreadExist=true;
                                            }
                                        }
                                        if (!isAlreadExist){
                                            ExtractedStrings.OnlineUserStatusList.add(onlineUsersStatus);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        updateAdapterRecentChat();
                    }
                    @Override
                    public void onFinish() {

                    }
                }.start();

            }
        });
    }
    private void floating_menu(View view)
    {
        mHomeFloatingMenuButton=(FloatingActionMenu)view.findViewById(R.id.homo_fab_menu_servises);
        mSelfieAlenaMenu = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.home_fab_menu_selfie_alena) ;
        mMusicMenu = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.home_fab_menu_music) ;
        mBusinessMenu = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.home_fab_menu_business) ;
        mMarketMenu = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.home_fab_menu_shop) ;

        //modifier Floating menu
        mHomeFloatingMenuButton.setMenuButtonColorNormal(getResources().getColor(R.color.home_menu_fab_calor_normal));
        mHomeFloatingMenuButton.setMenuButtonColorPressed(getResources().getColor(R.color.home_menu_fab_calor_pressed));

        //modifier menus
        mSelfieAlenaMenu.setColorNormal(getResources().getColor(R.color.home_menu_fab_calor_normal));
        mSelfieAlenaMenu.setColorPressed(getResources().getColor(R.color.home_menu_fab_calor_pressed));
        mMusicMenu.setColorNormal(getResources().getColor(R.color.home_menu_fab_calor_normal));
        mMusicMenu.setColorPressed(getResources().getColor(R.color.home_menu_fab_calor_pressed));
        mBusinessMenu.setColorNormal(getResources().getColor(R.color.home_menu_fab_calor_normal));
        mBusinessMenu.setColorPressed(getResources().getColor(R.color.home_menu_fab_calor_pressed));
        mMarketMenu.setColorNormal(getResources().getColor(R.color.home_menu_fab_calor_normal));
        mMarketMenu.setColorPressed(getResources().getColor(R.color.home_menu_fab_calor_pressed));
        //Listeners


        mSelfieAlenaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(myContext, PhotoGalleryActivity.class));

            }
        });
        mMusicMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                  RecentNotificationDb.getInstance(myContext).dropDB(myContext);
                }catch (Exception e){
                    Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
  /*  public  ArrayList<Integer> colors(){
        ArrayList<Integer> arrayList=new ArrayList<>();
        int a=0;
        for(int i=0;i<FriendDB.getInstance(myContext).getListFriendArray().size();i++){
            if (a == 6) {
                a=0;
            }
            int color=new ColorCreation().getColorList(FragmentHome.this.getActivity()).get(a);
            arrayList.add(color);
            a++;
        }
        return arrayList;
    }*/
    private ArrayList<Friend> getFriendList(){
        ArrayList<Friend> friends=new ArrayList<>();
        ArrayList<Friend> listFriend = FriendDB.getInstance(myContext).getListFriendArray();
        Friend friend=new Friend();
        friend.id = ExtractedStrings.UID;
        friend.name = ExtractedStrings.NAME;
        friend.status = ExtractedStrings.MY_STATUS;
        friends.add(friend);
        for (Friend friend1:listFriend){
            Friend friend2 = new Friend();
            friend2.id = friend1.id;
            friend2.name = friend1.name;
            friend2.status = friend1.status;
            friend2.image = friend1.image;
            friends.add(friend2);
        }
        return  friends;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void updateAdapterRecentChat(){
        recentChatList=RecentChatsDB.getInstance(myContext).getListRecentChats(myContext);
        //for user status
        ArrayList<Friend> listFriend=getFriendList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(myContext,LinearLayoutManager.HORIZONTAL,false);
        ConnectedContactsAdapter adapter = new ConnectedContactsAdapter(myContext, listFriend,FragmentHome.this);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(adapter);
        mList.scrollToPosition(0);
        //for chat history
        LinearLayoutManager chat_history_layout = new LinearLayoutManager(myContext,LinearLayoutManager.VERTICAL,false);
        chat_history_layout.setOrientation(LinearLayoutManager.VERTICAL);
        mAllChatsHistory.setLayoutManager(chat_history_layout);
        SoltByTimeRecentChats soltByTimeRecentChats =new SoltByTimeRecentChats(myContext);
        Collections.sort(recentChatList.getListRecentChatDataData(), soltByTimeRecentChats);
        FriendWithMoodsAppAdapter = new ListChatHistoryAdapter(myContext,recentChatList, this);
        layoutManager.setAutoMeasureEnabled(true);
        mAllChatsHistory.setHasFixedSize(true);
        mAllChatsHistory.setAdapter(FriendWithMoodsAppAdapter);
        mAllChatsHistory.scrollToPosition(0);
        if (recentChatList.getListRecentChatDataData().size()>0){
            ExtractedStrings.UpdateOnlineStatus = new CountDownTimer(System.currentTimeMillis(), ExtractedStrings.TIME_TO_REFRESH) {
                @Override
                public void onTick(long l) {
                    UsersStatus.updateFriendStatus(myContext, recentChatList);
                    UsersStatus.updateUserStatus(myContext);
                }

                @Override
                public void onFinish() {

                }
            };
           ExtractedStrings.UpdateOnlineStatus.start();
        }
    }
    private boolean isThisUserOnline(String id){
        if (ExtractedStrings.OnlineUserStatusList!=null){
            for (OnlineUsersStatus onlineUsersStatus:ExtractedStrings.OnlineUserStatusList){
                if (onlineUsersStatus.UserId.equals(id)){
                    if (onlineUsersStatus.isOnline){
                       return true;
                    }else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
    class ListChatHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        //private ListFriend listFriend;
        RecentChatRoom listChatsHistory;
        //  private List<ResentChats> listChatsHistory;
        private Context context;
        public  Map<String, Query> mapQuery;
        public  Map<String, DatabaseReference> mapQueryOnline;
        public  Map<String, ChildEventListener> mapChildListener;
        public  Map<String, ChildEventListener> mapChildListenerOnline;
        public  Map<String, Boolean> mapMark;
        private FragmentHome fragment;
        public  ViewGroup.LayoutParams params;
        private Bitmap src;

        public ListChatHistoryAdapter(Context context, RecentChatRoom listChatsHistory, FragmentHome fragment) {
            this.listChatsHistory = listChatsHistory;
            this.context = context;
            mapQuery = new HashMap<>();
            mapChildListener = new HashMap<>();
            mapMark = new HashMap<>();
            mapChildListenerOnline = new HashMap<>();
            mapQueryOnline = new HashMap<>();
            this.fragment = fragment;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_history, parent, false);
            return new ItemFriendViewHolder(context, view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
                //ResentChats resentChats=listChatsHistory.get(position);
                if (position==listChatsHistory.getListRecentChatDataData().size()){
                    ((ItemFriendViewHolder) holder).mLayoutIventContainer.setVisibility(View.VISIBLE);
                }else {
                    ((ItemFriendViewHolder) holder).mLayoutIventContainer.setVisibility(View.GONE);
                }
                final String name = listChatsHistory.getListRecentChatDataData().get(position).name;
                final String id = listChatsHistory.getListRecentChatDataData().get(position).id;
                /*final String avata = listChatsHistory.getListRecentChatDataData().get(position).small_profile_image;*/
                final String status = listChatsHistory.getListRecentChatDataData().get(position).status;
                final String positio = String.valueOf(position);
                final String msgType=AllChatsDB.getInstance(context).getInfoByMsgId(1,listChatsHistory.getListRecentChatDataData().get(position).lastMessage,context);
                final String lastmessagess =msgType.equals(ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE) ? AllChatsDB.getInstance(context).getInfoByMsgId(5,listChatsHistory.getListRecentChatDataData().get(position).lastMessage,context):
                        msgType.equals(ExtractedStrings.ITEM_MESSAGE_CONTACT_TYPE ) ? ExtractedStrings.RECENT_CHAT_MESSAGE_CONTACT :
                                msgType.equals(ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE) ? ExtractedStrings.RECENT_CHAT_MESSAGE_PHOTO :
                                        msgType.equals(ExtractedStrings.ITEM_MESSAGE_VOICE_TYPE) ? ExtractedStrings.RECENT_CHAT_MESSAGE_VOICE:
                                                msgType.equals(ExtractedStrings.ITEM_MESSAGE_DOCUMENT_TYPE) ? ExtractedStrings.RECENT_CHAT_MESSAGE_DOCUMENT:
                                                        msgType.equals(ExtractedStrings.ITEM_MESSAGE_APK_TYPE) ? ExtractedStrings.RECENT_CHAT_MESSAGE_APK:
                                                                ExtractedStrings.RECENT_CHAT_MESSAGE_VIDEO;
                final String messageStatus=AllChatsDB.getInstance(context).getInfoByMsgId(16,listChatsHistory.getListRecentChatDataData().get(position).lastMessage,context);
                final String idSender = listChatsHistory.getListRecentChatDataData().get(position).idSender;
                final String unReadMessage = listChatsHistory.getListRecentChatDataData().get(position).unReadMessage;
                final String imageDb=listChatsHistory.getListRecentChatDataData().get(position).small_profile_image;
                final String imagePath=String.valueOf(new File(context.getFilesDir(),id+Data_Storage_Path.jpgFileFormat));
                ((ItemFriendViewHolder) holder).txtMessage.setText(lastmessagess);
                ((ItemFriendViewHolder) holder).txtName.setText(name);
                if (ContactWatchService.profileImageStore==null){
                    ContactWatchService.profileImageStore=new HashMap<>();
                }
                if (ContactWatchService.profileImageStore.get(id)!=null){
                    ((ItemFriendViewHolder) holder).avata.setImageBitmap(ContactWatchService.profileImageStore.get(id));
                    ((ItemFriendViewHolder) holder).mLastMessageEvent.setImageBitmap(ContactWatchService.profileImageStore.get(id));
                }else {
                    if (!Data_Storage_Path.isThisPrivateFileExist(context,id,Data_Storage_Path.jpgFileFormat)){
                        Glide.with(context).load(imageDb).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Bitmap bitmap=((BitmapDrawable) resource).getBitmap();
                                ContactWatchService.profileImageStore.put(id,bitmap);
                                return false;
                            }
                        }).into(((ItemFriendViewHolder) holder).avata);
                        Glide.with(context).load(imageDb).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(((ItemFriendViewHolder) holder).mLastMessageEvent);
                    }else {
                        Glide.with(context).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Bitmap bitmap=((BitmapDrawable) resource).getBitmap();
                                ContactWatchService.profileImageStore.put(id,bitmap);
                                return false;
                            }
                        }).into(((ItemFriendViewHolder) holder).avata);
                        Glide.with(context).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(((ItemFriendViewHolder) holder).mLastMessageEvent);
                    }
                }
                if (ExtractedStrings.OnlineUserStatusList!=null){
                    for (OnlineUsersStatus onlineUsersStatus:ExtractedStrings.OnlineUserStatusList){
                        if (onlineUsersStatus.UserId.equals(id)){
                            if (onlineUsersStatus.isOnline){
                                //User is online
                            }
                        }
                    }
                }
                int no=RecentNotificationDb.getInstance(context).getUnReadMessageCount(context,id);
                if (no>0){
                    if (idSender.equals(ExtractedStrings.UID)){
                        ((ItemFriendViewHolder) holder).mImageMesssageStatus.setVisibility(View.VISIBLE);
                        ((ItemFriendViewHolder) holder).mLastMessageEvent.setVisibility(View.GONE);
                        ((ItemFriendViewHolder) holder).mUnReadMessageCounter.setVisibility(View.GONE);
                    }else {
                        ((ItemFriendViewHolder) holder).txtMessage.setTypeface(Typeface.DEFAULT_BOLD);
                        ((ItemFriendViewHolder) holder).txtName.setTypeface(Typeface.DEFAULT_BOLD);
                        ((ItemFriendViewHolder) holder).mUnReadMessageCounter.setVisibility(View.VISIBLE);
                        ((ItemFriendViewHolder) holder).mUnReadMessageCounter.setText(String.valueOf(no));
                        ((ItemFriendViewHolder) holder).mLastMessageEvent.setVisibility(View.GONE);
                        ((ItemFriendViewHolder) holder).mImageMesssageStatus.setVisibility(View.GONE);
                    }
                }else {
                    if (idSender.equals(ExtractedStrings.UID)){
                        ((ItemFriendViewHolder) holder).txtMessage.setTypeface(Typeface.DEFAULT);
                        ((ItemFriendViewHolder) holder).txtName.setTypeface(Typeface.DEFAULT);
                        ((ItemFriendViewHolder) holder).mImageMesssageStatus.setVisibility(View.VISIBLE);
                        ((ItemFriendViewHolder) holder).mLastMessageEvent.setVisibility(View.GONE);
                        ((ItemFriendViewHolder) holder).mUnReadMessageCounter.setVisibility(View.GONE);
                    }else {
                        ((ItemFriendViewHolder) holder).txtMessage.setTypeface(Typeface.DEFAULT);
                        ((ItemFriendViewHolder) holder).txtName.setTypeface(Typeface.DEFAULT);
                        ((ItemFriendViewHolder) holder).mUnReadMessageCounter.setVisibility(View.GONE);
                        ((ItemFriendViewHolder) holder).mImageMesssageStatus.setVisibility(View.GONE);
                        ((ItemFriendViewHolder) holder).mLastMessageEvent.setVisibility(View.VISIBLE);
                    }
                }

                ((ItemFriendViewHolder) holder).avata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomProfilePictureView customProfilePictureView=new CustomProfilePictureView(fragment.getActivity(),imageDb,id);
                        customProfilePictureView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        customProfilePictureView.setCancelable(true);
                        customProfilePictureView.show();
                    }
                });
                ((ItemFriendViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((ItemFriendViewHolder) holder).itemView.setBackgroundResource(R.drawable.abc_list_pressed_holo_light);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((ItemFriendViewHolder) holder).itemView.setBackgroundResource(R.drawable.transparent);
                            }
                        },40);
                        try {
                            if (id.startsWith("GROUP")){
                                Intent intent = new Intent(context, Chat_Activity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, name);
                                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, status);
                                bundle.putString(ExtractedStrings.INTENT_FRIENDS_POSITION, positio);
                                ArrayList<CharSequence> idFriend = getGroupMembersIds(id);
                                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, id);
                                intent.putExtras(bundle);
                                Chat_Activity.bitmapAvataFriend = new HashMap<>();
                                if (ContactWatchService.profileImageStore==null){
                                    ContactWatchService.profileImageStore=new HashMap<>();
                                }
                                if (ContactWatchService.profileImageStore.get(id)==null){
                                    if (Data_Storage_Path.isThisPrivateFileExist(context,id,Data_Storage_Path.jpgFileFormat)){
                                        File file=new File(context.getFilesDir(),id+Data_Storage_Path.jpgFileFormat);
                                        Bitmap bitmap=BitmapFactory.decodeFile(String.valueOf(file));
                                        Chat_Activity.bitmapAvataFriend.put(id, bitmap);
                                    }else{
                                        Chat_Activity.bitmapAvataFriend.put(id, null);
                                    }

                                }else{
                                    Chat_Activity.bitmapAvataFriend.put(id, ContactWatchService.profileImageStore.get(id));
                                }
                                mapMark.put(id, null);
                                context.startActivity(intent);
                                fragment.getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                            }else {
                                Intent intent = new Intent(context, Chat_Activity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, name);
                                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, status);
                                bundle.putString(ExtractedStrings.INTENT_FRIENDS_POSITION, positio);
                                ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                                idFriend.add(id);
                                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, id);
                                intent.putExtras(bundle);
                                Chat_Activity.bitmapAvataFriend = new HashMap<>();
                                if (ContactWatchService.profileImageStore==null){
                                    ContactWatchService.profileImageStore=new HashMap<>();
                                }
                                if (ContactWatchService.profileImageStore.get(id)==null){
                                    if (Data_Storage_Path.isThisPrivateFileExist(context,id,Data_Storage_Path.jpgFileFormat)){
                                        File file=new File(context.getFilesDir(),id+Data_Storage_Path.jpgFileFormat);
                                        Bitmap bitmap=BitmapFactory.decodeFile(String.valueOf(file));
                                        Chat_Activity.bitmapAvataFriend.put(id, bitmap);
                                    }else{
                                        Chat_Activity.bitmapAvataFriend.put(id, null);
                                    }

                                }else{
                                    Chat_Activity.bitmapAvataFriend.put(id, ContactWatchService.profileImageStore.get(id));
                                }
                                mapMark.put(id, null);
                                context.startActivity(intent);
                                fragment.getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                            }

                        }catch (Exception e){
                            Toast.makeText(context, "In Home Onclick "+"\n"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                String time = new SimpleDateFormat("EEE, d MMM yyyy").format(new Date(listChatsHistory.getListRecentChatDataData().get(position).timeStamps));
                String today = new SimpleDateFormat("EEE, d MMM yyyy").format(new Date(System.currentTimeMillis()));
                SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
                String timeRecent = myFormat.format(new Date(listChatsHistory.getListRecentChatDataData().get(position).timeStamps));
                String thisday = myFormat.format(new Date(System.currentTimeMillis()));

                if (today.equals(time)) {
                    ((ItemFriendViewHolder) holder).txtTime.setText(new SimpleDateFormat("HH:mm").format(new Date(listChatsHistory.getListRecentChatDataData().get(position).timeStamps)));
                } else {
                    try {
                        Date datetimeRecent = myFormat.parse(timeRecent);
                        Date datethisday = myFormat.parse(thisday);
                        long diff = datethisday.getTime() - datetimeRecent.getTime();
                        long dife = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                        int different = Integer.parseInt(String.valueOf(dife));

                        if (different == 1) {
                            ((ItemFriendViewHolder) holder).txtTime.setText("YESTERDAY");
                        } else if (different > 1 && different <= 7) {
                            ((ItemFriendViewHolder) holder).txtTime.setText(new SimpleDateFormat("dd MMMM").format(new Date(listChatsHistory.getListRecentChatDataData().get(position).timeStamps)));
                        } else {
                            ((ItemFriendViewHolder) holder).txtTime.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date(listChatsHistory.getListRecentChatDataData().get(position).timeStamps)));
                        }
                    } catch (Exception e) {
                    }
                }
                if (idSender.equals(ExtractedStrings.UID)){
                    if (messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_RECEIVED)){
                        ((ItemFriendViewHolder) holder).mImageMesssageStatus.setImageResource(R.drawable.bpg_message_received_by_user);
                    }else if (messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SENT)){
                        ((ItemFriendViewHolder) holder).mImageMesssageStatus.setImageResource(R.drawable.bpg_message_saved_to_server);
                    }else if (messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SAVED)){
                        ((ItemFriendViewHolder) holder).mImageMesssageStatus.setImageResource(R.drawable.bpg_message_saved_to_storage);
                    }
                }
                if (isThisUserOnline(id)){
                    ((ItemFriendViewHolder) holder).mOnlineStatusIcon.setVisibility(View.VISIBLE);
                   // ((ItemFriendViewHolder) holder).mOnlineStatusBackPhoto.startRippleAnimation();
                }else {
                    ((ItemFriendViewHolder) holder).mOnlineStatusIcon.setVisibility(View.GONE);
                   // ((ItemFriendViewHolder) holder).mOnlineStatusBackPhoto.stopRippleAnimation();
                }

        }

        private ArrayList<CharSequence> getGroupMembersIds(String id) {
            ArrayList<CharSequence> list=new ArrayList<>();
            String members="";
            for (Group memberIfo: GroupDB.getInstance(context,id).getListGroup(id)){
                members=members+", "+memberIfo.UserName;
                list.add(memberIfo.UserId);
            }
            return list;
        }
        @Override
        public int getItemCount() {
            return listChatsHistory.getListRecentChatDataData().size();
        }
    }
    class ConnectedContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        Context context;
        public ArrayList<Friend> listFriend;
        private ArrayList<Integer> colors;
        private FragmentHome fragmentHome;
        private Bitmap src;

        public ConnectedContactsAdapter(Context context, ArrayList<Friend> listFriend, FragmentHome fragmentHome) {
            this.context = context;
            this.listFriend = listFriend;
            this.colors = colors;
            this.fragmentHome = fragmentHome;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(context).inflate(R.layout.row_connected_contacts_in_home_item,parent, false);
            return new ItemContactsViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


           // ((ItemContactsViewHolder) holder).itemView.setLayoutParams(params);
            if (listFriend.get(position).id.equals(ExtractedStrings.UID)){
                ((ItemContactsViewHolder) holder).mImageProfile.setImageResource(R.drawable.my_status_add);
                ((ItemContactsViewHolder) holder).mImagesStatus.setImageBitmap(ExtractedStrings.mProfileImage);
                ((ItemContactsViewHolder) holder).mImagesStatus.setBorderColor(fragmentHome.getResources().getColor(R.color.download_progress_color));
                ((ItemContactsViewHolder) holder).mTextViewName.setText("");
                ((ItemContactsViewHolder) holder).mTextViewName.setTextColor(fragmentHome.getResources().getColor(R.color.download_progress_color));
               /* ((ItemContactsViewHolder) holder).mRippleOnlineStatus.startRippleAnimation();*/
            }
            else {
                final String id=listFriend.get(position).id;
                final String avata = listFriend.get(position).image;
                final String name=getFirstName(listFriend.get(position).name);
                final String imagePath=String.valueOf(new File(context.getFilesDir(),id+Data_Storage_Path.jpgFileFormat));
                Handler mHandler=new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                            if (ContactWatchService.profileImageStore==null){
                                ContactWatchService.profileImageStore=new HashMap<>();
                            }
                            if (ContactWatchService.profileImageStore.get(id)!=null){
                                ((ItemContactsViewHolder) holder).mImageProfile.setImageBitmap(ContactWatchService.profileImageStore.get(id));
                                ((ItemContactsViewHolder) holder).mImagesStatus.setImageBitmap(ContactWatchService.profileImageStore.get(id));
                            }else {
                                if (!Data_Storage_Path.isThisPrivateFileExist(context,id,Data_Storage_Path.jpgFileFormat)){
                                    Glide.with(context).load(avata).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(((ItemContactsViewHolder) holder).mImageProfile);
                                    Glide.with(context).load(avata).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(((ItemContactsViewHolder) holder).mImagesStatus);
                                }else {
                                    Glide.with(context).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(((ItemContactsViewHolder) holder).mImageProfile);
                                    Glide.with(context).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(((ItemContactsViewHolder) holder).mImagesStatus);
                                }
                            }

                            ((ItemContactsViewHolder) holder).mImageProfile.setBorderWidth(4);
                            // ((ItemContactsViewHolder) holder).mImageProfile.setBorderColor(colors.get(position));
                            ((ItemContactsViewHolder) holder).mTextViewName.setText(name);
                            if (isThisUserOnline(id)){
                                            ((ItemContactsViewHolder) holder).mTextOnlineStatus.setVisibility(View.VISIBLE);
                                            ((ItemContactsViewHolder) holder).mRippleOnlineStatus.startRippleAnimation();
                                        }else {
                                            ((ItemContactsViewHolder) holder).mTextOnlineStatus.setVisibility(View.GONE);
                                         //   ((ItemContactsViewHolder) holder).mRippleOnlineStatus.stopRippleAnimation();
                                        }
                    }
                });
            }
        }

        private String getFirstName(String name) {
            String finalName="";
            CharSequence n=name;
            for (int i=0;i<n.length();i++){
                if (n.charAt(i)==' '){
                    return finalName;
                }else {
                    finalName+=n.charAt(i);
                }
            }

            return finalName;
        }

        @Override
        public int getItemCount() {
            return listFriend.size();
        }
    }
    class ItemFriendViewHolder extends RecyclerView.ViewHolder{
        public EmojiconTextView mOnlineStatusIcon;
        public ImageView mImageMesssageStatus;
        public RippleBackground mOnlineStatusBackPhoto;
        public TextView mUnReadMessageCounter;
        public LinearLayout mLayoutIventContainer;
        public RecyclerView mRecyclerViewIvent;
        public LinearLayout mlayout_mesage_status;
        public TextView mTextUnreadMesagecount;
        public CircleImageView mLastMessageEvent;
        public View mView;
        public LinearLayout mLayoutRecentChat;
        public CircleImageView avata;
        public TextView txtTime;
        public TextView txtName;
        public EmojiconTextView txtMessage;
        private Context context;
        ItemFriendViewHolder(Context context, View itemView) {
            super(itemView);
            mView=itemView;
            avata = (CircleImageView) itemView.findViewById(R.id.image_view_chat_history_dispay);
            txtName = (TextView) itemView.findViewById(R.id.text_view_chat_history_username);
            txtTime = (TextView) itemView.findViewById(R.id.last_message_time);
            txtMessage = (EmojiconTextView) itemView.findViewById(R.id.text_view_chat_history_last_message);
            this.context = context;
            mUnReadMessageCounter = (TextView) itemView.findViewById(R.id.unread_count_messages);
            mImageMesssageStatus=(ImageView)itemView.findViewById(R.id.message_status_for_recentChat);
            mLayoutRecentChat=(LinearLayout)itemView.findViewById(R.id.layout_recent_chat_container);
            mTextUnreadMesagecount=(TextView)itemView.findViewById(R.id.unread_count_messages);
            mlayout_mesage_status=(LinearLayout)itemView.findViewById(R.id.layout_mesage_status);
            mLastMessageEvent=(CircleImageView)itemView.findViewById(R.id.last_message_event_char);

            mOnlineStatusIcon=(EmojiconTextView)itemView.findViewById(R.id.recent_chat_in_home_online_status);
            mLayoutIventContainer=(LinearLayout)itemView.findViewById(R.id.ivent_conatiner_in_home_fragment);
            mRecyclerViewIvent=(RecyclerView)itemView.findViewById(R.id.ivent_list_view_in_home_fragment);
        }

    }
    class ItemContactsViewHolder extends  RecyclerView.ViewHolder{

        public RippleBackground mRippleOnlineStatus;
        public TextView mTextOnlineStatus;
        public CircleImageView mImagesStatus;
        public CircleImageView mImageProfile;
        public EmojiconTextView mTextViewName;

        public ItemContactsViewHolder(View itemView) {
            super(itemView);
          //  mRippleOnlineStatus=(RippleBackground)itemView.findViewById(R.id.row_connected_contacts_in_home_content_background_online_status);
            mImagesStatus=(CircleImageView)itemView.findViewById(R.id.row_connected_contacts_in_home_image_status);
            mImageProfile=(CircleImageView)itemView.findViewById(R.id.row_connected_contacts_in_home_image_profile);
            mTextViewName=(EmojiconTextView)itemView.findViewById(R.id.row_connected_contacts_in_home_text_view_name);
            mTextOnlineStatus=(TextView)itemView.findViewById(R.id.row_connected_contacts_in_home_online_status);
        }
    }

}

