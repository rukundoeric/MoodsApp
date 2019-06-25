package com.moodsapp.prestein.moodsapp.data.Global_String;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Eric prestein on 12/30/2017.
 */

public class PermissionRequestCode {
    public static final int REC_AUDIO= 1;
    public static final int GALLERY_REQUEST =2;
    public static final int CAMERA_REQUEST=3;
    public static final int READ_CCT= 4;
    public static final int IO_REQUEST=5;
    public static final int IO_CO_RQST=6;
    public static final int IO_CA_RQST=7;


    public static final String[] IO_PERMISSIONS={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};
    public static final String[] IO_CONTACT_PERMISSIONS={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS};
    public static final String[] IO_CAMERA_PERMISSIONS={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    public static final String[] CONTACT_PERMISSIONS={Manifest.permission.READ_CONTACTS};
    public static final String[] REC_VOICE_PERMISSIONS={Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,};



    public static boolean hasPremissions(Context context,String... permissions){
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M && context !=null && permissions !=null){
            for (String permission:permissions){
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

}
