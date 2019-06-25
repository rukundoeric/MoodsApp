package com.moodsapp.prestein.moodsapp.util.InterfaceUtils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;

/**
 * Created by Eric prestein on 1/1/2018.
 */

public class MoodsAppPermissionRequest {

    private Chat_Activity chat_activity;

    public MoodsAppPermissionRequest(Chat_Activity chat_activity) {
        this.chat_activity = chat_activity;
    }
    public static boolean isCameraPermissionGranted(Context context){
        boolean i=false;
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                i=false;

        }else {
            i=true;
        }
        return i;
    }
    public static boolean isGalleryPermissionGranted(Context context){
        boolean i=false;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            i=false;
        }else {
            i=true;
        }
        return i;
    }
    public static boolean isAudioVoiceRecordPermissionGranted(Context context){
        boolean i=false;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            i=false;
        }else {
            i=true;
        }
        return i;
    }
    public static boolean isReadExternalStoragePermissionGranted(Context context){
        boolean i=false;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            i=false;
        }else {
            i=true;
        }
        return i;
    }
    public static boolean isWriteExternalStoragePermissionGranted(Context context){
        boolean i=false;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            i=false;
        }else {
            i=true;
        }
        return i;
    }


}
