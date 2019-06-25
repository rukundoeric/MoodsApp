package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument;

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
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReceiveDocument {
    private Message messageIt;
    private String FilePath;
    private Chat_Activity chat_activity;

    private Context context;
    private String DocUrl;
    private String RoomId;
    private String SenderId;
    private String ReceiverId;
    private String msgId;
    private ImageView DownLoadButton;
    private CircleProgressBar looding;
    private ProgressBar interCheckPro;
    double file_size=0;

    private String DocDetails;

    public ReceiveDocument(Chat_Activity chat_activity, Context context, Message message, ImageView mDocDownloadIcon, CircleProgressBar mDocProcessProgressLoading, ProgressBar mDocLoading) {
        this.chat_activity = chat_activity;

        this.context = context;
        this.DocUrl = message.AnyMediaUrl;
        RoomId = message.idRoom;
        SenderId = message.idSender;
        ReceiverId = message.idReceiver;
        this.messageIt=message;
        this.msgId = message.msgId;
        DownLoadButton = mDocDownloadIcon;
        this.looding = mDocProcessProgressLoading;
        this.interCheckPro = mDocLoading;
        FilePath = getFilePath(context,DocDetails);
        this.DocDetails = message.text+ " "+'|'+" "+message.PhotoDeviceUrl;;

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
                downLoadDocument();
            }else {
                DownLoadButton.setVisibility(View.VISIBLE);
                interCheckPro.setVisibility(View.GONE);
                looding.setVisibility(View.GONE);
                ToastMessage.makeText(chat_activity, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }

    public void downLoadDocument(){
        try{

            DownLoadButton.setVisibility(View.GONE);
            interCheckPro.setVisibility(View.GONE);
            looding.setVisibility(View.VISIBLE);
            new DawnloadTask().execute(DocUrl);
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
                FirebaseStorage.getInstance().getReferenceFromUrl(DocUrl).delete();
                DownLoadButton.setVisibility(View.GONE);
                interCheckPro.setVisibility(View.GONE);
                looding.setVisibility(View.GONE);
                UpdateMessage(FilePath);
            }
        }
    }
    public String getFilePath(Context context,String DocDetails)
    {
        String DocNameFile=messageIt.VideoDeviceUrl;
        String Extansion="."+messageIt.text.toLowerCase();

        String FILE_NAME="";
        String IMAGE_FINAL_PATH="";
        try {
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath+= Data_Storage_Path.DOCUMENT_MESSAGE_RECEIVED;
            IMAGE_FINAL_PATH=new File(filePath)+"/"+DocNameFile+Extansion;
            if (new File(IMAGE_FINAL_PATH).exists())
            {
                File[] list=new File(filePath).listFiles();
                int count=0;
                for (File f: list){
                    String name=f.getName();
                    if (name.endsWith(Extansion)){
                        if (name.startsWith(DocNameFile)){
                            count++;
                        }
                    }
                }

                FILE_NAME=DocNameFile+(count)+Extansion;
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                FILE_NAME=DocNameFile+Extansion;
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(context, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return IMAGE_FINAL_PATH;
    }
    private void UpdateMessage(String outPutPath){
        Message newMessageSQ = new Message();
        newMessageSQ.msgType= ExtractedStrings.ITEM_MESSAGE_DOCUMENT_TYPE;
        newMessageSQ.idRoom=RoomId;
        newMessageSQ.idSender =SenderId;
        newMessageSQ.idReceiver =ReceiverId;
        newMessageSQ.text = messageIt.text;
        newMessageSQ.timestamp = System.currentTimeMillis();
        newMessageSQ.PhotoDeviceUrl=messageIt.PhotoDeviceUrl;
        newMessageSQ.PhotoStringBase64= "-";
        newMessageSQ.VideoDeviceUrl=messageIt.VideoDeviceUrl;
        newMessageSQ.VoiceDeviceUrl="-";
        newMessageSQ.DocumentDeviceUrl=outPutPath;
        newMessageSQ.AnyMediaUrl= DocUrl;
        newMessageSQ.AnyMediaStatus=ExtractedStrings.MEDIA_DOWNLOADED;
        newMessageSQ.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
        newMessageSQ.msg_reprayed_id=ExtractedStrings.MESSAGE_REPLAYED_ID;
        AllChatsDB.getInstance(context).updateChats(context,newMessageSQ,msgId);
        chat_activity.UpdateAdapterChats(true);
    }

}
