package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessageVideoFriendHolder extends RecyclerView.ViewHolder {

    public LinearLayout mLayoutPlayVideoBack;
    public ImageView mPlayVideoButton;
    public CircleImageView avata;
    public ImageView mCancelDownloadVideo;
    public ProgressBar mInterCheckProgressLoading;
    public RelativeLayout mProccessProgressLoadingLayoutBack;
    public RelativeLayout mLayoutDownloadContentBack;
    public LinearLayout mLayoutSizeControl;
    public EmojiconTextView txtVideoMessageTimeFriend;
    public EmojiconTextView txtVideoMessageTimeDurationFriend;
    public ImageView mDownloadIconVideo;
    public CircleProgressBar mProccessProgressLoading;
    public LinearLayout mLayoutChatRounder;
    public LinearLayout mLayoutChatContainer;
    public RoundedImageView videoMessageFriend;


    public ItemMessageVideoFriendHolder(final View itemView) {
        super(itemView);
        avata = (CircleImageView) itemView.findViewById(R.id.video_profile_friend_for_message_video);

        mLayoutChatContainer=(LinearLayout)itemView.findViewById(R.id.layout3_for_video_message);
        mLayoutChatRounder=(LinearLayout)itemView.findViewById(R.id.layout_message_video_rounder_for_friend);
        mLayoutSizeControl=(LinearLayout)itemView.findViewById(R.id.layoutSizeControl_for_video_friend);

        videoMessageFriend=(RoundedImageView)itemView.findViewById(R.id.message_video_for_friend);
        txtVideoMessageTimeFriend=(EmojiconTextView)itemView.findViewById(R.id.time_message_video_for_friend);
        txtVideoMessageTimeDurationFriend=(EmojiconTextView)itemView.findViewById(R.id.time_message_video_for_friend);

        mLayoutPlayVideoBack=(LinearLayout)itemView.findViewById(R.id.layout_back_for_any_friend_video_play_button);
        mPlayVideoButton=(ImageView)itemView.findViewById(R.id.play_video_in_item_friend);

        mLayoutDownloadContentBack=(RelativeLayout)itemView.findViewById(R.id.layout_back_for_any_friend_video_upload_content_buttons);
        mDownloadIconVideo=(ImageView)itemView.findViewById(R.id.upload_video_in_item_friend);
        mCancelDownloadVideo=(ImageView)itemView.findViewById(R.id.cancel_upload_video_in_item_friend);

        mProccessProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.lauding_proccess_progress_upload_video_friend);
        mInterCheckProgressLoading=(ProgressBar)itemView.findViewById(R.id.lauding_progress_upload_video_friend);
        mLayoutPlayVideoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVideoMessageFrienClicked(itemView,getPosition());
            }
        });
        videoMessageFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVideoMessageFrienClicked(itemView,getPosition());
            }
        });
        mPlayVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVideoMessageFrienClicked(itemView,getPosition());
            }
        });
        mCancelDownloadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVideoDownloadCancelClicked(itemView,getPosition());
            }
        });
        mDownloadIconVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listeners.mOnItemChatClickedListener.onVideoDownloadClicked(itemView,getPosition());
            }
        });
        mLayoutDownloadContentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDownloadIconVideo.getVisibility()==View.VISIBLE){
                    Listeners.mOnItemChatClickedListener.onVideoDownloadClicked(itemView,getPosition());
                }
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

    }
}
