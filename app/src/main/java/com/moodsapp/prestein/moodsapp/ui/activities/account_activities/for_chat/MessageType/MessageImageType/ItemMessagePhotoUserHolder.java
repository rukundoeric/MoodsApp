package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessagePhotoUserHolder extends RecyclerView.ViewHolder {

    public ImageView mMessageStatus;
    public LinearLayout mLayoutDateContainer;
    public CircleImageView mDateIconFront;
    public CircleImageView mDateIconBehind;
    public EmojiconChatTextView mTextDate;
    public EmojiconTextView mImageSize;
    public ImageView mCancelUploadButton;
    public CircleProgressBar mProcessProgressLoading;
    public LinearLayout mLaoutUserPhotoSizeControl;
    public EmojiconTextView mDurationPercent;
    public RelativeLayout mLayoutUplodImageBack;
    public LinearLayout mUploadIconImage;
    public ProgressBar mProgressLoading;
    public LinearLayout userPhotoContaner;
    public LinearLayout mLayoutMessageRunder;
    public EmojiconTextView txtMessageTimeUser;
    public ImageView imgMessageUser;
    public ItemMessagePhotoUserHolder(final View itemView) {
        super(itemView);
        imgMessageUser=(ImageView)itemView.findViewById(R.id.message_image_for_user);
        txtMessageTimeUser=(EmojiconTextView)itemView.findViewById(R.id.time_message_image_for_user);
        mLayoutMessageRunder=(LinearLayout) itemView.findViewById(R.id.layout_message_photo_rounder_for_user);
        userPhotoContaner=(LinearLayout)itemView.findViewById(R.id.item_message_user__photo_container);
        mLaoutUserPhotoSizeControl=(LinearLayout)itemView.findViewById(R.id.layoutUserPhotoSizeControl);
        mImageSize=(EmojiconTextView)itemView.findViewById(R.id.upload_image_file_size_for_user);
        mLayoutUplodImageBack=(RelativeLayout)itemView.findViewById(R.id.layout_retry_back_for_item_user);
        mUploadIconImage=(LinearLayout)itemView.findViewById(R.id.upload_image_in_item_user);
        mProgressLoading=(ProgressBar)itemView.findViewById(R.id.lauding_progress_upload_image_user);
        mProcessProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.lauding_proccess_progress_upload_image_user);
        mCancelUploadButton=(ImageView)itemView.findViewById(R.id.cancel_upload_image_in_item_user);

        mLayoutDateContainer=(LinearLayout)itemView.findViewById(R.id.layout_message_date_for_photo_message_user);
        mDateIconFront=(CircleImageView)itemView.findViewById(R.id.date_front_icon_for_photo_user_message);
        mDateIconBehind=(CircleImageView)itemView.findViewById(R.id.date_behind_icon_for_photo_user_message);
        mTextDate = (EmojiconChatTextView) itemView.findViewById(R.id.date_photo_for_message_user);

        mMessageStatus=(ImageView)itemView.findViewById(R.id.message_status_for_user_photo_message);


        imgMessageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onImageMessageUserClicked(itemView,getPosition());
            }
        });
        mUploadIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onImageUploadClicked(itemView, getPosition());
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
        mCancelUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onImageUploadCancelClicked(itemView,getPosition());
            }
        });
 }
}
