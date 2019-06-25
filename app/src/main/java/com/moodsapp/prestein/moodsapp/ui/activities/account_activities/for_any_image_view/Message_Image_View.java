package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_any_image_view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.moodsapp.prestein.moodsapp.R;

public class Message_Image_View extends AppCompatActivity {
    Uri ImageToView;
    private String idFriend;
    private String User_id;
    private String Chat_Room_Type;
    private String roomId;
    private String nameFriend;
    private String statusFriend;
    private int friendPosition;
    private Object idRoom;

    public Message_Image_View(Uri imageToView, String idFriend, String user_id, String chat_Room_Type, String roomId, String nameFriend, String statusFriend, int friendPosition, Object idRoom) {
        ImageToView = imageToView;
        this.idFriend = idFriend;
        User_id = user_id;
        Chat_Room_Type = chat_Room_Type;
        this.roomId = roomId;
        this.nameFriend = nameFriend;
        this.statusFriend = statusFriend;
        this.friendPosition = friendPosition;
        this.idRoom = idRoom;
    }

    public Message_Image_View() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message__image__view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
       // if (id==)
        return super.onOptionsItemSelected(item);
    }
}
