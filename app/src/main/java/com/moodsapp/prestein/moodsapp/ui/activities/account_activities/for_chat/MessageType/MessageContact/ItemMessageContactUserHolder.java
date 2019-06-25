package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

//Voice Item View Type
public class ItemMessageContactUserHolder extends RecyclerView.ViewHolder {

    public ImageView mMessageStatus;
    public LinearLayout mLayoutContactRunder;
    public LinearLayout userContactContainer;
    public EmojiconTextView mContactInfoDisplay;
    public EmojiconTextView mContcatMessageTime;
    public EmojiconTextView mViewAllContactList;
    public CircleImageView mFirstImageContact;
    public CircleImageView mSecondImageContact;
    public CircleImageView mThirdImageContact;

    public ItemMessageContactUserHolder(final View itemView) {
        super(itemView);
        mContactInfoDisplay=(EmojiconTextView)itemView.findViewById(R.id.contact_number_view_in_contact_item_user);
        mContcatMessageTime=(EmojiconTextView)itemView.findViewById(R.id.time_message_image_for_user_contact);
        mViewAllContactList=(EmojiconTextView)itemView.findViewById(R.id.view_all_contact_in_contact_item_for_user);

        mFirstImageContact=(CircleImageView)itemView.findViewById(R.id.first_image_contact_in_contact_item_for_user);
        mSecondImageContact=(CircleImageView)itemView.findViewById(R.id.second_image_contact_in_contact_item_for_user);
        mThirdImageContact=(CircleImageView)itemView.findViewById(R.id.third_image_contact_in_contact_item_for_user);

        userContactContainer=(LinearLayout)itemView.findViewById(R.id.item_message_user_contact_container);
        mLayoutContactRunder=(LinearLayout)itemView.findViewById(R.id.layout_message_contact_rounder_for_user);

        mMessageStatus=(ImageView) itemView.findViewById(R.id.message_status_for_user_contact_message);

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
        mViewAllContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onContactViewClick(itemView, getPosition());
            }
        });
    }
}
