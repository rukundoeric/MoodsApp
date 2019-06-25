package com.moodsapp.prestein.moodsapp.data.Global_String;


import android.graphics.Bitmap;
import android.os.CountDownTimer;

import com.moodsapp.prestein.moodsapp.model.ItemNotification;
import com.moodsapp.prestein.moodsapp.model.OnlineUsersStatus;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.component.PhoneMediaControl;

import java.util.ArrayList;

public class ExtractedStrings {

    public static final String RECENT_CHAT_MESSAGE_PHOTO = "\uD83D\uDE99 Photo";
    public static final String RECENT_CHAT_MESSAGE_CONTACT = "Contact";
    public static final String RECENT_CHAT_MESSAGE_VOICE = "\uD83D\uDCBD Voice";
    public static final String RECENT_CHAT_MESSAGE_DOCUMENT = "Document";
    public static final String RECENT_CHAT_MESSAGE_VIDEO = "\uD83D\uDCF9 Video";
    public static final String RECENT_CHAT_MESSAGE_APK = "Apk";
    public static final String INTENT_ROOM_TYPE = "RoomType";

    public static final String SINGLE_ROOM_TYPE="SingleUserRoomType";
    public static final String GROUP_ROOM_TYPE="GroupRoomType";
    public static final String NO_INTERNET_CONNECTION_MESSAGE = "Connection failed, please try again later";
    public static final String INTENT_KEY_IMAGE_URL = "imageUrl";
    public static final String INTENT_KEY_VIDEO_URL = "videoUrl";
    public static final String INTENT_DOCUMENT_FILE_TYPE = "documentFileType";

    public static String MY_PROFILE_PICTURE_PATH ="" ;
    public static String MY_STATUS = "";
    public static String UID = "";
    public static String NAME = "";
    public static String MY_COUNTRY="";
    public static String MY_COUNTRY_CODE="";
    public static String CURRENT_TIME = "";
    public static String CURRENT_TIME_SUB_STRING = "";
    public static String MY_PROFILE_PICTURE = "";
    public static String NUMBERS_OF_CONTACTS_USERS = "";
    public static String NUMBERS_OF_CONNECTED_USERS = "";
    public static String STR_DEFAULT_BASE64 = "default";
    public static String STR_DEFAULT_BACK_TYPE = "primary";
    //my profile
    public static String MY_ID_FILE_NAME="profileids";
    public static String MY_NAME_FILE_NAME="profilenames";
    public static String MY_COUNTRY_FILE_NAME="profilecounties";
    public static String MY_IMAGE_FILE_NAME="profileimages";

    //more
    //    public static String UID = "6kU0SbJPF5QJKZTfvW1BqKolrx22";
    public static String INTENT_KEY_CHAT_FRIEND = "friendname";
    public static String INTENT_KEY_CHAT_AVATA = "friendavata";
    public static String INTENT_KEY_CHAT_STATUS = "friendstatus";
    public static String INTENT_KEY_CHAT_ID = "friendid";
    public static String INTENT_KEY_CHAT_FRIEND_ID = "UserId";
    public static String INTENT_KEY_CHAT_ROOM_ID = "roomid";
    public static String INTENT_FRIENDS_POSITION = "friendPos";
    public static String INTENT_MESSAGE_CONTACT_FILE="contactSvf";

    //Intent keys for Media sending
    public static String INTENT_USER_ID = "userIdentifier";
    public static String INTENT_MEDIA_PATH="mediaPath";
    public static String INTENT_MEDIA_TYPE="mediaType";



    //for file extension
    public static String DOCUMENT_FILE_TYPE_WORD_DOC =".docx";
    public static String DOCUMENT_FILE_TYPE_PW_POINT=".pptx";
    public static String DOCUMENT_FILE_TYPE_EXCEL=".xls";
    public static String DOCUMENT_FILE_TYPE_ZIP=".zip";
    public static String DOCUMENT_FILE_TYPE_ZIP_RAR=".rar";
    public static String DOCUMENT_FILE_TYPE_ZIP_7z=".7z";
    public static String DOCUMENT_FILE_TYPE_APK=".apk";
    public static String DOCUMENT_FILE_TYPE_PDF=".pdf";


    public static String INTENT_COUNTRY_NAME_CODE = "countrycodename";
    public static String INTENT_COUNTRY_CODE = "countrycode";
    public static String INTENT_COUNTRY_NAME = "countryname";
    public static String INTENT_PHONE_NUMBER = "phonenumber";


    public static String MESSAGE_REPLAYED_ID = "";
    public static String MESSAGE_REPLAYED_MESSAGE_TEXT = "";
    public static String INTENT_ID_ROOM = "idRoom";
    public static long TIME_TO_REFRESH = 10 * 1000;
    public static long TIME_TO_OFFLINE = 20 * 1000;
    public static String GET_FRIEND_IMAGE = "";
    //for message notification
    public static String NOTIFICATION_TYPE_MESSAGE = "NotificationTypeMessage";
    public static String NOTIFICATION_TYPE_EVENT = "NotificationTypeEvent";
    public static String NOTIFICATION_TYPE_MESSAGE_PHOTO = "NotificationTypeMessagePhoto";
    public static String NOTIFICATION_TYPE_MESSAGE_VOICE = "NotificationTypeMessageVoice";
    public static String NOTIFICATION_TYPE_MESSAGE_VIDEO = "NotificationTypeMessageVideo";
    public static String NOTIFICATION_TYPE_MESSAGE_CONTACT = "NotificationTypeMessageContact";
    //Runtime Strings
    public static String ITEM_MESSAGE_TEXT_TYPE = "TextMessage";
    public static String ITEM_MESSAGE_PHOTO_TYPE = "PhotoMessage";
    public static String ITEM_MESSAGE_VOICE_TYPE = "VoiceMessage";
    public static String ITEM_MESSAGE_VIDEO_TYPE = "VideoMessage";
    public static String ITEM_MESSAGE_DOCUMENT_TYPE = "DocumentMessage";
    public static String ITEM_MESSAGE_CONTACT_TYPE = "ContactMessage";
    public static String ITEM_MESSAGE_APK_TYPE="ApkMessage";
    //For Item type
    //Media status in chats
    public static String MEDIA_UPLOADED = "MediaUploaded";
    public static String MEDIA_NOT_UPLOADED = "MediaNotUploaded";
    public static String MEDIA_DOWNLOADED = "MediaDownloaded";
    public static String MEDIA_NOT_DOWNLOADED = "MediaNotDownloaded";
    //The Message Selected Id

    //Message status
    public static String MESSAGE_STATUS_SAVED="MessageSaved";
    public static String MESSAGE_STATUS_SENT="MessageSent";
    public static String MESSAGE_STATUS_RECEIVED="MessageReceived";
    public static String MESSAGE_STATUS_STILL_SENDING="MessageStillSending";

    public static String MESSAGE_STATUS_READED="MessageReaded";
    public static String MESSAGE_UNREADED="MessageUnReaded";

    public static String MESSAGE_SELECTED_ID="";
    public static ArrayList<String> MESSAGES_SELECTED_ID=null;
    public static int DeviceWidth=0;
    public static Bitmap mProfileImage=null;

    public static ItemNotification extraIntentItemNotificationMessage;
    public static CountDownTimer detectFriendOnline;
    public static CountDownTimer UpdateOnlineStatus;
    public static ArrayList<OnlineUsersStatus> OnlineUserStatusList;
    public static ArrayList<PhoneMediaControl.AlbumEntry> albumsSorted;
    public static String RecentActivity="";
    public static String OpenedUser="";
    public static Object ThumbnailsFolderName="Thumbnails";
}
