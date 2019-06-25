package com.moodsapp.prestein.moodsapp.util.InputOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getFileName {
    private String uid;
    Context context;
    public getFileName(Context context) {
        this.context=context;
    }
    public getFileName(String uid,Context context) {
        this.uid=uid;
        this.context=context;
    }
    public String getImageFileName(String uid,String outputPath){
        String FILE_NAME="";
        String IMAGE_FINAL_PATH="";
        try {
                String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
                filePath+= outputPath;
                File file=new File(filePath);
                if (file.exists())
                {
                   file.delete();
                }
                    file.mkdirs();
                    FILE_NAME=uid+".jpg";
                    IMAGE_FINAL_PATH=file+"/"+FILE_NAME;

        }catch (Exception r){
            r.printStackTrace();
        }
        return IMAGE_FINAL_PATH;
    }
    public String getImageFileName(String outputPath, boolean isDataDir)  {
        String FILE_NAME="";
        String IMAGE_FINAL_PATH="";
     try {
            if (isDataDir){
                IMAGE_FINAL_PATH=new CompressingImage(context).getRealPathFromURI(String.valueOf(getDataFilesThumbnailUriPath(uid,context)));
            }else {
                String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
                filePath+= outputPath;

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
            }
     }catch (Exception r){
         r.printStackTrace();
     }
        return IMAGE_FINAL_PATH;
    }

    public Uri getDataFilesThumbnailUriPath(String uid, Context context) {
      Uri outputUri = null;
            try {
                String ext = ".jpg";
                File file=context.getFilesDir();
                for (File file1:file.listFiles()){
                    if (file1.getName().startsWith(uid)){
                       context.deleteFile(String.valueOf(file1));
                       // outputUri = Uri.fromFile(new File(new CompressingImage(context).getRealPathFromURI(String.valueOf(Uri.fromFile(File.createTempFile(uid, ext, context.getFilesDir()))))));
                    }
                }
                outputUri = Uri.fromFile(new File(new CompressingImage(context).getRealPathFromURI(String.valueOf(Uri.fromFile(File.createTempFile(uid, ext, context.getFilesDir()))))));

            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file for output image", e);
            }
        return outputUri;
    }
    public FileOutputStream getPrivateOutPutStream(Context context,String uid) throws FileNotFoundException {
        String ext=".jpg";
        File file=new File(context.getFilesDir(),uid+ext);
        if (file.exists()){
            context.deleteFile(uid+ext);
        }
        return context.openFileOutput(uid+ext,Context.MODE_PRIVATE);
    }
    public boolean fileExist(Activity context, String fname){
        File file =context.getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
    //App cache directory
    public static String createThumbNailsImage(String fileName, Context context){
        String path=context.getFilesDir().getAbsolutePath()+File.separator+"thumbnails"+File.separator+fileName+".jpg";
        if (new File(path).exists()){
            new File(path).delete();
        }
        new File(path).mkdir();
        return path;
    }
}
