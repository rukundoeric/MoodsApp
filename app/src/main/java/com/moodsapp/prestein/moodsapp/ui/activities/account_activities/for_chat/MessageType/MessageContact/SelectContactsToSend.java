package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;
import com.moodsapp.prestein.moodsapp.util.DiviceActionUtils.HomeWatcher;
import com.moodsapp.prestein.moodsapp.util.DiviceActionUtils.OnHomePressedListener;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ConvertImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ContactItemSelect{
    public String id;
    public String name;
    public String number;
    public long timeStamp;

    public ContactItemSelect(String id, String name, String number, long timeStamp) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
public class SelectContactsToSend extends AppCompatActivity {

    private RecyclerView mListContactToBeSelected;
    private ArrayList<ContactItemSelect> contactList;
    private String User_id;
    private ListContactAdapter adapter;
    private Cursor cursor;
    public Toolbar toolbar;
    private File vcfFile;
    private Context context;
    public FloatingActionButton fab;
    private SparseBooleanArray mSparseBooleanArray;
    private ArrayList<CharSequence> idFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts_to_send);
        toolbar = (Toolbar) findViewById(R.id.toolbar_in_activity_select_contact_to_send);
        context=this;
        mSparseBooleanArray=new SparseBooleanArray();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(context, PermissionRequestCode.IO_CONTACT_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PermissionRequestCode.IO_CONTACT_PERMISSIONS, PermissionRequestCode.IO_CO_RQST);
        } else {
            LoadContactsList();
        }
    }

    private void LoadContactsList() {
            mListContactToBeSelected=(RecyclerView)findViewById(R.id.select_contact_list_in_activity_select_contact_to_send);
            Bundle bundle = getIntent().getExtras();
            //User_id = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID);
            //idFriends=bundle.getCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID);
           // toolbar.setSubtitle(FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));
            toolbar.setTitle("Contacts List");
/*
                ConvertImage convertImage=new ConvertImage();
                Resources res = getResources();
                String Image_path=FriendDB.getInstance(context).getInfoByIdUser(3,User_id,context);
                Bitmap src;
                if (Image_path.equals("default") || Image_path.equals(null)) {
                    src = BitmapFactory.decodeResource(res, R.drawable.avatar_default);
                } else {
                    byte[] imageBytes = Base64.decode(Image_path, Base64.DEFAULT);
                    src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                }
                Drawable Icon_drawable=new BitmapDrawable(getResources(),convertImage.getCircularBitmap(context,src));
                toolbar.setLogo(Icon_drawable);*/

                contactList=new ArrayList<>();
                cursor=this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC");

                while (cursor.moveToNext()) {
                    int i=0;
                    boolean isThisNummberLoaded=false;
                    String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    long contactsDate= Long.parseLong(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP)));
                    String pNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    ContactItemSelect listItem = new ContactItemSelect(String.valueOf(i),contactName, pNumber,contactsDate);
                if (contactList.size()>=1){
                    for (ContactItemSelect itemSelect:contactList){
                        if (itemSelect.getName().equals(contactName)){
                            isThisNummberLoaded=true;
                            break;
                        }
                    }
                }
                    if (!isThisNummberLoaded){
                        contactList.add(listItem);
                    }

                    i++;
                }
            fab = (FloatingActionButton) findViewById(R.id.fab_send_in_activity_select_contact_to_send);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                        StringBuilder contactList= new StringBuilder();
                        for (ContactItemSelect contact:adapter.getCheckedItems()){
                            contactList.append(getStringToWrite(contact.getName(), contact.getNumber()));
                        }
                        saveMessage(contactList.toString());
                }
            });
            UpdateAdapter(0);

            HomeWatcher mHomeWatcher = new HomeWatcher(this);
            mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
                @Override
                public void onHomePressed() {
                   mSparseBooleanArray.clear();
                }
                @Override
                public void onHomeLongPressed() {
                }
            });
            mHomeWatcher.startWatch();
    }

    private String getStringToWrite(String name,String phoneNumber){
        String s="BEGIN:VCARD\r\n"+"VERSION:3.0\r\n"+"FN:" + name + "\r\n"+"TEL;TYPE=WORK,VOICE:" + phoneNumber +"\r\n"+"END:VCARD\r\n";
        return s;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            switch (requestCode) {
                case PermissionRequestCode.IO_CO_RQST: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      LoadContactsList();
                    } else {
                        ActivityCompat.requestPermissions(this, PermissionRequestCode.IO_CONTACT_PERMISSIONS, PermissionRequestCode.IO_CO_RQST);
                    }
                    return;
                }

            }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                mSparseBooleanArray.clear();
                finish();
                break;
        }
        return true;
    }
    private void saveMessage(String contactList) {
        SendContact sendContact=new SendContact();
        sendContact.saveContact(context,contactList,User_id,idFriends,adapter.getCheckedItems());
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSparseBooleanArray.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSparseBooleanArray.clear();
        finish();
    }
    public void UpdateAdapter(int scrollTo) {
      LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            mListContactToBeSelected.setLayoutManager(linearLayoutManager);
            adapter=new ListContactAdapter(contactList,this,this);
            mListContactToBeSelected.setAdapter(adapter);
            mListContactToBeSelected.scrollToPosition(scrollTo);

    }

 private class ListContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<ContactItemSelect> list;
    Context context;
    SelectContactsToSend selectContactsToSend;

    public ListContactAdapter(List<ContactItemSelect> list, Context context, SelectContactsToSend selectContactsToSend) {
        this.list = list;
        this.context = context;
        this.selectContactsToSend = selectContactsToSend;
    }
    //Method to return selected Images
    public ArrayList<ContactItemSelect> getCheckedItems() {
        ArrayList<ContactItemSelect> mTempArry = new ArrayList<ContactItemSelect>();

        for (int i = 0; i < list.size(); i++) {
            Log.d("log", "i = " + mSparseBooleanArray.get(i));
            if (mSparseBooleanArray.get(i)) {
                ContactItemSelect contactItemSelect=new ContactItemSelect(list.get(i).getId(),list.get(i).getName(),list.get(i).getNumber(),list.get(i).getTimeStamp());
                mTempArry.add(contactItemSelect);
            }
        }
        return mTempArry;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_select_contact_to_sent,parent,false);
        return new ItemContactView(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final ContactItemSelect c=list.get(position);
           // String date=new SimpleDateFormat("dd MM yyyy").format(new Date(c.getTimeStamp()));
            String date = new SimpleDateFormat("dd MM yyyy").format(new Date(c.getTimeStamp()));
            ((ItemContactView) holder).mName.setText(String.format("%s %s", c.getName(), date));
            if(mSparseBooleanArray.get(position)) {
                ((ItemContactView) holder).mLayoutContainer.setBackgroundResource(R.drawable.layout_for_any_selected_back);
                ((ItemContactView) holder).mSelectIcon.setVisibility(View.VISIBLE);
                mSparseBooleanArray.put(position, true);
            } else {
                ((ItemContactView) holder).mLayoutContainer.setBackgroundResource(R.drawable.layout_for_any_released_back);
                ((ItemContactView) holder).mSelectIcon.setVisibility(View.GONE);
                mSparseBooleanArray.put(position, false);
            }

            ((ItemContactView) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!mSparseBooleanArray.get(position)) {
                        ((ItemContactView) holder).mLayoutContainer.setBackgroundResource(R.drawable.layout_for_any_selected_back);
                        ((ItemContactView) holder).mSelectIcon.setVisibility(View.VISIBLE);
                        mSparseBooleanArray.put(position, true);
                        Log.d("log", "pos = "+position);
                        if (getCheckedItems().size()==1){
                            Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_grow_fade_in_from_bottom);
                            fab.startAnimation(an2);
                            an2.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    fab.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                        }
                    } else {
                        ((ItemContactView) holder).mLayoutContainer.setBackgroundResource(R.drawable.layout_for_any_released_back);
                        ((ItemContactView) holder).mSelectIcon.setVisibility(View.GONE);
                        mSparseBooleanArray.put(position, false);
                        if (getCheckedItems().size()==0){
                            Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_shrink_fade_out_from_bottom);
                            fab.startAnimation(an);
                            an.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    fab.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
 private class ItemContactView extends RecyclerView.ViewHolder{

        public TextView mName;
        public LinearLayout mLayoutContainer;
        public CircleImageView mImage;
        public ImageView mSelectIcon;

        public ItemContactView(View itemView) {
            super(itemView);
            mName=(TextView)itemView.findViewById(R.id.text_view_contact_selected_name);
            mLayoutContainer=(LinearLayout)itemView.findViewById(R.id.layout_select_contact_container);
            mImage=(CircleImageView)itemView.findViewById(R.id.image_contact_in_select_contact);
            mSelectIcon=(ImageView)itemView.findViewById(R.id.selected_sign_in_contact_select);
        }
    }
}

