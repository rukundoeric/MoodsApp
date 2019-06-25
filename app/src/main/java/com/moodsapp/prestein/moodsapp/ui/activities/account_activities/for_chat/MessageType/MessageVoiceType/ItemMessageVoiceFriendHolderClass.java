package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.emojis_library.Helper.EmojiconChatTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.DateUtils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 3/18/2018.
 */

public class ItemMessageVoiceFriendHolderClass {
    private LinearLayout mLayoutDateContainer;
    private CircleImageView mDateIconBihind;
    private CircleImageView mDateIconFront;
    private EmojiconChatTextView mTextDate;
    private CircleImageView avataVoiceMessageIcon;
    private  ArrayList<Integer> colors;
    private Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private CircleProgressBar mDownloadLoading;
    private ProgressBar mInterCheckDownload;
    private RelativeLayout mAlreadDownloadedIcon;
    private ImageView mDownloadVoiceIcon;
    private CircleImageView avata;
    private SeekBar mSeekbarAudioPlay;
    private ImageView mPlayAudioRecorded;
    private LinearLayout mLayoutContaner;
    private LinearLayout mLayoutVoiceRunder;
    private TextView txtMessageTimeUser;
    private View itemView;
    public ItemMessageVoiceFriendHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, ArrayList<Integer> colorList, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, CircleProgressBar mDownloadLoading, RelativeLayout mAlreadDownloadedIcon, ProgressBar mInterCheckDownload, ImageView mDownloadVoiceIcon, CircleImageView avata, CircleImageView avataVoiceMessageIcon, SeekBar mSeekbarAudioPlay, ImageView mPlayAudioRecorded, LinearLayout mLayoutContaner, LinearLayout mLayoutVoiceRunder, TextView txtMessageTimeUser, LinearLayout mLayoutDateContainer, CircleImageView mDateIconBehind, CircleImageView mDateIconFront, EmojiconChatTextView mTextDate, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.src = src;
        this.colors=colorList;
        this.position = position;
        this.mDownloadLoading = mDownloadLoading;
        this.mInterCheckDownload = mInterCheckDownload;
        this.mAlreadDownloadedIcon = mAlreadDownloadedIcon;
        this.mDownloadVoiceIcon = mDownloadVoiceIcon;
        this.avata = avata;
        this.avataVoiceMessageIcon=avataVoiceMessageIcon;
        this.mSeekbarAudioPlay = mSeekbarAudioPlay;
        this.mPlayAudioRecorded = mPlayAudioRecorded;
        this.mLayoutContaner = mLayoutContaner;
        this.mLayoutVoiceRunder = mLayoutVoiceRunder;
        this.txtMessageTimeUser = txtMessageTimeUser;

        this.mLayoutDateContainer=mLayoutDateContainer;
        this.mDateIconBihind=mDateIconBehind;
        this.mDateIconFront=mDateIconFront;
        this.mTextDate=mTextDate;

        this.itemView = itemView;

    }

    public void setMessageVoiceFriendHolder(){
        if (position==0){
            mLayoutContaner.setPadding(5,16, ExtractedStrings.DeviceWidth/6,0);
            mLayoutVoiceRunder.setBackgroundResource(R.drawable.balloon_incoming_normal);
        }else {
            if (!consersation.getListMessageData().get(position-1).idSender.equals(ExtractedStrings.UID)){
                mLayoutVoiceRunder.setBackgroundResource(R.drawable.balloon_incoming_normal_ext);
                avata.setVisibility(CircleImageView.GONE);
                mLayoutContaner.setPadding(5,2, ExtractedStrings.DeviceWidth/6,0);
            }else {
                mLayoutVoiceRunder.setBackgroundResource(R.drawable.balloon_incoming_normal);
                avata.setVisibility(CircleImageView.VISIBLE);
                mLayoutContaner.setPadding(5,16, ExtractedStrings.DeviceWidth/6,0);
            }
        }
        txtMessageTimeUser.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));
        Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idSender);
        if (currentAvata != null) {
            avata.setImageBitmap(currentAvata);
            avataVoiceMessageIcon.setImageBitmap(currentAvata);
        } else {
            final String id = consersation.getListMessageData().get(position).idSender;
            if(bitmapAvataDB.get(id) == null){
                bitmapAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture"));
                bitmapAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            String avataStr = (String) dataSnapshot.getValue();
                            if(!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                            }else{
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                            }
                            //notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        if (position==0){
            mLayoutDateContainer.setVisibility(View.VISIBLE);
            if (currentAvata != null) {
                mDateIconFront.setImageBitmap(ExtractedStrings.mProfileImage);
            } else {
                mDateIconFront.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
            }
            if (ExtractedStrings.mProfileImage!=null){
                mDateIconBihind.setImageBitmap(currentAvata);
            }else {
                mDateIconBihind.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
            }
            mTextDate.setText(DateUtils.getChatTimeDate(consersation.getListMessageData().get(position).timestamp));
        }else {
            if (!DateUtils.hasSameDate(consersation.getListMessageData().get(position).timestamp, consersation.getListMessageData().get(position-1).timestamp)) {
                mLayoutDateContainer.setVisibility(View.VISIBLE);
                mLayoutVoiceRunder.setBackgroundResource(R.drawable.balloon_incoming_normal);
                avata.setVisibility(CircleImageView.VISIBLE);
                if (currentAvata != null) {
                    mDateIconFront.setImageBitmap(ExtractedStrings.mProfileImage);
                } else {
                    mDateIconFront.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                }
                if (ExtractedStrings.mProfileImage!=null){
                    mDateIconBihind.setImageBitmap(currentAvata);
                }else {
                    mDateIconBihind.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                }
                mTextDate.setText(DateUtils.getChatTimeDate(consersation.getListMessageData().get(position).timestamp));
            }else {
                mLayoutDateContainer.setVisibility(View.GONE);
            }
        }
        avata.setBorderWidth(2);
        avata.setBorderColor(colors.get(position));
        if (!consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_DOWNLOADED )){
            mSeekbarAudioPlay.setEnabled(false);
            mAlreadDownloadedIcon.setVisibility(View.GONE);
            mDownloadVoiceIcon.setVisibility(View.VISIBLE);
            mInterCheckDownload.setVisibility(View.GONE);
            mDownloadLoading.setVisibility(View.GONE);

        }else{
            mSeekbarAudioPlay.setEnabled(true);
            mAlreadDownloadedIcon.setVisibility(View.VISIBLE);
            mDownloadVoiceIcon.setVisibility(View.GONE);
            mInterCheckDownload.setVisibility(View.GONE);
            mDownloadLoading.setVisibility(View.GONE);
        }
        mSeekbarAudioPlay.setEnabled(false);
    }
}
