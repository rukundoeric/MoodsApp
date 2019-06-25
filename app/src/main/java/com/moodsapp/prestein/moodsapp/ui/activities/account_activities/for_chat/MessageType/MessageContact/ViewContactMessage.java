package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ConvertImage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Telephone;

class contactItem{
    public String name;
    public String number;

    public contactItem(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}


public class ViewContactMessage extends AppCompatActivity {

    private String User_id;
    private RecyclerView mContactViewList;
    private ArrayList<contactItem> contactList;
    private String Contact_cvf;
    private Context context=this;
    private ListContactMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_in_view_message_contact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
        mContactViewList=(RecyclerView)findViewById(R.id.view_contact_message_view_in_view_contact);
        Bundle bundle = getIntent().getExtras();
        User_id = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID);
        Contact_cvf=bundle.getString(ExtractedStrings.INTENT_MESSAGE_CONTACT_FILE);
        contactList=new ArrayList<>();
        toolbar.setSubtitle(FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));

            try {
                String phone="";
                List<VCard> vcards= Ezvcard.parse(Contact_cvf).all();
                for (VCard vCard:vcards){
                    String name=vCard.getFormattedName().getValue();
                    for (Telephone tel: vCard.getTelephoneNumbers()){
                        phone=tel.getText();
                    }
                    contactItem contact=new contactItem(name,phone);
                    contactList.add(contact);
                }
            }catch (Exception r){
                Toast.makeText(context, "In View Message \n"+r.getMessage(), Toast.LENGTH_SHORT).show();
            }
        UpadateAdapter();

        }catch (Exception r){
            Toast.makeText(context, "In View Contact \n"+r.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }


    private void UpadateAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mContactViewList.setLayoutManager(linearLayoutManager);
        adapter = new ListContactMessageAdapter(contactList, ViewContactMessage.this, ViewContactMessage.this);
        adapter.notifyDataSetChanged();
        linearLayoutManager.scrollToPosition(contactList.size() - 1);
        mContactViewList.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                Bundle bundle = new Bundle();
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS,FriendDB.getInstance(context).getInfoByIdUser(2,User_id,context));
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND,FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));
                Intent intent=new Intent(context, Chat_Activity.class);
                ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                idFriend.add(User_id);
                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS,FriendDB.getInstance(context).getInfoByIdUser(2,User_id,context));
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND,FriendDB.getInstance(context).getInfoByIdUser(1,User_id,context));
        Intent intent=new Intent(context, Chat_Activity.class);
        ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
        idFriend.add(User_id);
        bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
class ListContactMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<contactItem> contactLists;
    ViewContactMessage viewContactMessage;
    Context context;
    public ListContactMessageAdapter(ArrayList<contactItem> contactLists, ViewContactMessage viewContactMessage, Context context) {
        this.contactLists = contactLists;
        this.viewContactMessage = viewContactMessage;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.row_contact_message_list_item, parent, false);
            return new ItemContactViewType(view);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        try {
            contactItem contactItem = contactLists.get(position);
            NumberFormat numberFormat = new NumberFormat();
            FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("phone").equalTo(numberFormat.getTenDigits(numberFormat.removeChar(contactItem.getNumber()))).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String id = ((HashMap) dataSnapshot.getValue()).keySet().iterator().next().toString();
                        HashMap userMap = (HashMap) ((HashMap) dataSnapshot.getValue()).get(id);

                        try {
                            ConvertImage convertImage = new ConvertImage();
                            String Image_path = (String) userMap.get("small_profile_image");
                            Bitmap src;
                            if (Image_path.equals("default") || Image_path.equals(null)) {
                                src = BitmapFactory.decodeResource(viewContactMessage.getResources(), R.drawable.avatar_default);
                            } else {
                                byte[] imageBytes = Base64.decode(Image_path, Base64.DEFAULT);
                                src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            }
                            Drawable Icon_drawable = new BitmapDrawable(viewContactMessage.getResources(), convertImage.getCircularBitmap(context, src));
                            ((ItemContactViewType) holder).mImageContact.setImageDrawable(Icon_drawable);
                        } catch (Exception e) {
                            Toast.makeText(viewContactMessage, "In Adapter \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ((ItemContactViewType) holder).mImageContact.setImageBitmap(BitmapFactory.decodeResource(viewContactMessage.getResources(), R.drawable.avatar_default));
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            ((ItemContactViewType) holder).mContactName.setText(contactItem.getName());
            ((ItemContactViewType) holder).mContactNumber.setText(contactItem.getNumber());
        }catch (Exception f){
            Toast.makeText(viewContactMessage, "In On bind View \n"+f.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    public int getItemCount() {
        return contactLists.size();
    }
}
class ItemContactViewType extends RecyclerView.ViewHolder{
    public  CircleImageView mImageContact;
    public TextView mContactName;
    public TextView mContactNumber;
    public ImageView mCallContactIcon;
    public ImageView mMessageContactIcon;
    public Button mSaveContact;

    public ItemContactViewType(View itemView) {
        super(itemView);
        mImageContact=(CircleImageView)itemView.findViewById(R.id.view_contact_chat_image);
        mContactName=(TextView)itemView.findViewById(R.id.view_contact_chat_name);
        mContactNumber=(TextView)itemView.findViewById(R.id.view_contact_chat_phone_number);

        mCallContactIcon=(ImageView)itemView.findViewById(R.id.view_contact_chat_call_icon);
        mMessageContactIcon=(ImageView)itemView.findViewById(R.id.view_contact_chat_message_icon);

        mSaveContact=(Button)itemView.findViewById(R.id.view_contact_chat_save_contact);
    }
}