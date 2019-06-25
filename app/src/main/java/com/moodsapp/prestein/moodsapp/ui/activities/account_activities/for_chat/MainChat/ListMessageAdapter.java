package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType.ItemMessageApkFriendHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType.ItemMessageApkFriendHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType.ItemMessageApkUserHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType.ItemMessageApkUserHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact.ItemMessageContactFriendHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact.ItemMessageContactFriendHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact.ItemMessageContactUserHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact.ItemMessageContactUserHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument.ItemMessageDocumentFriendHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument.ItemMessageDocumentFriendHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument.ItemMessageDocumentUserHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument.ItemMessageDocumentUserHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType.ItemMessagePhotoFriendHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType.ItemMessagePhotoFriendHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType.ItemMessagePhotoUserHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType.ItemMessagePhotoUserHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType.ItemMessageFriendHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType.ItemMessageFriendHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType.ItemMessageUserHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType.ItemMessageUserHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo.ItemMessageVideoFriendHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo.ItemMessageVideoFriendHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo.ItemMessageVideoUserHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo.ItemMessageVideoUserHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType.ItemMessageVoiceFriendHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType.ItemMessageVoiceFriendHolderClass;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType.ItemMessageVoiceUserHolder;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType.ItemMessageVoiceUserHolderClass;


import java.util.ArrayList;
import java.util.HashMap;

