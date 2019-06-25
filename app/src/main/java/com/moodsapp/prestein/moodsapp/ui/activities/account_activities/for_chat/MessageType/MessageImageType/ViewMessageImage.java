package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ConvertImage;

import java.io.File;
import java.util.ArrayList;


public class ViewMessageImage extends AppCompatActivity {

    private ImageView mImageMessage;
    private String User_id;
    private Context context=this;
    private String ImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInViewMessageImage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        User_id = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID);
        toolbar.setTitle(FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));
        toolbar.setSubtitle(FriendDB.getInstance(context).getInfoByIdUser(2,User_id,context));
        ImagePath=bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_AVATA);
        mImageMessage=(ImageView)findViewById(R.id.ImageMessageInViewMessageImage);
        try {
            ConvertImage convertImage=new ConvertImage();
            Resources res = getResources();
            String Image_path= FriendDB.getInstance(context).getInfoByIdUser(3,User_id,context);
            Bitmap src;
            if (Image_path.equals("default") || Image_path.equals(null)) {
                src = BitmapFactory.decodeResource(res, R.drawable.avatar_default);
            } else {
                byte[] imageBytes = Base64.decode(Image_path, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
            Drawable Icon_drawable=new BitmapDrawable(getResources(),convertImage.getCircularBitmap(context,src));
            toolbar.setLogo(Icon_drawable);
        }catch (Exception e){
        }
        if (!ImagePath.equals("")) {
            if (new File(ImagePath).exists()){
                mImageMessage.setImageBitmap(BitmapFactory.decodeFile(ImagePath));
            }else{
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("File not found on your sdcard!")
                        .setIcon(R.drawable.ic_in_chat_error_message_sign)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
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
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_image_message,menu);
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
            case R.id.view_image_message_menu_id_share:
                Toast.makeText(this, "Share is Clicked", Toast.LENGTH_SHORT).show();
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
