package com.moodsapp.prestein.moodsapp.util.DirectoriesUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType.GetImageOrVideoFromGalleryOrCamera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER on 3/25/2018.
 */

public class CopyImageToImageSent {
    public   void CopyToImageSent(String user_id,String sourceLocationFile,Context context,Chat_Activity chat_activity){
        File source = new File(sourceLocationFile);
        String destinationPath = getImageSentFileNameLocation(context);
        File destination = new File(destinationPath);
        try
        {
            if(!source.exists()){
               source.mkdir();
            }

            InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(destination);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
            Bundle bundle = new Bundle();
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, user_id);
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_AVATA,String.valueOf(destinationPath));
            Intent intent=new Intent(context, GetImageOrVideoFromGalleryOrCamera.class);
            ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
            idFriend.add(user_id);
            bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
            intent.putExtras(bundle);
            chat_activity.startActivity(intent);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public String getImageSentFileNameLocation(Context context)
    {
        String FILE_NAME="";
        String IMAGE_FINAL_PATH="";
        try {
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath+= Data_Storage_Path.IMAGE_MESSAGE_SENT;

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

                FILE_NAME="IMG_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MOODSAPP"+(count)+".jpg";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                FILE_NAME="IMG_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"__MOODSAPP"+".jpg";
                IMAGE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(context, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return IMAGE_FINAL_PATH;
    }

}
