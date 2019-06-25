package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DatabaseReference;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.FileInputOutPutStream;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ItemMessageApkUserHolderClass {

    private ImageView messageStatus;
    public Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private Bitmap src;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private LinearLayout mLayoutApkContainer;
    private LinearLayout mLayoutApkRounder;
    private EmojiconTextView mApkTime;
    private ImageView mApkIconImage;
    private EmojiconTextView mApkName;
    private EmojiconTextView mApkDetails;
    private ImageView mApkUploadIcon;
    private ProgressBar mApkLoading;
    private CircleProgressBar mApkProcessProgressLoading;
    private View itemView;
    public ItemMessageApkUserHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, LinearLayout mLayoutApkContainer, LinearLayout mLayoutApkRounder, EmojiconTextView mApkTime, ImageView mApkIconImage, EmojiconTextView mApkName, EmojiconTextView mApkDetails, ImageView mApkUploadIcon, ProgressBar mApkLoading, CircleProgressBar mApkProcessProgressLoading, ImageView mMessageStatus, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.src = src;
        this.consersation = consersation;
        this.bitmapAvataDB = bitmapAvataDB;
        this.position = position;
        this.mLayoutApkContainer=mLayoutApkContainer;
        this.mLayoutApkRounder=mLayoutApkRounder;
        this.mApkTime=mApkTime;
        this.mApkIconImage=mApkIconImage;
        this.mApkName=mApkName;
        this.mApkDetails=mApkDetails;
        this.mApkUploadIcon=mApkUploadIcon;
        this.mApkLoading=mApkLoading;
        this.mApkProcessProgressLoading=mApkProcessProgressLoading;
        this.itemView=itemView;
        this.messageStatus=mMessageStatus;
    }

    public void setMessageApkUserHolder(){

        if (position==0){
            mLayoutApkContainer.setPadding(ExtractedStrings.DeviceWidth/4,16,2,2);
        }else {
            if (consersation.getListMessageData().get(position-1).idSender.equals(ExtractedStrings.UID)){
                mLayoutApkRounder.setBackgroundResource(R.drawable.balloon_outgoing_normal_ext);
                mLayoutApkContainer.setPadding(ExtractedStrings.DeviceWidth/4,2,2,2);
            }  else {
                mLayoutApkRounder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
                mLayoutApkContainer.setPadding(ExtractedStrings.DeviceWidth/4,16,2,2);
            }
        }
        try {
          /*  Lparams=mLayoutApkRounder.getLayoutParams();
            Lparams.width=ExtractedStrings.DeviceWidth-((ExtractedStrings.DeviceWidth/5)+10);
            mLayoutApkRounder.setLayoutParams(Lparams);
           */
          mApkTime.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));

        }catch (Exception e){
            Toast.makeText(context, "In Item Message User Holder \n"+e.getMessage()+ "\n" +e.getCause(), Toast.LENGTH_SHORT).show();
        }
        try {
            String packageName=consersation.getListMessageData().get(position).text;
            String AppName=new ApkInfoExtractor(context).GetAppName(packageName);
            String AppPath=consersation.getListMessageData().get(position).DocumentDeviceUrl;
            String AppSize= FileInputOutPutStream.getApkSize(new File(AppPath));
            BitmapDrawable bitmapDrawable= (BitmapDrawable) new ApkInfoExtractor(context).getAppIconByPackageName(packageName);
            mApkIconImage.setImageDrawable(bitmapDrawable);
            mApkName.setText(AppName);
            mApkDetails.setText("APK . "+AppSize);
        }catch (Exception e){
            Toast.makeText(chat_activity, "In load icon app "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mLayoutApkRounder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ApplicationPackageName=consersation.getListMessageData().get(position).text;
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                if(intent != null){
                    context.startActivity(intent);
                }
                else {
                    Toast.makeText(context,ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                }
            }
        });

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

        if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_NOT_UPLOADED)){
            mApkLoading.setVisibility(View.GONE);
            mApkProcessProgressLoading.setVisibility(View.GONE);
            mApkUploadIcon.setVisibility(View.VISIBLE);
        }else if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_UPLOADED )){
            mApkLoading.setVisibility(View.GONE);
            mApkProcessProgressLoading.setVisibility(View.GONE);
            mApkUploadIcon.setVisibility(View.GONE);
        }
        if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SAVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_storage);
        }else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SENT)) {
            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_server);
        }
        else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_RECEIVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_received_by_user);
        }
    /*    if (chat_activity.newVoiceIsSent){
            if (position==consersation.getListMessageData().size()-1){
                UploadApk();
            }
        }*/

        mApkUploadIcon.setClickable(true);
        mApkUploadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadApk();
            }
        });
    }

    private void UploadApk() {
        mApkLoading.setVisibility(View.VISIBLE);
        mApkProcessProgressLoading.setVisibility(View.GONE);
        mApkUploadIcon.setVisibility(View.GONE);
        String packageName=consersation.getListMessageData().get(position).text;
        String ApkPath=consersation.getListMessageData().get(position).DocumentDeviceUrl;
        String user_id=consersation.getListMessageData().get(position).idReceiver;
        String msgId=consersation.getListMessageData().get(position).msgId;

        new SendApk(chat_activity,context,packageName,ApkPath
                ,user_id,msgId,
                mApkUploadIcon,
                mApkLoading,
                mApkProcessProgressLoading).checkBeforeUpload(context);
    }
}
