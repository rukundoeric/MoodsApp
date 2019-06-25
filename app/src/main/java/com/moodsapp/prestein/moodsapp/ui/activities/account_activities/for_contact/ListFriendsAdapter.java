package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.service.BackgroundProcess.ContactWatchService;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Prestein on 10/3/2017.
 */

public class ListFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public ArrayList<Friend> listFriend;
    public Context context;
    public static Map<String, Query> mapQuery;
    public static Map<String, DatabaseReference> mapQueryOnline;
    public static Map<String, ChildEventListener> mapChildListener;
    public static Map<String, ChildEventListener> mapChildListenerOnline;
    public static Map<String, Boolean> mapMark;
    private ArrayList<Integer> colors;
    public Bitmap src;

    public ListFriendsAdapter(Context context, ArrayList<Friend> listFriend) {
        this.listFriend = listFriend;
        this.context = context;
        mapQuery = new HashMap<>();
        mapChildListener = new HashMap<>();
        mapMark = new HashMap<>();
        mapChildListenerOnline = new HashMap<>();
        mapQueryOnline = new HashMap<>();
        ExtractedStrings.NUMBERS_OF_CONNECTED_USERS= String.valueOf(listFriend.size());
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_connected, parent, false);
        return new ItemFriendViewHolder(context, view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final String name = listFriend.get(position).name;
            final String id = listFriend.get(position).id;
            final String status=listFriend.get(position).status;
            final String avata = listFriend.get(position).image;
            final CircleImageView image=((ItemFriendViewHolder) holder).avata;
            if (position==listFriend.size()-1){
                ((ItemFriendViewHolder) holder).linearLayout_invite_or_share.setVisibility(LinearLayout.VISIBLE);
            }     else {
                ((ItemFriendViewHolder)holder).linearLayout_invite_or_share.setVisibility(LinearLayout.GONE);
            }
            ((ItemFriendViewHolder) holder).txtName.setText(name);
            ((ItemFriendViewHolder) holder).txtStatus.setText(status);

            if (ContactWatchService.profileImageStore==null){
                ContactWatchService.profileImageStore=new HashMap<>();
            }
            if (ContactWatchService.profileImageStore.get(id)!=null){
                image.setImageBitmap(ContactWatchService.profileImageStore.get(id));
            }else {
                if (Data_Storage_Path.isThisPrivateFileExist(context,id,Data_Storage_Path.jpgFileFormat)){
                    String path=String.valueOf(new File(context.getFilesDir(),id+Data_Storage_Path.jpgFileFormat));
                    Glide.with(context).load(path).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                          Bitmap bitmap=((BitmapDrawable) resource).getBitmap();
                          ContactWatchService.profileImageStore.put(id,bitmap);
                            return false;
                        }
                    }).into(image);
                }else {
                    Glide.with(context).load(avata).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Bitmap bitmap=((BitmapDrawable) resource).getBitmap();
                            ContactWatchService.profileImageStore.put(id,bitmap);
                            return false;
                        }
                    }).into(image);
                }
            }
            ((ItemFriendViewHolder) holder).avata.setClickable(true);
            ((ItemFriendViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(context, Chat_Activity.class);
                        Bundle bundle = new Bundle();

                        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, name);
                        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, status);
                        ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                        idFriend.add(id);
                        bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, id);
                        intent.putExtras(bundle);

                        Chat_Activity.bitmapAvataFriend = new HashMap<>();
                        if (!avata.equals(ExtractedStrings.STR_DEFAULT_BASE64)) {
                           // byte[] decodedString = Base64.decode(avata, Base64.DEFAULT);
                            if (new File(avata).exists()){
                                Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeFile(avata));
                            }
                        } else {
                            Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default));
                        }
                        mapMark.put(id, null);
                        //ListingAdopter.fragment.startActivity(intent);

                        context.startActivity(intent);
                        // fragmentb.fragment.startActivityForResult(intent, AllMembersFragment.ACTION_START_CHAT);
                    }catch (Exception e){
                        Toast.makeText(context, "In On click"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }

    @Override
    public int getItemCount() {
        return listFriend.size();
    }

    class ItemFriendViewHolder extends RecyclerView.ViewHolder{
        private final View mView;
        public LinearLayout linearLayout_invite_or_share;
        public CircleImageView avata;
        public TextView txtName, txtTime, txtMessage,txtStatus;
        private Context context;

        ItemFriendViewHolder(Context context, View itemView) {
            super(itemView);
            mView=itemView;
            avata = (CircleImageView) itemView.findViewById(R.id.image_view_contact_display_connected);
            txtName = (TextView) itemView.findViewById(R.id.text_view_contact_username_connected);
            txtStatus = (TextView) itemView.findViewById(R.id.text_view_contact_phoneNo_connected);
            linearLayout_invite_or_share=(LinearLayout)itemView.findViewById(R.id.linear_invite_and_share);

            this.context = context;
        }
    }
}
