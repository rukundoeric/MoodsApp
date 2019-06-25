package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.storage.FirebaseStorage;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eric prestein on 1/24/2018.
 */

public class ReceiveVideo {
    private Chat_Activity chat_activity;
    private ImageView mCancelDownloadVideo;
    private String FileName;
    private Context context;
    private String videoUrl;
    private String idRoom;
    private String idSender;
    private String idReceiver;
    private String msgId;
    private RelativeLayout mLayoutDownloadContentBack;
    private ImageView mDownloadIconVideo;
    private CircleProgressBar mProccessProgressLoading;
    private ProgressBar mInterCheckProgressLoading;
    private LinearLayout mLayoutPlayVideoBack;
    private ImageView mPlayVideoButton;
    private Message message;
    private int file_size;

    public ReceiveVideo(Chat_Activity chat_activity, Context context, Message message, RelativeLayout mLayoutDownloadContentBack, ImageView mDownloadIconVideo, ImageView mCancelDownloadVideo, CircleProgressBar mProccessProgressLoading, ProgressBar mInterCheckProgressLoading, LinearLayout mLayoutPlayVideoBack, ImageView mPlayVideoButton) {

        this.chat_activity=chat_activity;
        this.context = context;
        this.videoUrl = message.AnyMediaUrl;
        this.idRoom = message.idRoom;
        this.idSender = message.idSender;
        this.idReceiver = message.idReceiver;
        this.msgId = message.msgId;
        this.message=message;
        this.mLayoutDownloadContentBack = mLayoutDownloadContentBack;
        this.mDownloadIconVideo = mDownloadIconVideo;
        this.mCancelDownloadVideo=mCancelDownloadVideo;
        this.mProccessProgressLoading = mProccessProgressLoading;
        this.mInterCheckProgressLoading = mInterCheckProgressLoading;
        this.mLayoutPlayVideoBack = mLayoutPlayVideoBack;
        this.mPlayVideoButton = mPlayVideoButton;
        this.FileName=getFilename();
    }

