package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

public class ItemMessageApkUserHolder extends RecyclerView.ViewHolder {
    public ImageView mMessageStatus;
    public LinearLayout mLayoutApkContainer;
    public LinearLayout mLayoutApkRounder;
    public EmojiconTextView mApkTime;
    public ImageView mApkIconImage;
    public EmojiconTextView mApkName;
    public EmojiconTextView mApkDetails;
    public ImageView mApkUploadIcon;
    public ProgressBar mApkLoading;
    public CircleProgressBar mApkProcessProgressLoading;

    public ItemMessageApkUserHolder(View itemView) {
        super(itemView);
        mApkIconImage=(ImageView)itemView.findViewById(R.id.app_icon_for_apk_user);
        mApkName=(EmojiconTextView)itemView.findViewById(R.id.app_name_text_view_for_apk_user);
        mApkDetails=(EmojiconTextView)itemView.findViewById(R.id.app_details_for_apk_user);
        mApkTime=(EmojiconTextView)itemView.findViewById(R.id.time_message_image_for_user_apk);

        mLayoutApkContainer=(LinearLayout)itemView.findViewById(R.id.item_message_user_apk_container);
        mLayoutApkRounder=(LinearLayout)itemView.findViewById(R.id.layout_message_apk_rounder_for_user);

        mApkUploadIcon=(ImageView)itemView.findViewById(R.id.upload_icon_for_item_user_apk);
        mApkLoading=(ProgressBar)itemView.findViewById(R.id.progress_for_apk_uploading_in_item_user);
        mApkProcessProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.lauding_progress_download_apk_user);

        mMessageStatus=(ImageView)itemView.findViewById(R.id.message_status_for_user_apk_message);

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
