package com.moodsapp.prestein.moodsapp.service.NotificationMessage;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home.Home_Activity;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.RecentNotificationDb;
import com.moodsapp.prestein.moodsapp.model.ItemNotification;
import com.moodsapp.prestein.moodsapp.service.RecentNotification.ItemNotifictaion;
import com.moodsapp.prestein.moodsapp.service.RecentNotification.RecentNotificationListClass;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ConvertImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by Eric prestein on 1/4/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    boolean lollipop = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    private SimpleDateFormat simpleDateFormat;
    private int NOTIFICATION_ID=757545453;
    private static RecentNotificationListClass recentNotificationMessage=null;
    private Bitmap bitmapBase;

    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data=remoteMessage.getData();
        ItemNotification itemNotification=new ItemNotification();
        itemNotification.title=data.get("userQuery");
        itemNotification.notId=data.get("NotificationId");
        itemNotification.iconUrl=data.get("IconUrl");
        itemNotification.msgId=data.get("NotificationId");
        itemNotification.msgType = data.get("msgType");
        itemNotification.idRoom = data.get("idRoom");
        itemNotification.idReceiver = data.get("idReceiver");
        itemNotification.idSender = data.get("idSender");
        itemNotification.text = data.get("text");
        itemNotification.PhotoDeviceUrl = data.get("PhotoDeviceUrl");
        itemNotification.PhotoStringBase64 = data.get("PhotoStringBase64");
        itemNotification.VoiceDeviceUrl = data.get("VoiceDeviceUrl");
        itemNotification.VideoDeviceUrl = data.get("VideoDeviceUrl");
        itemNotification.DocumentDeviceUrl = data.get("DocumentDeviceUrl");
        itemNotification.AnyMediaUrl = data.get("AnyMediaUrl");
        itemNotification.AnyMediaStatus = data.get("AnyMediaStatus");
        itemNotification.timestamp = String.valueOf(System.currentTimeMillis());
        itemNotification.msg_reprayed_id = data.get("msg_reprayed_id");
        itemNotification.msg_reprayed_message = data.get("msg_reprayed_message");
        simpleDateFormat=new SimpleDateFormat("HH:mm");
        try {
        if (remoteMessage.getData().size() > 0) {
            SaveNewNotification(itemNotification);
            if (MoodsApp.isChatActivityVisibility() && itemNotification.idSender.equals(ExtractedStrings.OpenedUser)){

            }else {
                pushNotification(itemNotification);
            }
            ExtractedStrings.extraIntentItemNotificationMessage=itemNotification;
            Intent i = new Intent(this, SaveNewMessage.class);
            startService(i);
           // saveMessage(itemNotification);
        }
        } catch (Exception e) {
        }
    }

    private void SaveNewNotification(ItemNotification itemNot) {
        try {
            String message="";
            if (itemNot.msgType.equals(ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE)){
                message=itemNot.text;
            }else if (itemNot.msgType.equals(ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE)){
                message=ExtractedStrings.RECENT_CHAT_MESSAGE_PHOTO;
            }else if (itemNot.msgType.equals(ExtractedStrings.ITEM_MESSAGE_VOICE_TYPE)){
                message=ExtractedStrings.RECENT_CHAT_MESSAGE_VOICE;
            }
            else if (itemNot.msgType.equals(ExtractedStrings.ITEM_MESSAGE_VIDEO_TYPE)){
                message=ExtractedStrings.RECENT_CHAT_MESSAGE_VIDEO;
            }
            else if (itemNot.msgType.equals(ExtractedStrings.ITEM_MESSAGE_CONTACT_TYPE)){
                message=ExtractedStrings.RECENT_CHAT_MESSAGE_CONTACT;
            }else if (itemNot.msgType.equals(ExtractedStrings.ITEM_MESSAGE_DOCUMENT_TYPE)){
                message=ExtractedStrings.RECENT_CHAT_MESSAGE_DOCUMENT;
            }else if (itemNot.msgType.equals(ExtractedStrings.ITEM_MESSAGE_APK_TYPE)){
                message=ExtractedStrings.RECENT_CHAT_MESSAGE_APK;
            }
                ItemNotifictaion itemNotifictaion = new ItemNotifictaion();
                itemNotifictaion.notificationId = itemNot.notId;
                itemNotifictaion.notificationIcon=itemNot.iconUrl;
                itemNotifictaion.title = itemNot.title;
                itemNotifictaion.message = message;
                itemNotifictaion.senderid = itemNot.idSender;
                RecentNotificationDb.getInstance(getApplicationContext()).addUnReadMessageNotification(itemNotifictaion);

        }catch (Exception g){
            Toast.makeText(this, "in save ItemNotification"
                    +g.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isOneSender(){
        int size=recentNotificationMessage.getListRecentNotification().size();
        for (int a=0;a<size;a++){
            if (a==size-1){
                return true;
            }else {
                if (!recentNotificationMessage.getListRecentNotification().get(0).senderid.equals(recentNotificationMessage.getListRecentNotification().get(a).senderid)){
                    return false;
                }
            }
        }
        return false;
    }
    public void pushNotification(ItemNotification itNot)
    {
        recentNotificationMessage = RecentNotificationDb.getInstance(this).getRecentNotificationListClass();
    // if (NotificationType.equals(ExtractedStrings.NOTIFICATION_TYPE_MESSAGE)){*/
         ShowMessageNotification(itNot);
   // }
    }
    private void ShowMessageNotification(ItemNotification itemNotify) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        long[] v={500,500};
        Intent notificationIntent = new Intent(this, Home_Activity.class);
        notificationIntent.putExtra("reminder", "Hello");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.InboxStyle inboxStyle=new NotificationCompat.InboxStyle();
        inboxStyle.setSummaryText(recentNotificationMessage.getListRecentNotification().size()+" "+"messages");
        inboxStyle.setBigContentTitle(itemNotify.title);
        if (recentNotificationMessage.getListRecentNotification().size() == 1 || isOneSender()){
            inboxStyle.setBigContentTitle(itemNotify.title);
            for (ItemNotifictaion notifictaion:recentNotificationMessage.getListRecentNotification()){
                inboxStyle.addLine(notifictaion.message);
            }
        }
        else {
            inboxStyle.setBigContentTitle("New messages");
            for (ItemNotifictaion notifictaion:recentNotificationMessage.getListRecentNotification()){
                inboxStyle.addLine(notifictaion.title+": "+notifictaion.message);
            }
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
                notificationBuilder.setContentTitle(itemNotify.title);
                notificationBuilder.setAutoCancel(true);
                notificationBuilder.setSound(defaultSoundUri);
                notificationBuilder.setVibrate(v);
                notificationBuilder.setStyle(inboxStyle);
                notificationBuilder.addAction(R.drawable.ic_notification_reply_message, "Reply",contentIntent); //Setting the action
         notificationBuilder.setContentIntent(contentIntent);
        if (isOneSender()){
           // notificationBuilder.setSmallIcon(R.mipmap.moodslogo);
            notificationBuilder.setLargeIcon(NotificationIcon(recentNotificationMessage.getListRecentNotification().get(0).senderid,itemNotify.iconUrl));
            notificationBuilder.setSmallIcon(R.mipmap.moodslogo);
        }else {
            notificationBuilder.setSmallIcon(R.mipmap.moodslogo);
        }

       if (lollipop){notificationBuilder.setPriority(Notification.PRIORITY_HIGH);}

        if (recentNotificationMessage.getListRecentNotification().size() <= 1){
            notificationBuilder.setContentText(itemNotify.text);
        }else {
            notificationBuilder.setContentText(recentNotificationMessage.getListRecentNotification().size()+" "+"messages");
        }

        Notification notification = notificationBuilder.build();
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }
    private Bitmap NotificationIcon(String userId,String urlIcon) {

            if (FriendDB.getInstance(this).getInfoByIdUser(3,userId, this) == null) {
                if (urlIcon.equals("")){
                    if (userId.startsWith("group")){
                        Bitmap bitmap1=BitmapFactory.decodeResource(this.getResources(), R.drawable.avatar_group);
                        return bitmap1;
                    }else {
                        ConvertImage convertImage=new ConvertImage();
                        Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.avatar_default);
                        return convertImage.getCircularBitmap(getApplicationContext(),bitmap);
                    }

                }else {
                    return new ConvertImage().getCircularBitmap(getApplicationContext(),getBitmapFromURL(urlIcon));
                }

               } else {
                byte[] image = Base64.decode(FriendDB.getInstance(this).getInfoByIdUser(3,userId, this), Base64.DEFAULT);
                Bitmap bitmap=new ConvertImage().getCircularBitmap(getApplicationContext(),BitmapFactory.decodeByteArray(image, 0, image.length));
                return bitmap;
            }
    }
    //Simple method for image downloading
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

