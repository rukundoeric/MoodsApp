package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.AllChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Message;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.AudioUtils.PlaySound;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eric prestein on 12/29/2017.
 */

public class CustomDialogRecordAudio extends Dialog {
    public Dialog d;
    public Activity activity;
    Chat_Activity chat_activity;
    private Handler handler;
    int btnState;
    String laps;
    private long lapsCount;
    long startIime;
    private TextView mRecordTimeDuration;
    private boolean mRecordStarted;
    public TextView mRecordTextViewDisplay;
    private ImageView mRecordLogo;
    public Button mRecordVoiceMessage;
    public ImageView mSendAudioButton;
    private long second;
    private long mills;
    private long seconds;
    public String RECORDED_VOICE_FINAL_PATH=null;
    private String FILE_NAME="";
    private String RECORD_TIME;

    public CustomDialogRecordAudio(Activity c,Chat_Activity chat_activity){
        super(c);
        this.activity=c;
        this.chat_activity=chat_activity;
    }
    @Override
    protected void onCreate(Bundle savedInstantState){
        super.onCreate(savedInstantState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sub_prompt_record_voice_message);
        // Here, thisActivity is the current activity
        handler=new Handler();
        lapsCount=0;
        laps="";
        btnState=1;
        loopFileName();
       /* RECORDED_VOICE_FINAL_PATH=Environment.getExternalStorageDirectory().getAbsolutePath();
        RECORDED_VOICE_FINAL_PATH+="/REC_"+new SimpleDateFormat("yyyy-MM-dd-mm-ss").format(new Date(System.currentTimeMillis()))+"_MD"+".3gp";
       */ mRecordTextViewDisplay=(TextView)findViewById(R.id.recording_text_view_display);
        mRecordTimeDuration=(TextView)findViewById(R.id.text_show_record_time_duration_record_dialog);
        mRecordLogo=(ImageView)findViewById(R.id.recording_logo_recording_dialog);
        mSendAudioButton=(ImageView)findViewById(R.id.send_voice_message);
        mSendAudioButton.setClickable(true);
        mSendAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RECORDED_VOICE_FINAL_PATH==null){
                    AlertDialog.Builder builder=new AlertDialog.Builder(chat_activity);
                    builder.setCancelable(false)
                            .setTitle("Error")
                            .setMessage("File not found")
                            .setNegativeButton("Ok", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();

                }else {
                    String msgId = chat_activity.User_id+ ExtractedStrings.UID+String.valueOf(System.currentTimeMillis());
                    Message newMessage = new Message();
                    newMessage.msgId=msgId;
                    newMessage.msgType= ExtractedStrings.ITEM_MESSAGE_VOICE_TYPE;
                    newMessage.idRoom= ExtractedStrings.UID+chat_activity.User_id;
                    newMessage.idSender = ExtractedStrings.UID;
                    newMessage.idReceiver = chat_activity.User_id;
                    newMessage.text = FILE_NAME;
                    newMessage.timestamp = System.currentTimeMillis();
                    newMessage.PhotoDeviceUrl="-";
                    newMessage.PhotoStringBase64=RECORD_TIME;
                    newMessage.VideoDeviceUrl="-";
                    newMessage.VoiceDeviceUrl=RECORDED_VOICE_FINAL_PATH;
                    newMessage.DocumentDeviceUrl="-";
                    newMessage.AnyMediaUrl="-";
                    newMessage.AnyMediaStatus= ExtractedStrings.MEDIA_NOT_UPLOADED;
                    newMessage.msg_reprayed_message = ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT;
                    newMessage.msg_reprayed_id= ExtractedStrings.MESSAGE_REPLAYED_ID;
                    newMessage.messageStatus=ExtractedStrings.MESSAGE_STATUS_SAVED;
                    AllChatsDB.getInstance(chat_activity).AddMessage(chat_activity,newMessage,false);
                    new PlaySound(activity.getApplicationContext(), R.raw.messagesended);
                    chat_activity.newVoiceIsSent=true;
                    chat_activity.UpdateAdapterChats(true);
                    chat_activity.dialogRecordAudio.dismiss();
                    // chat_activity.uploadAudioRecorede(RECORDED_VOICE_FINAL_PATH,chat_activity.User_id,FILE_NAME,ID,RECORD_TIME,null,null);
                }
            }
        });
        final ImageView closeDialog=(ImageView)findViewById(R.id.take_record_dialog_close);
        closeDialog.setClickable(true);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat_activity.dialogRecordAudio.dismiss();
            }
        });
        mRecordVoiceMessage=(Button)findViewById(R.id.button_record_audio_dialog);
        mRecordVoiceMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    if (event.getAction()==MotionEvent.ACTION_DOWN)
                    {
                        if (btnState==1)
                        {
                            startTimer();

                            chat_activity.startRecording(RECORDED_VOICE_FINAL_PATH);
                            mRecordLogo.setImageResource(R.drawable.ic_in_chat_take_voice_blue_24dp);

                        }

                    }
                    else if (event.getAction()==MotionEvent.ACTION_UP)
                    {
                        if (Integer.parseInt(String.valueOf(seconds)) >=1){
                            RECORD_TIME=mRecordTimeDuration.getText().toString().trim();
                            stopTimer();
                            chat_activity.stopRecording();
                            mRecordLogo.setImageResource(R.drawable.ic_in_chat_take_voice_white_24dp);
                            mRecordVoiceMessage.setVisibility(Button.GONE);
                            mSendAudioButton.setVisibility(ImageView.VISIBLE);
                            mRecordTextViewDisplay.setText("The record in recorded successful");
                        }else {
                            stopTimer();
                            chat_activity.stopRecord();
                            mRecordLogo.setImageResource(R.drawable.ic_in_chat_take_voice_white_24dp);
                            mRecordTimeDuration.setText(String.format("%02d:%02d",0,0));
                            mRecordVoiceMessage.setVisibility(Button.VISIBLE);
                            mSendAudioButton.setVisibility(ImageView.GONE);
                        }

                    }
                }catch (Exception r){
                    Toast.makeText(activity, "In on click start recording "+r.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;

            }
        });
    }
    private void loopFileName()
    {
        try {
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
            filePath+= Data_Storage_Path.VOICE_MESSAGE_SENT;
            if (new File(filePath).exists())
            {
                File[] list=new File(filePath).listFiles();
                int count=0;
                for (File f: list){
                    String name=f.getName();
                    if (name.endsWith(".3gp")){
                        count++;
                    }
                }

                FILE_NAME="REC_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MD"+(count)+".3gp";
                RECORDED_VOICE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
            else
            {
                new File(filePath).mkdirs();
                FILE_NAME="REC_"+new SimpleDateFormat("yyyyMMdd_mmss").format(new Date(System.currentTimeMillis()))+"_MD"+".3gp";
                RECORDED_VOICE_FINAL_PATH=new File(filePath)+"/"+FILE_NAME;
            }
        }catch (Exception e){
            Toast.makeText(chat_activity, "In file making dir"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void stopTimer() {
        try {
            handler.removeCallbacks(mRunnable);
            mRecordStarted=false;
        }catch (Exception r){
            Toast.makeText(activity, "In Stop time"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void startTimer() {
        try {
            mRecordStarted=true;
            handler.postDelayed(mRunnable,10L);
            laps="";
            startIime=System.currentTimeMillis();
            mRecordTextViewDisplay.setText("Recording...");
            btnState=2;
            mRecordTimeDuration.setText(String.format("%02d:%02d",0,0));
            lapsCount=0;
        }catch (Exception r){
            Toast.makeText(activity, "In Start time"+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private final Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            try {
                if (mRecordStarted)
                {
                    mills=(System.currentTimeMillis()-startIime);
                    second=mills/1000;
                    seconds=second%60;
                    mRecordTimeDuration.setText(String.format("%02d:%02d",second/60,second%60));
                    handler.postDelayed(mRunnable,10L);
                }
            }catch (Exception r){
                Toast.makeText(activity, "In Runnable"+r.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

}

