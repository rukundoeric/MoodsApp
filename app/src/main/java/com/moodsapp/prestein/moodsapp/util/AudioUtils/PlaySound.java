package com.moodsapp.prestein.moodsapp.util.AudioUtils;

import android.content.Context;
import android.media.MediaPlayer;

public  class PlaySound {
    public PlaySound(Context context,int sound) {
        MediaPlayer mPlayer = MediaPlayer.create(context, sound);
            mPlayer.start();
    }
}
