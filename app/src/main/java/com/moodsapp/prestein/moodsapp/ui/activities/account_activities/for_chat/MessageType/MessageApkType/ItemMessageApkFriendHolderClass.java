package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.FileInputOutPutStream;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessageApkFriendHolderClass {

    private ImageView mCancelDownloadButton;
    private HashMap<String, Bitmap> bitmapAvata;
    private ArrayList<Integer> colors;
    public Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private CircleImageView avata;
    private LinearLayout mLayoutApkContainer;
    private LinearLayout mLayoutApkRounder;
    private EmojiconTextView mApkTime;
    private ImageView mApkIconImage;
    private EmojiconTextView mApkName;
    private EmojiconTextView mApkDetails;
    private ImageView mApkDownloadIcon;
    private ProgressBar mApkLoading;
    private CircleProgressBar mApkProcessProgressLoading;
    private View itemView;

    public ItemMessageApkFriendHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, ArrayList<Integer> colorList, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, CircleImageView avata, LinearLayout mLayoutApkContainer, LinearLayout mLayoutApkRounder, EmojiconTextView mApkTime, ImageView mApkIconImage, EmojiconTextView mApkName, EmojiconTextView mApkDetails, ImageView mApkDownloadIcon, ProgressBar mApkLoading, CircleProgressBar mApkProcessProgressLoading, ImageView mCancelDownloadButton, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.bitmapAvata=bitmapAvata;
        this.consersation = consersation;
        this.bitmapAvataDB = bitmapAvataDB;
        this.src = src;
        this.position = position;
        this.colors=colorList;
        this.avata=avata;
        this.mLayoutApkContainer=mLayoutApkContainer;
        this.mLayoutApkRounder=mLayoutApkRounder;
        this.mApkTime=mApkTime;
        this.mApkIconImage=mApkIconImage;
        this.mApkName=mApkName;
        this.mApkDetails=mApkDetails;
        this.mApkDownloadIcon=mApkDownloadIcon;
        this.mApkLoading=mApkLoading;
        this.mApkProcessProgressLoading=mApkProcessProgressLoading;
        this.mCancelDownloadButton=mCancelDownloadButton;
        this.itemView=itemView;
    }

    public void setMessageApkFriendHolder(){
        if (position==0){
            mLayoutApkContainer.setPadding(5,16,ExtractedStrings.DeviceWidth/6,0);
            mLayoutApkRounder.setBackgroundResource(R.drawable.balloon_incoming_normal);
        }else {
            if (!consersation.getListMessageData().get(position-1).idSender.equals(ExtractedStrings.UID)){
                mLayoutApkRounder.setBackgroundResource(R.drawable.balloon_incoming_normal_ext);
                avata.setVisibility(CircleImageView.GONE);
                mLayoutApkContainer.setPadding(5,2,ExtractedStrings.DeviceWidth/6,0);
            }else {
                mLayoutApkRounder.setBackgroundResource(R.drawable.balloon_incoming_normal);
                avata.setVisibility(CircleImageView.VISIBLE);
                mLayoutApkContainer.setPadding(5,16,ExtractedStrings.DeviceWidth/6,0);
            }
        }
        mApkTime.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));
        Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idSender);
        if (currentAvata != null) {
            avata.setImageBitmap(currentAvata);
        } else {
            final String id = consersation.getListMessageData().get(position).idSender;
            if(bitmapAvataDB.get(id) == null){
                bitmapAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture"));
                bitmapAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            String avataStr = (String) dataSnapshot.getValue();
                            if(!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                            }else{
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                            }
                            //notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        avata.setBorderWidth(2);
        avata.setBorderColor(colors.get(position));
        try {
            if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_NOT_DOWNLOADED)){
                mApkLoading.setVisibility(View.GONE);
                mApkProcessProgressLoading.setVisibility(View.GONE);
                mApkDownloadIcon.setVisibility(View.VISIBLE);
                mCancelDownloadButton.setVisibility(View.GONE);
                try {
                    String AppIcon=consersation.getListMessageData().get(position).PhotoStringBase64;
                    if (AppIcon.equals("")) {
                        src = BitmapFactory.decodeResource(chat_activity.getResources(), R.drawable.avatar_default);
                    } else {
                        byte[] imageBytes = Base64.decode(AppIcon, Base64.DEFAULT);
                        src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    }
                    BitmapDrawable Image_drawable = new BitmapDrawable(chat_activity.getResources(), src);
                    mApkIconImage.setImageDrawable(Image_drawable);
                    mApkName.setText(consersation.getListMessageData().get(position).VideoDeviceUrl);
                    mApkDetails.setText("APK . "+   consersation.getListMessageData().get(position).VoiceDeviceUrl);
                } catch (Exception e) {
                }
            }else if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_DOWNLOADED )){
                mCancelDownloadButton.setVisibility(View.GONE);
                mApkLoading.setVisibility(View.GONE);
                mApkProcessProgressLoading.setVisibility(View.GONE);
                mApkDownloadIcon.setVisibility(View.GONE);
                String packageName=consersation.getListMessageData().get(position).text;
                if (new ApkInfoExtractor(context).isPackageInstalled(packageName)){
                    String AppName=new ApkInfoExtractor(context).GetAppName(packageName);
                    String AppPath=consersation.getListMessageData().get(position).DocumentDeviceUrl;
                    String AppSize= FileInputOutPutStream.getApkSize(new File(AppPath));
                    BitmapDrawable bitmapDrawable= (BitmapDrawable) new ApkInfoExtractor(context).getAppIconByPackageName(packageName);
                    mApkIconImage.setImageDrawable(bitmapDrawable);
                    mApkName.setText(AppName);
                    mApkDetails.setText("APK . "+AppSize);
                }else{
                    try {
                        String AppIcon=consersation.getListMessageData().get(position).PhotoStringBase64;
                        if (AppIcon.equals("")) {
                            src = BitmapFactory.decodeResource(chat_activity.getResources(), R.drawable.avatar_default);
                        } else {
                            byte[] imageBytes = Base64.decode(AppIcon, Base64.DEFAULT);
                            src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        }
                        BitmapDrawable Image_drawable = new BitmapDrawable(chat_activity.getResources(), src);
                        mApkIconImage.setImageDrawable(Image_drawable);
                        mApkName.setText(consersation.getListMessageData().get(position).VideoDeviceUrl);
                        mApkDetails.setText("APK . "+   consersation.getListMessageData().get(position).VoiceDeviceUrl);
                    } catch (Exception e) {
                    }
                }
            }

        }catch (Exception e){
            Toast.makeText(chat_activity, "In load icon app "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mLayoutApkRounder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName=consersation.getListMessageData().get(position).text;
                String filePath=consersation.getListMessageData().get(position).DocumentDeviceUrl;
                if (new ApkInfoExtractor(context).isPackageInstalled(packageName)){
                    String ApplicationPackageName=consersation.getListMessageData().get(position).text;
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                    if(intent != null){
                        context.startActivity(intent);
                    }
                    else {
                        Toast.makeText(context,ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    File file = new File(filePath);
                    if (file.exists()) {
                        Uri path = Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(path, "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        try {
                            chat_activity.startActivity(intent);
                        }
                        catch (ActivityNotFoundException e) {
                            Toast.makeText(context,
                                    "Error tying to install apk.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
        //mRecordTimeDuration.setText(consersation.getListMessageData().get(position).PhotoStringBase64);
        mApkDownloadIcon.setClickable(true);
        mApkDownloadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadApk();
            }
        });
    }
    private void DownloadApk() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(context, PermissionRequestCode.IO_PERMISSIONS)) {
            ActivityCompat.requestPermissions(chat_activity, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
        } else {
            String fileName=consersation.getListMessageData().get(position).VideoDeviceUrl;
            String apkUrl=consersation.getListMessageData().get(position).AnyMediaUrl;
            String idReceiver=consersation.getListMessageData().get(position).idReceiver;
            String msgId=consersation.getListMessageData().get(position).msgId;
            String idSender=consersation.getListMessageData().get(position).idSender;
            String idRoom=consersation.getListMessageData().get(position).idRoom;
            String base64Icon=consersation.getListMessageData().get(position).PhotoStringBase64;

            new ReceiveApk(chat_activity,
                    context,
                    fileName,
                    apkUrl,
                    idRoom,
                    idSender,
                    idReceiver,
                    msgId,
                    mApkDownloadIcon,
                    mApkProcessProgressLoading,
                    mApkLoading,
                    mCancelDownloadButton,
                    base64Icon).startDownloadTask();
        }

    }
}
