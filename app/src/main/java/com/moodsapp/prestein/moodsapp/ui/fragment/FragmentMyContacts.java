package com.moodsapp.prestein.moodsapp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.model.Profile;
import com.moodsapp.prestein.moodsapp.model.RecentChatRoom;
import com.moodsapp.prestein.moodsapp.service.BackgroundProcess.ContactWatchService;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_contact.ListFriendsAdapter;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_contact.ListItem;
import com.moodsapp.prestein.moodsapp.util.ColorUtils.ColorCreation;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.privateImageLoader;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentMyContacts extends android.app.Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String whenNoInternetAvalable="Connection failed, Please try again later.";
    private  FragmentActivity myContext;
    private Point ptSize=new Point();
    public static Resources res;
    private List<ListItem> listItems;
    public static RecentChatRoom recentChatRoom=null;
    private RecyclerView mAllContacts;
    private View myContacts;
    private ListFriendsAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Cursor c;
    private ArrayList<String> iconsToDowmload;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myContacts = inflater.inflate(R.layout.fragment_my_contacts, container, false);
        Toolbar ContactToolbar=(Toolbar)myContacts.findViewById(R.id.set_tool_bar_in_all_users_and_contacts_view);
        AppCompatActivity activity=(AppCompatActivity)myContext;
        setHasOptionsMenu(true);
        activity.setSupportActionBar(ContactToolbar);
        ContactToolbar.setTitle("Select contact");
        ContactToolbar.setSubtitle(ExtractedStrings.NUMBERS_OF_CONTACTS_USERS+" Contacts"/*+" and"+(Integer.valueOf(ExtractedStrings.NUMBERS_OF_CONNECTED_USERS)>1 ? "users":"user")*/);
        //ContactToolbar.setTitleTextColor(R.color.colorPrimaryDar);

        mAllContacts = (RecyclerView) myContacts.findViewById(R.id.all_user_connected_contacts);
        mSwipeRefreshLayout = (SwipeRefreshLayout) myContacts.findViewById(R.id.Refresh_connected_contact_users);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        try {
            if (!PermissionRequestCode.hasPremissions(myContext, PermissionRequestCode.CONTACT_PERMISSIONS)) {
                ActivityCompat.requestPermissions(getActivity(), PermissionRequestCode.CONTACT_PERMISSIONS, PermissionRequestCode.READ_CCT);
            } else {
                listItems=new ArrayList<>();
                showContact();
            }
            updateAdapterMyContacts();
        }catch (Exception e){
            Toast.makeText(myContext,"In update chat Recent:"+ e.getMessage()
                    , Toast.LENGTH_LONG).show();
        }

        return myContacts;
    }
    public void updateAdapterMyContacts(){
        LinearLayoutManager chat_history_layout = new LinearLayoutManager(myContext,LinearLayoutManager.VERTICAL,false);
        chat_history_layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new ListFriendsAdapter(myContext, FriendDB.getInstance(myContext).getListFriendArray());
        mAllContacts.setLayoutManager(chat_history_layout);
        mAllContacts.setAdapter(adapter);
        mAllContacts.scrollToPosition(0);

    }
    private class TaskRefreshContacts extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            iconsToDowmload=new ArrayList<String>();
            int i=0;
            c=myContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC");
            while (c.moveToNext())
            {
                try {
                    final String contactName=c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    final String  pNumber=new NumberFormat().removeChar(new NumberFormat().removeChar(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
                    final  String finalPhone=pNumber.length() > 10 ? pNumber:new NumberFormat().removeChar(new NumberFormat().removeChar(ExtractedStrings.MY_COUNTRY_CODE+new NumberFormat().getTenDigits(pNumber)));
                    FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.AllUserPath()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(finalPhone)){
                                if (!finalPhone.equals(new NumberFormat().removeChar(new NumberFormat().removeChar(ExtractedStrings.UID)))){
                                    FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.AllUserPath()+"/"+finalPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            HashMap userMap = (HashMap) dataSnapshot.getValue();
                                            final Friend user = new Friend();
                                            user.name = contactName;
                                            user.country=(String) userMap.get(Profile.countryInfo);
                                            user.status = (String) userMap.get(Profile.statusInfo);
                                            user.image = (String) userMap.get(Profile.small_profile_imageInfo);
                                            user.id = finalPhone;
                                            checkBeforAddFriend(finalPhone, user);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            ToastMessage.makeText(FragmentMyContacts.this.getActivity(),R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            ToastMessage.makeText(FragmentMyContacts.this.getActivity(),R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                        }
                    });
                }catch (Exception r){
                    Toast.makeText(myContext, "In whkle"+r.getMessage()+"\n"+r.getCause(), Toast.LENGTH_SHORT).show();
                }

            }

            return FriendDB.getInstance(myContext).getListFriendArray().size()>=0;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (mSwipeRefreshLayout.isRefreshing()){
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(myContext);
        }
        protected void onPostExecute(Boolean result) {
            if (result){
                if (FriendDB.getInstance(myContext).getListFriendArray().size()<=0){
                    mSwipeRefreshLayout.setRefreshing(true);
                    new TaskRefreshContacts().execute();
                }else {
                    mSwipeRefreshLayout.setRefreshing(true);
                    new TaskRefreshContacts().execute();
                }

            }else {
                if (FriendDB.getInstance(myContext).getListFriendArray().size()<=0){
                    mSwipeRefreshLayout.setRefreshing(false);
                }else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(myContext, whenNoInternetAvalable, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
    public  void checkBeforAddFriend(final String idFriend, Friend userInfo) {
            FriendDB.getInstance(myContext).checkBeforeAdd(userInfo,idFriend,myContext);
            adapter.notifyDataSetChanged();
            updateAdapterMyContacts();
            new privateImageLoader(myContext,userInfo.image,userInfo.id);
    }
    @Override
    public void onCreateOptionsMenu(Menu m, MenuInflater inf) {
        inf.inflate(R.menu.menu_all_user_contacts, m);
        super.onCreateOptionsMenu(m, inf);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        String defaultMsg="Please select one from menu";

        switch (id){
            case R.id.action_invite_users:
                Toast.makeText(myContext, item.getTitle(), Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_menu_Refresh:
                if (FriendDB.getInstance(myContext).getListFriendArray().size()<=0){
                    //mLinearLayoutInternetStatus.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(true);
                    new TaskIsInternetAvailable().execute();
                }else {
                    mSwipeRefreshLayout.setRefreshing(true);
                    new TaskIsInternetAvailable().execute();

                }
                return true;
            case R.id.action_menu_Help:
                Toast.makeText(myContext, item.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_icon_reflesh:
                if (FriendDB.getInstance(myContext).getListFriendArray().size()<=0){
                    //  mLinearLayoutInternetStatus.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(true);
                    new TaskIsInternetAvailable().execute();
                }else {
                    mSwipeRefreshLayout.setRefreshing(true);
                    new TaskIsInternetAvailable().execute();
                }
                return true;
            default:
                Toast.makeText(myContext, defaultMsg, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            switch (requestCode) {
                case PermissionRequestCode.READ_CCT: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        listItems=new ArrayList<>();
                        showContact();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), PermissionRequestCode.CONTACT_PERMISSIONS, PermissionRequestCode.READ_CCT);
                    }
                    return;
                }

            }
        }catch (Exception e){
            Toast.makeText(myContext,e.getMessage(),Toast.LENGTH_LONG);
        }
    }
    private void showContact(){
        c=myContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC");
        while (c.moveToNext())
        {
            final String contactName=c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            final String  pNumber=new NumberFormat().removeChar(new NumberFormat().removeChar(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
            final  String finalPhone=pNumber.length() > 10 ? pNumber:new NumberFormat().removeChar(new NumberFormat().removeChar(ExtractedStrings.MY_COUNTRY_CODE+new NumberFormat().getTenDigits(pNumber)));
            ListItem listItem=new ListItem(contactName,pNumber);
            listItems.add(listItem);
        }
    }
    @Override
    public void onRefresh() {
        if (FriendDB.getInstance(myContext).getListFriendArray().size()<=0){
            // mLinearLayoutInternetStatus.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(true);
            new TaskIsInternetAvailable().execute();
        }else {
            mSwipeRefreshLayout.setRefreshing(true);
            new TaskIsInternetAvailable().execute();
        }
    }
}

