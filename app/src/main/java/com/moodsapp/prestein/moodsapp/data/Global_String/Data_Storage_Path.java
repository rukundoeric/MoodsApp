package com.moodsapp.prestein.moodsapp.data.Global_String;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Eric prestein on 12/31/2017.
 */

public class Data_Storage_Path
{


    public static File getCacheStorage(Context context){
        return new File(context.getFilesDir().getAbsolutePath());
    }
    public static String isThisFileExistInCache(String fileName,Context context){
        File listFile[]=getCacheStorage(context).listFiles();
        for (File file:listFile){
            if (file.getName().startsWith(fileName)){
                return file.getAbsolutePath();
            }
        }
        return "default";
    }
    public static boolean isThisPrivateFileExist(Context context,String filename,String ext){
        File file=new File(context.getFilesDir(),filename+ext);
        return file.exists();
    }


    public static String jpgFileFormat=".jpg";
    public static  String PROFILES_IMAGES="/MoodsApp/Media/pictures/Profiles";


    public static  String VOICE_MESSAGE_SENT="/MoodsApp/media/audios/voice/MoodsApp voices sent";
    public static  String VOICE_MESSAGE_RECEIVED="/MoodsApp/Media/audios/voice/MoodsApp voices received";
    public static  String IMAGE_MESSAGE_SENT="/MoodsApp/Media/pictures/MoodsApp images sent";
    public static  String IMAGE_MESSAGE_RECEIVED="/MoodsApp/Media/pictures/MoodsApp images received/";

    public static  String IMAGE_PROFILE_PICTURE_DOWNLOADED_RECEIVED="/MoodsApp/media/pictures/MoodsApp profile";
    public static  String VIDEO_MESSAGE_SENT="/MoodsApp/Media/video/sent";
    public static  String VIDEO_MESSAGE_RECEIVED="/MoodsApp/Media/video/received";
    public static  String CONTACT_MESSAGE_SENT="/MoodsApp/Files/Contact/sent";
    public static  String CONTACT_MESSAGE_RECEIVED="/MoodsApp/Files/Contact/sent";
    public static  String APK_MESSAGE_SENT="/MoodsApp/Files/Apk/sent";
    public static String APK_MESSAGE_RECEIVED="/MoodsApp/Files/Apk/Received";

    public static  String DOCUMENT_MESSAGE_SENT="/MoodsApp/Files/Document/sent";
    public static String DOCUMENT_MESSAGE_RECEIVED="/MoodsApp/Files/Document/Received";


    public static  String ALL_CHAT_MESSAGE_PATH="/MoodsApp/Files/Database/Chat";
    public static  String MY_PROFILE_STORAGE_PATH="/MoodsApp/Files/Database/Profile";
    public static  String MY_PROFILE_STORAGE_PATH_DB="/MoodsApp/Files/Database/Profile/myProfile.db";
    public static  String USERS_PROFILE_STORAGE_PATH="/MoodsApp/Files/Database/Profile";
    public static  String MOODSAPP_HIDEN_FILES="/MoodsApp/AppFiles";


    public static  String IMAGE_MESSAGE_RECEIVED_TEST="/MoodsApp/Media/Test";

}
