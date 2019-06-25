package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.skyfishjy.library.RippleBackground;

import de.hdodenhof.circleimageview.CircleImageView;

;

/**
 * Created by Eric prestein on 1/1/2018.
 */

public class CustomProfilePictureView extends Dialog implements View.OnClickListener{
    private final String basePicture;
    private final String userId;
    private Activity activity;
    private RippleBackground mProfileBackPhoto;
    private CircleImageView mProfilePhoto;
    private FloatingActionButton mOpenChat;
    private FloatingActionButton mOpenProfileInfo;
    private Bitmap src;
    private Resources res;


    public CustomProfilePictureView(Activity activity,String basePicture,String userId){
        super(activity);
        this.activity=activity;
        this.basePicture=basePicture;
        this.userId=userId;
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sub_prompt_profile_picture_view);
        mProfileBackPhoto=(RippleBackground)findViewById(R.id.sub_prompty_content_background_profile_picture_view);
        mProfilePhoto=(CircleImageView)findViewById(R.id.sub_prompty_profile_picture_viewed);
        mOpenChat=(FloatingActionButton)findViewById(R.id.fab_sub_prompyt_profile_picture_send_message);
        mOpenProfileInfo=(FloatingActionButton)findViewById(R.id.fab_sub_prompty_profile_picture_view);
        try {
            res =activity.getResources();
            if (basePicture.equals("default")) {
                src = BitmapFactory.decodeResource(res, R.drawable.avatar_default);
            } else {
                byte[] imageBytes = Base64.decode(basePicture, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
            BitmapDrawable Image_drawable = new BitmapDrawable(res, src);
            mProfilePhoto.setImageDrawable(Image_drawable);
            mProfileBackPhoto.startRippleAnimation();
        } catch (Exception e) {
        }
        mOpenProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_ID,userId);
                Intent intent=new Intent(activity, UserProfileActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
       new TaskLoadProfilePicture().execute();
    }
    @Override
    public void onClick(View v) {

    }
    private class TaskLoadProfilePicture extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            try {
                if (userId.startsWith("GROUP")){
              /*      FirebaseDatabase.getInstance().getReference().child("Groups/Profiles/"+userId+"/Profile/GroupImage").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String s=dataSnapshot.getValue(String.class);
                            if (URLUtil.isHttpsUrl(s) || URLUtil.isHttpUrl(s)){

                                Glide.with(activity.getApplicationContext())
                                        .load(s).apply(new RequestOptions().override(20,20).optionalFitCenter().centerCrop())
                                        .into(mProfilePhoto);
                            }else {
                                Toast.makeText(activity, "Error in loading profile picture "+s, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            BitmapDrawable Image_drawable = new BitmapDrawable(res, src);
                            mProfilePhoto.setImageDrawable(Image_drawable);
                            ToastMessage.makeText(activity,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                            new TaskLoadProfilePicture().execute();
                        }

                    });
              */  }else {
                    FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.ImagePath(userId)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String s=dataSnapshot.getValue(String.class);
                            if (URLUtil.isHttpsUrl(s) || URLUtil.isHttpUrl(s)){

                                Glide.with(activity.getApplicationContext())
                                        .load(s).apply(new RequestOptions().override(20,20).optionalFitCenter().centerCrop())
                                        .into(mProfilePhoto);
                            }else {
                                Toast.makeText(activity, "Error in loading profile picture "+s, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            BitmapDrawable Image_drawable = new BitmapDrawable(res, src);
                            mProfilePhoto.setImageDrawable(Image_drawable);
                            ToastMessage.makeText(activity,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                            new TaskLoadProfilePicture().execute();
                        }

                    });
                }
             }catch (Exception e)
            {
                Toast.makeText(activity, "In glid loading"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return ConnectionDetector.isInternetAvailable(activity);
        }
        protected void onPostExecute(Boolean result) {
             if (result){

            }else {
                 ToastMessage.makeText(activity,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }
}
