package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessageFriendHolder extends RecyclerView.ViewHolder {

    public CircleImageView replayer_Image;
    public LinearLayout mLayoutDateContainer;
    public CircleImageView mDateIconFront;
    public CircleImageView mDateIconBehind;
    public EmojiconChatTextView mTextDate;
    public EmojiconTextView replayer_title;
    public EmojiconTextView replayer_message;
    public LinearLayout replayer_layout_container;
    public LinearLayout message_friend_wallpaper;
    public EmojiconTextView any_mesage_time;
    public LinearLayout friend_row_container;
    public EmojiconChatTextView txtContent;
    public CircleImageView avata;
    public ItemMessageFriendHolder(View itemView) {
        super(itemView);
        txtContent = (EmojiconChatTextView) itemView.findViewById(R.id.textContentFriend);
        avata = (CircleImageView) itemView.findViewById(R.id.image_icon_for_friend_chat);
        friend_row_container=(LinearLayout)itemView.findViewById(R.id.layout_for_friend_chat_container);
        any_mesage_time=(EmojiconTextView)itemView.findViewById(R.id.any_message_time_in_friend_text_message);
        message_friend_wallpaper=(LinearLayout)itemView.findViewById(R.id.message_friend_wallpaper);
        replayer_layout_container=(LinearLayout)itemView.findViewById(R.id.reply_layout_profile_information_in_item_message_friend);
        replayer_title=(EmojiconTextView)itemView.findViewById(R.id.message_title_reply_in_item_message_friend);
        replayer_message=(EmojiconTextView)itemView.findViewById(R.id.message_reply_in_item_message_friend);
        replayer_Image=(CircleImageView)itemView.findViewById(R.id.image_reply_profile_pic_in_message_friend);
        mLayoutDateContainer=(LinearLayout)itemView.findViewById(R.id.layout_message_date_for_text_message_friend);
        mDateIconFront=(CircleImageView)itemView.findViewById(R.id.date_front_icon_for_text_friend_message);
        mDateIconBehind=(CircleImageView)itemView.findViewById(R.id.date_behind_icon_for_text_friend_message);
        mTextDate = (EmojiconChatTextView) itemView.findViewById(R.id.date_text_for_message_friend);
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
