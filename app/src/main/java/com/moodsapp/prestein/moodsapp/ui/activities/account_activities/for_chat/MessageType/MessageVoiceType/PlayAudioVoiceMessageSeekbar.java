package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVoiceType;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;

/**
 * Created by Eric prestein on 1/1/2018.
 */

public class PlayAudioVoiceMessageSeekbar {
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    Handler handler;
    ImageView playImage;

    public PlayAudioVoiceMessageSeekbar(MediaPlayer mediaPlayer, SeekBar seekBar,Handler handler, ImageView playImage) {
        this.mediaPlayer = mediaPlayer;
        this.seekBar = seekBar;
        this.handler = handler;
        this.playImage=playImage;
    }

    public Runnable moveSeekBarThread = new Runnable() {

        public void run() {
            if(mediaPlayer.isPlaying()){

                int mediaPos_new = mediaPlayer.getCurrentPosition();
                int mediaMax_new = mediaPlayer.getDuration();
                seekBar.setMax(mediaMax_new);
                seekBar.setProgress(mediaPos_new);
                handler.postDelayed(this, 100); //Looping the thread after 0.1 second
                // seconds
            }
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public boolean isTouchStarted=false;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (isTouchStarted){
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                  isTouchStarted=true;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    isTouchStarted=false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    seekBar.setProgress(0);
                    playImage.setImageResource(R.drawable.ic_in_voice_message_play);
                    Chat_Activity.isAudioChatPlaying=false;
                    seekBar.setEnabled(false);
                    Chat_Activity.isAudioChatPlaying=false;
                    Chat_Activity.currentAudioItemPosition="05";
                    Chat_Activity.AudioMediaMax=0;
                    Chat_Activity.AudioMediaPos=0;
                    Chat_Activity.isAudioPaused=false;
                }
            });
        }
    };
}
