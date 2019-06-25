package com.moodsapp.prestein.moodsapp.service.BackgroundProcess;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.ui.fragment.FragmentMyContacts;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;

import java.util.HashMap;

public class detectNewContentObserver extends ContentObserver {
    private Context context;

    public detectNewContentObserver(Handler handler) {
        super(handler);
    }

    public detectNewContentObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        if (!selfChange) {
            try {
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED) {
                    ContentResolver cr = context.getContentResolver();
                    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                    if (cursor != null && cursor.getCount() > 0) {
                        //moving cursor to last position
                        //to get last element added
                        cursor.moveToLast();
                        String contactName = null;
                        final String photo = null;
                        String contactNumber = null;
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                        if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                            if (pCur != null) {
                                while (pCur.moveToNext()) {
                                    contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    if (contactNumber != null && contactNumber.length() > 0) {
                                        contactNumber = contactNumber.replace(" ", "");
                                    }
                                    contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                 //   String msg = "Name : " + contactName + " Contact No. : " + contactNumber;
                                    final String  pNumber=new NumberFormat().removeChar(new NumberFormat().removeChar(contactNumber));
                                    final  String finalPhone=pNumber.length() > 10 ? pNumber:new NumberFormat().removeChar(new NumberFormat().removeChar(ExtractedStrings.MY_COUNTRY_CODE+new NumberFormat().getTenDigits(pNumber)));
                                    final String finalContactName = contactName;
                                  //  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    FirebaseDatabase.getInstance().getReference().child("Users/Profiles").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChild(finalPhone)){
                                                if (!finalPhone.equals(new NumberFormat().removeChar(new NumberFormat().removeChar(ExtractedStrings.UID)))){
                                                    FirebaseDatabase.getInstance().getReference().child("Users/Profiles/"+finalPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            HashMap userMap = (HashMap) dataSnapshot.getValue();
                                                            final Friend user = new Friend();
                                                            user.name = finalContactName;
                                                            user.status = (String) userMap.get("status");
                                                            user.image = (String) userMap.get("small_profile_image");
                                                            user.id = finalPhone;
                                                            FriendDB.getInstance(context).checkBeforeAdd(user,user.id,context);
                                                        }
                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {
                                                          //  ToastMessage.makeText(context, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                                                        }
                                                    });
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                           // ToastMessage.makeText(FragmentMyContacts.this.getActivity(),R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                                        }
                                    });
                                }
                                pCur.close();
                            }
                        }
                        cursor.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
