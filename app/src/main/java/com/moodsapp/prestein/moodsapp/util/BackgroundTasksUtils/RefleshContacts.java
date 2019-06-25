package com.moodsapp.prestein.moodsapp.util.BackgroundTasksUtils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;

import java.util.HashMap;

/**
 * Created by USER on 3/23/2018.
 */

public class RefleshContacts extends AsyncTask<String,Void,String> {
    private Cursor c;
    private Context myContext;

    public RefleshContacts(Context myContext) {
        this.myContext = myContext;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Toast.makeText(myContext, "Stated executing", Toast.LENGTH_SHORT).show();
            c=myContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC");
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
        }catch (Exception e){
        }
        return null;
    }


}