public class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Chat_Activity chat_activity;
    private Context context;
    private ArrayList<Integer> colorList;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    public static ViewGroup.LayoutParams Lparams;

    public ListMessageAdapter(Context context, Consersation consersation, ArrayList<Integer> colors, HashMap<String, Bitmap> bitmapAvata, Bitmap bitmapAvataUser, Chat_Activity chat_activity) {
        this.context = context;
        this.colorList=colors;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataUser = bitmapAvataUser;
        bitmapAvataDB = new HashMap<>();
        this.chat_activity=chat_activity;
        }
    public  void SetOnItemClickListener(OnItemChatClickedListener mItemClickListener) {
        Listeners.mOnItemChatClickedListener = mItemClickListener;
    }
    public  void SetOnItemLongClickListener(OnItemChatLongClickedListener mItemLongClickListener) {
        Listeners.mOnItemChatLongClickedListener = mItemLongClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE:{
                View view_type_friend_message = LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend, parent, false);
                return new ItemMessageFriendHolder(view_type_friend_message);
            }
            case ItemTypeCode.VIEW_TYPE_USER_MESSAGE:{
                View view_type_user_message = LayoutInflater.from(context).inflate(R.layout.rc_item_message_user, parent, false);
                return new ItemMessageUserHolder(view_type_user_message);
            }
            case ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_PHOTO:{
                View view_type_friend_message_photo=LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend_photo, parent, false);
                return new ItemMessagePhotoFriendHolder(view_type_friend_message_photo);
            }
            case ItemTypeCode.VIEW_TYPE_USER_MESSAGE_PHOTO:{
                View view_type_user_message_photo=LayoutInflater.from(context).inflate(R.layout.rc_item_message_user_photo, parent, false);
                return  new ItemMessagePhotoUserHolder(view_type_user_message_photo);
            }
            case ItemTypeCode.VIEW_TYPE_USER_MESSAGE_VOICE:{
                View view_type_user_message_voice=LayoutInflater.from(context).inflate(R.layout.rc_item_message_user_voice, parent, false);
                return  new ItemMessageVoiceUserHolder(view_type_user_message_voice);
            }
            case ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_VOICE:{
                View view_type_friend_message_voice=LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend_voice, parent, false);
                return  new ItemMessageVoiceFriendHolder(view_type_friend_message_voice);
            }
            case ItemTypeCode.VIEW_TYPE_USER_MESSAGE_VIDEO:{
                View view_type_user_message_video=LayoutInflater.from(context).inflate(R.layout.rc_item_message_user_video, parent, false);
                return  new ItemMessageVideoUserHolder(view_type_user_message_video);
            }
            case ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_VIDEO:{
                View view_type_friend_message_video=LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend_video, parent, false);
                return  new ItemMessageVideoFriendHolder(view_type_friend_message_video);
            }
            case ItemTypeCode.VIEW_TYPE_USER_MESSAGE_CONTACT:{
                View view_type_user_message_contact=LayoutInflater.from(context).inflate(R.layout.rc_item_message_user_contact, parent, false);
                return  new ItemMessageContactUserHolder(view_type_user_message_contact);
            }
            case ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_CONTACT:{
                View view_type_friend_message_contact=LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend_contact, parent, false);
                return  new ItemMessageContactFriendHolder(view_type_friend_message_contact);
            }
            case ItemTypeCode.VIEW_TYPE_USER_MESSAGE_APK:{
                View view_type_user_message_apk=LayoutInflater.from(context).inflate(R.layout.rc_item_message_user_apk, parent, false);
                return  new ItemMessageApkUserHolder(view_type_user_message_apk);
            }
            case ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_APK:{
                View view_type_friend_message_apk=LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend_apk, parent, false);
                return  new ItemMessageApkFriendHolder(view_type_friend_message_apk);
            }
            case ItemTypeCode.VIEW_TYPE_USER_MESSAGE_DOCUMENT:{
                View view_type_user_message_document=LayoutInflater.from(context).inflate(R.layout.rc_item_message_user_document, parent, false);
                return  new ItemMessageDocumentUserHolder(view_type_user_message_document);
            }
            case ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_DOCUMENT:{
                View view_type_friend_message_document=LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend_document, parent, false);
                return  new ItemMessageDocumentFriendHolder(view_type_friend_message_document);
            }

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        long startTime = System.currentTimeMillis();
        if (holder instanceof ItemMessageFriendHolder) {
            new ItemMessageFriendHolderClass(chat_activity,
                    context,
                    consersation,
                    colorList,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    position,
                    ((ItemMessageFriendHolder) holder).replayer_title,
                    ((ItemMessageFriendHolder) holder).replayer_message,
                    ((ItemMessageFriendHolder) holder).replayer_Image,
                    ((ItemMessageFriendHolder) holder).replayer_layout_container,
                    ((ItemMessageFriendHolder) holder).message_friend_wallpaper,
                    ((ItemMessageFriendHolder) holder).any_mesage_time,
                    ((ItemMessageFriendHolder) holder).friend_row_container,
                    ((ItemMessageFriendHolder) holder).txtContent,
                    ((ItemMessageFriendHolder) holder).avata,

                    ((ItemMessageFriendHolder) holder).mLayoutDateContainer,
                    ((ItemMessageFriendHolder) holder).mDateIconBehind,
                    ((ItemMessageFriendHolder) holder).mDateIconFront,
                    ((ItemMessageFriendHolder) holder).mTextDate,

                    ((ItemMessageFriendHolder) holder).itemView).setMessageUserHolder();

        }
        else if (holder instanceof ItemMessageUserHolder) {
            new ItemMessageUserHolderClass(chat_activity,
                    context,
                    consersation,
                    colorList,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    position,
                    ((ItemMessageUserHolder) holder).replayer_title,
                    ((ItemMessageUserHolder) holder).replayer_message,
                    ((ItemMessageUserHolder) holder).replayer_layout_container,
                    ((ItemMessageUserHolder) holder).message_user_wallpaper,
                    ((ItemMessageUserHolder) holder).any_user_message_time,
                    ((ItemMessageUserHolder) holder).user_row_container,
                    ((ItemMessageUserHolder) holder).txtContent,
                    ((ItemMessageUserHolder) holder).avata,

                    ((ItemMessageUserHolder) holder).mLayoutDateContainer,
                    ((ItemMessageUserHolder) holder).mDateIconBehind,
                    ((ItemMessageUserHolder) holder).mDateIconFront,
                    ((ItemMessageUserHolder) holder).mTextDate,

                    ((ItemMessageUserHolder) holder).mLayout_message_looper,
                    ((ItemMessageUserHolder) holder).mMessageStatus,
                    ((ItemMessageUserHolder) holder).replayer_image,
                    ((ItemMessageUserHolder) holder).itemView).setMessageUserMessageHolder();
        }
        else if (holder instanceof ItemMessagePhotoFriendHolder){

            new ItemMessagePhotoFriendHolderClass(
                    chat_activity,
                    context,
                    consersation,
                    colorList,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessagePhotoFriendHolder) holder).mLayoutDownloadImageBack,
                    ((ItemMessagePhotoFriendHolder) holder).mLayoutSizeControl,
                    ((ItemMessagePhotoFriendHolder) holder).mDownloadIconImage,
                    ((ItemMessagePhotoFriendHolder) holder).mProgressLoading,
                    ((ItemMessagePhotoFriendHolder) holder).mLayoutChatRounder,
                    ((ItemMessagePhotoFriendHolder) holder).mLayoutChatContainer,
                    ((ItemMessagePhotoFriendHolder) holder).txtMessageTimeFriend,
                    ((ItemMessagePhotoFriendHolder) holder).imgMessageFriend,
                    ((ItemMessagePhotoFriendHolder) holder).Photoavata,
                    ((ItemMessagePhotoFriendHolder) holder).mProgressLoadingCkeckInternet,
                    ((ItemMessagePhotoFriendHolder) holder).mCancelDownloadButton,

                    ((ItemMessagePhotoFriendHolder) holder).mLayoutDateContainer,
                    ((ItemMessagePhotoFriendHolder) holder).mDateIconBehind,
                    ((ItemMessagePhotoFriendHolder) holder).mDateIconFront,
                    ((ItemMessagePhotoFriendHolder) holder).mTextDate,

                    ((ItemMessagePhotoFriendHolder) holder).itemView
            ).setMessagePhotoFriendHolder();

        }
        else if (holder instanceof ItemMessagePhotoUserHolder){

            new ItemMessagePhotoUserHolderClass(chat_activity,
                    context,
                    consersation,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessagePhotoUserHolder) holder).mImageSize,
                    ((ItemMessagePhotoUserHolder) holder).mDurationPercent,
                    ((ItemMessagePhotoUserHolder) holder).mLayoutUplodImageBack,
                    ((ItemMessagePhotoUserHolder) holder).mLaoutUserPhotoSizeControl,
                    ((ItemMessagePhotoUserHolder) holder).mUploadIconImage,
                    ((ItemMessagePhotoUserHolder) holder).mProgressLoading,
                    ((ItemMessagePhotoUserHolder) holder).mProcessProgressLoading,
                    ((ItemMessagePhotoUserHolder) holder).userPhotoContaner,
                    ((ItemMessagePhotoUserHolder) holder).mLayoutMessageRunder,
                    ((ItemMessagePhotoUserHolder) holder).txtMessageTimeUser,
                    ((ItemMessagePhotoUserHolder) holder).imgMessageUser,
                    ((ItemMessagePhotoUserHolder) holder).mCancelUploadButton,

                    ((ItemMessagePhotoUserHolder) holder).mLayoutDateContainer,
                    ((ItemMessagePhotoUserHolder) holder).mDateIconBehind,
                    ((ItemMessagePhotoUserHolder) holder).mDateIconFront,
                    ((ItemMessagePhotoUserHolder) holder).mTextDate,
                    ((ItemMessagePhotoUserHolder) holder).mMessageStatus,
                    ((ItemMessagePhotoUserHolder) holder).itemView).setMessagePhotoUserHolder();
        }
        else if (holder instanceof ItemMessageVoiceUserHolder){
            new ItemMessageVoiceUserHolderClass(chat_activity,
                    context,
                    consersation,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageVoiceUserHolder) holder).voiceAvata,
                    ((ItemMessageVoiceUserHolder) holder).mRecordTimeDuration,
                    ((ItemMessageVoiceUserHolder) holder).mProcessProgressLoading,
                    ((ItemMessageVoiceUserHolder) holder).mLoadingUpload,
                    ((ItemMessageVoiceUserHolder) holder).mUploadIcon,
                    ((ItemMessageVoiceUserHolder) holder).mRecorederIcon,
                    ((ItemMessageVoiceUserHolder) holder).mSeekbarAudioPlay,
                    ((ItemMessageVoiceUserHolder) holder).mPlayAudioRecorded,
                    ((ItemMessageVoiceUserHolder) holder).mLayoutContaner,
                    ((ItemMessageVoiceUserHolder) holder).mImageReplyer,
                    ((ItemMessageVoiceUserHolder) holder).mTextMessage,
                    ((ItemMessageVoiceUserHolder) holder).mLayoutReplyer,
                    ((ItemMessageVoiceUserHolder) holder).mLayoutVoiceRunder,
                    ((ItemMessageVoiceUserHolder) holder).txtMessageTimeUser,

                    ((ItemMessageVoiceUserHolder) holder).mLayoutDateContainer,
                    ((ItemMessageVoiceUserHolder) holder).mDateIconBehind,
                    ((ItemMessageVoiceUserHolder) holder).mDateIconFront,
                    ((ItemMessageVoiceUserHolder) holder).mTextDate,
                    ((ItemMessageVoiceUserHolder) holder).mMessageStatus,
                    ((ItemMessageVoiceUserHolder) holder).itemView
                    ).setMessageVoiceUserHolder();

        }
        else if (holder instanceof ItemMessageVoiceFriendHolder){
            new ItemMessageVoiceFriendHolderClass(chat_activity,
                    context,
                    consersation,
                    colorList,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageVoiceFriendHolder) holder).mDownloadLoading,
                    ((ItemMessageVoiceFriendHolder) holder).mAlreadDownloadedIcon,
                    ((ItemMessageVoiceFriendHolder) holder).mInterCheckDownload,
                    ((ItemMessageVoiceFriendHolder) holder).mDownloadVoiceIcon,
                    ((ItemMessageVoiceFriendHolder) holder).avata,
                    ((ItemMessageVoiceFriendHolder) holder).avataVoiceMessageIcon,
                    ((ItemMessageVoiceFriendHolder) holder).mSeekbarAudioPlay,
                    ((ItemMessageVoiceFriendHolder) holder).mPlayAudioRecorded,
                    ((ItemMessageVoiceFriendHolder) holder).mLayoutContaner,
                    ((ItemMessageVoiceFriendHolder) holder).mLayoutVoiceRunder,
                    ((ItemMessageVoiceFriendHolder) holder).txtMessageTimeUser,

                    ((ItemMessageVoiceFriendHolder) holder).mLayoutDateContainer,
                    ((ItemMessageVoiceFriendHolder) holder).mDateIconBehind,
                    ((ItemMessageVoiceFriendHolder) holder).mDateIconFront,
                    ((ItemMessageVoiceFriendHolder) holder).mTextDate,


                    ((ItemMessageVoiceFriendHolder) holder).itemView
            ).setMessageVoiceFriendHolder();
        }        else if (holder instanceof ItemMessageApkUserHolder){
            new ItemMessageApkUserHolderClass(chat_activity,
                    context,
                    consersation,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageApkUserHolder) holder).mLayoutApkContainer,
                    ((ItemMessageApkUserHolder) holder).mLayoutApkRounder,
                    ((ItemMessageApkUserHolder) holder).mApkTime,
                    ((ItemMessageApkUserHolder) holder).mApkIconImage,
                    ((ItemMessageApkUserHolder) holder).mApkName,
                    ((ItemMessageApkUserHolder) holder).mApkDetails,
                    ((ItemMessageApkUserHolder) holder).mApkUploadIcon,
                    ((ItemMessageApkUserHolder) holder).mApkLoading,
                    ((ItemMessageApkUserHolder) holder).mApkProcessProgressLoading,
                    ((ItemMessageApkUserHolder) holder).mMessageStatus,
                    ((ItemMessageApkUserHolder) holder).itemView
            ).setMessageApkUserHolder();

        }
        else if (holder instanceof ItemMessageApkFriendHolder){
            new ItemMessageApkFriendHolderClass(chat_activity,
                    context,
                    consersation,
                    colorList,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageApkFriendHolder) holder).avata,
                    ((ItemMessageApkFriendHolder) holder).mLayoutApkContainer,
                    ((ItemMessageApkFriendHolder) holder).mLayoutApkRounder,
                    ((ItemMessageApkFriendHolder) holder).mApkTime,
                    ((ItemMessageApkFriendHolder) holder).mApkIconImage,
                    ((ItemMessageApkFriendHolder) holder).mApkName,
                    ((ItemMessageApkFriendHolder) holder).mApkDetails,
                    ((ItemMessageApkFriendHolder) holder).mApkDownloadIcon,
                    ((ItemMessageApkFriendHolder) holder).mApkLoading,
                    ((ItemMessageApkFriendHolder) holder).mApkProcessProgressLoading,
                    ((ItemMessageApkFriendHolder) holder).mCancelDownloadButton,
                    ((ItemMessageApkFriendHolder) holder).itemView
            ).setMessageApkFriendHolder();

        }
