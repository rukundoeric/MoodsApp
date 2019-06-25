package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moodsapp.emojis_library.Actions.EmojIconActions;
import com.moodsapp.emojis_library.Helper.EmojiconEditText;
import com.moodsapp.prestein.moodsapp.ActivityRunningStatus.Chat_Activity_Status;
import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.GroupDB;
import com.moodsapp.prestein.moodsapp.data.Database.RecentNotificationDb;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;
import com.moodsapp.prestein.moodsapp.model.Consersation;
import com.moodsapp.prestein.moodsapp.model.Group;
import com.moodsapp.prestein.moodsapp.model.ListFriend;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.service.NotificationMessage.SaveNewMessage;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact.ViewContactMessage;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument.ReceiveDocument;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument.SendDocument;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType.ReceiveImage;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType.SendImage;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageTextType.SendTextMessage;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo.ReceiveVideo;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo.SendVideo;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType.CustomDialogRecordAudio;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType.PlayAudioVoiceMessageSeekbar;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType.ReceiveVoice;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType.SendVoice;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home.UserProfileActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui.PhotoViewActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui.VideoPlayerActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home.Home_Activity;
import com.moodsapp.prestein.moodsapp.util.AudioUtils.PlaySound;
import com.moodsapp.prestein.moodsapp.util.ColorUtils.ColorCreation;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.IntentTypeString;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.SubstringUtils;
import com.r0adkll.slidr.Slidr;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class Chat_Activity extends AppCompatActivity {
    private static final String LOG_TAG = "record_log";
    private String rot_id="true";
    private ImageView mChatProfileImage;
    private TextView mChatProfileName;
    private FloatingActionButton mImageBittonSend;
    public String User_id;
    private String your_id;
    private ImageView mOpenEmojis;
    private View rootView;
    private static final String TAG = Chat_Activity.class.getSimpleName();
    public EmojiconEditText mChatMessageText;
    private EmojIconActions emojIcon;
    private FloatingActionButton mOpenMoreMedia;
    private Context mediaContext=this;
    private LinearLayout mLayoutOpenMedia;
    public static Resources res;
    Uri mImageUri=Uri.EMPTY;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mChatMine,mChatFriend;
    private String mMessege;
    private ProgressDialog mProgress;
    private Uri viewPhotoUri;
    private Point ptSize=new Point();
    public ListMessageAdapter adapter;
    public String roomId;
    private ArrayList<CharSequence> idFriend;
    public Consersation consersation=null;
    private ImageButton btnSend;
    private EditText editWriteMessage;
    public static HashMap<String, Bitmap> bitmapAvataFriend;
    public Bitmap bitmapAvataUser;
    private String nameFriend;
    private String statusFriend;
    /*  private String imageFreind;*/
    private BitmapDrawable Image_drawable;
    private Toolbar conversation_toolbar;
    public static int Divice_with;
    public File folderForSharedPhoto_dir;
    private ImageView mOpenGalleryMoreMedia;
    private ImageView mOpenCameraMoreMedia;
    private String imageBase64_camera;
    private Context context=this;
    private String imageBase64_gallery;
    private ListFriend listFriend;
    private int friendPosition;
    public String LastMessageUser;
    private long LastMessageUserTime;
    public int MessageTimeDuration;
    private String idRoom;
    private String RecntNuberOfUnReadMessage;
    /*private String imageFreind;*/
    public LinearLayout mLayoutReplyInChatTextEnter;
    public CircleImageView mImageReplyInChatTextEnter;
    public TextView mMessageViewTextReplyInChatText;
    public AppBarLayout menu_item_toolbar;
    public ActionMenuView menu_item_toolbar_chat;
    private ImageView mClosemLayoutReplyInChatTextEnter;
    private MediaRecorder myAudioRecorder;
    private ImageView mStartRecording;
    public CustomDialogRecordAudio dialogRecordAudio;
    private String pictureImagePath="";
    public CustomDialogMediaShow customDialogRecordAudio;
    public boolean newVoiceIsSent=false;
    public boolean newImageIsSent=false;
    private RecyclerView recyclerChat;
    public Menu menuItemChatToolbarMenu;
    public LinearLayoutManager linearConversationLayoutManager;
    private String galleryIntentType;
    public  Context ctx;
    public ArrayList<CharSequence> idFriends;
    private ArrayList<CharSequence> UserRoomType;
    private ArrayList<SendImage.TaskObject> mSendImageTasksList;
    private SendImage sendMessageImageUploalod;
    private SeekBar mSeekbarAudioPlay;
    private Handler seekHandler=new Handler();
    private MediaPlayer mp ;
    private SeekBar mPreviorSeekbarAudioPlay;
    private ImageView mPreviorPlayAudioRecorded;
    public static boolean isAudioChatPlaying=false;
    public static String currentAudioItemPosition="05";
    public static int AudioMediaMax;
    public static int AudioMediaPos=0;
    public static boolean isAudioPaused=false;
    public CustomDialogMediaShow customDialogMediaShow;
    private CountDownTimer mRefleshUserOnline;
    private CountDownTimer mUpdfateUserOnline;
    public boolean isRetryAllowed=true;
    private TextView mNameViewTextReplyInChatText;

    public Chat_Activity(Context c) {
        this.ctx=c;
    }
    public Chat_Activity() {
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
           // WifiP2pManager wifiP2pManager=new WifiP2pManager();
            conversation_toolbar=(Toolbar)findViewById(R.id.conversation_toolbar);
            menu_item_toolbar_chat=(ActionMenuView)findViewById(R.id.menu_item_chat_tool_bar);
            menu_item_toolbar=(AppBarLayout)findViewById(R.id.menu_item_chat_app_bar);
            rootView = (View) findViewById(R.id.chat_conversation_container);
            mStartRecording=(ImageView)findViewById(R.id.start_recording_in_chat_activity);
            mLayoutReplyInChatTextEnter=(LinearLayout)findViewById(R.id.reply_layout_profile_information_text_enter);
            mNameViewTextReplyInChatText=(TextView)findViewById(R.id.message_reply_name_in_chat_text_enter);
            mImageReplyInChatTextEnter=(CircleImageView)findViewById(R.id.image_reply_profile_pic_in_text_enter);
            mMessageViewTextReplyInChatText=(TextView)findViewById(R.id.message_reply_in_chat_text_enter);
            mClosemLayoutReplyInChatTextEnter=(ImageView)findViewById(R.id.cancel_message_reply_in_item_message_friend);
            mImageBittonSend = (FloatingActionButton) findViewById(R.id.chat_button_send);
            mChatMessageText = (EmojiconEditText) findViewById(R.id.chat_message_text);
            mOpenEmojis = (ImageView) findViewById(R.id.open_chat_emojis);
            mOpenMoreMedia = (FloatingActionButton) findViewById(R.id.open_more_media_btn);
            recyclerChat = (RecyclerView) findViewById(R.id.chat_displayor);
            Slidr.attach(this);
            ExtractedStrings.MESSAGES_SELECTED_ID=new ArrayList<>();
            setSupportActionBar(conversation_toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // rootView.setBackgroundResource(R.drawable.chatback);
            //ReplaceFont.replaceDefaultFont(this,"DEFAULT","cooljazz.ttf");

            res = getResources();
            //get Divice Width
            DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
            Divice_with = metrics.widthPixels;

            mStartRecording.setClickable(true);
            mStartRecording.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!PermissionRequestCode.hasPremissions(getApplicationContext(), PermissionRequestCode.REC_VOICE_PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Chat_Activity.this, PermissionRequestCode.REC_VOICE_PERMISSIONS, PermissionRequestCode.REC_AUDIO);
                    } else {
                        dialogRecordAudio=new CustomDialogRecordAudio(Chat_Activity.this, Chat_Activity.this);
                        dialogRecordAudio.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogRecordAudio.setCancelable(true);
                        dialogRecordAudio.show();
                    }

                }
            });

            mClosemLayoutReplyInChatTextEnter.setClickable(true);
            mClosemLayoutReplyInChatTextEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLayoutReplyInChatTextEnter.setVisibility(RelativeLayout.GONE);
                }
            });

            mChatMessageText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mMessege = mChatMessageText.getText().toString().trim();
                    if (!TextUtils.isEmpty(mMessege)) {
                        mOpenMoreMedia.setVisibility(View.GONE);
                        mImageBittonSend.setImageDrawable(getResources().getDrawable(R.drawable.input_send_near_color_primary));

                    } else {
                        mOpenMoreMedia.setVisibility(View.VISIBLE);
                        mImageBittonSend.setImageDrawable(getResources().getDrawable(R.drawable.yes_ok));
                    }
                }
            });
            //initialisation
            try {
                Bundle bundle = getIntent().getExtras();
                UserRoomType= bundle.getCharSequenceArrayList(ExtractedStrings.INTENT_ROOM_TYPE);
                idFriends = bundle.getCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID);
                User_id = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID);
                nameFriend = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND);
                statusFriend = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_STATUS);
                ExtractedStrings.OpenedUser=User_id;
            }catch (Exception e){
                Toast.makeText(this, "In Chat ACTIVITY"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
            }

            mProgress = new ProgressDialog(this);
            //Linear layout manager call
            //------------------------------------
            //open media button
            mOpenMoreMedia.setClickable(true);
  /*          mOpenMoreMedia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rot_id.equals("true")) {
                        rot_id = "false";
                        Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
                        mOpenMoreMedia.startAnimation(an);
                        an.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                customDialogMediaShow=new CustomDialogMediaShow(Chat_Activity.this,User_id,nameFriend,statusFriend,idFriend);
                                customDialogMediaShow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                customDialogMediaShow.setCancelable(true);
                                customDialogMediaShow.show();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    } else {
                        rot_id = "true";
                        Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.back_rotate);
                        mOpenMoreMedia.startAnimation(an2);
                        an2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }
            });*/
            //-------------------------------------------------

            //open emojis button
            emojIcon=new EmojIconActions(getApplicationContext(),rootView,mChatMessageText,mOpenEmojis,"#214253","#FFFFFFFF","#FFFFFFFF");
            //emojIcon = new EmojIconActions(this, rootView, mChatMessageText, mOpenEmojis);
            emojIcon.ShowEmojIcon();
            emojIcon.setIconsIds(R.drawable.ic_key_boad_replace, R.drawable.ic_insert_emoticon_black_24dp);
            emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
                @Override
                public void onKeyboardOpen() {
                    Log.e(TAG, "Keyboard opened!");
                }

                @Override
                public void onKeyboardClose() {
                    Log.e(TAG, "Keyboard closed");
                }
            });
            MediaFilePopup.MediaKeyBoardAction mediaKeyBoardAction=new MediaFilePopup.MediaKeyBoardAction(this,mOpenMoreMedia,rootView,mChatMessageText,User_id,idFriend,statusFriend,nameFriend);
            mediaKeyBoardAction.showMediaTypes();
            mediaKeyBoardAction.setKeyboardListener(new MediaFilePopup.KeyboardListener() {
                @Override
                public void onKeyboardOpen() {

                }

                @Override
                public void onKeyboardClose() {

                }
            });
            //-----------------------------------------------------------------

             /*   try {
                    Resources res = FragmentHome.res;
                    if (imageFreind.equals("default") || imageFreind.equals(null) || imageFreind.length()<1000) {
                        src = BitmapFactory.decodeResource(res, R.drawable.avatar_default);
                    } else {
                        byte[] imageBytes = Base64.decode(imageFreind, Base64.DEFAULT);
                        src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    }
                    Image_drawable = new BitmapDrawable(res, src);
                } catch (Exception e) {
                }*/
            conversation_toolbar.setTitle(nameFriend);
            getSupportActionBar().setTitle(nameFriend);
            //Updating adapter chats
            consersation= AllChatsDB.getInstance(getApplicationContext()).getListMessages(getApplicationContext() ,User_id, ExtractedStrings.UID);
            adapter = new ListMessageAdapter(getApplicationContext(), consersation,colors() ,bitmapAvataFriend, bitmapAvataUser,Chat_Activity.this);
            UpdateAdapterChats(true);
            mImageBittonSend.setClickable(true);
            mImageBittonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = mChatMessageText.getText().toString().trim();
                    if (message.length()>0) {
                        SendMessage(message);
                    }else{
                        SendMessage("\uD83D\uDC48");
                    }
                }
            });

            if (User_id.length()<=0 || User_id==null){
                finish();
                startActivity(new Intent(Chat_Activity.this, Home_Activity.class));
            }

            LocalBroadcastManager.getInstance(this).registerReceiver(
                    new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            UpdateAdapterChats(true);
                            new PlaySound(context, R.raw.messagereceived);
                            stopService(new Intent(Chat_Activity.this, SaveNewMessage.class));
                          //  DeleteReceivedMessage();
                        }
                    }, new IntentFilter(SaveNewMessage.ACTION_NEW_MESSAGE_RECEIVED)
            );
            setOnItemClickListeners();
            setOnLongClickListeners();
            DeleteReceivedMessage();
        /*    if (idFriends.size()<=1) {
                mUpdfateUserOnline = new CountDownTimer(System.currentTimeMillis(), ExtractedStrings.TIME_TO_REFRESH) {
                    @Override
                    public void onTick(long l) {
                        UsersStatus.updateUserStatus(getApplicationContext(), User_id);
                        UsersStatus.updateUserStatus(getApplicationContext());
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                mUpdfateUserOnline.start();
            }*/


            if (idFriends.size()<2){
           /*     mRefleshUserOnline = new CountDownTimer(System.currentTimeMillis(), ExtractedStrings.TIME_TO_REFRESH) {
                    @Override
                    public void onTick(long l) {
                        FirebaseDatabase.getInstance().getReference().child("Users/Profiles/" + User_id + "/onlineStatus/isOnline").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                             *//*   try {
                                    boolean isOnline=dataSnapshot.getValue(boolean.class);
                                    if (isOnline){
                                        conversation_toolbar.setSubtitle("Online");
                                    }else {
                                        FirebaseDatabase.getInstance().getReference().child("Users/Profiles/" + User_id + "/onlineStatus/lastSeen").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                long lastSeen=dataSnapshot.getValue(long.class);
                                                conversation_toolbar.setSubtitle(DateUtils.getLastSeenTimeDate(lastSeen));
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }catch (Exception r){
                                    conversation_toolbar.setSubtitle(statusFriend);
                                }*//*

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                mRefleshUserOnline.start();*/
            }else {
                conversation_toolbar.setSubtitle(getGroupMembers(User_id));
            }


    }
    private String getGroupMembers(String id) {
        String members="";
        for (Group memberIfo: GroupDB.getInstance(context,id).getListGroup(id)){
            String sep= members.length()==0?"":", ";
            members=members+sep+memberIfo.UserName;
        }
        return members;
    }
    private void setOnItemClickListeners(){
        adapter.SetOnItemClickListener(new OnItemChatClickedListener() {
            @Override
            public void onItemChatClick(View view, int position) {
                if (!ExtractedStrings.MESSAGES_SELECTED_ID.isEmpty()){
                    if (ExtractedStrings.MESSAGES_SELECTED_ID.contains(consersation.getListMessageData().get(position).msgId)){
                        if (ExtractedStrings.MESSAGES_SELECTED_ID.size()<=1){
                            view.setBackgroundResource(R.drawable.layout_for_any_released_back);
                            clearMessageSelected();
                            UpdateAdapterChats(true);
                            if (ExtractedStrings.MESSAGES_SELECTED_ID.size()>1){
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_forward_id).setVisible(false);
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(false);
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_content_copy_id).setVisible(false);
                            }else {
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_forward_id).setVisible(true);
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(true);
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_content_copy_id).setVisible(true);
                            }
                            if(!consersation.getListMessageData().get(position).equals(ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE)){
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(false);
                            }else {
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(true);
                            }

                        }else {
                            ExtractedStrings.MESSAGES_SELECTED_ID.remove(consersation.getListMessageData().get(position).msgId);
                            view.setBackgroundResource(R.drawable.layout_for_any_released_back);
                            if (ExtractedStrings.MESSAGES_SELECTED_ID.size()>1){
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_forward_id).setVisible(false);
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(false);
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_content_copy_id).setVisible(false);
                            }else {
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_forward_id).setVisible(true);
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(true);
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_content_copy_id).setVisible(true);
                            }
                            if(!consersation.getListMessageData().get(position).equals(ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE)){
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(false);
                            }else {
                                menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(true);
                            }

                        }
                    }else {
                        ExtractedStrings.MESSAGES_SELECTED_ID.add(consersation.getListMessageData().get(position).msgId);
                        view.setBackgroundResource(R.drawable.layout_selected_chat);
                        if (ExtractedStrings.MESSAGES_SELECTED_ID.size()>1){
                            menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_forward_id).setVisible(false);
                            menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(false);
                            menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_content_copy_id).setVisible(false);
                        }else {
                            menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_forward_id).setVisible(true);
                            menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(true);
                            menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_content_copy_id).setVisible(true);
                        }
                        if(!consersation.getListMessageData().get(position).equals(ExtractedStrings.ITEM_MESSAGE_TEXT_TYPE)){
                            menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(false);
                        }else {
                            menuItemChatToolbarMenu.findItem(R.id.ic_in_chat_reply_id).setVisible(true);
                        }

                    }
                }
            }
            @Override
            public void onVoiceUploadClicked(View view, int position) {
                RelativeLayout mRecorederIcon = (RelativeLayout) view.findViewById(R.id.record_icon_for_uploaded_voice_in_item_user_voice);
                ImageView mUploadIcon = (ImageView) view.findViewById(R.id.upload_icon_for_item_user_voice);
                ProgressBar mLoadingUpload = (ProgressBar) view.findViewById(R.id.progress_for_voice_uploading_in_item_user_foice);
                CircleProgressBar mProcessProgressLoading = (CircleProgressBar) view.findViewById(R.id.auding_progress_download_voice_user);
              /*  ImageView mCancelUploadVoice = (ImageView) view.findViewById(R.id.cancel_upload_voice_icon_for_item_user_voice);
              */  mProcessProgressLoading.setVisibility(View.GONE);
                mRecorederIcon.setVisibility(View.GONE);
                mUploadIcon.setVisibility(View.GONE);
               /* mCancelUploadVoice.setVisibility(View.VISIBLE);
               */ mLoadingUpload.setVisibility(View.VISIBLE);
                final String recordTime = consersation.getListMessageData().get(position).PhotoStringBase64;
                final String UserId = consersation.getListMessageData().get(position).idReceiver;
                final String msgID = consersation.getListMessageData().get(position).msgId;
                final String filePath = consersation.getListMessageData().get(position).VoiceDeviceUrl;
                final String fileName = consersation.getListMessageData().get(position).text;
                new SendVoice(Chat_Activity.this,context,filePath,fileName,UserId,msgID,recordTime,mProcessProgressLoading,mLoadingUpload,mRecorederIcon,mUploadIcon).checkBeforeUpload();
            }
            @Override
            public void onImageUploadClicked(View view, int position) {
                mSendImageTasksList=new ArrayList<>();
                RelativeLayout mLayoutUplodImageBack = (RelativeLayout) view.findViewById(R.id.layout_retry_back_for_item_user);
                LinearLayout mUploadIconImage = (LinearLayout) view.findViewById(R.id.upload_image_in_item_user);
                ProgressBar mProgressLoading = (ProgressBar) view.findViewById(R.id.lauding_progress_upload_image_user);
                CircleProgressBar mProcessProgressLoading = (CircleProgressBar) view.findViewById(R.id.lauding_proccess_progress_upload_image_user);
                ImageView mCancelUploadButton = (ImageView) view.findViewById(R.id.cancel_upload_image_in_item_user);
                mLayoutUplodImageBack.setVisibility(View.VISIBLE);
                mUploadIconImage.setVisibility(View.GONE);
                mProcessProgressLoading.setVisibility(View.GONE);
                mProgressLoading.setVisibility(View.VISIBLE);
                mCancelUploadButton.setVisibility(View.VISIBLE);
                String imagePath=consersation.getListMessageData().get(position).PhotoDeviceUrl;
                String user_id=consersation.getListMessageData().get(position).idReceiver;
                String msgId=consersation.getListMessageData().get(position).msgId;
                sendMessageImageUploalod=new SendImage(Chat_Activity.this,context,imagePath
                        ,user_id,msgId,
                        mLayoutUplodImageBack,
                        mUploadIconImage,
                        mProgressLoading,
                        mProcessProgressLoading,
                        mCancelUploadButton);
               sendMessageImageUploalod.checkBeforeUpload();
            }
            //OnImageDownloadClicekd
            @Override
            public void onImageDownloadClicked(View view, int position) {
                RelativeLayout mLayoutDownloadImageBack = (RelativeLayout) view.findViewById(R.id.layout_retry_back_for_item_friend);
                ImageView mDownloadIconImage = (ImageView) view.findViewById(R.id.download_image_in_item_friend);
                CircleProgressBar mProgressLoading = (CircleProgressBar) view.findViewById(R.id.lauding_progress_upload_image_friend);
                ProgressBar mProgressLoadingCkeckInternet = (ProgressBar) view.findViewById(R.id.check_lauding_progress_upload_image_friend);
                ImageView mCancelDownloadButton = (ImageView) view.findViewById(R.id.cancel_download_image_in_item_friend);
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(context, PermissionRequestCode.IO_PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Chat_Activity.this, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                } else {
                    String imageUrl=consersation.getListMessageData().get(position).AnyMediaUrl;
                    String idReceiver=consersation.getListMessageData().get(position).idReceiver;
                    String msgId=consersation.getListMessageData().get(position).msgId;
                    String idSender=consersation.getListMessageData().get(position).idSender;
                    String idRoom=consersation.getListMessageData().get(position).idRoom;
                    new ReceiveImage(Chat_Activity.this,
                            context,
                            imageUrl,
                            idRoom,
                            idSender,
                            idReceiver,
                            msgId,
                            mLayoutDownloadImageBack,
                            mDownloadIconImage,
                            mProgressLoading,
                            mCancelDownloadButton,
                            mProgressLoadingCkeckInternet).startDownloadTask();
                }
            }
            @Override
            public void onVoiceDownloadClicked(View view, int position) {
                ImageView mPlayAudioRecorded = (ImageView) view.findViewById(R.id.play_voice_button_in_friend);
                mSeekbarAudioPlay=(SeekBar)view.findViewById(R.id.in_chat_item_voice_message_SeekBar_for_friend);
                //Icons ref
                ImageView mDownloadVoiceIcon = (ImageView) view.findViewById(R.id.download_icon_for_item_friend_voice);
                ProgressBar mInterCheckDownload = (ProgressBar) view.findViewById(R.id.interCheck_progress_for_voice_downloading_in_item_friend_voice);
                RelativeLayout mAlreadDownloadedIcon = (RelativeLayout) view.findViewById(R.id.voice_alread_downloaded_icon_in_friend);
                CircleProgressBar mDownloadLoading = (CircleProgressBar) view.findViewById(R.id.auding_progress_download_voice_friend);

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(context, PermissionRequestCode.IO_PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Chat_Activity.this, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                } else {
                    String voiceUrl=consersation.getListMessageData().get(position).AnyMediaUrl;
                    String idReceiver=consersation.getListMessageData().get(position).idReceiver;
                    String msgId=consersation.getListMessageData().get(position).msgId;
                    String idSender=consersation.getListMessageData().get(position).idSender;
                    String idRoom=consersation.getListMessageData().get(position).idRoom;

                    new ReceiveVoice(Chat_Activity.this,
                            context,
                            voiceUrl,
                            idRoom,
                            idSender,
                            idReceiver,
                            msgId,
                            mPlayAudioRecorded,
                            mDownloadVoiceIcon,
                            mDownloadLoading,
                            mInterCheckDownload,
                            mAlreadDownloadedIcon).startDownloadTask();
                }
            }
            @Override
            public void onVideoUploadClicked(View view, int position) {
                 ImageView mUploadIconImage = (ImageView) view.findViewById(R.id.upload_video_in_item_user);
                ProgressBar mProgressLoading = (ProgressBar) view.findViewById(R.id.lauding_progress_upload_video_user);
                 LinearLayout mPlayVideoBack = (LinearLayout) view.findViewById(R.id.layout_back_for_any_user_video_play_button);
                RelativeLayout mUploadIconsImageBack = (RelativeLayout) view.findViewById(R.id.layout_back_for_any_user_video_upload_content_buttons);
                CircleProgressBar mProcessProgressLoading = (CircleProgressBar) view.findViewById(R.id.lauding_proccess_progress_upload_video_user);
                ImageView mCancelUploadButton = (ImageView) view.findViewById(R.id.cancel_upload_video_in_item_user);
                mUploadIconsImageBack.setVisibility(View.VISIBLE);
                mUploadIconImage.setVisibility(View.GONE);
                mProcessProgressLoading.setVisibility(View.GONE);
                mProgressLoading.setVisibility(View.VISIBLE);
                mPlayVideoBack.setVisibility(View.GONE);
                new SendVideo(Chat_Activity.this,
                        context,consersation.getListMessageData().get(position),
                        mUploadIconImage,
                        mProgressLoading,
                        mUploadIconsImageBack,
                        mProcessProgressLoading,
                        mCancelUploadButton,mPlayVideoBack).checkBeforeUpload(context);
            }
            @Override
            public void onVideoDownloadClicked(View view, int position) {

                LinearLayout mLayoutPlayVideoBack = (LinearLayout) view.findViewById(R.id.layout_back_for_any_friend_video_play_button);
                ImageView mPlayVideoButton = (ImageView) view.findViewById(R.id.play_video_in_item_friend);

                RelativeLayout mLayoutDownloadContentBack = (RelativeLayout) view.findViewById(R.id.layout_back_for_any_friend_video_upload_content_buttons);
                ImageView mDownloadIconVideo = (ImageView) view.findViewById(R.id.upload_video_in_item_friend);
                ImageView mCancelDownloadVideo = (ImageView) view.findViewById(R.id.cancel_upload_video_in_item_friend);

                CircleProgressBar mProccessProgressLoading = (CircleProgressBar) view.findViewById(R.id.lauding_proccess_progress_upload_video_friend);
                ProgressBar mInterCheckProgressLoading = (ProgressBar) view.findViewById(R.id.lauding_progress_upload_video_friend);

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(context, PermissionRequestCode.IO_PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Chat_Activity.this, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                } else {
                    new ReceiveVideo(Chat_Activity.this,
                            context,
                           consersation.getListMessageData().get(position),
                            mLayoutDownloadContentBack,
                            mDownloadIconVideo,
                            mCancelDownloadVideo,
                            mProccessProgressLoading,
                            mInterCheckProgressLoading,
                            mLayoutPlayVideoBack,
                            mPlayVideoButton).startDownloadTask();
                }
            }
            @Override
            public void onApkUploadClicked(View view, int position) {

            }
            @Override
            public void onApkDownloadClicked(View view, int position) {

            }
            @Override
            public void onDocumentUploadClicked(View view, int position) {
                ImageView mDocUploadIcon = (ImageView) view.findViewById(R.id.upload_icon_for_item_user_document);
                ProgressBar mDocLoading = (ProgressBar) view.findViewById(R.id.progress_for_document_uploading_in_item_user);
                CircleProgressBar mDocProcessProgressLoading = (CircleProgressBar) view.findViewById(R.id.lauding_progress_download_document_user);
               /* ImageView mCancelUploadButton = (ImageView) view.findViewById(R.id.cancel_upload_icon_for_item_user_document);
                */mDocLoading.setVisibility(View.VISIBLE);
                mDocProcessProgressLoading.setVisibility(View.GONE);
                mDocUploadIcon.setVisibility(View.GONE);
                Message message=consersation.getListMessageData().get(position);
                new SendDocument(Chat_Activity.this,context,message,idFriends,
                        mDocUploadIcon,
                        mDocLoading,
                        mDocProcessProgressLoading).checkBeforeUpload(context);

            }
            @Override
            public void onDocumentDownloadClicked(View view, int position) {
               ImageView mDocDownloadIcon = (ImageView) view.findViewById(R.id.download_icon_for_item_friend_document);
                ProgressBar mDocLoading = (ProgressBar) view.findViewById(R.id.progress_for_document_downloading_in_item_friend);
                CircleProgressBar mDocProcessProgressLoading = (CircleProgressBar) view.findViewById(R.id.lauding_progress_download_document_friend);


                RoundedImageView mDocPageFirstPageView = (RoundedImageView) view.findViewById(R.id.pdf_first_page_view_in_document_friend);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !PermissionRequestCode.hasPremissions(context, PermissionRequestCode.IO_PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Chat_Activity.this, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                } else {
                    Message message=consersation.getListMessageData().get(position);
                    new ReceiveDocument(Chat_Activity.this,
                            context,message,
                            mDocDownloadIcon,
                            mDocProcessProgressLoading,
                            mDocLoading).startDownloadTask();
                }
            }
            @Override
            public void onImageUploadCancelClicked(View view, int position) {
                mSendImageTasksList=new ArrayList<>();
                RelativeLayout mLayoutUplodImageBack = (RelativeLayout) view.findViewById(R.id.layout_retry_back_for_item_user);
                LinearLayout mUploadIconImage = (LinearLayout) view.findViewById(R.id.upload_image_in_item_user);
                ProgressBar mProgressLoading = (ProgressBar) view.findViewById(R.id.lauding_progress_upload_image_user);
                CircleProgressBar mProcessProgressLoading = (CircleProgressBar) view.findViewById(R.id.lauding_proccess_progress_upload_image_user);
                ImageView mCancelUploadButton = (ImageView) view.findViewById(R.id.cancel_upload_image_in_item_user);
                mLayoutUplodImageBack.setVisibility(View.VISIBLE);
                mUploadIconImage.setVisibility(View.GONE);
                mProcessProgressLoading.setVisibility(View.GONE);
                mProgressLoading.setVisibility(View.VISIBLE);
                mCancelUploadButton.setVisibility(View.VISIBLE);
                String imagePath=consersation.getListMessageData().get(position).PhotoDeviceUrl;
                String user_id=consersation.getListMessageData().get(position).idReceiver;
                String msgId=consersation.getListMessageData().get(position).msgId;
                sendMessageImageUploalod.cancelUpload();
            }
            @Override
            public void onImageDownloadCancelClicked(View view, int position) {

            }
            @Override
            public void onVoiceUploadCancelClicked(View view, int position) {
                RelativeLayout mRecorederIcon = (RelativeLayout) view.findViewById(R.id.record_icon_for_uploaded_voice_in_item_user_voice);
                ImageView mUploadIcon = (ImageView) view.findViewById(R.id.upload_icon_for_item_user_voice);
                ProgressBar mLoadingUpload = (ProgressBar) view.findViewById(R.id.progress_for_voice_uploading_in_item_user_foice);
                CircleProgressBar mProcessProgressLoading = (CircleProgressBar) view.findViewById(R.id.auding_progress_download_voice_user);
                 mProcessProgressLoading.setVisibility(View.GONE);
                mRecorederIcon.setVisibility(View.GONE);
                mUploadIcon.setVisibility(View.GONE);
                mLoadingUpload.setVisibility(View.VISIBLE);
                final String recordTime = consersation.getListMessageData().get(position).PhotoStringBase64;
                final String UserId = consersation.getListMessageData().get(position).idReceiver;
                final String msgID = consersation.getListMessageData().get(position).msgId;
                final String filePath = consersation.getListMessageData().get(position).VoiceDeviceUrl;
                final String fileName = consersation.getListMessageData().get(position).text;
                new SendVoice(Chat_Activity.this,context,filePath,fileName,UserId,msgID,recordTime,mProcessProgressLoading,mLoadingUpload,mRecorederIcon,mUploadIcon).cancelUpload();
            }
            @Override
            public void onVoiceDownloadCancelClicked(View view, int position) {

            }
            @Override
            public void onVideoUploadCancelClicked(View view, int position) {

            }
            @Override
            public void onVideoDownloadCancelClicked(View view, int position) {

            }
            @Override
            public void onAudioPlayButtonClicked(View view, int position) {
               ImageView mPlayAudioRecorded = (ImageView) view.findViewById(R.id.play_button_in_chat_message_voice_for_user);
                mSeekbarAudioPlay = (SeekBar) view.findViewById(R.id.in_chat_item_voice_message_SeekBar_for_user);

                if (new File(consersation.getListMessageData().get(position).VoiceDeviceUrl).exists()){
                    if (currentAudioItemPosition.equals(String.valueOf(position))){
                        if (isAudioPaused){
                            mPlayAudioRecorded.setImageResource(R.drawable.ic_in_chat_voice_message_pause_24dp);
                            mp.start();
                            mPreviorSeekbarAudioPlay.setMax(AudioMediaMax); // Set the Maximum range of the
                            mPreviorSeekbarAudioPlay.setProgress(AudioMediaPos);// set current progress to song's
                            seekHandler.removeCallbacks(new PlayAudioVoiceMessageSeekbar(mp, mPreviorSeekbarAudioPlay, seekHandler, mPreviorPlayAudioRecorded).moveSeekBarThread);
                            seekHandler.postDelayed(new PlayAudioVoiceMessageSeekbar(mp, mPreviorSeekbarAudioPlay,  seekHandler, mPreviorPlayAudioRecorded).moveSeekBarThread, 100);
                            mPreviorSeekbarAudioPlay.setEnabled(true);
                            isAudioPaused=false;
                            mPreviorSeekbarAudioPlay=mSeekbarAudioPlay;
                            mPreviorPlayAudioRecorded=mPlayAudioRecorded;
                        }else {
                            mp.pause();
                            mPreviorSeekbarAudioPlay=mSeekbarAudioPlay;
                            mPreviorPlayAudioRecorded=mPlayAudioRecorded;
                            mPreviorSeekbarAudioPlay.setProgress(mPreviorSeekbarAudioPlay.getProgress());
                            mPreviorPlayAudioRecorded.setImageResource(R.drawable.ic_in_voice_message_play);
                            AudioMediaPos = mp.getCurrentPosition();
                            AudioMediaMax = mp.getDuration();
                            mPreviorSeekbarAudioPlay.setEnabled(false);
                            isAudioPaused=true;
                        }
                    }else {

                        currentAudioItemPosition=String.valueOf(position);
                        if (isAudioChatPlaying){
                            mp.stop();
                            mPreviorSeekbarAudioPlay.setProgress(0);
                            mPreviorPlayAudioRecorded.setImageResource(R.drawable.ic_in_voice_message_play);
                            mPreviorSeekbarAudioPlay=mSeekbarAudioPlay;
                            mPreviorPlayAudioRecorded=mPlayAudioRecorded;
                            mPreviorSeekbarAudioPlay.setEnabled(false);
                            mSeekbarAudioPlay.setEnabled(true);
                            mp = new  MediaPlayer();
                            mPlayAudioRecorded.setImageResource(R.drawable.ic_in_chat_voice_message_pause_24dp);
                            try {
                                mp.setDataSource(consersation.getListMessageData().get(position).VoiceDeviceUrl);
                                mp.prepare();
                            } catch (IOException e) {

                            }
                            mp.start();
                            mSeekbarAudioPlay.setMax(mp.getDuration());

                            try {
                                mp.setDataSource(consersation.getListMessageData().get(position).VoiceDeviceUrl);
                            } catch (IllegalArgumentException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (SecurityException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IllegalStateException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            AudioMediaPos = mp.getCurrentPosition();
                            AudioMediaMax = mp.getDuration();
                            mSeekbarAudioPlay.setMax(AudioMediaMax); // Set the Maximum range of the
                            mSeekbarAudioPlay.setProgress(AudioMediaPos);// set current progress to song's
                            seekHandler.removeCallbacks(new PlayAudioVoiceMessageSeekbar(mp, mSeekbarAudioPlay, seekHandler, mPlayAudioRecorded).moveSeekBarThread);
                            seekHandler.postDelayed(new PlayAudioVoiceMessageSeekbar(mp, mSeekbarAudioPlay,  seekHandler, mPlayAudioRecorded).moveSeekBarThread, 100);

                        }else {
                            isAudioChatPlaying=true;
                            mPreviorSeekbarAudioPlay=mSeekbarAudioPlay;
                            mPreviorPlayAudioRecorded=mPlayAudioRecorded;
                            mPreviorSeekbarAudioPlay.setEnabled(false);
                            mSeekbarAudioPlay.setEnabled(true);
                            mp = new MediaPlayer();
                            mPlayAudioRecorded.setImageResource(R.drawable.ic_in_chat_voice_message_pause_24dp);
                            try {
                                mp.setDataSource(consersation.getListMessageData().get(position).VoiceDeviceUrl);
                                mp.prepare();
                            } catch (IOException e) {

                            }
                            mp.start();
                            mSeekbarAudioPlay.setMax(mp.getDuration());

                            try {
                                mp.setDataSource(consersation.getListMessageData().get(position).VoiceDeviceUrl);
                            } catch (IllegalArgumentException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (SecurityException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IllegalStateException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            AudioMediaPos = mp.getCurrentPosition();
                            AudioMediaMax = mp.getDuration();
                            mSeekbarAudioPlay.setMax(AudioMediaMax); // Set the Maximum range of the
                            mSeekbarAudioPlay.setProgress(AudioMediaPos);// set current progress to song's
                            seekHandler.removeCallbacks(new PlayAudioVoiceMessageSeekbar(mp, mSeekbarAudioPlay, seekHandler, mPlayAudioRecorded).moveSeekBarThread);
                            seekHandler.postDelayed(new PlayAudioVoiceMessageSeekbar(mp, mSeekbarAudioPlay, seekHandler, mPlayAudioRecorded).moveSeekBarThread, 100);

                        }
                    }

                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setCancelable(false)
                            .setIcon(R.drawable.ic_in_chat_error_message_sign)
                            .setTitle("Error")
                            .setMessage("File does not exist.\n"+"do you want to delete this Item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }

            }
            @Override
            public void onReceivedVoicePlayClicked(View itemView, int position) {
                ImageView mPlayAudioRecorded = (ImageView) itemView.findViewById(R.id.play_voice_button_in_friend);
                mSeekbarAudioPlay=(SeekBar)itemView.findViewById(R.id.in_chat_item_voice_message_SeekBar_for_friend);
                if (new File(consersation.getListMessageData().get(position).VoiceDeviceUrl).exists()){
                    if (currentAudioItemPosition.equals(String.valueOf(position))){
                        if (isAudioPaused){
                            mPlayAudioRecorded.setImageResource(R.drawable.ic_in_chat_voice_message_pause_24dp);
                            mp.start();
                            mPreviorSeekbarAudioPlay.setMax(AudioMediaMax); // Set the Maximum range of the
                            mPreviorSeekbarAudioPlay.setProgress(AudioMediaPos);// set current progress to song's
                            seekHandler.removeCallbacks(new PlayAudioVoiceMessageSeekbar(mp, mPreviorSeekbarAudioPlay, seekHandler, mPreviorPlayAudioRecorded).moveSeekBarThread);
                            seekHandler.postDelayed(new PlayAudioVoiceMessageSeekbar(mp, mPreviorSeekbarAudioPlay,  seekHandler, mPreviorPlayAudioRecorded).moveSeekBarThread, 100);
                            mPreviorSeekbarAudioPlay.setEnabled(true);
                            isAudioPaused=false;
                            mPreviorSeekbarAudioPlay=mSeekbarAudioPlay;
                            mPreviorPlayAudioRecorded=mPlayAudioRecorded;
                        }else {
                            mp.pause();
                            mPreviorSeekbarAudioPlay=mSeekbarAudioPlay;
                            mPreviorPlayAudioRecorded=mPlayAudioRecorded;
                            mPreviorSeekbarAudioPlay.setProgress(mPreviorSeekbarAudioPlay.getProgress());
                            mPreviorPlayAudioRecorded.setImageResource(R.drawable.ic_in_voice_message_play);
                            AudioMediaPos = mp.getCurrentPosition();
                            AudioMediaMax = mp.getDuration();
                            mPreviorSeekbarAudioPlay.setEnabled(false);
                            isAudioPaused=true;
                        }
                    }else {

                        currentAudioItemPosition=String.valueOf(position);
                        if (isAudioChatPlaying){
                            mp.stop();
                            mPreviorSeekbarAudioPlay.setProgress(0);
                            mPreviorPlayAudioRecorded.setImageResource(R.drawable.ic_in_voice_message_play);
                            mPreviorSeekbarAudioPlay=mSeekbarAudioPlay;
                            mPreviorPlayAudioRecorded=mPlayAudioRecorded;
                            mPreviorSeekbarAudioPlay.setEnabled(false);
                            mSeekbarAudioPlay.setEnabled(true);
                            mp = new  MediaPlayer();
                            mPlayAudioRecorded.setImageResource(R.drawable.ic_in_chat_voice_message_pause_24dp);
                            try {
                                mp.setDataSource(consersation.getListMessageData().get(position).VoiceDeviceUrl);
                                mp.prepare();
                            } catch (IOException e) {

                            }
                            mp.start();
                            mSeekbarAudioPlay.setMax(mp.getDuration());

                            try {
                                mp.setDataSource(consersation.getListMessageData().get(position).VoiceDeviceUrl);
                            } catch (IllegalArgumentException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (SecurityException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IllegalStateException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            AudioMediaPos = mp.getCurrentPosition();
                            AudioMediaMax = mp.getDuration();
                            mSeekbarAudioPlay.setMax(AudioMediaMax); // Set the Maximum range of the
                            mSeekbarAudioPlay.setProgress(AudioMediaPos);// set current progress to song's
                            seekHandler.removeCallbacks(new PlayAudioVoiceMessageSeekbar(mp, mSeekbarAudioPlay, seekHandler, mPlayAudioRecorded).moveSeekBarThread);
                            seekHandler.postDelayed(new PlayAudioVoiceMessageSeekbar(mp, mSeekbarAudioPlay,  seekHandler, mPlayAudioRecorded).moveSeekBarThread, 100);

                        }else {
                            isAudioChatPlaying=true;
                            mPreviorSeekbarAudioPlay=mSeekbarAudioPlay;
                            mPreviorPlayAudioRecorded=mPlayAudioRecorded;
                            mPreviorSeekbarAudioPlay.setEnabled(false);
                            mSeekbarAudioPlay.setEnabled(true);
                            mp = new MediaPlayer();
                            mPlayAudioRecorded.setImageResource(R.drawable.ic_in_chat_voice_message_pause_24dp);
                            try {
                                mp.setDataSource(consersation.getListMessageData().get(position).VoiceDeviceUrl);
                                mp.prepare();
                            } catch (IOException e) {

                            }
                            mp.start();
                            mSeekbarAudioPlay.setMax(mp.getDuration());

                            try {
                                mp.setDataSource(consersation.getListMessageData().get(position).VoiceDeviceUrl);
                            } catch (IllegalArgumentException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (SecurityException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IllegalStateException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            AudioMediaPos = mp.getCurrentPosition();
                            AudioMediaMax = mp.getDuration();
                            mSeekbarAudioPlay.setMax(AudioMediaMax); // Set the Maximum range of the
                            mSeekbarAudioPlay.setProgress(AudioMediaPos);// set current progress to song's
                            seekHandler.removeCallbacks(new PlayAudioVoiceMessageSeekbar(mp, mSeekbarAudioPlay, seekHandler, mPlayAudioRecorded).moveSeekBarThread);
                            seekHandler.postDelayed(new PlayAudioVoiceMessageSeekbar(mp, mSeekbarAudioPlay, seekHandler, mPlayAudioRecorded).moveSeekBarThread, 100);

                        }
                    }

                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setCancelable(false)
                            .setIcon(R.drawable.ic_in_chat_error_message_sign)
                            .setTitle("Error")
                            .setMessage("File does not exist.\n"+"do you want to delete this Item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onImageMessageUserClicked(View itemView, int position) {
                String url=consersation.getListMessageData().get(position).PhotoDeviceUrl;
                if (new File(url).exists()){
                    Intent intent=new Intent(Chat_Activity.this, PhotoViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_KEY_IMAGE_URL,url );
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setCancelable(false)
                            .setIcon(R.drawable.ic_in_chat_error_message_sign)
                            .setTitle("Error")
                            .setMessage("File does not exist.\n"+"do you want to delete this Item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            }
            @Override
            public void onImageMessageFriendClicked(View itemView, int position) {
                String url=consersation.getListMessageData().get(position).PhotoDeviceUrl;
                if (new File(url).exists()){
                    Intent intent=new Intent(Chat_Activity.this, PhotoViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_KEY_IMAGE_URL,url );
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setCancelable(false)
                            .setIcon(R.drawable.ic_in_chat_error_message_sign)
                            .setTitle("Error")
                            .setMessage("File does not exist.\n"+"do you want to delete this Item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }

            }

            @Override
            public void onVideoMessageUserClicked(View itemView, int position) {
                String url=consersation.getListMessageData().get(position).VideoDeviceUrl;
                if (new File(url).exists()){
                    Intent intent=new Intent(Chat_Activity.this, VideoPlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_KEY_VIDEO_URL, url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setCancelable(false)
                            .setIcon(R.drawable.ic_in_chat_error_message_sign)
                            .setTitle("Error")
                            .setMessage("File does not exist.\n"+"do you want to delete this Item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onVideoMessageFrienClicked(View itemView, int position) {
                String url=consersation.getListMessageData().get(position).VideoDeviceUrl;
                if (new File(url).exists()){
                    Intent intent=new Intent(Chat_Activity.this, VideoPlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_KEY_VIDEO_URL, url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
               else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setCancelable(false)
                            .setIcon(R.drawable.ic_in_chat_error_message_sign)
                            .setTitle("Error")
                            .setMessage("File does not exist.\n"+"do you want to delete this Item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onOpenDocCliked(View itemView,final int position) {
               final Chat_Activity  chat_activity=Chat_Activity.this;
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
                                            , consersation.getListMessageData().get(position).idReceiver , consersation.getListMessageData().get(position).idSender,ExtractedStrings.MESSAGES_SELECTED_ID);
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

            @Override
            public void onContactViewClick(View itemView, int position) {
                String user_id=consersation.getListMessageData().get(position).idReceiver;
                String ContactList=consersation.getListMessageData().get(position).text;
                Bundle bundle = new Bundle();
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, user_id);
                bundle.putString(ExtractedStrings.INTENT_MESSAGE_CONTACT_FILE,ContactList);
                Intent intent=new Intent(Chat_Activity.this, ViewContactMessage.class);
                ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                idFriend.add(user_id);
                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        conversation_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (idFriend.size()>1){

                }else {*/
               UserProfileActivity.avata=bitmapAvataFriend.get(User_id);
                    Bundle bundle=new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_ID,User_id);
                    Intent intent=new Intent(Chat_Activity.this, UserProfileActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

             //   }

            }
        });
    }
    private void setOnLongClickListeners(){
        adapter.SetOnItemLongClickListener(new OnItemChatLongClickedListener() {
            @Override
            public void onItemChatItemLongClick(View view, int position) {
                String id=consersation.getListMessageData().get(position).idSender;
                String message=consersation.getListMessageData().get(position).text;
                ExtractedStrings.MESSAGES_SELECTED_ID.add(consersation.getListMessageData().get(position).msgId);
               new CreateMenuMethodsChatItem().CreateMenuFunction(id, message,context, Chat_Activity.this);
                view.setBackgroundResource(R.drawable.layout_selected_chat);
            }
        });
    }
    private ArrayList<Integer> colors(){
        ArrayList<Integer> arrayList=new ArrayList<>();
        int a=0;
        for(int i=0;i<consersation.getListMessageData().size();i++){
            if (a == 6) {
                a=0;
            }
            int color=new ColorCreation().getColorList(this).get(a);
            arrayList.add(color);
            a++;
        }
        return arrayList;
    }
    private void SendMessage(String message){
            new SendTextMessage(Chat_Activity.this,getApplicationContext()).SendMessage(User_id,message,idFriends);
    }
    public void UpdateAdapterChats (boolean isRetryAlwd) {
        isRetryAllowed=isRetryAlwd;
        Handler mHandler=new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
                    recyclerChat.setLayoutManager(linearLayoutManager);
                    recyclerChat.getLayoutManager().setAutoMeasureEnabled(true);
                    recyclerChat.setNestedScrollingEnabled(false);
                    recyclerChat.setHasFixedSize(true);
                    recyclerChat.setItemViewCacheSize(200);
                    recyclerChat.setDrawingCacheEnabled(true);
                    recyclerChat.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    consersation= AllChatsDB.getInstance(getApplicationContext()).getListMessages(getApplicationContext() ,User_id, ExtractedStrings.UID);
                    adapter = new ListMessageAdapter(getApplicationContext(), consersation,colors() ,bitmapAvataFriend, bitmapAvataUser,Chat_Activity.this);
                    adapter.notifyDataSetChanged();
                    linearLayoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);
                    recyclerChat.setAdapter(adapter);
                    RecentNotificationDb.getInstance(getApplicationContext()).DeleteMessageNotification(getApplicationContext(),User_id);
                }catch (Exception g){
                    Toast.makeText(mediaContext, "In Adapter" +g.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void DeleteReceivedMessage(){
        try {
            for (final Message message:consersation.getListMessageData()){
                if (!message.idSender.equals(ExtractedStrings.UID)){
                    if (!message.messageStatus.equals(ExtractedStrings.MESSAGE_STATUS_READED)){
                        FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.NotificationPath(String.valueOf(message.idReceiver))).child(message.msgId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Message newMsg=message;
                                newMsg.messageStatus=ExtractedStrings.MESSAGE_STATUS_READED;
                                AllChatsDB.getInstance(getApplicationContext()).CkeckBeforeAddMessage(getApplicationContext(),newMsg,true);
                            }
                        });
                    }
                }
            }
        }catch (Exception r){

        }

    }
    public void startRecording(String fileName) {
        try {
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            myAudioRecorder.setOutputFile(fileName);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            try {
                myAudioRecorder.prepare();
                myAudioRecorder.start();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
                Toast.makeText(this, "in start recording prepare method" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(mediaContext, "in create file path"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void stopRecord() {
        try {
            myAudioRecorder.stop();
        }catch (Exception e){
            Toast.makeText(mediaContext, "In Stop Reocord"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void stopRecording() {
        try {
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;
        }catch (Exception e){
            Toast.makeText(mediaContext, "In Stop Reocord"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void clearMessageSelected(){
        menu_item_toolbar.setVisibility(View.GONE);
        ExtractedStrings.MESSAGES_SELECTED_ID.clear();
        ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";
        ExtractedStrings.MESSAGE_REPLAYED_ID="";
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        startActivity(new Intent(Chat_Activity.this,Home_Activity.class));
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        Chat_Activity_Status.isChatActivityRunning=false;
        Chat_Activity_Status.isNewImageMessageSent=false;
        Chat_Activity_Status.isNewVoiceImageSent=false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.conversation_menus_user, menu);
            ChatMenuOptionPrepare();
            try {
                Bitmap bitmap=null;
                Resources res = getResources();
                if (bitmapAvataFriend==null) {
                    bitmap= BitmapFactory.decodeResource(res, R.drawable.avatar_default);
                } else {
                    bitmap= bitmapAvataFriend.get(User_id);
                    if (bitmap == null) {
                        bitmap= BitmapFactory.decodeResource(res, R.drawable.avatar_default);
                    }
                }

                RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                menu.findItem(R.id.chat_small_profile_icon_menu).setIcon(roundedBitmapDrawable);
            }catch (Exception e){
                Toast.makeText(mediaContext, "In Menu Icon \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            Toast.makeText(mediaContext, "In On Option Menu"+"\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    public void ChatMenuOptionPrepare() {
        ActionMenuView menuItemChatToolBar = (ActionMenuView) findViewById(R.id.menu_item_chat_tool_bar);
        menuItemChatToolbarMenu = menuItemChatToolBar.getMenu();
        getMenuInflater().inflate(R.menu.chat_option_menu, menuItemChatToolbarMenu);
        for (int i = 0; i < menuItemChatToolbarMenu.size(); i++) {
            menuItemChatToolbarMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id=item.getItemId();
            if (id==android.R.id.home){
                startActivity(new Intent(Chat_Activity.this,Home_Activity.class));
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
            if (id== R.id.ic_in_chat_back_id){
                menu_item_toolbar.setVisibility(AppBarLayout.GONE);
                clearMessageSelected();
                UpdateAdapterChats(true);
            }
            else if (id== R.id.ic_in_chat_reply_id){
                if (ExtractedStrings.MESSAGE_REPLAYED_ID.equals("")|| ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT.equals("")){
                    menu_item_toolbar.setVisibility(AppBarLayout.GONE);
                }else {
                    String name="";
                    try {
                        name=FriendDB.getInstance(getApplicationContext()).getInfoByIdUser(1,ExtractedStrings.MESSAGE_REPLAYED_ID,getApplicationContext());
                    }catch (Exception e){
                        name="+ "+ExtractedStrings.MESSAGE_REPLAYED_ID;
                    }
                    mImageReplyInChatTextEnter.setImageDrawable(Image_drawable);
                    mMessageViewTextReplyInChatText.setText(ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT);
                    mNameViewTextReplyInChatText.setText(name);
                    menu_item_toolbar.setVisibility(AppBarLayout.GONE);
                    mLayoutReplyInChatTextEnter.setVisibility(RelativeLayout.VISIBLE);
                    new CreateMenuMethodsChatItem().setImageInReplyerTextEnter(FriendDB.getInstance(context).getImageByIdUser(ExtractedStrings.MESSAGE_REPLAYED_ID, context),this);
                }
            }else if (id== R.id.ic_in_chat_forward_id){
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String title="MoodsApp";
                String description= ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                intent.putExtra(Intent.EXTRA_SUBJECT,title);
                intent.putExtra(Intent.EXTRA_TEXT,description);
                ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";
                ExtractedStrings.MESSAGE_REPLAYED_ID="";
                startActivity(Intent.createChooser(intent,"Share with.."));
            }else if (id== R.id.ic_in_chat_content_copy_id){
                ClipboardManager clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("simple text", ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(mediaContext, "Message copied", Toast.LENGTH_SHORT).show();
                menu_item_toolbar.setVisibility(AppBarLayout.GONE);
                ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT="";
                ExtractedStrings.MESSAGE_REPLAYED_ID="";
                clearMessageSelected();
            }else if (id== R.id.ic_in_chat_delete_id){
                menu_item_toolbar.setVisibility(AppBarLayout.GONE);
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder
                        .setIcon(R.drawable.ic_in_chat_question_black_24dp)
                        .setMessage("Are you sure, you want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AllChatsDB.getInstance(getApplicationContext()).DeleteMessages(getApplicationContext(),User_id,ExtractedStrings.UID ,ExtractedStrings.MESSAGES_SELECTED_ID);
                                UpdateAdapterChats(true);
                                clearMessageSelected();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearMessageSelected();
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }else if (id==android.R.id.home){
                Chat_Activity_Status.isChatActivityRunning=false;
                Chat_Activity_Status.isNewImageMessageSent=false;
                Chat_Activity_Status.isNewVoiceImageSent=false;
            }
        }catch (Exception e){
            Toast.makeText(mediaContext, "In OnOptionItemSelect"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void callCamera(String finalImagePath){
        pictureImagePath=finalImagePath;
        Uri outputFileUri= FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getApplicationContext().getPackageName()+".provider",new File(finalImagePath));
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);
        cameraIntent.putExtra(Intent.EXTRA_RETURN_RESULT,true);
        cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Log.d("LOGGED","pictureImagePath :"+finalImagePath);
        Log.d("LOGGED","outputFileUri :"+outputFileUri);
        startActivityForResult(cameraIntent, PermissionRequestCode.CAMERA_REQUEST);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionRequestCode.REC_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {
                    ActivityCompat.requestPermissions(Chat_Activity.this, PermissionRequestCode.REC_VOICE_PERMISSIONS, PermissionRequestCode.REC_AUDIO);
                }
                return;
            }
            case PermissionRequestCode.IO_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {
                    ActivityCompat.requestPermissions(Chat_Activity.this, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                }
                return;
            }

        }
    }

    public void callDocument(){
        Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("*/*");
        galleryIntentType="document";
        startActivityForResult(galleryIntent, PermissionRequestCode.GALLERY_REQUEST);
    }



    @Override
    protected void onStart() {
        super.onStart();
        MoodsApp.ChatActivityStarted();
    }
    @Override
    protected void onPause() {
        super.onPause();
        MoodsApp.ChatActivityPaused();

    }
    @Override
    protected void onStop() {
        super.onStop();
        MoodsApp.ChatActivityStoped();
        if (idFriends.size()<2){
        //    mRefleshUserOnline.cancel();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        UpdateAdapterChats(true);
        MoodsApp.ChatActivityResumed();
    }
}



