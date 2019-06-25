package com.moodsapp.prestein.moodsapp.service.BackgroundProcess;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;

import java.util.HashMap;

public class refleshContacts  {
    public static final String TEXT_MESSAGE =refleshContacts.class.getName() ;
    private final int TIME_TO_REFLESH_CONTACTS= 60 * 1000;
    private Cursor c;
    Context context;

    public refleshContacts(Context context) {
        this.context = context;
        c=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                while (c.moveToNext())
                {
                    try {
                        final String contactName=c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        final String  pNumber=new NumberFormat().removeChar(new NumberFormat().removeChar(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
                        final  String finalPhone=pNumber.length() > 10 ? pNumber:new NumberFormat().removeChar(new NumberFormat().removeChar(ExtractedStrings.MY_COUNTRY_CODE+new NumberFormat().getTenDigits(pNumber)));
                        FirebaseDatabase.getInstance().getReference().child("Users/Profiles").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(finalPhone)){
                                    if (!finalPhone.equals(new NumberFormat().removeChar(new NumberFormat().removeChar(ExtractedStrings.UID)))){
                                        FirebaseDatabase.getInstance().getReference().child("Users/Profiles/"+finalPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                HashMap userMap = (HashMap) dataSnapshot.getValue();
                                                Friend user = new Friend();
                                                user.name = contactName;
                                                user.status = (String) userMap.get("status");
                                                user.image = (String) userMap.get("small_profile_image");
                                                user.id = finalPhone;
                                                checkBeforAddFriend(finalPhone, user);
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }catch (Exception r){
                    }

                }

            }

        });

    }

    public  void checkBeforAddFriend(final String idFriend, Friend userInfo) {
        try {
            FriendDB.getInstance(context).checkBeforeAdd(userInfo,idFriend,context);
        }catch (Exception e){
            Toast.makeText(context,"In check before Add"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
