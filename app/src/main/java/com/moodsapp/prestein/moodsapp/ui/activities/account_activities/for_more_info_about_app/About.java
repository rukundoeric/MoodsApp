package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_more_info_about_app;

import android.content.ClipboardManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class About extends AppCompatActivity {

    private TextView mText;
    private EditText mUrl;
    private ProgressBar looding;
    private Button mPaste;
    private Button mDownload;
    private String resultUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mUrl=(EditText)findViewById(R.id.test_download_url_input);
        looding=(ProgressBar)findViewById(R.id.test_download_progress);
        mPaste=(Button)findViewById(R.id.test_paste_url_button);
        mDownload=(Button)findViewById(R.id.test_download_button);
        String url=mUrl.getText().toString();
        try {
            resultUrl = java.net.URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TaskIsInternetAvailable().execute();
                Toast.makeText(About.this, "started", Toast.LENGTH_SHORT).show();
            }
        });
        mPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasteTrailerUrl();
            }
        });
    }
    private void pasteTrailerUrl() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (clipboard.getText().equals("")){
            Toast.makeText(getApplicationContext(),"No copied content!",Toast.LENGTH_LONG).show();
        }else{
            mUrl.setText(clipboard.getText());
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

                FILE_NAME="IMG_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MoodsApp"+(count)+".mp4";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                FILE_NAME="IMG_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MoodsApp"+".mp4";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return IMAGE_FINAL_PATH;
    }
    private class DawnloadTask extends AsyncTask<String,Integer, String> {
        private File FileName;

        @Override
        protected String doInBackground(String... strings) {
            try {Toast.makeText(About.this, "started", Toast.LENGTH_SHORT).show();
                FileName= new File(getFilename());
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
                Toast.makeText(getApplicationContext(), "Error in on post Execute: "+s, Toast.LENGTH_SHORT).show();
            }else {
               // FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl).delete();
              //  looding.setVisibility(View.GONE);
                Toast.makeText(About.this, "Download complete", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(getApplicationContext());
        }
        protected void onPostExecute(Boolean result) {
            if (result){
                new DawnloadTask().execute(resultUrl);

            }else {
               looding.setIndeterminate(false);
                ToastMessage.makeText(About.this, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
            }
        }
    }

}
 