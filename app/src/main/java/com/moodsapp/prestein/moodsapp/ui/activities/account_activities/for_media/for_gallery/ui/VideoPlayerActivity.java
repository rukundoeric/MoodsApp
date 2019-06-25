package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.r0adkll.slidr.Slidr;

import java.io.File;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView mContentView;

    public void playVideo(String url) {
        MediaController m = new MediaController(this);
        mContentView.setMediaController(m);
        Uri u = Uri.fromFile(new File(url));
        mContentView.setVideoURI(u);
        mContentView.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_for_video_player_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContentView = findViewById(R.id.videoView_for_video_play_activity);
        Slidr.attach(this);
        Bundle bundle=getIntent().getExtras();
        String url=bundle.getString(ExtractedStrings.INTENT_KEY_VIDEO_URL);
        playVideo(url);
        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


}
