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
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessageVoiceFriendHolder extends RecyclerView.ViewHolder {

    public ImageView mCancelVoiceDownload;
    public LinearLayout mLayoutDateContainer;
    public CircleImageView mDateIconFront;
    public CircleImageView mDateIconBehind;
    public EmojiconChatTextView mTextDate;
    public CircleImageView avataVoiceMessageIcon;
    public ImageView mDownloadVoiceIcon;
    public ProgressBar mInterCheckDownload;
    public CircleProgressBar mDownloadLoading;
    public RelativeLayout mAlreadDownloadedIcon;
    public ImageView mRecorederIcon;

    public CircleImageView avata;
    public SeekBar mSeekbarAudioPlay;
    public ImageView mPlayAudioRecorded;
    public LinearLayout mLayoutContaner;
    public LinearLayout mLayoutVoiceRunder;
    public TextView txtMessageTimeUser;
    public ItemMessageVoiceFriendHolder(final View itemView) {
        super(itemView);

        avata=(CircleImageView)itemView.findViewById(R.id.image_icon_for_voice_friend_chat);
        avataVoiceMessageIcon=(CircleImageView)itemView.findViewById(R.id.image_icon_for_voice_friend_chat_message);
        txtMessageTimeUser=(TextView)itemView.findViewById(R.id.any_message_time_in_voice_friend_text_message);
        mLayoutVoiceRunder=(LinearLayout) itemView.findViewById(R.id.voice_message_friend_wallpaper);

        mLayoutContaner=(LinearLayout)itemView.findViewById(R.id.layout_for_friend_chat_voice_container);
        mPlayAudioRecorded=(ImageView)itemView.findViewById(R.id.play_voice_button_in_friend);
        mSeekbarAudioPlay=(SeekBar)itemView.findViewById(R.id.in_chat_item_voice_message_SeekBar_for_friend);

        //Icons ref
        mDownloadVoiceIcon=(ImageView)itemView.findViewById(R.id.download_icon_for_item_friend_voice);
        mInterCheckDownload=(ProgressBar)itemView.findViewById(R.id.interCheck_progress_for_voice_downloading_in_item_friend_voice);
        mAlreadDownloadedIcon=(RelativeLayout)itemView.findViewById(R.id.voice_alread_downloaded_icon_in_friend);
        mDownloadLoading=(CircleProgressBar)itemView.findViewById(R.id.auding_progress_download_voice_friend);

      /*  mCancelVoiceDownload=(ImageView)itemView.findViewById(R.id.cancel_download_voice_icon_for_item_friend_voice);
*/
        mLayoutDateContainer=(LinearLayout)itemView.findViewById(R.id.layout_message_date_for_voice_message_friend);
        mDateIconFront=(CircleImageView)itemView.findViewById(R.id.date_front_icon_for_voice_friend_message);
        mDateIconBehind=(CircleImageView)itemView.findViewById(R.id.date_behind_icon_for_voice_friend_message);
        mTextDate = (EmojiconChatTextView) itemView.findViewById(R.id.date_voice_for_message_friend);

        mDownloadVoiceIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVoiceDownloadClicked(itemView, getPosition());
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
                Listeners.mOnItemChatClickedListener.onReceivedVoicePlayClicked(itemView, getPosition());
            }
        });
    }
}
