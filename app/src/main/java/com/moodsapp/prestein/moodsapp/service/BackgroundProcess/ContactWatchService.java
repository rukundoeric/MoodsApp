package com.moodsapp.prestein.moodsapp.service.BackgroundProcess;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.model.Profile;
import com.moodsapp.prestein.moodsapp.service.OnineAndOfflineUsersStatus.UsersStatus;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.privateImageLoader;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ContactWatchService extends Service {
    public static HashMap<String, Bitmap> profileImageStore;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private final int TIME_TO_REFLESH_CONTACTS= 3 * 60 * 1000;
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            try {
                refleshContact();
                //Register contact observer
                startContactObserver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void startContactObserver(){
        try{
            //Registering contact observer
            getApplication().getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true,new detectNewContentObserver(new Handler(),getApplicationContext()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            Intent broadcastIntent = new Intent("android.intent.action.BOOT_COMPLETED");
            sendBroadcast(broadcastIntent);
            //Code below is commented.
            //Turn it on if you want to run your service even after your app is closed
            Intent intent=new Intent(getApplicationContext(), ContactWatchService.class);
            startService(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void refleshContact() {
         new CountDownTimer(System.currentTimeMillis(), TIME_TO_REFLESH_CONTACTS) {
            @Override
            public void onTick(long l) {
                new TaskIsInternetAvailable().execute();
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }
    private ArrayList<String> getFriendsIds(){
        ArrayList<Friend> friends=FriendDB.getInstance(ContactWatchService.this).getListFriendArray();
        ArrayList<String> ids=new ArrayList<>();
        for (Friend friend:friends){
            ids.add(friend.id);
        }
        return ids;
    }
    public  void checkBeforAddFriend(final String idFriend, Friend userInfo) {
      FriendDB.getInstance(this).checkBeforeAdd(userInfo,idFriend,this);
    }
    @SuppressLint("StaticFieldLeak")
    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(ContactWatchService.this);
        }
        protected void onPostExecute(Boolean result) {
            if (result){
                final Cursor c=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC");
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
                                                        user.country=(String) userMap.get(Profile.countryInfo);
                                                        user.status = (String) userMap.get(Profile.statusInfo);
                                                        user.image = (String) userMap.get(Profile.small_profile_imageInfo);
                                                        user.id = finalPhone;
                                                        checkBeforAddFriend(finalPhone, user);
                                                        new privateImageLoader(ContactWatchService.this,user.image,user.id);

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
            }else {
       /*         for (String FILENAME:getFriendsIds()){
                    if (ContactWatchService.profileImageStore==null){
                        ContactWatchService.profileImageStore=new HashMap<>();
                    }
                    File file=new File(getFilesDir(),FILENAME);
                    Bitmap bitmap=null;
                    if (file.exists()){
                        bitmap= BitmapFactory.decodeFile(String.valueOf(file));
                    }else{
                        bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.avatar_default);
                    }
                    ContactWatchService.profileImageStore.put(FILENAME,bitmap);
                    if (MoodsApp.isHomeActivityVisibility() || MoodsApp.isChatActivityVisibility()){
                        Toast.makeText(ContactWatchService.this, "Connection failed, please try again later", Toast.LENGTH_SHORT).show();
                    }
                }*/
             }
        }
    }
}
