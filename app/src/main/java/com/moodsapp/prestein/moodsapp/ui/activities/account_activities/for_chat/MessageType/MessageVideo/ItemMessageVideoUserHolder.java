package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

public class ItemMessageVideoUserHolder extends RecyclerView.ViewHolder {
    public ImageView mMessageStatus;
    public ImageView mCancelUploadButton;
    public RelativeLayout mProcessProgressLoadingBack;
    public CircleProgressBar mProcessProgressLoading;
    public LinearLayout mPlayVideoBack;
    public RelativeLayout mUploadIconsImageBack;
    public LinearLayout mlayoutUserVideoSizeControl;
    public ImageView mPlayVideo;
    /*public LinearLayout mLayoutUplodImageBack;*/
    public ImageView mUploadIconImage;
    public ProgressBar mProgressLoading;
    public LinearLayout userPhotoContaner;
    public LinearLayout mLayoutMessageRunder;
    public EmojiconTextView txtMessageTimeUser;
    public ImageView imgMessageUser;
    public ItemMessageVideoUserHolder(final View itemView) {
        super(itemView);
        imgMessageUser=(ImageView)itemView.findViewById(R.id.message_video_for_user);
        txtMessageTimeUser=(EmojiconTextView)itemView.findViewById(R.id.time_message_video_for_user);
        mLayoutMessageRunder=(LinearLayout) itemView.findViewById(R.id.layout_message_photo_rounder_for_user);
        userPhotoContaner=(LinearLayout)itemView.findViewById(R.id.item_message_user_video_container);
        mlayoutUserVideoSizeControl=(LinearLayout)itemView.findViewById(R.id.mLayoutUserVideoSizeControl);
        // mLayoutUplodImageBack=(LinearLayout)itemView.findViewById(R.id.layout_retry_back_for_item_user);
        mUploadIconImage=(ImageView)itemView.findViewById(R.id.upload_video_in_item_user);
        mProgressLoading=(ProgressBar)itemView.findViewById(R.id.lauding_progress_upload_video_user);
        mPlayVideo=(ImageView)itemView.findViewById(R.id.play_video_in_item_user);
        mPlayVideoBack=(LinearLayout)itemView.findViewById(R.id.layout_back_for_any_user_video_play_button);
        mUploadIconsImageBack=(RelativeLayout)itemView.findViewById(R.id.layout_back_for_any_user_video_upload_content_buttons);
        mProcessProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.lauding_proccess_progress_upload_video_user);
        mCancelUploadButton=(ImageView)itemView.findViewById(R.id.cancel_upload_video_in_item_user);

        mMessageStatus=(ImageView)itemView.findViewById(R.id.message_status_for_user_video_message);


        mPlayVideoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVideoMessageUserClicked(itemView,getPosition());
            }
        });
        imgMessageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVideoMessageUserClicked(itemView,getPosition());
            }
        });
        mPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVideoMessageUserClicked(itemView,getPosition());
            }
        });
        mCancelUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onVideoUploadCancelClicked(itemView,getPosition());
            }
        });
        mUploadIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listeners.mOnItemChatClickedListener.onVideoUploadClicked(itemView,getPosition());
            }
        });
        mUploadIconsImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadIconImage.getVisibility()==View.VISIBLE){
                    Listeners.mOnItemChatClickedListener.onVideoUploadClicked(itemView,getPosition());
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
