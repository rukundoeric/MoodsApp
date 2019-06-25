package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.AudioUtils.PlaySound;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage1kb;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Eric prestein on 1/24/2018.
 */

public class SendImage {

    private ImageView CancelUpload;
    private Chat_Activity chat_activity;
    private Context context;
    private  String filePath;
    private String user_id;
    private String msgID ;
    private RelativeLayout UploadBack ;
    private LinearLayout UploadIcon;
    private ProgressBar UploadLoading;
    private CircleProgressBar mProcessProgressLoading;
    private UploadTask uploadImageTask;
    private TaskIsInternetAvailable taskIsInternetAvailable;

    public SendImage(Context context) {
        this.context = context;
    }
    public class TaskObject{
        public int position;
        public UploadTask uploadTask;
        public TaskIsInternetAvailable isInternetAvailable;
    }
    public SendImage(Chat_Activity chat_activity, Context context, String imagePath, String user_id, String msgId, RelativeLayout mLayoutUplodImageBack, LinearLayout mUploadIconImage, ProgressBar mProgressLoading, CircleProgressBar mProcessProgressLoading, ImageView mCancelUploadButton) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.filePath = imagePath;
        this.user_id = user_id;
        this.msgID = msgId;
        this.mProcessProgressLoading=mProcessProgressLoading;
        this.UploadBack = mLayoutUplodImageBack;
        this.UploadIcon = mUploadIconImage;
        this.UploadLoading = mProgressLoading;
        this.CancelUpload=mCancelUploadButton;
    }
    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(context);
        }
        protected void onPostExecute(Boolean result) {
           // Toast.makeText(context, String.valueOf(result), Toast.LENGTH_SHORT).show();
            if (result){
                UploadLoading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.GONE);
                UploadBack.setVisibility(View.VISIBLE);
                mProcessProgressLoading.setVisibility(View.VISIBLE);
                CancelUpload.setVisibility(View.VISIBLE);
                mProcessProgressLoading.setIndeterminate(false);
                mProcessProgressLoading.setMax(100);
                uploadImageMessages();
            }else {
                UploadLoading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.VISIBLE);
                UploadBack.setVisibility(View.VISIBLE);
                mProcessProgressLoading.setVisibility(View.GONE);
                CancelUpload.setVisibility(View.GONE);
                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);

            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            UploadLoading.setVisibility(View.GONE);
            UploadIcon.setVisibility(View.VISIBLE);
            UploadBack.setVisibility(View.VISIBLE);
            mProcessProgressLoading.setVisibility(View.GONE);
            CancelUpload.setVisibility(View.GONE);
        }
    }
    public void checkBeforeUpload(){
            taskIsInternetAvailable=new TaskIsInternetAvailable();
            taskIsInternetAvailable.execute();
    }
    public void cancelUpload(){
        if (uploadImageTask!=null){
            uploadImageTask.cancel();
            UploadLoading.setVisibility(View.GONE);
            UploadIcon.setVisibility(View.VISIBLE);
            UploadBack.setVisibility(View.VISIBLE);
            mProcessProgressLoading.setVisibility(View.GONE);
            CancelUpload.setVisibility(View.GONE);
        }
        else {
            if (taskIsInternetAvailable!=null){
                taskIsInternetAvailable.cancel(true);
                UploadLoading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.VISIBLE);
                UploadBack.setVisibility(View.VISIBLE);
                mProcessProgressLoading.setVisibility(View.GONE);
                CancelUpload.setVisibility(View.GONE);
            }else {
                UploadLoading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.VISIBLE);
                UploadBack.setVisibility(View.VISIBLE);
                mProcessProgressLoading.setVisibility(View.GONE);
                CancelUpload.setVisibility(View.GONE);
            }

        }
    }
    public void seveImageMessage(String User_id,ArrayList<String> imagePaths){
        try {
            for (int i=0;i<imagePaths.size();i++){
                Uri uri=Uri.fromFile(new File(imagePaths.get(i)));
                String finalFilePath= new CompressingImage(context).compressImage(String.valueOf(uri), Data_Storage_Path.IMAGE_MESSAGE_SENT,false);
                if (finalFilePath!=null) {
                    String msgId = user_id+ ExtractedStrings.UID+String.valueOf(System.currentTimeMillis());
                    Message newMessage = new Message();
                    newMessage.msgId=msgId;
                    newMessage.msgType= ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE;
                    newMessage.idRoom= ExtractedStrings.UID+User_id;
                    newMessage.idSender = ExtractedStrings.UID;
                    newMessage.idReceiver = User_id;
                    newMessage.text ="-";
                    newMessage.timestamp = System.currentTimeMillis();
                    newMessage.PhotoDeviceUrl= finalFilePath;
                    newMessage.PhotoStringBase64="-";
                    newMessage.VideoDeviceUrl="-";
                    newMessage.VoiceDeviceUrl="-";
                    newMessage.DocumentDeviceUrl="-";
                    newMessage.AnyMediaUrl="-";
                    newMessage.AnyMediaStatus= ExtractedStrings.MEDIA_NOT_UPLOADED;
                    newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                    newMessage.msg_reprayed_id= ExtractedStrings.MESSAGE_REPLAYED_ID;
                    newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SAVED;
                    AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,false);
                    new PlaySound(context, R.raw.messagesended);
                    ExtractedStrings.MESSAGE_REPLAYED_ID="";
                    ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";

                }
            }
            Chat_Activity_Status.isNewImageMessageSent=true;
        }catch (Exception e){
            Toast.makeText(context, "In Save image \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public  void uploadImageMessages(){
        try {
            final Uri uri=Uri.fromFile(new File(filePath));
            uploadImageTask=FirebaseStorage.getInstance().getReference().child(Firebase_data_path.MessageImageStoragePath(user_id,ExtractedStrings.UID)).putFile(uri);
            uploadImageTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            final Uri thumbernail=Uri.fromFile(new File(new CompressingImage1kb(String.valueOf(System.currentTimeMillis()),context).compressImage(String.valueOf(uri),"",true)));
                            FirebaseStorage.getInstance().getReference().child(Firebase_data_path.MessageImageStoragePath(user_id,ExtractedStrings.UID)).putFile(thumbernail).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot1) {
                                    String downloadUrl=taskSnapshot.getDownloadUrl().toString();
                                    String downloadUrl2=taskSnapshot1.getDownloadUrl().toString();
                                    Message newMessageSQ = new Message();
                                    newMessageSQ.msgType= ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE;
                                    newMessageSQ.idRoom= ExtractedStrings.UID+user_id;
                                    newMessageSQ.idSender = ExtractedStrings.UID;
                                    newMessageSQ.idReceiver =user_id;
                                    newMessageSQ.text = "-";
                                    newMessageSQ.timestamp = System.currentTimeMillis();
                                    newMessageSQ.PhotoDeviceUrl=filePath;
                                    newMessageSQ.PhotoStringBase64= downloadUrl2;
                                    newMessageSQ.VideoDeviceUrl="-";
                                    newMessageSQ.VoiceDeviceUrl="-";
                                    newMessageSQ.DocumentDeviceUrl="-";
                                    newMessageSQ.AnyMediaUrl= downloadUrl;
                                    newMessageSQ.AnyMediaStatus= ExtractedStrings.MEDIA_UPLOADED;
                                    newMessageSQ.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                                    newMessageSQ.msg_reprayed_id= ExtractedStrings.MESSAGE_REPLAYED_ID;
                                    newMessageSQ.messageStatus=ExtractedStrings.MESSAGE_STATUS_SENT;
                                    AllChatsDB.getInstance(context).updateChats(context,newMessageSQ,msgID);
                                    newMessageSQ.AnyMediaStatus= ExtractedStrings.MEDIA_NOT_DOWNLOADED;
                                    FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(user_id)).child(msgID).setValue(newMessageSQ);
                                    chat_activity.UpdateAdapterChats(true);
                                    UploadLoading.setVisibility(View.GONE);
                                    UploadIcon.setVisibility(View.VISIBLE);
                                    UploadBack.setVisibility(View.GONE);
                                    ExtractedStrings.MESSAGE_REPLAYED_ID="";
                                    ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";
                                    File f=  new File(new CompressingImage1kb(context).getRealPathFromURI(thumbernail.toString()));
                                    if (f.exists()){
                                      f.delete();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    UploadBack.setVisibility(View.VISIBLE);
                    UploadLoading.setVisibility(View.GONE);
                    UploadIcon.setVisibility(View.VISIBLE);
                    mProcessProgressLoading.setVisibility(View.GONE);
                    CancelUpload.setVisibility(View.GONE);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            int currentprogress = (int) progress;
                            mProcessProgressLoading.setProgress(currentprogress);
                        }
                    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Upload is paused");
                        }
                    });

        }catch (Exception r){
            Toast.makeText(context, "In Uploading"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
