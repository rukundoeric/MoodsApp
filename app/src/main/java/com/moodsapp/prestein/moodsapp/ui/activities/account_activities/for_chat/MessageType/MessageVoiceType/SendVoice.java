package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.File;

/**
 * Created by Eric prestein on 1/24/2018.
 */

public class SendVoice {
    private ImageView mCancelUpload;
    private Chat_Activity chat_activity;
    private Context context;
    private String filename;
    private String voiceName;
    private String user_id;
    private String msgID;
    private String RecordTime;
    private CircleProgressBar mProcessProgressLoading;
    private ProgressBar Loading;
    private RelativeLayout RecorderIcon;
    private ImageView UploadIcon;
    private UploadTask uploadVoiceTask;
    private TaskIsInternetAvailable taskIsInternetAvailable;

    public SendVoice(Chat_Activity chat_activity, Context context, String filename, String voiceName, String user_id, String msgID, String recordTime, CircleProgressBar mProcessProgressLoading, ProgressBar loading, RelativeLayout recorderIcon, ImageView UploadIcon) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.filename = filename;
        this.voiceName = voiceName;
        this.user_id = user_id;
        this.msgID = msgID;
        this.RecordTime = recordTime;
        this.mProcessProgressLoading=mProcessProgressLoading;
        this.Loading = loading;
        this.RecorderIcon = recorderIcon;
        this.UploadIcon=UploadIcon;
        this.mCancelUpload=mCancelUpload;
    }
    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(context);
        }
        protected void onPostExecute(Boolean result) {
            // Toast.makeText(context, String.valueOf(result), Toast.LENGTH_SHORT).show();
            if (result){
                mProcessProgressLoading.setVisibility(View.VISIBLE);
                UploadIcon.setVisibility(View.GONE);
                RecorderIcon.setVisibility(View.GONE);
                Loading.setVisibility(View.GONE);
                mProcessProgressLoading.setIndeterminate(false);
                mProcessProgressLoading.setMax(100);
                uploadAudioRecoredes();
            }else {
                mProcessProgressLoading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.VISIBLE);
                mCancelUpload.setVisibility(View.GONE);
                RecorderIcon.setVisibility(View.GONE);
                Loading.setVisibility(View.GONE);
                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }

    public void cancelUpload(){
        if (uploadVoiceTask!=null){
            uploadVoiceTask.cancel();
            Loading.setVisibility(View.GONE);
            UploadIcon.setVisibility(View.VISIBLE);
            mProcessProgressLoading.setVisibility(View.GONE);
            mCancelUpload.setVisibility(View.GONE);
        }
        else {
            if (taskIsInternetAvailable != null) {
                taskIsInternetAvailable.cancel(true);
                Loading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.VISIBLE);
                mProcessProgressLoading.setVisibility(View.GONE);
                mCancelUpload.setVisibility(View.GONE);
            }else {
                Loading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.VISIBLE);
                mProcessProgressLoading.setVisibility(View.GONE);
                mCancelUpload.setVisibility(View.GONE);
            }

        }
    }
    public void checkBeforeUpload(){
        try{
           taskIsInternetAvailable=new TaskIsInternetAvailable();
            taskIsInternetAvailable.execute();
        }catch (Exception e){
            Toast.makeText(chat_activity, "In send voice" +e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void uploadAudioRecoredes(){
        try {
            Uri uri=Uri.fromFile(new File(filename));
            uploadVoiceTask=FirebaseStorage.getInstance().getReference().child(Firebase_data_path.MessageVoiceStoragePath(user_id,ExtractedStrings.UID)).putFile(uri);
            uploadVoiceTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUrl=taskSnapshot.getDownloadUrl().toString();
                    Message newMessage = new Message();
                    newMessage.msgType= ExtractedStrings.ITEM_MESSAGE_VOICE_TYPE;
                    newMessage.idRoom= ExtractedStrings.UID+user_id;
                    newMessage.idSender = ExtractedStrings.UID;
                    newMessage.idReceiver =user_id;
                    newMessage.text = voiceName;
                    newMessage.timestamp = System.currentTimeMillis();
                    newMessage.PhotoDeviceUrl="-";
                    newMessage.PhotoStringBase64=RecordTime;
                    newMessage.VideoDeviceUrl="-";
                    newMessage.VoiceDeviceUrl=filename;
                    newMessage.DocumentDeviceUrl="-";
                    newMessage.AnyMediaUrl= downloadUrl;
                    newMessage.AnyMediaStatus= ExtractedStrings.MEDIA_NOT_DOWNLOADED;
                    newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                    newMessage.msg_reprayed_id= ExtractedStrings.MESSAGE_REPLAYED_ID;
                    newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SENT;
                    Loading.setVisibility(View.GONE);
                    mProcessProgressLoading.setVisibility(View.GONE);
                    RecorderIcon.setVisibility(View.VISIBLE);
                    newMessage.AnyMediaStatus= ExtractedStrings.MEDIA_NOT_DOWNLOADED;
                    FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(user_id)).child(msgID).setValue(newMessage);
                    newMessage.AnyMediaStatus= ExtractedStrings.MEDIA_UPLOADED;
                    AllChatsDB.getInstance(context).updateChats(context,newMessage,msgID);
                    chat_activity.newVoiceIsSent=false;
                    // chat_activity.UpdateAdapterChats();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    int currentprogress = (int) progress;
                    mProcessProgressLoading.setProgress(currentprogress);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProcessProgressLoading.setVisibility(View.GONE);
                    UploadIcon.setVisibility(View.VISIBLE);
                    RecorderIcon.setVisibility(View.GONE);
                    Loading.setVisibility(View.GONE);
                    ToastMessage.makeText(chat_activity,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                }
            });;
        }catch (Exception r){
            Toast.makeText(context, "In Uploading"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
