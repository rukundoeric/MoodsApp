package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Listeners;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessageDocumentFriendHolder extends RecyclerView.ViewHolder {
    public RoundedImageView mDocPageFirstPageView;
    public ImageView mCancelDownloadButton;
    public CircleImageView avata;
    public LinearLayout mLayoutDocContainer;
    public LinearLayout mLayoutDocRounder;
    public EmojiconTextView mDocTime;
    public ImageView mDocIconImage;
    public EmojiconTextView mDocName;
    public EmojiconTextView mDocDetails;
    public ImageView mDocDownloadIcon;
    public ProgressBar mDocLoading;
    public CircleProgressBar mDocProcessProgressLoading;

    public ItemMessageDocumentFriendHolder(final View itemView) {
        super(itemView);
        avata=(CircleImageView) itemView.findViewById(R.id.image_icon_for_document_friend_chat);

        mDocIconImage=(ImageView)itemView.findViewById(R.id.doc_icon_for_document_friend);
        mDocName=(EmojiconTextView)itemView.findViewById(R.id.doc_name_text_view_for_document_friend);
        mDocDetails=(EmojiconTextView)itemView.findViewById(R.id.doc_details_for_document_friend);
        mDocTime=(EmojiconTextView)itemView.findViewById(R.id.any_message_time_in_document_friend_text_message);

        mLayoutDocContainer=(LinearLayout)itemView.findViewById(R.id.layout_for_friend_chat_document_container);
        mLayoutDocRounder=(LinearLayout)itemView.findViewById(R.id.layout_message_document_rounder_for_friend);

        mDocDownloadIcon=(ImageView)itemView.findViewById(R.id.download_icon_for_item_friend_document);
        mDocLoading=(ProgressBar)itemView.findViewById(R.id.progress_for_document_downloading_in_item_friend);
        mDocProcessProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.lauding_progress_download_document_friend);

       /* mCancelDownloadButton=(ImageView)itemView.findViewById(R.id.cancel_download_icon_for_item_friend_document);
*/
        mDocPageFirstPageView=(RoundedImageView)itemView.findViewById(R.id.pdf_first_page_view_in_document_friend);
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
        mDocDownloadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onDocumentDownloadClicked(itemView,getPosition());
            }
        });
    }
}
