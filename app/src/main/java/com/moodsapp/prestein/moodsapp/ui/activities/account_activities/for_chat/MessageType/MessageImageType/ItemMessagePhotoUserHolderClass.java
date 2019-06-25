package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DatabaseReference;
import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.DateUtils.DateUtils;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.FileInputOutPutStream;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 3/18/2018.
 */

public class ItemMessagePhotoUserHolderClass {
    private ImageView messageStatus;
    private EmojiconTextView mImageFileSize;
    private ImageView mCancelUploadButton;
    public Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private EmojiconTextView mDurationPercent;
    private RelativeLayout mLayoutUplodImageBack;
    private LinearLayout mLaoutUserPhotoSizeControl;
    private LinearLayout mUploadIconImage;
    private ProgressBar mProgressLoading;
    private CircleProgressBar mProcessProgressLoading;
    private LinearLayout userPhotoContaner;
    private LinearLayout mLayoutMessageRunder;
    private EmojiconTextView txtMessageTimeUser;
    private ImageView imgMessageUser;
    private LinearLayout mLayoutDateContainer;
    private CircleImageView mDateIconBihind;
    private CircleImageView mDateIconFront;
    private EmojiconChatTextView mTextDate;
    private View itemView;

    public ItemMessagePhotoUserHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, EmojiconTextView mImageSize, EmojiconTextView mDurationPercent, RelativeLayout mLayoutUplodImageBack, LinearLayout mLaoutUserPhotoSizeControl, LinearLayout mUploadIconImage, ProgressBar mProgressLoading, CircleProgressBar mProcessProgressLoading, LinearLayout userPhotoContaner, LinearLayout mLayoutMessageRunder, EmojiconTextView txtMessageTimeUser, ImageView imgMessageUser, ImageView mCancelUploadButton, LinearLayout mLayoutDateContainer, CircleImageView mDateIconBehind, CircleImageView mDateIconFront, EmojiconChatTextView mTextDate, ImageView mMessageStatus, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.src = src;
        this.position = position;
        this.mImageFileSize=mImageSize;
        this.mDurationPercent = mDurationPercent;
        this.mLayoutUplodImageBack = mLayoutUplodImageBack;
        this.mLaoutUserPhotoSizeControl=mLaoutUserPhotoSizeControl;
        this.mUploadIconImage = mUploadIconImage;
        this.mProgressLoading = mProgressLoading;
        this.mProcessProgressLoading=mProcessProgressLoading;
        this.userPhotoContaner = userPhotoContaner;
        this.mLayoutMessageRunder = mLayoutMessageRunder;
        this.txtMessageTimeUser = txtMessageTimeUser;
        this.imgMessageUser = imgMessageUser;
        this.itemView = itemView;
        this.mCancelUploadButton=mCancelUploadButton;
        this.mLayoutDateContainer=mLayoutDateContainer;
        this.mDateIconBihind=mDateIconBehind;
        this.mDateIconFront=mDateIconFront;
        this.mTextDate=mTextDate;
        this.messageStatus=mMessageStatus;
    }

    public void setMessagePhotoUserHolder(){
        final String imgUri=consersation.getListMessageData().get(position).PhotoDeviceUrl;
       if (position==0){
                userPhotoContaner.setPadding(0,16,2,2);
                mLayoutMessageRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
            }
           else{
                String idSender=consersation.getListMessageData().get(position-1).idSender;

               if (idSender.equals(ExtractedStrings.UID)){
                        mLayoutMessageRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal_ext);
                        userPhotoContaner.setPadding(0,2,2,2);
                    }
                    else {
                        mLayoutMessageRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
                        userPhotoContaner.setPadding(0,16,2,2);
                    }
        }
        Lparams=mLaoutUserPhotoSizeControl.getLayoutParams();
        int width= ExtractedStrings.DeviceWidth-((ExtractedStrings.DeviceWidth/3));
        // int height=Chat_Activity.Divice_with-((ExtractedStrings.DeviceWidth/5)+2);
        Lparams.height=width;
        Lparams.width=width;
        mLaoutUserPhotoSizeControl.setLayoutParams(Lparams);
        //mLayoutMessageRunder.setPadding(3,0,3,0);
        txtMessageTimeUser.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));

        if (new File(imgUri).exists()){
            String ImageSize= FileInputOutPutStream.getImageSize(new File(imgUri));
            mImageFileSize.setText(ImageSize);
        }else{

        }
        final Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idReceiver);
        if (position==0){
            mLayoutDateContainer.setVisibility(View.VISIBLE);
            if (ExtractedStrings.mProfileImage != null) {
                mDateIconFront.setImageBitmap(currentAvata);
            } else {
                mDateIconFront.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
            }
            if (ExtractedStrings.mProfileImage!=null){
                mDateIconBihind.setImageBitmap(ExtractedStrings.mProfileImage);
            }else {
                mDateIconBihind.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
            }
            mTextDate.setText(DateUtils.getChatTimeDate(consersation.getListMessageData().get(position).timestamp));
        }else {
            if (!DateUtils.hasSameDate(consersation.getListMessageData().get(position).timestamp, consersation.getListMessageData().get(position-1).timestamp)) {
                mLayoutDateContainer.setVisibility(View.VISIBLE);
                mLayoutMessageRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
                mTextDate.setText(DateUtils.getChatTimeDate(consersation.getListMessageData().get(position).timestamp));
                if (ExtractedStrings.mProfileImage != null) {
                    mDateIconFront.setImageBitmap(currentAvata);
                } else {
                    mDateIconFront.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                }
                if (ExtractedStrings.mProfileImage!=null){
                    mDateIconBihind.setImageBitmap(ExtractedStrings.mProfileImage);
                }else {
                    mDateIconBihind.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                }
            }else {
                mLayoutDateContainer.setVisibility(View.GONE);
            }
        }
        String isMediaUploaded=consersation.getListMessageData().get(position).AnyMediaStatus;
        if (isMediaUploaded.equals(ExtractedStrings.MEDIA_NOT_UPLOADED)){
                mLayoutUplodImageBack.setVisibility(View.VISIBLE);
                mUploadIconImage.setVisibility(View.VISIBLE);
                mProgressLoading.setVisibility(View.GONE);
                mProcessProgressLoading.setVisibility(View.GONE);
                mCancelUploadButton.setVisibility(View.GONE);
            }
            else{
                mLayoutUplodImageBack.setVisibility(View.GONE);
                mUploadIconImage.setVisibility(View.GONE);
                mProgressLoading.setVisibility(View.GONE);
                mProcessProgressLoading.setVisibility(View.GONE);
            }
        try {
            File image=new File(imgUri);
            if(image.exists()){
             Glide.with(context).load(imgUri).apply(new RequestOptions().optionalFitCenter().placeholder(R.drawable.loading_video_before).centerCrop()).into(imgMessageUser);
           //     Bitmap bitmap = Glide.with(context).asBitmap().apply(new RequestOptions().optionalFitCenter().centerCrop().fitCenter().centerInside()).load(imgUri).submit().get();
            }else{
                Glide.with(context).load(R.drawable.loading_video_before).apply(new RequestOptions().optionalFitCenter().placeholder(R.drawable.loading_video_before).centerCrop()).into(imgMessageUser);
            }
        }catch (Exception e){
            Toast.makeText(context, "In Item Message User Holder \n"+e.getMessage()+ "\n" +e.getCause(), Toast.LENGTH_SHORT).show();
        }

        if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SAVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_storage);
        }else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SENT)) {
            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_server);
        }
        else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_RECEIVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_received_by_user);
        }
    }
}
