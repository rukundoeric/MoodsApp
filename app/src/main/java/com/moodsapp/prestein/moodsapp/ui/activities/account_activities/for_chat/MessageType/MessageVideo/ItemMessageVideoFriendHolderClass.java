package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 3/19/2018.
 */

public class ItemMessageVideoFriendHolderClass {
    private Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private CircleImageView avata;
    private ImageView mCancelDownloadVideo;
    private ProgressBar mInterCheckProgressLoading;
    private RelativeLayout mLayoutDownloadContentBack;
    private LinearLayout mLayoutSizeControl;
    private EmojiconTextView txtVideoMessageTimeFriend;
    private EmojiconTextView txtVideoMessageTimeDurationFriend;
    private ImageView mDownloadIconVideo;
    private CircleProgressBar mProccessProgressLoading;
    private LinearLayout mLayoutChatRounder;
    private LinearLayout mLayoutChatContainer;
    private RoundedImageView videoMessageFriend;
    private LinearLayout mLayoutPlayVideoBack;
    private ImageView mPlayVideoButton;
    private View itemView;
    public ItemMessageVideoFriendHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, ArrayList<Integer> colorList, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, CircleImageView avata, ImageView mCancelDownloadVideo, ProgressBar mInterCheckProgressLoading, RelativeLayout mLayoutDownloadContentBack, LinearLayout mLayoutSizeControl, EmojiconTextView txtVideoMessageTimeFriend, EmojiconTextView txtVideoMessageTimeDurationFriend, ImageView mDownloadIconVideo, CircleProgressBar mProccessProgressLoading, LinearLayout mLayoutChatRounder, LinearLayout mLayoutChatContainer, RoundedImageView videoMessageFriend, LinearLayout mLayoutPlayVideoBack, ImageView mPlayVideoButton, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.src = src;
        this.position = position;
        this.avata = avata;
        this.mCancelDownloadVideo = mCancelDownloadVideo;
        this.mInterCheckProgressLoading = mInterCheckProgressLoading;
        this.mLayoutDownloadContentBack = mLayoutDownloadContentBack;
        this.mLayoutSizeControl = mLayoutSizeControl;
        this.txtVideoMessageTimeFriend = txtVideoMessageTimeFriend;
        this.txtVideoMessageTimeDurationFriend = txtVideoMessageTimeDurationFriend;
        this.mDownloadIconVideo = mDownloadIconVideo;
        this.mProccessProgressLoading = mProccessProgressLoading;
        this.mLayoutChatRounder = mLayoutChatRounder;
        this.mLayoutChatContainer = mLayoutChatContainer;
        this.videoMessageFriend = videoMessageFriend;
        this.mLayoutPlayVideoBack=mLayoutPlayVideoBack;
        this.mPlayVideoButton=mPlayVideoButton;
        this.itemView=itemView;
    }

    public void setMessageVideoHolder() {
        if (position==0){
            mLayoutChatContainer.setPadding(5,16,0,0);
            mLayoutChatRounder.setBackgroundResource(R.drawable.balloon_incoming_normal);
        }else {
            if (!consersation.getListMessageData().get(position-1).idSender.equals(ExtractedStrings.UID)){
                mLayoutChatRounder.setBackgroundResource(R.drawable.balloon_incoming_normal_ext);
                avata.setVisibility(CircleImageView.GONE);
                mLayoutChatContainer.setPadding(5,2,0,0);
            }else {
                mLayoutChatRounder.setBackgroundResource(R.drawable.balloon_incoming_normal);
                avata.setVisibility(CircleImageView.VISIBLE);
                mLayoutChatContainer.setPadding(5,16,0,0);
            }
        }
        if (!consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_DOWNLOADED)){
            mLayoutDownloadContentBack.setVisibility(View.VISIBLE);
            mDownloadIconVideo.setVisibility(View.VISIBLE);
            mProccessProgressLoading.setVisibility(View.GONE);
            mInterCheckProgressLoading.setVisibility(View.GONE);
            mCancelDownloadVideo.setVisibility(View.GONE);
            mLayoutPlayVideoBack.setVisibility(View.GONE);
            mPlayVideoButton.setVisibility(View.GONE);
            String url=consersation.getListMessageData().get(position).AnyMediaUrl;
            Glide.with(context).load(url).apply(new RequestOptions().override(15,15).optionalFitCenter().placeholder(R.drawable.loading_video_before).error(R.drawable.loading_video_before).centerCrop()).thumbnail(Glide.with(context).load(url)).into(videoMessageFriend);
        }else {
            mLayoutDownloadContentBack.setVisibility(View.GONE);
            mDownloadIconVideo.setVisibility(View.GONE);
            mProccessProgressLoading.setVisibility(View.GONE);
            mInterCheckProgressLoading.setVisibility(View.GONE);
            mCancelDownloadVideo.setVisibility(View.GONE);
            mLayoutPlayVideoBack.setVisibility(View.VISIBLE);
            mPlayVideoButton.setVisibility(View.VISIBLE);
            String url=consersation.getListMessageData().get(position).VideoDeviceUrl;
            Glide.with(context).load(url).apply(new RequestOptions().optionalFitCenter().placeholder(R.drawable.loading_video_before).error(R.drawable.loading_video_before).centerCrop()).thumbnail(Glide.with(context).load(url)).into(videoMessageFriend);
        }
        Lparams=mLayoutSizeControl.getLayoutParams();
        int width=ExtractedStrings.DeviceWidth-((ExtractedStrings.DeviceWidth/3));
        Lparams.width=width;
        Lparams.height=width;
        mLayoutSizeControl.setLayoutParams(Lparams);

        txtVideoMessageTimeFriend.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));
        Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idSender);
        if (currentAvata != null) {
            avata.setImageBitmap(currentAvata);
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
                                chat_activity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                            }else{
                                chat_activity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

        try {
            String status = consersation.getListMessageData().get(position).AnyMediaStatus;

            if (status.equals(ExtractedStrings.MEDIA_DOWNLOADED)) {
                final String imgUri = consersation.getListMessageData().get(position).PhotoDeviceUrl;
                File image = new File(imgUri);
                if (image.exists()) {
                    Glide.with(context).load(imgUri).apply(new RequestOptions().optionalFitCenter().placeholder(R.drawable.loading_video_before).centerCrop()).into(avata);
                } else {
                    Glide.with(context).load(R.drawable.loading_video_before).apply(new RequestOptions().optionalFitCenter().placeholder(R.drawable.loading_video_before).centerCrop()).into(avata);
                }
            } else {
                if(!Data_Storage_Path.isThisFileExistInCache("ThurberNail_"+consersation.getListMessageData().get(position).msgId,context).equals("default")){
                    Glide.with(context).load(Data_Storage_Path.isThisFileExistInCache("ThurberNail_"+consersation.getListMessageData().get(position).msgId,context)).into(avata);
                }else {
                    final String url = consersation.getListMessageData().get(position).PhotoStringBase64;
                    Glide.with(context).load(url).apply(new RequestOptions().optionalFitCenter().placeholder(R.drawable.loading_video_before).centerCrop())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }
                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    File file=new File(new getFileName("ThurberNail_"+consersation.getListMessageData().get(position).msgId,context).getImageFileName("",true));
                                    Bitmap bitmap=((BitmapDrawable) resource).getBitmap();
                                    ByteArrayOutputStream baus=new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG,0,baus);
                                    byte[] bitmapdata=baus.toByteArray();
                                    FileOutputStream fous= null;
                                    try {
                                        fous = new FileOutputStream(file);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        fous.write(bitmapdata);
                                        fous.flush();
                                        fous.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    FirebaseStorage.getInstance().getReferenceFromUrl(url).delete();
                                    return false;
                                }
                            }).into(avata);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "In Item Message User Holder \n" + e.getMessage() + "\n" + e.getCause(), Toast.LENGTH_SHORT).show();
        }

    }




}
