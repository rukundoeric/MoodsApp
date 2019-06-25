package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DatabaseReference;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageCroping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by USER on 3/19/2018.
 */

public class ItemMessageVideoUserHolderClass {

    private ImageView messageStatus;
    private ImageView mCancelUploadButton;
    private Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private ImageView mUploadIconImage;
    private ImageView mPlayVideo;
    private LinearLayout mlayoutUserVideoSizeControl;
    private ProgressBar mProgressLoading;
    private LinearLayout mPlayVideoBack;
    private RelativeLayout mUploadIconsImageBack;
    private CircleProgressBar mProcessProgressLoading;
    private LinearLayout userPhotoContaner;
    private LinearLayout mLayoutMessageRunder;
    private EmojiconTextView txtMessageTimeUser;
    private ImageView imgMessageUser;
    private View itemView;

    public ItemMessageVideoUserHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, ArrayList<Integer> colorList, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, ImageView mUploadIconImage, ImageView mPlayVideo, LinearLayout mlayoutUserVideoSizeControl, ProgressBar mProgressLoading, LinearLayout mPlayVideoBack, RelativeLayout mUploadIconsImageBack, CircleProgressBar mProcessProgressLoading, LinearLayout userPhotoContaner, LinearLayout mLayoutMessageRunder, EmojiconTextView txtMessageTimeUser, ImageView imgMessageUser, ImageView mCancelUploadButton, ImageView mMessageStatus, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataDB = bitmapAvataDB;
        this.bitmapAvataUser = bitmapAvataUser;
        this.src = src;
        this.position = position;
        this.mUploadIconImage = mUploadIconImage;
        this.mPlayVideo=mPlayVideo;
        this.mlayoutUserVideoSizeControl=mlayoutUserVideoSizeControl;
        this.mProgressLoading = mProgressLoading;
        this.mPlayVideoBack=mPlayVideoBack;
        this.mUploadIconsImageBack=mUploadIconsImageBack;
        this.mProcessProgressLoading=mProcessProgressLoading;
        this.userPhotoContaner = userPhotoContaner;
        this.mLayoutMessageRunder = mLayoutMessageRunder;
        this.txtMessageTimeUser = txtMessageTimeUser;
        this.imgMessageUser = imgMessageUser;
        this.itemView = itemView;
        this.mCancelUploadButton=mCancelUploadButton;
        this.messageStatus=mMessageStatus;
    }

    public void setMessageVideoHolder(){
        if (position==0){
            userPhotoContaner.setPadding(Chat_Activity.Divice_with/5,16,2,2);
            mLayoutMessageRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
        }
        else{
            String idSender=consersation.getListMessageData().get(position-1).idSender;

            if (idSender.equals(ExtractedStrings.UID)){

                //  icon.setVisibility(View.GONE);
                mLayoutMessageRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal_ext);
                userPhotoContaner.setPadding(Chat_Activity.Divice_with/5,2,2,2);
            }
            else {
                mLayoutMessageRunder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
                userPhotoContaner.setPadding(Chat_Activity.Divice_with/5,16,2,2);

            }
        }

        if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_NOT_UPLOADED)){
            mUploadIconsImageBack.setVisibility(View.VISIBLE);
            mUploadIconImage.setVisibility(View.VISIBLE);
            mProcessProgressLoading.setVisibility(View.GONE);
            mProgressLoading.setVisibility(View.GONE);
            mCancelUploadButton.setVisibility(View.GONE);
        }else {
            mCancelUploadButton.setVisibility(View.GONE);
            mUploadIconsImageBack.setVisibility(View.GONE);
            mUploadIconImage.setVisibility(View.GONE);
            mProgressLoading.setVisibility(View.GONE);
            mProcessProgressLoading.setVisibility(View.GONE);
        }
        try {
            Lparams=mlayoutUserVideoSizeControl.getLayoutParams();
            int width=Chat_Activity.Divice_with-((Chat_Activity.Divice_with/3));
            Lparams.height=width;
            Lparams.width=width;
            mlayoutUserVideoSizeControl.setLayoutParams(Lparams);
            txtMessageTimeUser.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));

            imgMessageUser.setImageBitmap(getBitmapStringFromVideo());

        }catch (Exception e){
            Toast.makeText(context, "In Item Message User Holder \n"+e.getMessage()+ "\n" +e.getCause(), Toast.LENGTH_SHORT).show();
        }

        if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SAVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_storage);
        }else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_SENT)) {
            messageStatus.setImageResource(R.drawable.bpg_message_saved_to_server);
        }
        else if (consersation.getListMessageData().get(position).messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_RECEIVED)){
            messageStatus.setImageResource(R.drawable.bpg_message_received_by_user);
        }
    }



    private Bitmap getBitmapStringFromVideo(){
            String VideoPath=consersation.getListMessageData().get(position).VideoDeviceUrl;
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(VideoPath,   MediaStore.Images.Thumbnails.MINI_KIND);
            Matrix matrix = new Matrix();
            Bitmap bmThumbnail = Bitmap.createBitmap(thumb, 0, 0,thumb.getWidth(), thumb.getHeight(), matrix, true);
            bmThumbnail= ImageCroping.cropToSquare(bmThumbnail);
            return bmThumbnail;
    }


}
