package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

//Message Item View Type
public class ItemMessageUserHolder extends RecyclerView.ViewHolder {
    public CircleImageView replayer_image;
    public ImageView mMessageStatus;
    public EmojiconChatTextView mTextDate;
    public LinearLayout mLayoutDateContainer;
    public CircleImageView mDateIconFront;
    public CircleImageView mDateIconBehind;
    public LinearLayout mLayout_message_looper;
    public EmojiconTextView replayer_title;
    public EmojiconTextView replayer_message;
    public LinearLayout replayer_layout_container;
    public LinearLayout message_user_wallpaper;
    public EmojiconTextView any_user_message_time;
    public LinearLayout user_row_container;
    public EmojiconChatTextView txtContent;
    public CircleImageView avata;

    public ItemMessageUserHolder(View itemView) {
        super(itemView);
        txtContent = (EmojiconChatTextView) itemView.findViewById(R.id.textContentUser);
        user_row_container=(LinearLayout)itemView.findViewById(R.id.layout1);
        any_user_message_time=(EmojiconTextView)itemView.findViewById(R.id.any_user_message_time);
        message_user_wallpaper=(LinearLayout)itemView.findViewById(R.id.message_user_wallpaper);
        replayer_layout_container=(LinearLayout) itemView.findViewById(R.id.reply_layout_profile_information_in_item_message_user);
        replayer_title=(EmojiconTextView)itemView.findViewById(R.id.message_title_reply_in_item_message_user);
        replayer_message=(EmojiconTextView)itemView.findViewById(R.id.message_reply_in_item_message_user);
        replayer_image=(CircleImageView)itemView.findViewById(R.id.image_reply_profile_pic_in_message_user);
        /*mLayout_message_looper=(LinearLayout)itemView.findViewById(R.id.layout_message_looper);
*/
        mLayoutDateContainer=(LinearLayout)itemView.findViewById(R.id.layout_message_date_for_text_message_user);
        mDateIconFront=(CircleImageView)itemView.findViewById(R.id.date_front_icon_for_text_user_message);
        mDateIconBehind=(CircleImageView)itemView.findViewById(R.id.date_behind_icon_for_text_user_message);
        mTextDate = (EmojiconChatTextView) itemView.findViewById(R.id.date_text_for_message_user);

        mMessageStatus=(ImageView)itemView.findViewById(R.id.message_status_for_user_text_message);

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
