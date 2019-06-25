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

public class ItemMessageDocumentUserHolder extends RecyclerView.ViewHolder {
    public RoundedImageView mDocPageFirstBackPageView;
    public ImageView mMessageStatus;
    public ImageView mCancelUploadButton;
    public RoundedImageView mDocPageFirstPageView;
    public LinearLayout mLayoutDocContainer;
    public LinearLayout mLayoutDocRounder;
    public EmojiconTextView mDocTime;
    public ImageView mDocIconImage;
    public EmojiconTextView mDocName;
    public EmojiconTextView mDocDetails;
    public ImageView mDocUploadIcon;
    public ProgressBar mDocLoading;
    public CircleProgressBar mDocProcessProgressLoading;

    public ItemMessageDocumentUserHolder(final View itemView) {
        super(itemView);
        mDocIconImage=(ImageView)itemView.findViewById(R.id.doc_icon_for_document_user);
        mDocName=(EmojiconTextView)itemView.findViewById(R.id.doc_name_text_view_for_document_user);
        mDocDetails=(EmojiconTextView)itemView.findViewById(R.id.doc_details_for_document_user);
        mDocTime=(EmojiconTextView)itemView.findViewById(R.id.time_message_image_for_user_document);

        mLayoutDocContainer=(LinearLayout)itemView.findViewById(R.id.item_message_user_document_container);
        mLayoutDocRounder=(LinearLayout)itemView.findViewById(R.id.layout_message_document_rounder_for_user);

        mDocUploadIcon=(ImageView)itemView.findViewById(R.id.upload_icon_for_item_user_document);
        mDocLoading=(ProgressBar)itemView.findViewById(R.id.progress_for_document_uploading_in_item_user);
        mDocProcessProgressLoading=(CircleProgressBar)itemView.findViewById(R.id.lauding_progress_download_document_user);

        mDocPageFirstPageView=(RoundedImageView)itemView.findViewById(R.id.pdf_first_page_view_in_document_user);
        mDocPageFirstBackPageView=(RoundedImageView)itemView.findViewById(R.id.pdf_first_page_background_view_in_document_user);

        mMessageStatus=(ImageView)itemView.findViewById(R.id.message_status_for_user_document_message);

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
        mDocUploadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onDocumentUploadClicked(itemView,getPosition());
            }
        });
        mLayoutDocRounder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listeners.mOnItemChatClickedListener.onOpenDocCliked(itemView, getPosition());
            }
        });
    }
}
