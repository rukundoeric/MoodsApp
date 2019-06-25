package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
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

public class ReceiveImage {
    private Chat_Activity chat_activity;
    private ImageView mCancelDownloadButton;
    private Context context;
    private String imageUrl;
    private String RoomId;
    private String SenderId;
    private String ReceiverId;
    private String msgId;
    private RelativeLayout LoadingBack;
    private ImageView DownLoadButton;
    private CircleProgressBar looding;
    private ProgressBar interCheckPro;
    double file_size=0;
    private String FileName="";



    public ReceiveImage(Chat_Activity chat_activity, Context context, String imageUrl, String idRoom, String idSender, String idReceiver, String msgId, RelativeLayout mLayoutDownloadImageBack, ImageView mDownloadIconImage, CircleProgressBar mProgressLoading, ImageView mCancelDownloadButton, ProgressBar interCheckPro) {
        this.chat_activity=chat_activity;
        this.context = context;
        this.imageUrl = imageUrl;
        this.RoomId=idRoom;
        this.SenderId = idSender;
        this.ReceiverId = idReceiver;
        this.msgId = msgId;
        LoadingBack = mLayoutDownloadImageBack;
        DownLoadButton = mDownloadIconImage;
        this.interCheckPro=interCheckPro;
        this.looding = mProgressLoading;
        this.mCancelDownloadButton=mCancelDownloadButton;
        this.FileName=getFilename();
    }
    public void startDownloadTask(){
        LoadingBack.setVisibility(View.VISIBLE);
        DownLoadButton.setVisibility(View.GONE);
        interCheckPro.setVisibility(View.VISIBLE);
        looding.setVisibility(View.VISIBLE);
        mCancelDownloadButton.setVisibility(View.VISIBLE);
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
                LoadingBack.setVisibility(View.VISIBLE);
                DownLoadButton.setVisibility(View.VISIBLE);
                interCheckPro.setVisibility(View.GONE);
                looding.setVisibility(View.GONE);
                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }

    public void downLoadImage(){
        try{
            LoadingBack.setVisibility(View.VISIBLE);
            DownLoadButton.setVisibility(View.GONE);
            interCheckPro.setVisibility(View.VISIBLE);
            looding.setVisibility(View.VISIBLE);
            mCancelDownloadButton.setVisibility(View.VISIBLE);
            new DawnloadTask().execute(imageUrl);
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
            LoadingBack.setVisibility(View.VISIBLE);
            DownLoadButton.setVisibility(View.GONE);
            looding.setIndeterminate(true);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            looding.setIndeterminate(false);
            looding.setMax(100);
            looding.setProgress(values[0]);
            // looding.setTooltipText(new DecimalFormat("%").format(file_size / 1000000));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Toast.makeText(context, "Error in on post Execute: "+s, Toast.LENGTH_SHORT).show();
            }else {
                FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl).delete();
                LoadingBack.setVisibility(View.GONE);
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
            filePath+= Data_Storage_Path.IMAGE_MESSAGE_RECEIVED;

            if (new File(filePath).exists())
            {
                File[] list=new File(filePath).listFiles();
                int count=0;
                for (File f: list){
                    String name=f.getName();
                    if (name.endsWith(".jpg") || name.endsWith(".png")){
                        count++;
                    }
                }

                FILE_NAME="IMG_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MoodsApp"+(count)+".jpg";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                FILE_NAME="IMG_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MoodsApp"+".jpg";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(context, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return IMAGE_FINAL_PATH;
    }
    private void UpdateMessage(String outPutPath){
        Message newMessageSQ = new Message();
        newMessageSQ.msgType= ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE;
        newMessageSQ.idRoom=RoomId;
        newMessageSQ.idSender =SenderId;
        newMessageSQ.idReceiver =ReceiverId;
        newMessageSQ.text = "-";
        newMessageSQ.timestamp = System.currentTimeMillis();
        newMessageSQ.PhotoDeviceUrl=outPutPath;
        newMessageSQ.PhotoStringBase64= "-";
        newMessageSQ.VideoDeviceUrl="-";
        newMessageSQ.VoiceDeviceUrl="-";
        newMessageSQ.DocumentDeviceUrl="-";
        newMessageSQ.AnyMediaUrl= imageUrl;
        newMessageSQ.AnyMediaStatus= ExtractedStrings.MEDIA_DOWNLOADED;
        newMessageSQ.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
        newMessageSQ.msg_reprayed_id= ExtractedStrings.MESSAGE_REPLAYED_ID;
        AllChatsDB.getInstance(context).updateChats(context,newMessageSQ,msgId);
        chat_activity.UpdateAdapterChats(true);
    }
}
