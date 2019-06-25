package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

//Photoo Item View Type
public class ItemMessagePhotoFriendHolder extends RecyclerView.ViewHolder {


    public LinearLayout mLayoutDateContainer;
    public CircleImageView mDateIconFront;
    public CircleImageView mDateIconBehind;
    public EmojiconChatTextView mTextDate;
    public ImageView mCancelDownloadButton;
    public LinearLayout mLayoutSizeControl;
    public ProgressBar mProgressLoadingCkeckInternet;
    public TextView mDurationPercent;
    public RelativeLayout mLayoutDownloadImageBack;
    public ImageView mDownloadIconImage;
    public CircleProgressBar mProgressLoading;
    public LinearLayout mLayoutChatRounder;
    public LinearLayout mLayoutChatContainer;
    public EmojiconTextView txtMessageTimeFriend;
    public RoundedImageView imgMessageFriend;
    public CircleImageView Photoavata;

    public ItemMessagePhotoFriendHolder(final View itemView) {
        super(itemView);
        imgMessageFriend=(RoundedImageView)itemView.findViewById(R.id.message_image_for_friend);
        txtMessageTimeFriend=(EmojiconTextView) itemView.findViewById(R.id.time_message_image_for_friend);
        Photoavata = (CircleImageView) itemView.findViewById(R.id.image_profile_friend_for_message_photo);
        mLayoutChatContainer=(LinearLayout)itemView.findViewById(R.id.layout3_for_photo_message);
        mLayoutChatRounder=(LinearLayout)itemView.findViewById(R.id.layout_message_photo_rounder_for_friend);
        mLayoutSizeControl=(LinearLayout)itemView.findViewById(R.id.layoutSizeControl_for_image_friend);
        mLayoutDownloadImageBack=(RelativeLayout)itemView.findViewById(R.id.layout_retry_back_for_item_friend);
        mDownloadIconImage=(ImageView)itemView.findViewById(R.id.download_image_in_item_friend);
        mProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.lauding_progress_upload_image_friend);
        mProgressLoadingCkeckInternet=(ProgressBar)itemView.findViewById(R.id.check_lauding_progress_upload_image_friend);
        mCancelDownloadButton=(ImageView)itemView.findViewById(R.id.cancel_download_image_in_item_friend);

        mLayoutDateContainer=(LinearLayout)itemView.findViewById(R.id.layout_message_date_for_photo_message_friend);
        mDateIconFront=(CircleImageView)itemView.findViewById(R.id.date_front_icon_for_photo_friend_message);
        mDateIconBehind=(CircleImageView)itemView.findViewById(R.id.date_behind_icon_for_photo_friend_message);
        mTextDate = (EmojiconChatTextView) itemView.findViewById(R.id.date_photo_for_message_friend);

        imgMessageFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onImageMessageFriendClicked(itemView,getPosition());
            }
        });
        mDownloadIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onImageDownloadClicked(itemView,getPosition());
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
        mCancelDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onImageDownloadCancelClicked(itemView,getPosition());
            }
        });
    }
}
