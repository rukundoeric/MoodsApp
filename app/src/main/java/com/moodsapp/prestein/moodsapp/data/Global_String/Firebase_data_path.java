package com.moodsapp.prestein.moodsapp.data.Global_String;

import com.moodsapp.prestein.moodsapp.model.Profile;

/**
 * Created by Eric prestein on 1/3/2018.
 */

public class Firebase_data_path {
    //for myProfile
    public static String DeviceIdPath(String userId){
        return "Users/Profiles/"+userId+"/"+ Profile.DeviceIdInfo;
    }
    public static String NamePath(String userId){
        return "Users/Profiles/"+userId+"/"+ Profile.nameInfo;
    }
    public static String StatusPath(String userId){
        return "Users/Profiles/"+userId+"/"+ Profile.statusInfo;
    }
    public static String PhonePath(String userId){
        return "Users/Profiles/"+userId+"/"+ Profile.phoneInfo;
    }
    public static String ImagePath(String userId){
        return "Users/Profiles/"+userId+"/"+ Profile.profile_imageInfo;
    }
    public static String SmallImagePath(String userId){
        return "Users/Profiles/"+userId+"/"+ Profile.small_profile_imageInfo;
    }
    public static String NotificationPath(String receiverId){
        return "Notifications/"+receiverId;
    }

    public static String ProfilesImageStoragePath(String uid,String fileName){
        String path="Users/"+uid+"/ProfilePicture/"+fileName;
    return path;
    }

    public static String ProfilePath(String userId){
        return "Users/Profiles/"+userId;
    }
    public static String AllUserPath(){
        return "Users/Profiles";
    }


    public static String MessageImageStoragePath(String receiverId,String senderId){
        String path="Users/"+receiverId+"/Messages/"+senderId+"/MessagePhoto/"+String.valueOf(System.currentTimeMillis());
        return path;
    }
    public static String MessageDocumentStoragePath(String receiverId,String senderId){
        String path="Users/"+receiverId+"/Messages/"+senderId+"/MessageDocument/"+String.valueOf(System.currentTimeMillis());
        return path;
    }
    public static String MessageVoiceStoragePath(String receiverId,String senderId){
        String path="Users/"+receiverId+"/Messages/"+senderId+"/MessageVoice/"+String.valueOf(System.currentTimeMillis());
        return path;
    }
    public static String MessageVideoStoragePath(String receiverId,String senderId){
        String path="Users/"+receiverId+"/Messages/"+senderId+"/MessageVideo/"+String.valueOf(System.currentTimeMillis());
        return path;
    }
    public static String MessageApkStoragePath(String receiverId,String senderId,String fileName){
        String path="Users/"+receiverId+"/Messages/"+senderId+"/MessageApk/"+fileName;
        return path;
    }
}
