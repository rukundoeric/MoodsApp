package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.FileInputOutPutStream;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eric prestein on 1/24/2018.
 */

public class ReceiveApk {
    private Chat_Activity chat_activity;
    private ImageView mCancelDownloadButton;
    private Context context;
    private String apkUrl;
    private String RoomId;
    private String SenderId;
    private String ReceiverId;
    private String msgId;
    private ImageView DownLoadButton;
    private CircleProgressBar looding;
    private ProgressBar interCheckPro;
    double file_size=0;
    private String FilePath="";
    private String fileName;
    private String base64Icon;


    public ReceiveApk(Chat_Activity chat_activity, Context context, String fileName, String apkUrl, String IdRoom, String SenderId, String ReceiverId, String msgId, ImageView downLoadButton, CircleProgressBar looding, ProgressBar interCheckPro, ImageView mCancelDownloadButton, String base64Icon) {
        this.chat_activity=chat_activity;
        this.context = context;
        this.apkUrl = apkUrl;
        this.RoomId=IdRoom;
        this.SenderId = SenderId;
        this.ReceiverId = ReceiverId;
        this.msgId = msgId;
        DownLoadButton = downLoadButton;
        this.interCheckPro=interCheckPro;
        this.looding = looding;
        this.FilePath=getFilePath(context,fileName);
        this.fileName=fileName;
        this.mCancelDownloadButton=mCancelDownloadButton;
        this.base64Icon=base64Icon;
    }

    public void startDownloadTask(){
        DownLoadButton.setVisibility(View.GONE);
        interCheckPro.setVisibility(View.VISIBLE);
        looding.setVisibility(View.GONE);
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
                mCancelDownloadButton.setVisibility(View.GONE);
                DownLoadButton.setVisibility(View.VISIBLE);
                interCheckPro.setVisibility(View.GONE);
                looding.setVisibility(View.GONE);
                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }

    public void downLoadImage(){
        try{
            mCancelDownloadButton.setVisibility(View.VISIBLE);
            DownLoadButton.setVisibility(View.GONE);
            interCheckPro.setVisibility(View.GONE);
            looding.setVisibility(View.VISIBLE);
            new DawnloadTask().execute(apkUrl);
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
                    output=new FileOutputStream(FilePath);
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
            DownLoadButton.setVisibility(View.GONE);
            mCancelDownloadButton.setVisibility(View.VISIBLE);
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
                Toast.makeText(context, "Download Error: "+s, Toast.LENGTH_SHORT).show();
            }else {
                FirebaseStorage.getInstance().getReferenceFromUrl(apkUrl).delete();
                mCancelDownloadButton.setVisibility(View.GONE);
                DownLoadButton.setVisibility(View.GONE);
                interCheckPro.setVisibility(View.GONE);
                looding.setVisibility(View.GONE);
                UpdateMessage(FilePath);
            }
        }
    }
    public String getFilePath(Context context,String ApkName)
    {
        String FILE_NAME="";
        String IMAGE_FINAL_PATH="";
        try {
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath+= Data_Storage_Path.APK_MESSAGE_SENT;
            IMAGE_FINAL_PATH=new File(filePath)+"/"+ApkName+".apk";
            if (new File(IMAGE_FINAL_PATH).exists())
            {
                File[] list=new File(filePath).listFiles();
                int count=0;
                for (File f: list){
                    String name=f.getName();
                    if (name.endsWith(".apk")){
                        if (name.startsWith(ApkName)){
                            count++;
                        }
                    }
                }

                FILE_NAME=ApkName+(count)+".apk";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                FILE_NAME=ApkName+".apk";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(context, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return IMAGE_FINAL_PATH;
    }
    private void UpdateMessage(String outPutPath){
        Message newMessageSQ = new Message();
        newMessageSQ.msgType= ExtractedStrings.ITEM_MESSAGE_APK_TYPE;
        newMessageSQ.idRoom=RoomId;
        newMessageSQ.idSender =SenderId;
        newMessageSQ.idReceiver =ReceiverId;
        newMessageSQ.text = "-";
        newMessageSQ.timestamp = System.currentTimeMillis();
        newMessageSQ.PhotoDeviceUrl="-";
        newMessageSQ.PhotoStringBase64= base64Icon;
        newMessageSQ.VideoDeviceUrl=fileName;
        newMessageSQ.VoiceDeviceUrl= FileInputOutPutStream.getApkSize(new File(outPutPath));;
        newMessageSQ.DocumentDeviceUrl=outPutPath;
        newMessageSQ.AnyMediaUrl= apkUrl;
        newMessageSQ.AnyMediaStatus=ExtractedStrings.MEDIA_DOWNLOADED;
        newMessageSQ.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
        newMessageSQ.msg_reprayed_id=ExtractedStrings.MESSAGE_REPLAYED_ID;
        AllChatsDB.getInstance(context).updateChats(context,newMessageSQ,msgId);
        chat_activity.UpdateAdapterChats(true);
    }
}
