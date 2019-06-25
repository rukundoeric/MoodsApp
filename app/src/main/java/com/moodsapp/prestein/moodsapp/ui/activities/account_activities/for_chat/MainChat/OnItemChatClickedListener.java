package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat;

import android.view.View;

public interface OnItemChatClickedListener {
    void onItemChatClick(View view, int position);
    void onVoiceUploadClicked(View view,int position);
    void onImageUploadClicked(View view,int position);
    void onImageDownloadClicked(View view,int position);
    void onVoiceDownloadClicked(View view,int position);
    void onVideoUploadClicked(View view,int position);
    void onVideoDownloadClicked(View view,int position);
    void onApkUploadClicked(View view,int position);
    void onApkDownloadClicked(View view,int position);
    void onDocumentUploadClicked(View view,int position);
    void onDocumentDownloadClicked(View view, int position);
    void onImageUploadCancelClicked(View view,int position);
    void onImageDownloadCancelClicked(View view,int position);
    void onVoiceUploadCancelClicked(View view,int position);
    void onVoiceDownloadCancelClicked(View view, int position);
    void onVideoUploadCancelClicked(View view,int position);
    void onVideoDownloadCancelClicked(View view,int position);
    void onAudioPlayButtonClicked(View view, int position);
    void onReceivedVoicePlayClicked(View itemView, int position);

    void onImageMessageUserClicked(View itemView, int position);
    void onImageMessageFriendClicked(View itemView, int position);

    void onVideoMessageUserClicked(View itemView, int position);
    void onVideoMessageFrienClicked(View itemView , int position);

    void onOpenDocCliked(View itemView, int position);

    void onContactViewClick(View itemView, int position);
}
