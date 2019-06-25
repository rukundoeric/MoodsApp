package com.moodsapp.prestein.moodsapp.service.OnineAndOfflineUsersStatus;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.RecentChatRoom;
import com.moodsapp.prestein.moodsapp.model.ResentChats;

import java.util.HashMap;

public class UsersStatus {
    public static void updateUserStatus(Context context){
        if(isNetworkConnected(context)) {
            String uid = ExtractedStrings.UID;
            if (!uid.equals("")) {
                FirebaseDatabase.getInstance().getReference().child("Users/Profiles/" + uid + "/onlineStatus/isOnline").setValue(true);
                FirebaseDatabase.getInstance().getReference().child("Users/Profiles/" + uid + "/onlineStatus/lastSeen").setValue(System.currentTimeMillis());
            }
        }
    }

    public static void updateFriendStatus(Context context, RecentChatRoom listFriend){
        if(isNetworkConnected(context)) {
            for (ResentChats friend : listFriend.getListRecentChatDataData()) {
                final String fid = friend.id;
                FirebaseDatabase.getInstance().getReference().child("Users/Profiles/" + fid + "/onlineStatus").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            HashMap mapStatus = (HashMap) dataSnapshot.getValue();
                            if ((boolean) mapStatus.get("isOnline") && (System.currentTimeMillis() - (long) mapStatus.get("lastSeen")) > ExtractedStrings.TIME_TO_OFFLINE) {
                                FirebaseDatabase.getInstance().getReference().child("Users/Profiles/" + fid + "/onlineStatus/isOnline").setValue(false);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        }
    }
    public static void updateUserStatus(Context context, final String UserId){
        if(isNetworkConnected(context)) {

                FirebaseDatabase.getInstance().getReference().child("Users/Profiles/" + UserId + "/onlineStatus").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            HashMap mapStatus = (HashMap) dataSnapshot.getValue();
                            if ((boolean) mapStatus.get("isOnline") && (System.currentTimeMillis() - (long) mapStatus.get("lastSeen")) > ExtractedStrings.TIME_TO_OFFLINE) {
                                FirebaseDatabase.getInstance().getReference().child("Users/Profiles/" + UserId + "/onlineStatus/isOnline").setValue(false);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
        }
    }
    public static boolean isNetworkConnected(Context context) {
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null;
        }catch (Exception e){
            return true;
        }
    }
}
