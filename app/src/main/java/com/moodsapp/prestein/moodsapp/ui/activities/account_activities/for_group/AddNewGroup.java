package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_group;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moodsapp.emojis_library.Actions.EmojIconActions;
import com.moodsapp.emojis_library.Helper.EmojiconEditText;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.GroupDB;
import com.moodsapp.prestein.moodsapp.data.Database.GroupListDB;
import com.moodsapp.prestein.moodsapp.data.Database.MyProfileDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.model.Group;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageUtils80px;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;
import com.skyfishjy.library.RippleBackground;
import com.moodsapp.cropper.CropImage;
import com.moodsapp.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddNewGroup extends AppCompatActivity{

    private ImageView mOpenEmojis;
    private EmojIconActions emojIcon;
    private View rootView;
    private EmojiconEditText mGroupName;
    private static final String TAG = Chat_Activity.class.getSimpleName();
    private FloatingActionButton mButtonCreateGroup;
    private CircleImageView mImageAdd;
    private LinearLayout mLayoutCreateGroup;
    private static final int GALLERY_REQUEST=1;
    private Uri mImageUri;
    private DatabaseReference mDatabaseUsers;
    private String groupName;
    private Context context=this;
    private String imageBase="default";
    private RecyclerView mUsersRecyclerList;
    private SparseBooleanArray mSparseBooleanArray;
    private ListUserAdapter UsersAdapter;
    private GridView mSelectedUsersGlidView;
    private LinearLayout mSelectedUsersLayoutContainer;
    private LinearLayout mUserLayoutContainer;
    private ArrayList<Friend> mUsersList;
    private ProgressDialog mProgress;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rootView=(View)findViewById(R.id.create_group_layout);
        mGroupName=(EmojiconEditText)findViewById(R.id.add_group_name);
        mButtonCreateGroup=(FloatingActionButton)findViewById(R.id.fab_create_group_apply);
        mButtonCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UsersAdapter.getCheckedItems().size()+1 < 3) {
                    Toast.makeText(AddNewGroup.this, "Add at lease two people to create group", Toast.LENGTH_SHORT).show();
                } else {
                    if (mGroupName.getText().length() == 0) {
                        Toast.makeText(AddNewGroup.this, "Enter group name", Toast.LENGTH_SHORT).show();
                    } else {
                            new TaskIsInternetAvailable().execute();
                    }
                }
            }
        });
        mImageAdd=(CircleImageView)findViewById(R.id.add_group_image);
        mImageAdd.setClickable(true);
        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageGroup();
            }
        });
        try {
            mSelectedUsersGlidView=(GridView)findViewById(R.id.add_new_group_grid_view_selected_member);
            mSelectedUsersLayoutContainer=(LinearLayout)findViewById(R.id.addGroupselectedUsersLayoutContainer);
            mUserLayoutContainer=(LinearLayout)findViewById(R.id.addGroupUsersLayoutContainer);
            mSelectedUsersGlidView.setVerticalSpacing(5);
            mSelectedUsersGlidView.setHorizontalSpacing(5);
            mUsersRecyclerList=(RecyclerView)findViewById(R.id.list_user_in_member_selector);
            mSparseBooleanArray = new SparseBooleanArray();
            mUsersList=FriendDB.getInstance(getApplicationContext()).getListFriendArray();
            if (mUsersList.size()<=0){
                mSelectedUsersLayoutContainer.setVisibility(View.GONE);
                mUserLayoutContainer.setVisibility(View.GONE);
            }else {
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                UsersAdapter = new ListUserAdapter(mUsersList, mSparseBooleanArray, getApplicationContext());
                mUsersRecyclerList.setLayoutManager(linearLayoutManager);
                mUsersRecyclerList.setAdapter(UsersAdapter);
                mUsersRecyclerList.setItemAnimator(new DefaultItemAnimator());
                UsersAdapter.SetOnItemClickListener(new ListUserAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CircleImageView img=view.findViewById(R.id.row_users_in_create_group_selected_sign);
                        if(!mSparseBooleanArray.get(position)) {
                            img.setVisibility(View.VISIBLE);
                            mSparseBooleanArray.put(position, true);
                            UpdateSelectedUsers(UsersAdapter.getCheckedItems());
                        } else {

                            img.setVisibility(View.GONE);
                            mSparseBooleanArray.put(position, false);
                            UpdateSelectedUsers(UsersAdapter.getCheckedItems());
                        }
                    }
                });
            }


        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void UpdateSelectedUsers(ArrayList<Friend> selectedUsers) {
        if (selectedUsers.size()<=0){
            mSelectedUsersLayoutContainer.setVisibility(View.GONE);
            hideFabButton();

        }else {
            if (selectedUsers.size()-1==0){
                unHideFabButton();
            }else if (selectedUsers.size()>1){
                shakeButton();
            }
            mSelectedUsersLayoutContainer.setVisibility(View.VISIBLE);
            UserSelectedListAdapter selectedImagesAdapter = new UserSelectedListAdapter(this, selectedUsers);
            mSelectedUsersGlidView.setAdapter(selectedImagesAdapter);
        }
    }
    private void shakeButton(){
        Animation an= AnimationUtils.loadAnimation(getBaseContext(),R.anim.shake);
        mButtonCreateGroup.setAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void hideFabButton() {
        Animation an= AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        mButtonCreateGroup.setAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mButtonCreateGroup.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void unHideFabButton() {
        mButtonCreateGroup.setVisibility(View.VISIBLE);
        Animation an= AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_in);
        mButtonCreateGroup.setAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void loadImageGroup() {
        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERY_REQUEST);
    }
    private void createGroup() {

        String nameGroup=mGroupName.getText().toString();
        if (!nameGroup.equals("")){
            mProgress = new ProgressDialog(this);
            mProgress.setMessage("Creating group...");
            mProgress.show();
            final String idGroup ="GROUP_"+(ExtractedStrings.UID + System.currentTimeMillis())+"_"+ new NumberFormat().removeChar(nameGroup.toUpperCase());
            final Group group=new Group();
            group.GroupId=idGroup;
            group.GroupName=nameGroup;
            group.GroupImage=imageBase;
            FirebaseDatabase.getInstance().getReference().child("Groups/Profiles/"+idGroup+"/Profile").setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    final ArrayList<Friend> selectedUserList=UsersAdapter.getCheckedItems();
                    Friend friend1=new Friend();
                    friend1.id=ExtractedStrings.UID;
                    friend1.name=ExtractedStrings.NAME;
                    friend1.status=ExtractedStrings.MY_STATUS;
                    friend1.image= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(4,getApplicationContext());
                    selectedUserList.add(friend1);
                    if (selectedUserList.size()>=3){
                        for (Friend friend:selectedUserList){
                            Group groupMember=new Group();
                            groupMember.UserId=friend.id;
                            groupMember.UserName=friend.name;
                            groupMember.UserStatus=friend.status;
                            groupMember.UserImage=friend.image;
                            groupMember.UserRole=friend.id.equals(ExtractedStrings.UID)?"Admin":"member";
                            FirebaseDatabase.getInstance().getReference().child("Groups/Profiles/"+idGroup+"/Members/"+friend.id).setValue(groupMember);
                            FirebaseDatabase.getInstance().getReference().child("Users/Profiles/"+friend.id+"/MyGroups").push().setValue(idGroup);
                        }
                        try {
                            GroupListDB.getInstance(getApplicationContext()).CheckBeforeAddGroup(getApplicationContext(),group);
                            for (Friend friend2:selectedUserList){
                                Group groupMember=new Group();
                                groupMember.UserId=friend2.id;
                                groupMember.UserName=friend2.name;
                                groupMember.UserStatus=friend2.status;
                                groupMember.UserImage=friend2.image;
                                groupMember.UserRole=friend2.id.equals(ExtractedStrings.UID)?"Admin":"member";
                                GroupDB.getInstance(getApplicationContext(),idGroup).checkBeforeAddGroup(groupMember,friend2.id,getApplicationContext(),idGroup);
                            }
                            mProgress.dismiss();
                            Toast.makeText(context, "Group created successful", Toast.LENGTH_SHORT).show();
                        }catch (Exception r){
                            Toast.makeText(context, r.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgress.dismiss();
                    ToastMessage.makeText(AddNewGroup.this,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                }
            });
        }
    }
    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(context);
        }
        protected void onPostExecute(Boolean result) {
            if (result){
                createGroup();
            }else {
                ToastMessage.makeText(AddNewGroup.this,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){

            Uri ImageUri=data.getData();



            try {
                InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                imgBitmap = ImageUtils80px.cropToSquare(imgBitmap);
                InputStream is = ImageUtils80px.convertBitmapToInputStream(imgBitmap);
                final Bitmap liteImage = ImageUtils80px.makeImageLite(is,
                        imgBitmap.getWidth(), imgBitmap.getHeight(),
                        ImageUtils80px.AVATAR_WIDTH, ImageUtils80px.AVATAR_HEIGHT);

                imageBase = ImageUtils80px.encodeBase64(liteImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Uri outputUri= null;
            try {
                outputUri = Uri.fromFile(new File(new getFileName(context).getImageFileName("",true)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView
                            .Guidelines.ON)
                    .setAspectRatio(1,1)
                    .setBackgroundColor(getApplicationContext().getResources().getColor(R.color.background))
                    .setActivityMenuIconColor(getApplicationContext().getResources().getColor(R.color.colorPrimary))
                    .setOutputUri(outputUri)
                    .start(this);



        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                mImageUri=result.getUri();
                mImageAdd.setImageURI(mImageUri);
                String path= new CompressingImage(getApplicationContext()).getRealPathFromURI(String.valueOf(mImageUri));
                Toast.makeText(context, path, Toast.LENGTH_SHORT).show();
            }
            else if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error=result.getError();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onPause() {
        super.onPause();

    }
}
class ListUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<Friend> listFriend;
    SparseBooleanArray mSparseBooleanArray;
    Context context;
    private Bitmap src;
    private OnItemClickListener mItemClickListener;

    public ListUserAdapter(ArrayList<Friend> listFriend, SparseBooleanArray mSparseBooleanArray, Context context) {
        this.listFriend = listFriend;
        this.mSparseBooleanArray = mSparseBooleanArray;
        this.context = context;
    }
    //Method to return selected User
    public ArrayList<Friend> getCheckedItems() {
        ArrayList<Friend> mTempArry = new ArrayList<>();
        for (int i = 0; i < listFriend.size(); i++) {
            Log.d("log", "i = " + mSparseBooleanArray.get(i));
            if (mSparseBooleanArray.get(i)) {
                mTempArry.add(listFriend.get(i));
            }
        }
        return mTempArry;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users_in_create_group,parent, false);
        return new ListUsersItem(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try{
            if (mSparseBooleanArray.get(position)) {
                ((ListUsersItem) holder).mSelectedSign.setVisibility(View.VISIBLE);
            } else {
                ((ListUsersItem) holder).mSelectedSign.setVisibility(View.GONE);
            }
            final String name = listFriend.get(position).name;
            final String status=listFriend.get(position).status;
            final String avata = listFriend.get(position).image;
            try {
                if (avata.equals("default")) {
                    src = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default);
                } else {
                    byte[] imageBytes = Base64.decode(avata, Base64.DEFAULT);
                    src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                }
                BitmapDrawable Image_drawable = new BitmapDrawable(context.getResources(), src);
                ((ListUsersItem) holder).mImageView.setImageDrawable(Image_drawable);
                ((ListUsersItem) holder).mUserName.setText(name);
                ((ListUsersItem) holder).mUserStatus.setText(status);
                ((ListUsersItem) holder).mRipplerBackground.startRippleAnimation();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        return listFriend.size();
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    class ListUsersItem extends RecyclerView.ViewHolder{
        public RippleBackground mRipplerBackground;
        public CircleImageView mSelectedSign;
        public CircleImageView mImageView;
        public EmojiconTextView mUserName;
        public EmojiconTextView mUserStatus;
        public ListUsersItem(final View itemView) {
            super(itemView);
            mRipplerBackground=(RippleBackground)itemView.findViewById(R.id.row_users_in_create_group_content_background);
            mSelectedSign=(CircleImageView)itemView.findViewById(R.id.row_users_in_create_group_selected_sign);
            mImageView=(CircleImageView)itemView.findViewById(R.id.row_users_in_create_group_image);
            mUserName=(EmojiconTextView)itemView.findViewById(R.id.row_users_in_create_group_name);
            mUserStatus=(EmojiconTextView)itemView.findViewById(R.id.row_users_in_create_group_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getPosition());
                }
            });
        }

    }
}
class UserSelectedListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Friend> selectedUsers;
    private Bitmap src;
    public UserSelectedListAdapter(Context context, ArrayList<Friend> selectedUsers) {
        this.context = context;
        this.selectedUsers = selectedUsers;
    }
    @Override
    public int getCount() {
        return selectedUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return selectedUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        SelectedUsersItem viewHolder = null;
        // no view at this position, so creating a new view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_selected_group_member, viewGroup, false);//Inflate layout
            viewHolder = new SelectedUsersItem(view);
            view.setTag(viewHolder);
        } else {  // recycling a view
            viewHolder = (SelectedUsersItem) view.getTag();
        }
        try{
            final String name = selectedUsers.get(position).name;
            final String avata = selectedUsers.get(position).image;
            try {
                if (avata.equals("default")) {
                    src = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default);
                } else {
                    byte[] imageBytes = Base64.decode(avata, Base64.DEFAULT);
                    src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                }
                BitmapDrawable Image_drawable = new BitmapDrawable(context.getResources(), src);
                viewHolder.mImageView.setImageDrawable(Image_drawable);
                viewHolder.mTextUserName.setText(name);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception r){
            Toast.makeText(context, r.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
class SelectedUsersItem extends  RecyclerView.ViewHolder{
    public CircleImageView mImageView;
    public EmojiconTextView mTextUserName;

    public SelectedUsersItem(View itemView) {
        super(itemView);
        mImageView=(CircleImageView)itemView.findViewById(R.id.row_selected_member_profile_image);
        mTextUserName=(EmojiconTextView)itemView.findViewById(R.id.row_selected_member_name);
    }
}