else if (holder instanceof ItemMessageContactUserHolder){
            new ItemMessageContactUserHolderClass(chat_activity,
                    context,
                    consersation,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageContactUserHolder) holder).mLayoutContactRunder,
                    ((ItemMessageContactUserHolder) holder).userContactContainer,
                    ((ItemMessageContactUserHolder) holder).mContactInfoDisplay,
                    ((ItemMessageContactUserHolder) holder).mContcatMessageTime,
                    ((ItemMessageContactUserHolder) holder).mViewAllContactList,
                    ((ItemMessageContactUserHolder) holder).mFirstImageContact,
                    ((ItemMessageContactUserHolder) holder).mSecondImageContact,
                    ((ItemMessageContactUserHolder) holder).mThirdImageContact,

                    ((ItemMessageContactUserHolder) holder).mMessageStatus,

                    ((ItemMessageContactUserHolder) holder).itemView
            ).setMessageContactUserHolder();
        }
        else if (holder instanceof ItemMessageContactFriendHolder){
            new ItemMessageContactFriendHolderClass(chat_activity,
                    context,
                    consersation,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    colorList,
                    ((ItemMessageContactFriendHolder) holder).mViewConatct,
                    ((ItemMessageContactFriendHolder) holder).mTextContactView,
                    ((ItemMessageContactFriendHolder) holder).mTextContactTime,
                    ((ItemMessageContactFriendHolder) holder).mThirdContactPhoto,
                    ((ItemMessageContactFriendHolder) holder).mFirstContactPhoto,
                    ((ItemMessageContactFriendHolder) holder).mSecondContactPhoto,
                    ((ItemMessageContactFriendHolder) holder).avata,
                    ((ItemMessageContactFriendHolder) holder).mLayoutContaner,
                    ((ItemMessageContactFriendHolder) holder).mLayoutContactRunder,
                    ((ItemMessageContactFriendHolder) holder).itemView
            ).setMessageContactFriendHolder();
        }
        else if (holder instanceof ItemMessageVideoUserHolder){

            new ItemMessageVideoUserHolderClass(chat_activity,
                    context,
                    consersation,
                    colorList,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageVideoUserHolder) holder).mUploadIconImage,
                    ((ItemMessageVideoUserHolder) holder).mPlayVideo,
                    ((ItemMessageVideoUserHolder) holder).mlayoutUserVideoSizeControl,
                    ((ItemMessageVideoUserHolder) holder).mProgressLoading,
                    ((ItemMessageVideoUserHolder) holder).mPlayVideoBack,
                    ((ItemMessageVideoUserHolder) holder).mUploadIconsImageBack,
                    ((ItemMessageVideoUserHolder) holder).mProcessProgressLoading,
                    ((ItemMessageVideoUserHolder) holder).userPhotoContaner,
                    ((ItemMessageVideoUserHolder) holder).mLayoutMessageRunder,
                    ((ItemMessageVideoUserHolder) holder).txtMessageTimeUser,
                    ((ItemMessageVideoUserHolder) holder).imgMessageUser,
                    ((ItemMessageVideoUserHolder) holder).mCancelUploadButton,
                    ((ItemMessageVideoUserHolder) holder).mMessageStatus,
                    ((ItemMessageVideoUserHolder) holder).itemView
            ).setMessageVideoHolder();

        }
        else if (holder instanceof ItemMessageVideoFriendHolder){
            new ItemMessageVideoFriendHolderClass(chat_activity,
                    context,
                    consersation,
                    colorList,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageVideoFriendHolder) holder).avata,
                    ((ItemMessageVideoFriendHolder) holder).mCancelDownloadVideo,
                    ((ItemMessageVideoFriendHolder) holder).mInterCheckProgressLoading,
                    ((ItemMessageVideoFriendHolder) holder).mLayoutDownloadContentBack,
                    ((ItemMessageVideoFriendHolder) holder).mLayoutSizeControl,
                    ((ItemMessageVideoFriendHolder) holder).txtVideoMessageTimeFriend,
                    ((ItemMessageVideoFriendHolder) holder).txtVideoMessageTimeDurationFriend,
                    ((ItemMessageVideoFriendHolder) holder).mDownloadIconVideo,
                    ((ItemMessageVideoFriendHolder) holder).mProccessProgressLoading,
                    ((ItemMessageVideoFriendHolder) holder).mLayoutChatRounder,
                    ((ItemMessageVideoFriendHolder) holder).mLayoutChatContainer,
                    ((ItemMessageVideoFriendHolder) holder).videoMessageFriend,
                    ((ItemMessageVideoFriendHolder) holder).mLayoutPlayVideoBack,
                    ((ItemMessageVideoFriendHolder) holder).mPlayVideoButton,
                    ((ItemMessageVideoFriendHolder) holder).itemView
            ).setMessageVideoHolder();

        }else if (holder instanceof ItemMessageDocumentFriendHolder){
            new ItemMessageDocumentFriendHolderClass(chat_activity,
                    context,
                    consersation,
                    colorList,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageDocumentFriendHolder) holder).avata,
                    ((ItemMessageDocumentFriendHolder) holder).mLayoutDocContainer,
                    ((ItemMessageDocumentFriendHolder) holder).mLayoutDocRounder,
                    ((ItemMessageDocumentFriendHolder) holder).mDocTime,
                    ((ItemMessageDocumentFriendHolder) holder).mDocIconImage,
                    ((ItemMessageDocumentFriendHolder) holder).mDocName,
                    ((ItemMessageDocumentFriendHolder) holder).mDocDetails,
                    ((ItemMessageDocumentFriendHolder) holder).mDocDownloadIcon,
                    ((ItemMessageDocumentFriendHolder) holder).mDocLoading,
                    ((ItemMessageDocumentFriendHolder) holder).mDocProcessProgressLoading,
                    ((ItemMessageDocumentFriendHolder) holder).mDocPageFirstPageView,
                    ((ItemMessageDocumentFriendHolder) holder).itemView
            ).setMessageDocFriendHolder();

        }   else if (holder instanceof ItemMessageDocumentUserHolder){
            new ItemMessageDocumentUserHolderClass(chat_activity,
                    context,
                    consersation,
                    bitmapAvata,
                    bitmapAvataDB,
                    bitmapAvataUser,
                    src,
                    Lparams,
                    position,
                    ((ItemMessageDocumentUserHolder) holder).mLayoutDocContainer,
                    ((ItemMessageDocumentUserHolder) holder).mLayoutDocRounder,
                    ((ItemMessageDocumentUserHolder) holder).mDocTime,
                    ((ItemMessageDocumentUserHolder) holder).mDocIconImage,
                    ((ItemMessageDocumentUserHolder) holder).mDocName,
                    ((ItemMessageDocumentUserHolder) holder).mDocDetails,
                    ((ItemMessageDocumentUserHolder) holder).mDocUploadIcon,
                    ((ItemMessageDocumentUserHolder) holder).mDocLoading,
                    ((ItemMessageDocumentUserHolder) holder).mDocProcessProgressLoading,
                    ((ItemMessageDocumentUserHolder) holder).mCancelUploadButton,
                    ((ItemMessageDocumentUserHolder) holder).mDocPageFirstPageView,
                    ((ItemMessageDocumentUserHolder) holder).mDocPageFirstBackPageView,
                    ((ItemMessageDocumentUserHolder) holder).itemView
            ).setMessageDocUserHolder();

        }

       /* Toast.makeText(chat_activity,   String.valueOf(System.currentTimeMillis() - startTime), Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public int getItemViewType(int position) {
        String messageType=consersation.getListMessageData().get(position).msgType;
        String idSender=consersation.getListMessageData().get(position).idSender;
        if (idSender.equals(ExtractedStrings.UID)){
            if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_USER_MESSAGE_PHOTO;
            }else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_USER_MESSAGE;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_VOICE_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_USER_MESSAGE_VOICE;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_CONTACT_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_USER_MESSAGE_CONTACT;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_VIDEO_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_USER_MESSAGE_VIDEO;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_APK_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_USER_MESSAGE_APK;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_DOCUMENT_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_USER_MESSAGE_DOCUMENT;
            }
        }else {
            if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_PHOTO_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_PHOTO;
            }else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_VOICE_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_VOICE;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_CONTACT_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_CONTACT;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_VIDEO_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_VIDEO;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_APK_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_APK;
            }
            else if (messageType.equals(ExtractedStrings.ITEM_MESSAGE_DOCUMENT_TYPE)){
                ItemTypeCode.ItemViewTypeInChat = ItemTypeCode.VIEW_TYPE_FRIEND_MESSAGE_DOCUMENT;
            }
        }
        return ItemTypeCode.ItemViewTypeInChat;
    }

    @Override
    public int getItemCount() {
        return consersation.getListMessageData().size();
    }


}
