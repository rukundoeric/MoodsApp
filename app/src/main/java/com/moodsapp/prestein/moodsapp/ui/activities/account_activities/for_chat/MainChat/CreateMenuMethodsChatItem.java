package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.AppBarLayout;
import android.util.Base64;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;

/**
 * Created by Eric prestein on 12/17/2017.
 */

public class CreateMenuMethodsChatItem {
    public void CreateMenuFunction(String id, String message, Context context, Chat_Activity chat_activity){
        try {
            /*Extracting id and message to be focused in Option*/
            ExtractedStrings.MESSAGE_REPLAYED_ID = id;
            ExtractedStrings.MESSAGE_REPLAYED_MESSAGE_TEXT = message;
            /*End*/
            chat_activity.menu_item_toolbar.setVisibility(AppBarLayout.VISIBLE);
        }catch (Exception d){
            Toast.makeText(context, "In Create Menu Function"+"\n"+d.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void setImageInReplyerTextEnter(String imagePro,Chat_Activity chat_activity){
        try {
            Bitmap bitmap;
            Resources res = chat_activity.getResources();
            if (imagePro.equals("default")) {
                bitmap = BitmapFactory.decodeResource(res, R.drawable.avatar_default);
            } else {
                byte[] imageBytes = Base64.decode(imagePro, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
            BitmapDrawable Image_drawable = new BitmapDrawable(res, bitmap);
            chat_activity.mImageReplyInChatTextEnter.setImageDrawable(Image_drawable);
        } catch (Exception e) {
        }
    }

}


