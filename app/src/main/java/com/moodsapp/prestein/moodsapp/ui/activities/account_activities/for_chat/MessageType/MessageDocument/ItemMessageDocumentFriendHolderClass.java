package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.IntentTypeString;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.SubstringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemMessageDocumentFriendHolderClass {

    private RoundedImageView pdfPageView;
    private HashMap<String, Bitmap> bitmapAvata;
    private ArrayList<Integer> colors;
    public Chat_Activity chat_activity;
    private Context context;
    private Consersation consersation;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private Bitmap src;
    private static ViewGroup.LayoutParams Lparams;
    private int position;
    private CircleImageView avata;
    private LinearLayout mLayoutDocContainer;
    private LinearLayout mLayoutDocRounder;
    private EmojiconTextView mDocTime;
    private ImageView mDocIconImage;
    private EmojiconTextView mDocName;
    private EmojiconTextView mDocDetails;
    private ImageView mDocDownloadIcon;
    private ProgressBar mDocLoading;
    private CircleProgressBar mDocProcessProgressLoading;
    private View itemView;

    public ItemMessageDocumentFriendHolderClass(Chat_Activity chat_activity, Context context, Consersation consersation, ArrayList<Integer> colorList, HashMap<String, Bitmap> bitmapAvata, HashMap<String, DatabaseReference> bitmapAvataDB, Bitmap bitmapAvataUser, Bitmap src, ViewGroup.LayoutParams lparams, int position, CircleImageView avata, LinearLayout mLayoutDocContainer, LinearLayout mLayoutDocRounder, EmojiconTextView mDocTime, ImageView mDocIconImage, EmojiconTextView mDocName, EmojiconTextView mDocDetails, ImageView mDocDownloadIcon, ProgressBar mDocLoading, CircleProgressBar mDocProcessProgressLoading, RoundedImageView pdfPageView, View itemView) {
        this.chat_activity = chat_activity;
        this.context = context;
        this.bitmapAvata = bitmapAvata;
        this.consersation = consersation;
        this.bitmapAvataDB = bitmapAvataDB;
        this.src = src;
        this.position = position;
        this.colors = colorList;
        this.avata = avata;
        this.mLayoutDocContainer = mLayoutDocContainer;
        this.mLayoutDocRounder = mLayoutDocRounder;
        this.mDocTime = mDocTime;
        this.mDocIconImage = mDocIconImage;
        this.mDocName = mDocName;
        this.mDocDetails = mDocDetails;
        this.mDocDownloadIcon = mDocDownloadIcon;
        this.mDocLoading = mDocLoading;
        this.mDocProcessProgressLoading = mDocProcessProgressLoading;
        this.pdfPageView = pdfPageView;
        this.itemView = itemView;
    }

    public void setMessageDocFriendHolder() {
        if (position == 0) {
            mLayoutDocContainer.setPadding(5, 16, ExtractedStrings.DeviceWidth / 6, 0);
            mLayoutDocRounder.setBackgroundResource(R.drawable.balloon_incoming_normal);
        } else {
            if (!consersation.getListMessageData().get(position - 1).idSender.equals(ExtractedStrings.UID)) {
                mLayoutDocRounder.setBackgroundResource(R.drawable.balloon_incoming_normal_ext);
                avata.setVisibility(CircleImageView.GONE);
                mLayoutDocContainer.setPadding(5, 2, ExtractedStrings.DeviceWidth / 6, 0);
            } else {
                mLayoutDocRounder.setBackgroundResource(R.drawable.balloon_incoming_normal);
                avata.setVisibility(CircleImageView.VISIBLE);
                mLayoutDocContainer.setPadding(5, 16, ExtractedStrings.DeviceWidth / 6, 0);
            }
        }
        mDocTime.setText(new SimpleDateFormat("HH:mm").format(new Date(consersation.getListMessageData().get(position).timestamp)));
        Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idSender);
        if (currentAvata != null) {
            avata.setImageBitmap(currentAvata);
        } else {
            final String id = consersation.getListMessageData().get(position).idSender;
            if (bitmapAvataDB.get(id) == null) {
                bitmapAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("Users/" + id + "/small_profile_picture"));
                bitmapAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            String avataStr = (String) dataSnapshot.getValue();
                            if (!avataStr.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                                byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                            } else {
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
        avata.setBorderWidth(2);
        avata.setBorderColor(colors.get(position));

        try {
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
            if (!consersation.getListMessageData().get(position).AnyMediaStatus.equals(ExtractedStrings.MEDIA_DOWNLOADED)) {
                mDocLoading.setVisibility(View.GONE);
                mDocProcessProgressLoading.setVisibility(View.GONE);
                mDocDownloadIcon.setVisibility(View.VISIBLE);
                pdfPageView.setVisibility(View.GONE);
            } else {
                mDocLoading.setVisibility(View.GONE);
                mDocProcessProgressLoading.setVisibility(View.GONE);
                mDocDownloadIcon.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Toast.makeText(chat_activity, "In load icon app " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mLayoutDocContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OpenDoc();
            }
        });

    }

    private void OpenDoc() {
        String fileExtension = new SubstringUtils().getSubStringByChar(' ', consersation.getListMessageData().get(position).text, false);
        String intentType=new IntentTypeString().getIntentTypeByExtension(new IntentTypeString().getIntentType(),fileExtension);
        File file = new File(consersation.getListMessageData().get(position).DocumentDeviceUrl);
        if (file.exists()) {
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, intentType);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                chat_activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context,
                        "No Application Available to View "+fileExtension+ "Files.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    .setIcon(R.drawable.ic_in_chat_question_black_24dp)
                    .setMessage("This file is no longer exist on your device, do you want to remove this message?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ExtractedStrings.MESSAGES_SELECTED_ID = new ArrayList<>();
                            ExtractedStrings.MESSAGES_SELECTED_ID.add(consersation.getListMessageData().get(position).msgId);
                            AllChatsDB.getInstance(context).DeleteMessages(context
                                    , consersation.getListMessageData().get(position).idReceiver, consersation.getListMessageData().get(position).idSender,ExtractedStrings.MESSAGES_SELECTED_ID);
                            chat_activity.UpdateAdapterChats(true);
                            chat_activity.clearMessageSelected();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    chat_activity.clearMessageSelected();
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }

}







