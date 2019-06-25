package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DatabaseReference;
import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.DateUtils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 3/18/2018.
 */

public class ItemMessageVoiceUserHolderClass {
    private ImageView messageStatus;
    private CircleImageView voiceAvata;
    public Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private TextView mRecordTimeDuration;
    private CircleProgressBar mProcessProgressLoading;
    private ProgressBar mLoadingUpload;
    private ImageView mUploadIcon;
    private RelativeLayout mRecorederIcon;
    private SeekBar mSeekbarAudioPlay;
    private ImageView mPlayAudioRecorded;
    private LinearLayout mLayoutContaner;
    private CircleImageView mImageReplyer;
    private EmojiconTextView mTextMessage;
    private LinearLayout mLayoutReplyer;
    private LinearLayout mLayoutVoiceRunder;
    private TextView txtMessageTimeUser;
    private LinearLayout mLayoutDateContainer;
    private CircleImageView mDateIconBihind;
    private CircleImageView mDateIconFront;
    private EmojiconChatTextView mTextDate;
    private View itemView;

    public ItemMessageVoiceUserHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, CircleImageView voiceAvata, TextView mRecordTimeDuration, CircleProgressBar mProcessProgressLoading, ProgressBar mLoadingUpload, ImageView mUploadIcon, RelativeLayout mRecorederIcon, SeekBar mSeekbarAudioPlay, ImageView mPlayAudioRecorded, LinearLayout mLayoutContaner, CircleImageView mImageReplyer, EmojiconTextView mTextMessage, LinearLayout mLayoutReplyer, LinearLayout mLayoutVoiceRunder, TextView txtMessageTimeUser, LinearLayout mLayoutDateContainer, CircleImageView mDateIconBehind, CircleImageView mDateIconFront, EmojiconChatTextView mTextDate, ImageView mMessageStatus, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.src = src;
        this.position = position;
        this.voiceAvata=voiceAvata;
        this.mRecordTimeDuration = mRecordTimeDuration;
        this.mProcessProgressLoading=mProcessProgressLoading;
        this.mLoadingUpload = mLoadingUpload;
        this.mUploadIcon = mUploadIcon;
        this.mRecorederIcon = mRecorederIcon;
        this.mSeekbarAudioPlay = mSeekbarAudioPlay;
        this.mPlayAudioRecorded = mPlayAudioRecorded;
        this.mLayoutContaner = mLayoutContaner;
        this.mImageReplyer = mImageReplyer;
        this.mTextMessage = mTextMessage;
        this.mLayoutReplyer = mLayoutReplyer;
        this.mLayoutVoiceRunder = mLayoutVoiceRunder;
        this.txtMessageTimeUser = txtMessageTimeUser;
        this.itemView = itemView;
        this.mLayoutDateContainer=mLayoutDateContainer;
        this.mDateIconBihind=mDateIconBehind;
        this.mDateIconFront=mDateIconFront;
        this.messageStatus=mMessageStatus;
        this.mTextDate=mTextDate;
    }

    public void setMessageVoiceUserHolder(){
        if (position==0){
            mLayoutContaner.setPadding(ExtractedStrings.DeviceWidth/4,16,2,2);
        }else {
            if (consersation.getListMessageData().get(position-1).idSender.equals(ExtractedStrings.UID)){
                mLayoutVoiceRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal_ext);
                mLayoutContaner.setPadding(ExtractedStrings.DeviceWidth/4,2,2,2);
            }  else {
                mLayoutVoiceRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
                mLayoutContaner.setPadding(ExtractedStrings.DeviceWidth/4,16,2,2);
            }
        }
        try {
            if (ExtractedStrings.mProfileImage==null){
                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default);
                voiceAvata.setImageBitmap(bitmap);
            }else {
                voiceAvata.setImageBitmap(ExtractedStrings.mProfileImage);
            }
            txtMessageTimeUser.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));

        }catch (Exception e){
            Toast.makeText(context, "In Item Message User Holder \n"+e.getMessage()+ "\n" +e.getCause(), Toast.LENGTH_SHORT).show();
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
                mLayoutVoiceRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
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
        if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_NOT_UPLOADED)){
            mRecorederIcon.setVisibility(View.GONE);
            mUploadIcon.setVisibility(View.VISIBLE);
            mLoadingUpload.setVisibility(View.GONE);
            mProcessProgressLoading.setVisibility(View.GONE);
        }else if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_UPLOADED )){
            mRecorederIcon.setVisibility(View.VISIBLE);
            mUploadIcon.setVisibility(View.GONE);
            mLoadingUpload.setVisibility(View.GONE);
            mProcessProgressLoading.setVisibility(View.GONE);
        }
        if (chat_activity.newVoiceIsSent){
            if (position==consersation.getListMessageData().size()-1){
                //UploadVoice();
          }
        }
        mRecordTimeDuration.setText(consersation.getListMessageData().get(position).PhotoStringBase64);
        mSeekbarAudioPlay.setEnabled(false);
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
