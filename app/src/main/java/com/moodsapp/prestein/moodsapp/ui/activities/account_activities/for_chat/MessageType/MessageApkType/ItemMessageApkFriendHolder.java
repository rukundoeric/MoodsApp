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

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessageApkFriendHolder extends RecyclerView.ViewHolder {
    public ImageView mCancelDownloadButton;
    public CircleImageView avata;
    public LinearLayout mLayoutApkContainer;
    public LinearLayout mLayoutApkRounder;
    public EmojiconTextView mApkTime;
    public ImageView mApkIconImage;
    public EmojiconTextView mApkName;
    public EmojiconTextView mApkDetails;
    public ImageView mApkDownloadIcon;
    public ProgressBar mApkLoading;
    public CircleProgressBar mApkProcessProgressLoading;

    public ItemMessageApkFriendHolder(View itemView) {
        super(itemView);
        avata=(CircleImageView) itemView.findViewById(R.id.image_icon_for_apk_friend_chat);

        mApkIconImage=(ImageView)itemView.findViewById(R.id.app_icon_for_apk_friend);
        mApkName=(EmojiconTextView)itemView.findViewById(R.id.app_name_text_view_for_apk_friend);
        mApkDetails=(EmojiconTextView)itemView.findViewById(R.id.app_details_for_apk_friend);
        mApkTime=(EmojiconTextView)itemView.findViewById(R.id.any_message_time_in_apk_friend_text_message);

        mLayoutApkContainer=(LinearLayout)itemView.findViewById(R.id.layout_for_friend_chat_apk_container);
        mLayoutApkRounder=(LinearLayout)itemView.findViewById(R.id.apk_message_friend_wallpaper);

        mApkDownloadIcon=(ImageView)itemView.findViewById(R.id.download_icon_for_item_friend_apk);
        mApkLoading=(ProgressBar)itemView.findViewById(R.id.progress_for_apk_downloading_in_item_friend);
        mApkProcessProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.lauding_progress_download_apk_friend);


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
