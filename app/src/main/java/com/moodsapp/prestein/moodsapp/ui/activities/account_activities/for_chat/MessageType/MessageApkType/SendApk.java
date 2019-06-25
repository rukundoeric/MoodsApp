package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.moodsapp.prestein.moodsapp.ActivityRunningStatus.Chat_Activity_Status;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;

import java.io.File;


public class SendApk {
    private String ApkPath;
    private String PackageName;
    private Context context;
    private String user_id;
    private String msgID ;
    private Chat_Activity chat_activity;
    private ProgressBar mApkProgressBar;
    private CircleProgressBar mApkProcessProgressBar;
    private ImageView mApkUploadIcon;

    public SendApk(Context context) {
        this.context = context;
    }

    public SendApk(Chat_Activity chat_activity, Context context, String PackageName, String apkPath, String user_id, String msgId, ImageView mApkUploadIcon, ProgressBar mApkLoading, CircleProgressBar mApkProcessProgressLoading) {
       this.chat_activity=chat_activity;
       this.context=context;
       this.ApkPath=apkPath;
       this.PackageName=PackageName;
       this.user_id=user_id;
       this.msgID=msgId;
       this.mApkUploadIcon=mApkUploadIcon;
       this.mApkProgressBar=mApkLoading;
       this.mApkProcessProgressBar=mApkProcessProgressLoading;
    }

    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(context);
        }
        protected void onPostExecute(Boolean result) {
            // Toast.makeText(context, String.valueOf(result), Toast.LENGTH_SHORT).show();
            if (result){
                String fileName= new NumberFormat().removeChar(new ApkInfoExtractor(context).GetAppName(PackageName));
                mApkProgressBar.setVisibility(View.GONE);
                mApkUploadIcon.setVisibility(View.GONE);
                mApkProcessProgressBar.setVisibility(View.VISIBLE);
                mApkProcessProgressBar.setIndeterminate(false);
                mApkProcessProgressBar.setMax(100);
                uploadApkMessages(fileName,PackageName);
            }else {
                mApkProgressBar.setVisibility(View.GONE);
                mApkUploadIcon.setVisibility(View.VISIBLE);
                mApkProcessProgressBar.setVisibility(View.GONE);
                mApkProcessProgressBar.setVisibility(View.GONE);
                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);

            }
        }
    }
    public void checkBeforeUpload(final Context context){
        TaskIsInternetAvailable taskIsInternetAvailable=new TaskIsInternetAvailable();
        taskIsInternetAvailable.execute();

    }
    public void saveApkMessage(String User_id,String apkPath,String ApkPackageName){
        try {
            if (apkPath!=null) {
                String msgId = user_id+ ExtractedStrings.UID+String.valueOf(System.currentTimeMillis());
                Message newMessage = new Message();
                newMessage.msgId=msgId;
                newMessage.msgType= ExtractedStrings.ITEM_MESSAGE_APK_TYPE;
                newMessage.idRoom=ExtractedStrings.UID+User_id;
                newMessage.idSender = ExtractedStrings.UID;
                newMessage.idReceiver = User_id;
                newMessage.text =ApkPackageName;
                newMessage.timestamp = System.currentTimeMillis();
                newMessage.PhotoDeviceUrl= "-";
                newMessage.PhotoStringBase64="-";
                newMessage.VideoDeviceUrl="-";
                newMessage.VoiceDeviceUrl="-";
                newMessage.DocumentDeviceUrl=apkPath;
                newMessage.AnyMediaUrl="-";
                newMessage.AnyMediaStatus=ExtractedStrings.MEDIA_NOT_UPLOADED;
                newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                newMessage.msg_reprayed_id=ExtractedStrings.MESSAGE_REPLAYED_ID;
                newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SAVED;
                AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                ExtractedStrings.MESSAGE_REPLAYED_ID="";
                ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";
                Chat_Activity_Status.isNewImageMessageSent=true;
            }
        }catch (Exception e){
            Toast.makeText(context, "In Save image \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public  void uploadApkMessages(final String fileName,final String ApkPackageName){
        try {
            final Uri uri=Uri.fromFile(new File(ApkPath));
            FirebaseStorage.getInstance().getReference().child(Firebase_data_path.MessageApkStoragePath(user_id,ExtractedStrings.UID,fileName)).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUrl=taskSnapshot.getDownloadUrl().toString();
                    Message newMessageSQ = new Message();
                    newMessageSQ.msgType=ExtractedStrings.ITEM_MESSAGE_APK_TYPE;
                    newMessageSQ.idRoom=ExtractedStrings.UID+user_id;
                    newMessageSQ.idSender = ExtractedStrings.UID;
                    newMessageSQ.idReceiver =user_id;
                    newMessageSQ.text = ApkPackageName;
                    newMessageSQ.timestamp = System.currentTimeMillis();
                    newMessageSQ.PhotoDeviceUrl="-";
                    newMessageSQ.PhotoStringBase64= new ApkInfoExtractor(context).GetAppName(ApkPackageName);
                    newMessageSQ.VideoDeviceUrl="-";
                    newMessageSQ.VoiceDeviceUrl="-";
                    newMessageSQ.DocumentDeviceUrl=ApkPath;
                    newMessageSQ.AnyMediaUrl= downloadUrl;
                    newMessageSQ.AnyMediaStatus=ExtractedStrings.MEDIA_UPLOADED;
                    newMessageSQ.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                    newMessageSQ.msg_reprayed_id=ExtractedStrings.MESSAGE_REPLAYED_ID;
                    newMessageSQ.messageStatus=ExtractedStrings.MESSAGE_STATUS_SENT;
                    AllChatsDB.getInstance(context).updateChats(context,newMessageSQ,msgID);
                    FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(user_id)).child(msgID).setValue(newMessageSQ);
                    chat_activity.UpdateAdapterChats(true);
                    mApkProcessProgressBar.setVisibility(View.GONE);
                    mApkProgressBar.setVisibility(View.GONE);
                    mApkUploadIcon.setVisibility(View.GONE);
                    ExtractedStrings.MESSAGE_REPLAYED_ID="";
                    ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    int currentprogress = (int) progress;
                    mApkProcessProgressBar.setProgress(currentprogress);
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mApkProcessProgressBar.setVisibility(View.GONE);
                    mApkProgressBar.setVisibility(View.GONE);
                    mApkUploadIcon.setVisibility(View.VISIBLE);
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception r){
            Toast.makeText(context, "In Uploading"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
