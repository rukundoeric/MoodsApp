package com.moodsapp.prestein.moodsapp.util.ImageUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.service.BackgroundProcess.ContactWatchService;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public  class privateImageLoader {
    private Context context;
    public privateImageLoader(Context context, String url, String fileName) {
        this.context = context;
        new DownloadTask(fileName).execute(url);
    }
    @SuppressLint("StaticFieldLeak")
    private  class DownloadTask extends AsyncTask<String,Integer, String> {
        String FILENAME;
        DownloadTask(String FILENAME) {
            this.FILENAME=FILENAME;
        }
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
                    input=connection.getInputStream();
                    output=new getFileName(context).getPrivateOutPutStream(context,FILENAME);
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
            }catch (Exception r){
                return execute().toString();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (ContactWatchService.profileImageStore==null){
                ContactWatchService.profileImageStore=new HashMap<>();
            }
             File file=new File(context.getFilesDir(),FILENAME+ Data_Storage_Path.jpgFileFormat);
             Bitmap bitmap;
                if (s != null) {
                    if (file.exists()){
                       bitmap= BitmapFactory.decodeFile(String.valueOf(file));
                    }else{
                       bitmap=null;
                    }
            }else {
              if(file.exists()){
                   bitmap= BitmapFactory.decodeFile(String.valueOf(file));
              }else{
                  bitmap=null;
                  }
            }
            ContactWatchService.profileImageStore.put(FILENAME,bitmap);
        }
    }
}
