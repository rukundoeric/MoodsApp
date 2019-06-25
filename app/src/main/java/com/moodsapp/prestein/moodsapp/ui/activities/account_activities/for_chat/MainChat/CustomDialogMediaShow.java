package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType.ApkListActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact.SelectContactsToSend;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui.PhotoGalleryActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui.VideoGalleryActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Eric prestein on 1/1/2018.
 */

public class CustomDialogMediaShow extends Dialog {
    private String User_id;
    private ArrayList<CharSequence> idFriend;
    private String status;
    private String name;
    private Chat_Activity activity;
    private Dialog dialog;
    private ImageView mCallGallery;
    private ImageView mCallCamera;
    private ImageView mCallDocument;
    private ImageView mCallContact;
    private ImageView mCallAudio;
    private ImageView mCallVideo;

    public CustomDialogMediaShow(Chat_Activity activity,String User_id, String name, String status, ArrayList<CharSequence> idFriend){
        super(activity);
        this.activity=activity;
        this.User_id=User_id;
        this.name=name;
        this.status=status;
        this.idFriend=idFriend;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sub_prompt_media_option);
        mCallGallery=(ImageView)findViewById(R.id.call_gallery_in_chat_conversation);
        mCallCamera=(ImageView)findViewById(R.id.call_camera_in_chat_conversation);
        mCallDocument=(ImageView)findViewById(R.id.call_document_in_chat_conversation);
        mCallContact=(ImageView)findViewById(R.id.call_contact_in_chat_conversation);
        mCallAudio=(ImageView)findViewById(R.id.call_audio_in_chat_conversation);
        mCallVideo=(ImageView)findViewById(R.id.call_video_in_chat_conversation);
        mCallGallery.setClickable(true);
        mCallGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtractedStrings.RecentActivity=this.getClass().getName();
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(activity.getApplicationContext(), PermissionRequestCode.IO_PERMISSIONS)) {
                    ActivityCompat.requestPermissions(activity, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                } else {
                    Intent intent = new Intent(activity, PhotoGalleryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fromContext", activity.getClass().getName());
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, name);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, status);
                    bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                    intent.putExtras(bundle);
                //    activity.customDialogMediaShow.dismiss();
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }
        });
        mCallCamera.setClickable(true);
        mCallCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   // activity.customDialogRecordAudio.dismiss();
                   // activity.callCamera(cameraFileName());
            }
        });
        mCallDocument.setClickable(true);
        mCallDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // activity.customDialogRecordAudio.dismiss();
              ///  activity.callDocument();
            }
        });
        mCallAudio.setClickable(true);
        mCallAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ExtractedStrings.INTENT_USER_ID, User_id);
                Intent intent=new Intent(activity, ApkListActivity.class);
                ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                idFriend.add(User_id);
                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        mCallVideo.setClickable(true);
        mCallVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtractedStrings.RecentActivity=this.getClass().getName();
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(activity.getApplicationContext(), PermissionRequestCode.IO_PERMISSIONS)) {
                    ActivityCompat.requestPermissions(activity, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                } else {
                    Intent intent = new Intent(activity, VideoGalleryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fromContext", activity.getClass().getName());
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, name);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, status);
                    bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                    intent.putExtras(bundle);
                  //  activity.customDialogMediaShow.dismiss();
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }
        });
        mCallContact.setClickable(true);
        mCallContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // activity.customDialogRecordAudio.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                Intent intent=new Intent(activity, SelectContactsToSend.class);
                ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                idFriend.add(User_id);
                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
    }
    private String cameraFileName()
    {
        String CameraImagePath="";
        try {
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath+= Data_Storage_Path.IMAGE_MESSAGE_SENT;

            if (new File(filePath).exists())
            {
                File[] list=new File(filePath).listFiles();
                int count=0;
                for (File f: list){
                    String name=f.getName();
                    if (name.endsWith(".png")|| name.endsWith(".gif") || name.endsWith(".jpg")){
                        count++;
                    }
                }

                String FILE_NAME="IMG_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MD"+(count)+".png";
                CameraImagePath=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                String FILE_NAME="IMG_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MD"+".png";
                CameraImagePath=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(activity, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return CameraImagePath;
    }
}
