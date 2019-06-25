package com.moodsapp.prestein.moodsapp.util.DownloadUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileTask {
    private static String FileName;
    private static String FileUrl;
    private Context context;
    public static void downloadImage(Context context,String url){
        FileUrl = url;
        FileName=new getFileName(context).getImageFileName("",true);
        new DownloadTask().execute();
    }
    private static class DownloadTask extends AsyncTask<String,Integer, String> {
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
            }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          /*  if (s != null) {
                Toast.makeText(context, "Error in on post Execute: "+s, Toast.LENGTH_SHORT).show();
            }else {
                LoadingBack.setVisibility(View.GONE);
                UpdateMessage(FileName);
            }*/
        }
    }
}
