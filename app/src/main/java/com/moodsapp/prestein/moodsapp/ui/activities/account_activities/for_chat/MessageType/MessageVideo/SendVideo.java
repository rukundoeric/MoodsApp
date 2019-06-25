package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage1kb;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageCroping;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.FileInputOutPutStream;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER on 3/16/2018.
 */

public class SendVideo {
    private Message message;
    private LinearLayout mPlayVideoBack;
    private ImageView mCancelUploadButton;
    private Chat_Activity chat_activity;
    private Context context;
    private  String filePath;
    private String user_id;
    private String msgID ;
    private ImageView UploadIcon;
    private ProgressBar UploadLoading;
    private RelativeLayout UploadIconsImageBack;
    private CircleProgressBar mProcessProgressLoading;
    public SendVideo(Context context) {
        this.context = context;
    }
    public SendVideo(Chat_Activity chat_activity, Context context,Message message /*String videoPath, String user_id, String msgId*/, ImageView mUploadIconImage, ProgressBar mProgressLoading, RelativeLayout mUploadIconsImageBack, CircleProgressBar mProcessProgressLoading, ImageView mCancelUploadButton, LinearLayout mPlayVideoBack) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.filePath = message.AnyMediaUrl;
        this.message=message;
        this.user_id = user_id;
        this.msgID = message.msgId;
        this.UploadIcon = mUploadIconImage;
        this.UploadLoading = mProgressLoading;
        this.UploadIconsImageBack=mUploadIconsImageBack;
        this.mProcessProgressLoading=mProcessProgressLoading;
        this.mCancelUploadButton=mCancelUploadButton;
        this.mPlayVideoBack=mPlayVideoBack;
    }
    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(context);
        }
        protected void onPostExecute(Boolean result) {
            // Toast.makeText(context, String.valueOf(result), Toast.LENGTH_SHORT).show();
            if (result){
                UploadIconsImageBack.setVisibility(View.VISIBLE);
                UploadLoading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.GONE);
                mProcessProgressLoading.setVisibility(View.VISIBLE);
                mCancelUploadButton.setVisibility(View.VISIBLE);
                mPlayVideoBack.setVisibility(View.GONE);
                mProcessProgressLoading.setIndeterminate(false);
                mProcessProgressLoading.setMax(100);
                uploadVideoMessages();
            }else {

                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);

                mPlayVideoBack.setVisibility(View.VISIBLE);
                UploadIconsImageBack.setVisibility(View.VISIBLE);
                mProcessProgressLoading.setVisibility(View.GONE);
                mCancelUploadButton.setVisibility(View.GONE);
                UploadLoading.setVisibility(View.GONE);
                UploadIcon.setVisibility(View.VISIBLE);
            }
        }
    }
    public void checkBeforeUpload(final Context context){
        SendVideo.TaskIsInternetAvailable taskIsInternetAvailable=new SendVideo.TaskIsInternetAvailable();
        taskIsInternetAvailable.execute();

    }
    public void saveVideoMessage(String User_id,ArrayList<String> imagePaths){
        try {
            for (int i=0;i<imagePaths.size();i++) {
                String finalFilePath = imagePaths.get(i);
                if (finalFilePath != null) {
                    String msgId = user_id+ ExtractedStrings.UID+String.valueOf(System.currentTimeMillis());
                    Message newMessage = new Message();
                    newMessage.msgId = msgId;
                    newMessage.msgType = ExtractedStrings.ITEM_MESSAGE_VIDEO_TYPE;
                    newMessage.idRoom = ExtractedStrings.UID + User_id;
                    newMessage.idSender = ExtractedStrings.UID;
                    newMessage.idReceiver = User_id;
                    newMessage.text = "-";
                    newMessage.timestamp = System.currentTimeMillis();
                    newMessage.PhotoDeviceUrl = "-";
                    newMessage.PhotoStringBase64 = "-";
                    newMessage.VideoDeviceUrl = finalFilePath;
                    newMessage.VoiceDeviceUrl = "-";
                    newMessage.DocumentDeviceUrl = "-";
                    newMessage.AnyMediaUrl = "-";
                    newMessage.AnyMediaStatus = ExtractedStrings.MEDIA_NOT_UPLOADED;
                    newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                    newMessage.msg_reprayed_id = ExtractedStrings.MESSAGE_REPLAYED_ID;
                    newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SAVED;
                    AllChatsDB.getInstance(context).CkeckBeforeAddMessage(context, newMessage, false);
                    new PlaySound(context, R.raw.messagesended);
                    ExtractedStrings.MESSAGE_REPLAYED_ID = "";
                    ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT = "";
                    Chat_Activity_Status.isNewImageMessageSent = true;
                }
            }
        }catch (Exception e){
            Toast.makeText(this.context, "In Save image \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public  void uploadVideoMessages(){
        try {
            final Uri uri=Uri.fromFile(new File(filePath));
            FirebaseStorage.getInstance().getReference().child(Firebase_data_path.MessageVideoStoragePath(user_id,ExtractedStrings.UID)).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                  final Uri thundernail=Uri.fromFile(new File(new CompressingImage1kb(String.valueOf(System.currentTimeMillis()),context).compressImage(createVideoThumbernail().toString(),"",true)));
                  FirebaseStorage.getInstance().getReference().child(Firebase_data_path.MessageVideoStoragePath(user_id,ExtractedStrings.UID)).putFile(thundernail).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot1) {
                          String downloadUrl=taskSnapshot.getDownloadUrl().toString();
                          String downloadUr2=taskSnapshot1.getDownloadUrl().toString();
                          Message newMessageSQ = new Message();
                          newMessageSQ.msgType=ExtractedStrings.ITEM_MESSAGE_VIDEO_TYPE;
                          newMessageSQ.idRoom=ExtractedStrings.UID+user_id;
                          newMessageSQ.idSender = ExtractedStrings.UID;
                          newMessageSQ.idReceiver =user_id;
                          newMessageSQ.text = "-";
                          newMessageSQ.timestamp = System.currentTimeMillis();
                          newMessageSQ.PhotoDeviceUrl="-";
                          newMessageSQ.PhotoStringBase64= downloadUr2;
                          newMessageSQ.VideoDeviceUrl=filePath;
                          newMessageSQ.VoiceDeviceUrl="-";
                          newMessageSQ.DocumentDeviceUrl="-";
                          newMessageSQ.AnyMediaUrl= downloadUrl;
                          newMessageSQ.AnyMediaStatus=ExtractedStrings.MEDIA_UPLOADED;
                          newMessageSQ.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                          newMessageSQ.msg_reprayed_id=ExtractedStrings.MESSAGE_REPLAYED_ID;
                          newMessageSQ.messageStatus=ExtractedStrings.MESSAGE_STATUS_SENT;
                          AllChatsDB.getInstance(context).updateChats(context,newMessageSQ,msgID);
                          newMessageSQ.AnyMediaStatus=ExtractedStrings.MEDIA_NOT_DOWNLOADED;
                          FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(user_id)).child(msgID).setValue(newMessageSQ);
                          //chat_activity.UpdateAdapterChats();
                          mPlayVideoBack.setVisibility(View.VISIBLE);
                          UploadIconsImageBack.setVisibility(View.GONE);
                          UploadLoading.setVisibility(View.GONE);
                          UploadIcon.setVisibility(View.GONE);
                          ExtractedStrings.MESSAGE_REPLAYED_ID="";
                          ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";
                          File f=  new File(new CompressingImage1kb(context).getRealPathFromURI(thundernail.toString()));
                          if (f.exists()){
                              f.delete();
                          }
                      }
                  });

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
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    UploadIconsImageBack.setVisibility(View.VISIBLE);
                    UploadLoading.setVisibility(View.GONE);
                    UploadIcon.setVisibility(View.VISIBLE);
                    mProcessProgressLoading.setVisibility(View.GONE);
                    mProcessProgressLoading.setVisibility(View.GONE);
                    mCancelUploadButton.setVisibility(View.GONE);
                    mPlayVideoBack.setVisibility(View.VISIBLE);
                }
            });

        }catch (Exception r){
            Toast.makeText(context, "In Uploading"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private Uri createVideoThumbernail(){
        File file=new File(new getFileName("ThurberNail_"+message.msgId,context).getImageFileName("",true));
        Bitmap bitmap=getBitmapVideoThunderNail();
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0,bos);
        byte[] bitmapdata=bos.toByteArray();
        try {
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }
    private Bitmap getBitmapVideoThunderNail(){
        String VideoPath=message.VideoDeviceUrl;
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(VideoPath,   MediaStore.Images.Thumbnails.MINI_KIND);
        Matrix matrix = new Matrix();
        Bitmap bmThumbnail = Bitmap.createBitmap(thumb, 0, 0,thumb.getWidth(), thumb.getHeight(), matrix, true);
        bmThumbnail= ImageCroping.cropToSquare(bmThumbnail);
        return bmThumbnail;
    }
    public void copyVideoToMoodsAppFolder(Context context,String userId,String sourcePath){
        String destinationPath=getFilename();
        File source=new File(sourcePath);
        File destination=new File(destinationPath);
        try {
        if (source.exists()){
            InputStream in=new FileInputStream(source);
            OutputStream out=new FileOutputStream(destination);
            byte[] buf=new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            in.close();
            out.close();
           // saveVideoMessage(context,userId,destinationPath);
        }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    public String getFilename()
    {
        String FILE_NAME="";
        String IMAGE_FINAL_PATH="";
        try {
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath+= Data_Storage_Path.VIDEO_MESSAGE_SENT;

            if (new File(filePath).exists())
            {
                File[] list=new File(filePath).listFiles();
                int count=0;
                for (File f: list){
                    String name=f.getName();
                    if (name.endsWith(".mp4")){
                        count++;
                    }
                }

                FILE_NAME="VID_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MOODSAPP"+(count)+".mp4";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                FILE_NAME="VID_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MOODSAPP"+".mp4";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(context, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return IMAGE_FINAL_PATH;
    }
}
