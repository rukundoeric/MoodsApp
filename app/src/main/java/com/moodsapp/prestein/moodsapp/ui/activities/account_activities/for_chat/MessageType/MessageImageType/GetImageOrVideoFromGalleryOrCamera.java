package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.moodsapp.emojis_library.Actions.EmojIconActions;
import com.moodsapp.emojis_library.Helper.EmojiconEditText;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType.SendTextMessage;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo.SendVideo;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageCroping;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.FileInputOutPutStream;

import java.io.File;
import java.util.ArrayList;

public class GetImageOrVideoFromGalleryOrCamera extends AppCompatActivity {

    private ImageView mOpenEmojis;
    private EmojiconEditText mImageComment;
    private ProgressBar mProgressBar;
    private EmojIconActions emojIcon;
    private View rootView;
    private final String TAG="GetImageOrVideoFromGalleryOrCamera";
    private String User_id;
    private String nameFriend;
    private String statusFriend;
    private FriendDB recentChatsDB;
    private Context context=this;
    private String mediaPath;
    private  String nameFreind;
    private ArrayList<CharSequence> idFriend;
    private String mediaType;
    private LinearLayout mlayoutPlayButton;
    private ImageView mPlayButton;
    private LinearLayout mMainView;
    private ArrayList<CharSequence> idFriends;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image_from_gallery_or_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInGetImageFromGalleryOrCamera);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
        Bundle bundle = getIntent().getExtras();
        User_id = bundle.getString(ExtractedStrings.INTENT_USER_ID);
        idFriend = bundle.getCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID);
        mediaPath=bundle.getString(ExtractedStrings.INTENT_MEDIA_PATH);
        nameFreind=bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND);
        mediaType=bundle.getString(ExtractedStrings.INTENT_MEDIA_TYPE);
        rootView=(View)findViewById(R.id.FromGalleryOrCameraInChatLayout);
        mOpenEmojis=(ImageView)findViewById(R.id.OpenEmojisInGetImageFromGalleryOrCamera);
        mImageComment=(EmojiconEditText)findViewById(R.id.ImageCommentFromGalleryOrCamera);
        mlayoutPlayButton=(LinearLayout)findViewById(R.id.layout_back_for_video_gonna_be_sent);
        mPlayButton=(ImageView)findViewById(R.id.play_video_in_video_gonna_be_sent);
        mMainView=(LinearLayout)findViewById(R.id.layout_main_back_for_media_gonna_be_sent);
       // mProgressBar=(ProgressBar)findViewById(R.id.ProgressBarInGetImageFromGalleryOrCamera);
        emojIcon = new EmojIconActions(this, rootView, mImageComment, mOpenEmojis);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_insert_emoji_keyboard_write, R.drawable.ic_insert_emoji_write);
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onKeyboardOpen() {
                Log.e(TAG, "Keyboard opened!");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onKeyboardClose() {
                Log.e(TAG, "Keyboard closed");
            }
        });
            toolbar.setTitle(nameFreind);
            toolbar.setSubtitle(FriendDB.getInstance(context).getInfoByIdUser(2,User_id,context));
        if (mediaPath.length()>0){

            if (new File(mediaPath).exists()) {
                if(mediaType.equals("image")){
                    mPlayButton.setVisibility(View.GONE);
                    mlayoutPlayButton.setVisibility(View.GONE);
                    BitmapDrawable Image_drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeFile(mediaPath));
                    mMainView.setBackground(Image_drawable);
                }else if(mediaType.equals("video")){
                    mPlayButton.setVisibility(View.VISIBLE);
                    mlayoutPlayButton.setVisibility(View.VISIBLE);
                    BitmapDrawable Image_drawable=new BitmapDrawable(getResources(),getBitmapStringFromVideo(mediaPath));
                    mMainView.setBackground(Image_drawable);
                }
            }else {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("File not found,make that you have selected existing file")
                        .setCancelable(false)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
            }
        }
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendImageChatFromGalleryOrCamera);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (mediaType.equals("image")){
                            SendImage sendImage=new SendImage(context);
                            //sendImage.seveImageMessage(User_id,mediaPath);
                            if(mImageComment.getText().toString().length()>0){
                              //  new SendTextMessage(context).SendMessage(User_id,mImageComment.getText().toString(), idFriends);
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, FriendDB.getInstance(context).getInfoByIdUser(2,User_id,context));
                            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));
                            Intent intent=new Intent(context, Chat_Activity.class);
                            ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                            idFriend.add(User_id);
                            bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }else if (mediaType.equals("video")){
                            if (FileInputOutPutStream.getFileSize(new File(mediaPath))>30){
                                Toast.makeText(context, "You are not allowed to send the file which is bigger than 30 MB, try to compress it and send it again.", Toast.LENGTH_LONG).show();
                            }else{
                            SendVideo sendVideo=new SendVideo(context);
                            sendVideo.copyVideoToMoodsAppFolder(context,User_id,mediaPath);
                                if(mImageComment.getText().toString().length()>0){
                                    new SendTextMessage(context).SendMessage(User_id,mImageComment.getText().toString(), idFriends);
                                }
                            Bundle bundle = new Bundle();
                            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, FriendDB.getInstance(context).getInfoByIdUser(2,User_id,context));
                            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));
                            Intent intent=new Intent(context, Chat_Activity.class);
                            ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                            idFriend.add(User_id);
                            bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(context, "In SENDING \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (Exception e){
            Toast.makeText(context, "In Open GetImageFroom Camera \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private Bitmap getBitmapStringFromVideo(String VideoPath){
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(VideoPath,   MediaStore.Images.Thumbnails.MINI_KIND);
        Matrix matrix = new Matrix();
        Bitmap bmThumbnail = Bitmap.createBitmap(thumb, 0, 0,thumb.getWidth(), thumb.getHeight(), matrix, true);
        bmThumbnail= ImageCroping.cropToSquare(bmThumbnail);
        return bmThumbnail;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_image_from_gallery_or_camera_message_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
       switch (id){
           case android.R.id.home:
               Bundle bundle = new Bundle();
               bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
               bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, FriendDB.getInstance(context).getInfoByIdUser(2,User_id,context));
               bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));
               Intent intent=new Intent(context, Chat_Activity.class);
               ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
               idFriend.add(User_id);
               bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
               intent.putExtras(bundle);
               startActivity(intent);
               finish();
               break;
       }
       return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, FriendDB.getInstance(context).getInfoByIdUser(2,User_id,context));
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));
        Intent intent=new Intent(context, Chat_Activity.class);
        ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
        idFriend.add(User_id);
        bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
