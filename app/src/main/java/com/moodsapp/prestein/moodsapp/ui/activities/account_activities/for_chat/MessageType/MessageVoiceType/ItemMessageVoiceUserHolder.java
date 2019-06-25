package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

//Voice Item View Type
public class ItemMessageVoiceUserHolder extends RecyclerView.ViewHolder {
    public ImageView mMessageStatus;
    public LinearLayout mLayoutDateContainer;
    public CircleImageView mDateIconFront;
    public CircleImageView mDateIconBehind;
    public EmojiconChatTextView mTextDate;
    public ImageView mCancelUploadVoice;
    public CircleImageView voiceAvata;
    public CircleProgressBar mProcessProgressLoading;
    public TextView mRecordTimeDuration;
    public ProgressBar mLoadingUpload;
    public ImageView mUploadIcon;
    public RelativeLayout mRecorederIcon;
    public SeekBar mSeekbarAudioPlay;
    public ImageView mPlayAudioRecorded;
    public LinearLayout mLayoutContaner;
    public CircleImageView mImageReplyer;
    public EmojiconTextView mTextMessage;
    public LinearLayout mLayoutReplyer;
    public LinearLayout mLayoutVoiceRunder;
    public TextView txtMessageTimeUser;
    public ItemMessageVoiceUserHolder(final View itemView) {
        super(itemView);
        voiceAvata=(CircleImageView)itemView.findViewById(R.id.image_icon_for_voice_user_chat_message);
        txtMessageTimeUser=(TextView)itemView.findViewById(R.id.time_message_image_for_user_voice);
        mLayoutVoiceRunder=(LinearLayout) itemView.findViewById(R.id.layout_message_voice_rounder_for_user);
        mLayoutReplyer=(LinearLayout)itemView.findViewById(R.id.reply_layout_profile_information_in_item_message_user);
        mLayoutContaner=(LinearLayout)itemView.findViewById(R.id.item_message_user_voice_container);
        mPlayAudioRecorded=(ImageView)itemView.findViewById(R.id.play_button_in_chat_message_voice_for_user);
        mSeekbarAudioPlay=(SeekBar)itemView.findViewById(R.id.in_chat_item_voice_message_SeekBar_for_user);
        mRecorederIcon=(RelativeLayout)itemView.findViewById(R.id.record_icon_for_uploaded_voice_in_item_user_voice);
        mUploadIcon=(ImageView)itemView.findViewById(R.id.upload_icon_for_item_user_voice);
        mLoadingUpload=(ProgressBar)itemView.findViewById(R.id.progress_for_voice_uploading_in_item_user_foice);
        mProcessProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.auding_progress_download_voice_user);
        mRecordTimeDuration=(TextView)itemView.findViewById(R.id.time_duration_for_user_voice);


        mLayoutDateContainer=(LinearLayout)itemView.findViewById(R.id.layout_message_date_for_voice_message_user);
        mDateIconFront=(CircleImageView)itemView.findViewById(R.id.date_front_icon_for_voice_user_message);
        mDateIconBehind=(CircleImageView)itemView.findViewById(R.id.date_behind_icon_for_voice_user_message);
        mTextDate = (EmojiconChatTextView) itemView.findViewById(R.id.date_voice_for_message_user);


        mMessageStatus=(ImageView)itemView.findViewById(R.id.message_status_for_user_voice_message);


        mUploadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVoiceUploadClicked(itemView, getPosition());
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onItemChatClick(v, getPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Listeners.mOnItemChatLongClickedListener.onItemChatItemLongClick(v, getPosition());
                return false;
            }
        });
        mPlayAudioRecorded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onAudioPlayButtonClicked(itemView, getPosition());
            }
        });

    }
}
