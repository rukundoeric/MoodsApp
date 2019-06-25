package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceFragment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.GroupListDB;
import com.moodsapp.prestein.moodsapp.data.Database.RecentNotificationDb;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.model.Group;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.resourceshelper.CountryCode.Countries;
import com.moodsapp.prestein.moodsapp.resourceshelper.CountryCode.Country;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_group.Group_List_Adopter;
import com.moodsapp.prestein.moodsapp.ui.fragment.FragmentGroup;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageUtilsDefault;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;
import com.r0adkll.slidr.Slidr;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageCroping.cropToSquare;

public class UserProfileActivity extends AppCompatActivity {

    private RecyclerView mMediaView;
    private Consersation consersation;
    private String User_id;
    private mediaAdapter mMediaAdapter;
    private RecyclerView mCommonGroupView;
    private ArrayList<Group> listGroup;
    private Group_List_Adopter mCommonAdapter;
    private LinearLayout mCommonLayoutParent;
    private LinearLayout mMediaChatsLayoutParent;
    private LinearLayout mCommonGroupLayoutParent;
    private LinearLayout mMediaChatsLayoutParentDiver;
    private LinearLayout mCommonGroupLayoutParentDivider;
    private ImageView mImageUserProfile;
    private CircleImageView mCountryFlag;
    private EmojiconTextView mUserName;
    private EmojiconTextView mUserStatus;
    private EmojiconTextView mUserNationality;
    private EmojiconTextView mUserPhoneNumber;
    private ImageView mOpenChatsButton;
    private ImageView mCallButton;
    private EmojiconTextView mMediaCount;
    private EmojiconTextView mGroupCommonCount;
    private Toolbar toolbar;
    public static Bitmap avata;
    private CollapsingToolbarLayout mUserProfileCollapse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        toolbar = (Toolbar) findViewById(R.id.user_profile_activity_toolbar_in_user_profile);
        setSupportActionBar(toolbar);
        init();
        if (User_id==null || User_id.equals("")){
            onBackPressed();
        }else {
            sedContentProfile();
            addPreferancesInSettingsSection();
            UpdateAdapterMediaChats();
            UpdateAdapterCoomnGroup();
        }
    }

    private void init() {
        Bundle bundle=getIntent().getExtras();
        User_id=bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_ID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Slidr.attach(this);
        mImageUserProfile=(ImageView)findViewById(R.id.user_profile_activity_image_in_user_profile);
        mCountryFlag=(CircleImageView)findViewById(R.id.user_profile_nationality_flag_in_user_profile);
        mUserName=(EmojiconTextView)findViewById(R.id.user_profile_user_name_in_user_profile);
        mUserStatus=(EmojiconTextView)findViewById(R.id.user_profile_status_in_user_profile);
        mUserNationality=(EmojiconTextView)findViewById(R.id.user_profile_nationality_in_user_profile);
        mUserPhoneNumber=(EmojiconTextView)findViewById(R.id.user_profile_phone_number_in_user_profile);
        mOpenChatsButton=(ImageView)findViewById(R.id.user_profile_open_chat_button_in_user_profile);
        mCallButton=(ImageView)findViewById(R.id.user_profile_call_button_in_user_profile);
        mMediaCount=(EmojiconTextView)findViewById(R.id.user_profile_media_count_in_user_profile);
        mGroupCommonCount=(EmojiconTextView)findViewById(R.id.user_profile_common_group_count_in_user_profile);
        mUserProfileCollapse=(CollapsingToolbarLayout)findViewById(R.id.user_profile_toolbar_collapse_in_user_profile);
        mMediaView=(RecyclerView)findViewById(R.id.user_profile_media_message_view);
        mCommonGroupView=(RecyclerView)findViewById(R.id.user_profile_fragment_group_all_group_view);
        //layout containers
        mMediaChatsLayoutParent=(LinearLayout)findViewById(R.id.list_view_media_chats_parent_layout);
        mCommonGroupLayoutParent=(LinearLayout)findViewById(R.id.Common_group_list_view_parent_layout);
        //layout dividers
        mMediaChatsLayoutParentDiver=(LinearLayout)findViewById(R.id.list_view_media_chats_parent_layout_divider);
        mCommonGroupLayoutParentDivider=(LinearLayout)findViewById(R.id.Common_group_list_view_parent_layout_divider);
    }
    Bitmap profile_bitmap = null;
    private void sedContentProfile(){
        ViewGroup.LayoutParams layoutParams;
        layoutParams=mUserProfileCollapse.getLayoutParams();
        layoutParams.height=ExtractedStrings.DeviceWidth;
        mUserProfileCollapse.setLayoutParams(layoutParams);
        ArrayList<Friend> listFriend = FriendDB.getInstance(getApplicationContext()).getListFriendArray();
        mImageUserProfile.setImageBitmap(avata);
        for (Friend friend:listFriend){
            if (friend.id.equals(User_id)){
                new TaskIsInternetAvailable().execute(friend.image);
                mUserName.setText(friend.name);
                mUserStatus.setText(friend.status);
                mUserNationality.setText(friend.country);
                getSupportActionBar().setTitle(friend.name);
                mCountryFlag.setImageBitmap(getCountryFlag(friend.country));
                mUserPhoneNumber.setText(getPhoneNumber(friend.id,friend.country));

            }
        }
    }





    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        String url;
        protected Boolean doInBackground(String... strings) {
            url=strings[0];
            return ConnectionDetector.isInternetAvailable(getApplicationContext());
        }
        protected void onPostExecute(Boolean result) {
            if (result){
                Glide.with(getApplicationContext()).load(url).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (avata!=null){
                            mImageUserProfile.setImageBitmap(avata);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        profile_bitmap =((BitmapDrawable) resource).getBitmap();
                        return false;
                    }
                }).into(mImageUserProfile);

            }else {
                ToastMessage.makeText(UserProfileActivity.this, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }




    private Bitmap getCountryFlag(String country){
        Bitmap bitmap = null;
        ArrayList<Country> countries=new ArrayList<>();
        new Country().AddCountryInList(countries);
        for (Country country1:countries){
            if (country1.getCountryName().toLowerCase().equals(country.toLowerCase())){
               bitmap= BitmapFactory.decodeResource(getResources(),new Country().getFlagResID(country1.getCountryNameCode()));
            }
        }
        return bitmap;
    }
    private String getPhoneNumber(String phone,String country){
        String openBar="+(",closeBar=")",space=" ",finalString="";
        String code="";
        int a=0;
        boolean isCodeFinished=false;
        ArrayList<Country> countries=new ArrayList<>();
        new Country().AddCountryInList(countries);
        for (Country country1:countries){
            if (country1.getCountryName().equals(country)){
                code=country1.getCountryCode();
            }
        }
        StringBuilder sb=new StringBuilder(phone);
        for (int i=0;i<sb.length();i++){
            if (i==0){
                finalString=finalString+openBar+sb.charAt(i);
                if (new NumberFormat().removeChar(finalString).equals(code)){
                    finalString=finalString+closeBar+space;
                    isCodeFinished=true;
                }
            } else {
                if (isCodeFinished){
                    StringBuilder nsb=new StringBuilder(phone.substring(code.length()));
                    if (a==3){
                        finalString=finalString+space+nsb.charAt(i-(code.length()-1));
                        a=0;
                    }else {
                        finalString=finalString+nsb.charAt(i-(code.length()-1));
                        a++;
                    }
                }else {
                    finalString=finalString+sb.charAt(i);
                }
            }
        }
        return finalString;
    }
    private void UpdateAdapterMediaChats () {
        Handler mHandler=new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                    consersation= AllChatsDB.getInstance(getApplicationContext()).getMediaListMessages(getApplicationContext() ,User_id, ExtractedStrings.UID);
                    if (consersation.getListMessageData().size()<=0){
                        mMediaChatsLayoutParent.setVisibility(View.GONE);
                        mMediaChatsLayoutParentDiver.setVisibility(View.GONE);
                    }else {
                        mMediaChatsLayoutParentDiver.setVisibility(View.VISIBLE);
                        mMediaChatsLayoutParent.setVisibility(View.VISIBLE);

                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
                        mMediaView.setLayoutManager(linearLayoutManager);
                        mMediaView.getLayoutManager().setAutoMeasureEnabled(true);
                        mMediaView.setNestedScrollingEnabled(false);
                        mMediaView.setHasFixedSize(true);
                        mMediaView.setItemViewCacheSize(200);
                        mMediaView.setDrawingCacheEnabled(true);
                        mMediaView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        mMediaAdapter = new mediaAdapter(getApplicationContext(),consersation);
                        mMediaAdapter.notifyDataSetChanged();
                        mMediaView.setAdapter(mMediaAdapter);
                       mMediaCount.setText(String.valueOf(consersation.getListMessageData().size()));
                    }


            }
        });
    }
    private void UpdateAdapterCoomnGroup(){
      new Handler().post(new Runnable() {
          @Override
          public void run() {
              listGroup = GroupListDB.getInstance(getApplicationContext()).getUserCommonListGroups(getApplicationContext(),User_id);
              if (listGroup.size()<=0){
                  mCommonGroupLayoutParent.setVisibility(View.GONE);
                  mCommonGroupLayoutParentDivider.setVisibility(View.GONE);
              }else
              {
                  mCommonGroupLayoutParent.setVisibility(View.VISIBLE);
                  mCommonGroupLayoutParentDivider.setVisibility(View.VISIBLE);
                  mCommonGroupView = (RecyclerView) findViewById(R.id.fragment_group_all_group_view);
                  mCommonGroupView.setLayoutManager(new GridLayoutManager(UserProfileActivity.this,2));
                  mCommonAdapter = new Group_List_Adopter(getApplicationContext(), listGroup,UserProfileActivity.this);

                  mCommonGroupView.setAdapter(mCommonAdapter);
                  mGroupCommonCount.setText(listGroup.size());
              }
          }
      });
    }
    private void addPreferancesInSettingsSection() {
        SettingsSection settingsSection=new SettingsSection();
        FragmentManager manager=getFragmentManager();
        manager.beginTransaction().replace(R.id.frame_setting_container_in_user_profile,settingsSection,settingsSection.getTag()).commit();
    }
    @SuppressLint("ValidFragment")
    private static class SettingsSection extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.user_profile_settings);
        }
    }
    class mediaAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
        Context context;
        Consersation consersation;
        ViewGroup.LayoutParams lparams;

        public mediaAdapter(Context context, Consersation consersation) {
            this.context = context;
            this.consersation = consersation;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_selected_view, parent, false);
            return new MediaItem(context, view);
        }
        private Bitmap getBitmapStringFromVideo(String VideoPath){
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(VideoPath,   MediaStore.Images.Thumbnails.MINI_KIND);
            Matrix matrix = new Matrix();
            Bitmap bmThumbnail = Bitmap.createBitmap(thumb, 0, 0,thumb.getWidth(), thumb.getHeight(), matrix, true);
            bmThumbnail= cropToSquare(bmThumbnail);
            return bmThumbnail;
        }
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            lparams=((MediaItem) holder).mImage.getLayoutParams();
            int width=ExtractedStrings.DeviceWidth/3;
            lparams.height=width;
            lparams.width=width;

            ((MediaItem) holder).mImage.setLayoutParams(lparams);
            if (consersation.getListMessageData().get(position).msgType.equals(ExtractedStrings.ITEM_MESSAGE_VIDEO_TYPE)){
                if (new File(consersation.getListMessageData().get(position).VideoDeviceUrl).exists()){
                    ((MediaItem) holder).mPlayButton.setVisibility(View.VISIBLE);
                    ((MediaItem) holder).mImage.setImageBitmap(getBitmapStringFromVideo(consersation.getListMessageData().get(position).VideoDeviceUrl));
                }else {
                    consersation.getListMessageData().remove(consersation.getListMessageData().get(position));
                }
            }else{
                if (new File(consersation.getListMessageData().get(position).PhotoDeviceUrl).exists()){
                    ((MediaItem) holder).mPlayButton.setVisibility(View.GONE);
                    Glide.with(context).load(consersation.getListMessageData().get(position).PhotoDeviceUrl).apply(new RequestOptions().optionalFitCenter()).into(((MediaItem) holder).mImage);
                }
                else {
                    consersation.getListMessageData().remove(consersation.getListMessageData().get(position));
                }
            }
        }

        @Override
        public int getItemCount() {
            return consersation.getListMessageData().size();
        }

        private class MediaItem extends RecyclerView.ViewHolder
        {
            public LinearLayout mPlayButton;
            public ImageView mImage;
            MediaItem(Context context, View itemView) {
                super(itemView);
                mImage = (ImageView) itemView.findViewById(R.id.imgSelectedImagesView);
                mPlayButton=(LinearLayout) itemView.findViewById(R.id.layout_back_for_play_video_button);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   selectedListerner.onSelectedClicked(getLayoutPosition());
                    }
                });
            }

        }
    }
}
