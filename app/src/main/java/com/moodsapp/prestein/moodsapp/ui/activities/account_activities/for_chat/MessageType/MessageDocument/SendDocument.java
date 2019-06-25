package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument;

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
import com.moodsapp.prestein.moodsapp.util.AudioUtils.PlaySound;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.FileInputOutPutStream;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.SubstringUtils;

import java.io.File;
import java.util.ArrayList;


public class SendDocument {
    private Message message;
    private Context context;
    private Chat_Activity chat_activity;
    private ProgressBar mDocProgressBar;
    private CircleProgressBar mDocProcessProgressBar;
    private ImageView mDocUploadIcon;
    private ArrayList<CharSequence> idFriends;
    public SendDocument(Context context) {
        this.context = context;
    }
    public SendDocument(Chat_Activity chat_activity, Context context, Message message,ArrayList<CharSequence>  idFriends,ImageView mDocUploadIcon, ProgressBar mDocLoading, CircleProgressBar mDocProcessProgressLoading) {
        this.chat_activity=chat_activity;
        this.context=context;
        this.message=message;
        this.idFriends=idFriends;
        this.mDocUploadIcon=mDocUploadIcon;
        this.mDocProgressBar=mDocLoading;
        this.mDocProcessProgressBar=mDocProcessProgressLoading;

    }

    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(context);
        }
        protected void onPostExecute(Boolean result) {
            // Toast.makeText(context, String.valueOf(result), Toast.LENGTH_SHORT).show();
            if (result){
                mDocProgressBar.setVisibility(View.GONE);
                mDocUploadIcon.setVisibility(View.GONE);
                mDocProcessProgressBar.setVisibility(View.VISIBLE);
                mDocProcessProgressBar.setIndeterminate(false);
                mDocProcessProgressBar.setMax(100);
                uploadDocMessages();
            }else {
                mDocProgressBar.setVisibility(View.GONE);
                mDocUploadIcon.setVisibility(View.VISIBLE);
                mDocProcessProgressBar.setVisibility(View.GONE);
                mDocProcessProgressBar.setVisibility(View.GONE);
                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);

            }
        }
    }
    public void checkBeforeUpload(final Context context){
        TaskIsInternetAvailable taskIsInternetAvailable=new TaskIsInternetAvailable();
        taskIsInternetAvailable.execute();

    }
    public void saveDocMessage(String User_id,ArrayList<String> documentsPath){

            for (int i=0;i<documentsPath.size();i++) {
                String docPath = documentsPath.get(i);
                String extension=new SubstringUtils().getSubStringByChar('.',new File(docPath).getName(),true);
                String size= FileInputOutPutStream.getDocumentSize(new File(docPath));
                String name= new SubstringUtils().getSubStringByChar('.',new File(docPath).getName(),false);
               /* Bitmap bitmap= new BitmapDrawable(context.getResources(),new DocumentConverter(context).pdfToBitmap(new File(docPath))).getBitmap();
                String screenShort= ConvertImage.From_Bitmap_to_StringDefault1(context, bitmap,100,100);
*/

                String msgId = User_id + ExtractedStrings.UID + String.valueOf(System.currentTimeMillis());
                Message newMessage = new Message();
                newMessage.msgId = msgId;
                newMessage.msgType = ExtractedStrings.ITEM_MESSAGE_DOCUMENT_TYPE;
                newMessage.idRoom = ExtractedStrings.UID + User_id;
                newMessage.idSender = ExtractedStrings.UID;
                newMessage.idReceiver = User_id;
                newMessage.text = extension.toLowerCase();
                newMessage.timestamp = System.currentTimeMillis();
                newMessage.PhotoDeviceUrl = size.toLowerCase();
                newMessage.PhotoStringBase64 = "-";
                newMessage.VideoDeviceUrl = name;
                newMessage.VoiceDeviceUrl = "-";
                newMessage.DocumentDeviceUrl = docPath;
                newMessage.AnyMediaUrl = "-";
                newMessage.AnyMediaStatus = ExtractedStrings.MEDIA_NOT_UPLOADED;
                newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                newMessage.msg_reprayed_id = ExtractedStrings.MESSAGE_REPLAYED_ID;
                newMessage.messageStatus = ExtractedStrings.MESSAGE_STATUS_SAVED;
                AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context, newMessage, false);
                new PlaySound(context, R.raw.messagesended);
                ExtractedStrings.MESSAGE_REPLAYED_ID = "";
                ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT = "";

            }
            Chat_Activity_Status.isNewImageMessageSent=true;

    }