    public void startDownloadTask(){
        mLayoutDownloadContentBack.setVisibility(View.VISIBLE);
        mDownloadIconVideo.setVisibility(View.GONE);
        mProccessProgressLoading.setVisibility(View.GONE);
        mInterCheckProgressLoading.setVisibility(View.VISIBLE);
        mLayoutPlayVideoBack.setVisibility(View.GONE);
        mPlayVideoButton.setVisibility(View.GONE);
        mCancelDownloadVideo.setVisibility(View.VISIBLE);
        new TaskIsInternetAvailable().execute();
    }
    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(context);
        }
        protected void onPostExecute(Boolean result) {
            if (result){
                downLoadImage();
            }else {
                mLayoutDownloadContentBack.setVisibility(View.VISIBLE);
                mDownloadIconVideo.setVisibility(View.VISIBLE);
                mProccessProgressLoading.setVisibility(View.GONE);
                mInterCheckProgressLoading.setVisibility(View.GONE);
                mLayoutPlayVideoBack.setVisibility(View.GONE);
                mPlayVideoButton.setVisibility(View.GONE);
                mCancelDownloadVideo.setVisibility(View.GONE);
                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }

    public void downLoadImage(){
        try{
            mLayoutDownloadContentBack.setVisibility(View.VISIBLE);
            mDownloadIconVideo.setVisibility(View.GONE);
            mProccessProgressLoading.setVisibility(View.VISIBLE);
            mInterCheckProgressLoading.setVisibility(View.VISIBLE);
            mLayoutPlayVideoBack.setVisibility(View.GONE);
            mPlayVideoButton.setVisibility(View.GONE);
            mCancelDownloadVideo.setVisibility(View.VISIBLE);
            new DawnloadTask().execute(videoUrl);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Can't create a folder", Toast.LENGTH_SHORT).show();
        }
    }

    private class DawnloadTask extends AsyncTask<String,Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                InputStream input=null;
                OutputStream output=null;
                HttpURLConnection connection=null;
                try {
                    URL url=new URL(strings[0]);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.connect();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                        return "Sever returned HTTP "+connection.getResponseCode()+ " "
                                + connection.getResponseMessage();
                    }
                    int fileLenght=connection.getContentLength();
                    file_size=fileLenght;
                    input=connection.getInputStream();
                    output=new FileOutputStream(FileName);
                    byte data[]=new byte[4096];
                    long total=0;
                    int count;
                    while((count = input.read(data)) != -1){
                        if (isCancelled()){
                            return null;
                        }
                        total +=count;
                        if (fileLenght > 0) {
                            publishProgress((int) (total * 100 /fileLenght));
                        }
                        output.write(data,0,count);
                    }
                }catch (Exception e){
                    return e.toString();
                }finally {
                    try{
                        if (output !=null){
                            output.close();
                        }
                        if (input!=null){
                            input.close();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if (connection !=null){
                        connection.disconnect();
                    }
                }
            }finally {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLayoutDownloadContentBack.setVisibility(View.VISIBLE);
            mCancelDownloadVideo.setVisibility(View.VISIBLE);
            mDownloadIconVideo.setVisibility(View.GONE);
            mProccessProgressLoading.setVisibility(View.VISIBLE);
            mInterCheckProgressLoading.setVisibility(View.VISIBLE);
            mLayoutPlayVideoBack.setVisibility(View.GONE);
            mPlayVideoButton.setVisibility(View.GONE);
            mProccessProgressLoading.setIndeterminate(true);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProccessProgressLoading.setIndeterminate(false);
            mProccessProgressLoading.setMax(100);
            mProccessProgressLoading.setProgress(values[0]);
            // looding.setTooltipText(new DecimalFormat("%").format(file_size / 1000000));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Toast.makeText(context, "Download Error! : "+s, Toast.LENGTH_SHORT).show();
            }else {
                FirebaseStorage.getInstance().getReferenceFromUrl(message.PhotoStringBase64).delete();
                mLayoutDownloadContentBack.setVisibility(View.GONE);
                UpdateMessage(FileName);
            }
        }
    }
    public String getFilename()
    {
        String FILE_NAME="";
        String IMAGE_FINAL_PATH="";
        try {
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath+= Data_Storage_Path.VIDEO_MESSAGE_RECEIVED;

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

                FILE_NAME="VID_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MoodsApp"+(count)+".mp4";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                FILE_NAME="VID_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MoodsApp"+".mp4";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(context, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return IMAGE_FINAL_PATH;
    }
    private void UpdateMessage(String outPutPath){
        Message newMessageSQ = new Message();
        newMessageSQ.msgType= ExtractedStrings.ITEM_MESSAGE_VIDEO_TYPE;
        newMessageSQ.idRoom=idRoom;
        newMessageSQ.idSender =idSender;
        newMessageSQ.idReceiver =idReceiver;
        newMessageSQ.text = "-";
        newMessageSQ.timestamp = System.currentTimeMillis();
        newMessageSQ.PhotoDeviceUrl="-";
        newMessageSQ.PhotoStringBase64= "-";
        newMessageSQ.VideoDeviceUrl=outPutPath;
        newMessageSQ.VoiceDeviceUrl="-";
        newMessageSQ.DocumentDeviceUrl="-";
        newMessageSQ.AnyMediaUrl= videoUrl;
        newMessageSQ.AnyMediaStatus=ExtractedStrings.MEDIA_DOWNLOADED;
        newMessageSQ.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
        newMessageSQ.msg_reprayed_id=ExtractedStrings.MESSAGE_REPLAYED_ID;
        AllChatsDB.getInstance(context).updateChats(context,newMessageSQ,msgId);
        chat_activity.UpdateAdapterChats(true);
    }
}
