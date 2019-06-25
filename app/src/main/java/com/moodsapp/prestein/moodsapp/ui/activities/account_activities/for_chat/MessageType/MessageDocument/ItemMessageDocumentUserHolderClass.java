package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DatabaseReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ItemMessageDocumentUserHolderClass {

    private ImageView mCancelUploadButton;
    private RoundedImageView pdfPageView;
    private RoundedImageView pdfBack;
    public Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private Bitmap src;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap bitmapAvataUser;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private LinearLayout mLayoutDocContainer;
    private LinearLayout mLayoutDocRounder;
    private EmojiconTextView mDocTime;
    private ImageView mDocIconImage;
    private EmojiconTextView mDocName;
    private EmojiconTextView mDocDetails;
    private ImageView mDocUploadIcon;
    private ProgressBar mDocLoading;
    private CircleProgressBar mDocProcessProgressLoading;
    private View itemView;
    private ViewGroup.LayoutParams Lparams2;

    public ItemMessageDocumentUserHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, LinearLayout mLayoutDocContainer, LinearLayout mLayoutDocRounder, EmojiconTextView mDocTime, ImageView mDocIconImage, EmojiconTextView mDocName, EmojiconTextView mDocDetails, ImageView mDocUploadIcon, ProgressBar mDocLoading, CircleProgressBar mDocProcessProgressLoading, ImageView mCancelUploadButton, RoundedImageView mDocPageFirstPageView, RoundedImageView mDocPageFirstBackPageView, View itemView) {

        this.chat_activity = chat_activity;
        this.context = context;
        this.consersation = consersation;
        this.src = src;
        this.consersation = consersation;
        this.bitmapAvataDB = bitmapAvataDB;
        this.position = position;
        this.mLayoutDocContainer=mLayoutDocContainer;
        this.mLayoutDocRounder=mLayoutDocRounder;
        this.mDocTime=mDocTime;
        this.mDocIconImage=mDocIconImage;
        this.mDocName=mDocName;
        this.mDocDetails=mDocDetails;
        this.mDocUploadIcon=mDocUploadIcon;
        this.mDocLoading=mDocLoading;
        this.mCancelUploadButton=mCancelUploadButton;
        this.mDocProcessProgressLoading=mDocProcessProgressLoading;
        this.pdfPageView=mDocPageFirstPageView;
        this.pdfBack=mDocPageFirstBackPageView;
        this.itemView=itemView;

    }


    public void setMessageDocUserHolder(){

        if (position==0){
            mLayoutDocContainer.setPadding(ExtractedStrings.DeviceWidth/4,16,2,2);
        }else {
            if (consersation.getListMessageData().get(position-1).idSender.equals(ExtractedStrings.UID)){
                mLayoutDocRounder.setBackgroundResource(R.drawable.balloon_outgoing_normal_ext);
                mLayoutDocContainer.setPadding(ExtractedStrings.DeviceWidth/4,2,2,2);
            }  else {
                mLayoutDocRounder.setBackgroundResource(R.drawable.balloon_outgoing_normal);
                mLayoutDocContainer.setPadding(ExtractedStrings.DeviceWidth/4,16,2,2);
            }
        }

            mDocTime.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));
            mDocName.setText(consersation.getListMessageData().get(position).VideoDeviceUrl);
            String fileDetails=consersation.getListMessageData().get(position).text+ " "+'|'+" "+consersation.getListMessageData().get(position).PhotoDeviceUrl;
            mDocDetails.setText(fileDetails.toUpperCase());
            String pdfDoc="pdf";
            String wordDoc="docx";
            String pptDoc="pptx";
            String xls="xls";
            String zipDoc="zip";
            String apkDoc="apk";
            String zipRar="rar";
            String zip7z="7z";
            if (fileDetails.startsWith(pdfDoc) || fileDetails.startsWith(pdfDoc.toUpperCase())){
                mDocIconImage.setImageResource(R.drawable.documents_icon_pdf);
               /* final String path=consersation.getListMessageData().get(position).DocumentDeviceUrl;
                if (new File(path).exists()){
                    itemView.findViewById(R.id.document_details_back_in_document_user).setBackgroundResource(R.drawable.layout_write_rounder_for_file_sender);
                    pdfBack.setVisibility(View.VISIBLE);
                    pdfPageView.setVisibility(View.VISIBLE);
                    Lparams=pdfPageView.getLayoutParams();
                    Lparams2=pdfBack.getLayoutParams();
                    Lparams2.height=ExtractedStrings.DeviceWidth/3;
                    Lparams.height=ExtractedStrings.DeviceWidth/3;
                    pdfPageView.setLayoutParams(Lparams);
                    pdfBack.setLayoutParams(Lparams2);
                 new android.os.Handler().post(new Runnable() {
                     @Override
                     public void run() {
                         BitmapDrawable bitmapDrawable= new BitmapDrawable(chat_activity.getResources(),new DocumentConverter(context).pdfToBitmap(new File(path)));
                         pdfPageView.setBackground(bitmapDrawable);
                     }
                 });
                }*/
            }else if (fileDetails.startsWith(wordDoc) || fileDetails.startsWith(wordDoc.toUpperCase())){
                mDocIconImage.setImageResource(R.drawable.documents_icon_doc);
            }else if (fileDetails.startsWith(pptDoc) || fileDetails.startsWith(pptDoc.toUpperCase())){
                mDocIconImage.setImageResource(R.drawable.documents_icon_ppt);
            }else if (fileDetails.startsWith(apkDoc) || fileDetails.startsWith(apkDoc.toUpperCase())){
                mDocIconImage.setImageResource(R.drawable.documents_icon_apk);
            }
            else if (fileDetails.startsWith(xls) || fileDetails.startsWith(xls.toUpperCase())){
                mDocIconImage.setImageResource(R.drawable.documents_icon_xls);
            }else if (fileDetails.startsWith(zipDoc.toUpperCase()) ||
                    fileDetails.startsWith(zipRar.toUpperCase()) ||
                    fileDetails.startsWith(zip7z.toUpperCase()) ){
                mDocIconImage.setImageResource(R.drawable.documents_icon_zip);
            }


        if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_NOT_UPLOADED)){
            mDocLoading.setVisibility(View.GONE);
            mDocProcessProgressLoading.setVisibility(View.GONE);
            mDocUploadIcon.setVisibility(View.VISIBLE);
        }else if (consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_UPLOADED )){
            mDocLoading.setVisibility(View.GONE);
            mDocProcessProgressLoading.setVisibility(View.GONE);
            mDocUploadIcon.setVisibility(View.GONE);

        }


    }


}