/*    public void saveDocMessage(String User_id,Uri documentPath){
        try {
            for (int i=0;i<imagePaths.size();i++){
                Uri uri=Uri.fromFile(new File(imagePaths.get(i)));
                String finalFilePath= new CompressingImage(context).compressImage(String.valueOf(uri), Data_Storage_Path.IMAGE_MESSAGE_SENT);
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
    *//*    try {
            String mediaPath= new ConvertTypeMedia().getUriRealPath(context,documentPath);
            String extension=SubstringUtils.getSubStringFromLast(context,'.',mediaPath);

            if (new File(mediaPath).exists()){
                Toast.makeText(context, "Exist", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Not exist "+mediaPath, Toast.LENGTH_SHORT).show();
            }

            String intentType=new IntentTypeString().getIntentTypeByExtension(new IntentTypeString().getIntentType(),extension);
            if (intentType.equals("") || intentType.equals(null)){
                Toast.makeText(context, "The file you selected is not acceptable!", Toast.LENGTH_SHORT).show();
            }else {
                if (new File(mediaPath).exists()){
                    String size=FileInputOutPutStream.getApkSize(new File(mediaPath));
                    String details=extension+" "+size;
                    if (mediaPath!=null) {
                        String msgId=String.valueOf(System.currentTimeMillis());
                        Message newMessage = new Message();
                        newMessage.msgId=msgId;
                        newMessage.msgType= ExtractedStrings.ITEM_MESSAGE_APK_TYPE;
                        newMessage.idRoom=ExtractedStrings.UID+User_id;
                        newMessage.idSender = ExtractedStrings.UID;
                        newMessage.idReceiver = User_id;
                        newMessage.text =details;
                        newMessage.timestamp = System.currentTimeMillis();
                        newMessage.PhotoDeviceUrl= "-";
                        newMessage.PhotoStringBase64="-";
                        newMessage.VideoDeviceUrl="-";
                        newMessage.VoiceDeviceUrl="-";
                        newMessage.DocumentDeviceUrl=mediaPath;
                        newMessage.AnyMediaUrl="-";
                        newMessage.AnyMediaStatus=ExtractedStrings.MEDIA_NOT_UPLOADED;
                        newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                        newMessage.msg_reprayed_id=ExtractedStrings.MESSAGE_REPLAYED_ID;
                        AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context,newMessage,User_id,msgId);
                        ExtractedStrings.MESSAGE_REPLAYED_ID="";
                        ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";
                        Chat_Activity_Status.isNewImageMessageSent=true;
                    }
                }else {
                    Toast.makeText(context, "Media not exist", Toast.LENGTH_SHORT).show();
                }
            }


        }catch (Exception e){
            Toast.makeText(context, "In Save image \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }*//*
    }*/
    public  void uploadDocMessages(){
        try {
            final String DocPath=message.DocumentDeviceUrl;
            final String user_id=message.idReceiver;
            final String docName=message.VideoDeviceUrl;
            final Uri uri=Uri.fromFile(new File(DocPath));
            FirebaseStorage.getInstance().getReference().child(Firebase_data_path.MessageDocumentStoragePath(user_id,ExtractedStrings.UID)).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUrl=taskSnapshot.getDownloadUrl().toString();
                    Message newMessageSQ = new Message();
                    newMessageSQ.msgType=ExtractedStrings.ITEM_MESSAGE_DOCUMENT_TYPE;
                    newMessageSQ.idRoom=ExtractedStrings.UID+user_id;
                    newMessageSQ.idSender = ExtractedStrings.UID;
                    newMessageSQ.idReceiver =user_id;
                    newMessageSQ.text = message.text;
                    newMessageSQ.timestamp = System.currentTimeMillis();
                    newMessageSQ.PhotoDeviceUrl=message.PhotoDeviceUrl;
                    newMessageSQ.PhotoStringBase64="-";
                    newMessageSQ.VideoDeviceUrl=docName;
                    newMessageSQ.VoiceDeviceUrl="-";
                    newMessageSQ.DocumentDeviceUrl=DocPath;
                    newMessageSQ.AnyMediaUrl= downloadUrl;
                    newMessageSQ.AnyMediaStatus=ExtractedStrings.MEDIA_UPLOADED;
                    newMessageSQ.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                    newMessageSQ.msg_reprayed_id=ExtractedStrings.MESSAGE_REPLAYED_ID;
                    newMessageSQ.messageStatus=ExtractedStrings.MESSAGE_STATUS_SENT;
                    AllChatsDB.getInstance(context).updateChats(context,newMessageSQ,message.msgId);
                    for (CharSequence idFre:idFriends){
                        FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(idFre.toString())).push().setValue(newMessageSQ);
                    }
                    chat_activity.UpdateAdapterChats(true);
                    mDocProcessProgressBar.setVisibility(View.GONE);
                    mDocProgressBar.setVisibility(View.GONE);
                    mDocUploadIcon.setVisibility(View.GONE);
                    ExtractedStrings.MESSAGE_REPLAYED_ID="";
                    ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    int currentprogress = (int) progress;
                    mDocProcessProgressBar.setProgress(currentprogress);
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDocProcessProgressBar.setVisibility(View.GONE);
                    mDocProgressBar.setVisibility(View.GONE);
                    mDocUploadIcon.setVisibility(View.VISIBLE);
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception r){
            Toast.makeText(context, "In Uploading"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
