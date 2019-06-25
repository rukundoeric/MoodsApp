package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessageContactFriendHolder extends RecyclerView.ViewHolder {

    public EmojiconTextView mViewConatct;
    public EmojiconTextView mTextContactView;
    public EmojiconTextView mTextContactTime;
    public CircleImageView mThirdContactPhoto;
    public CircleImageView mFirstContactPhoto;
    public CircleImageView  mSecondContactPhoto;
    public CircleImageView avata;
    public LinearLayout mLayoutContaner;
    public LinearLayout mLayoutContactRunder;
    public ItemMessageContactFriendHolder(final View itemView) {
        super(itemView);

        avata=(CircleImageView)itemView.findViewById(R.id.image_icon_for_contact_friend_chat);
        mLayoutContactRunder=(LinearLayout) itemView.findViewById(R.id.contact_message_friend_wallpaper);

        mLayoutContaner=(LinearLayout)itemView.findViewById(R.id.layout_for_friend_chat_contact_container);
        mTextContactView=(EmojiconTextView)itemView.findViewById(R.id.contact_number_view_in_contact_item_friend);
        mTextContactTime=(EmojiconTextView)itemView.findViewById(R.id.time_message_image_for_friend_contact);

        mThirdContactPhoto=(CircleImageView)itemView.findViewById(R.id.third_image_contact_in_contact_item_for_friend);
        mSecondContactPhoto=(CircleImageView)itemView.findViewById(R.id.second_image_contact_in_contact_item_for_friend);
        mFirstContactPhoto=(CircleImageView)itemView.findViewById(R.id.first_image_contact_in_contact_item_for_friend);

        mViewConatct=(EmojiconTextView)itemView.findViewById(R.id.view_all_contact_in_contact_item_for_friend);

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
        mViewConatct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onContactViewClick(itemView, getPosition());
            }
        });
    }
}
