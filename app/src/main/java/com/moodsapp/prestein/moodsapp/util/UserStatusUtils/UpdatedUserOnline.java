package com.moodsapp.prestein.moodsapp.util.UserStatusUtils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.data.Database.RecentChatsDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.RecentChatRoom;
import com.moodsapp.prestein.moodsapp.service.OnineAndOfflineUsersStatus.UsersStatus;

public class UpdatedUserOnline {
    private final RecentChatRoom recentChatRoom;
    private static CountDownTimer mCountDownTimer;
    private UpdatedUserOnline(final Context context) {
        recentChatRoom = RecentChatsDB.getInstance(context).getListRecentChats(context);
        if (recentChatRoom.getListRecentChatDataData().size()>0){
            mCountDownTimer = new CountDownTimer(System.currentTimeMillis(), ExtractedStrings.TIME_TO_REFRESH) {
                @Override
                public void onTick(long l) {
                    UsersStatus.updateFriendStatus(context, recentChatRoom);
                    UsersStatus.updateUserStatus(context);
                }

                @Override
                public void onFinish() {

                }
            };
            mCountDownTimer.start();
            Toast.makeText(context, "Reflesh started", Toast.LENGTH_SHORT).show();
        }
    }
    public static void startOnlineDetector(Context context){
        new UpdatedUserOnline(context);
    }
    public static void stopOnlineDetector(){
        if (mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
    }
}

