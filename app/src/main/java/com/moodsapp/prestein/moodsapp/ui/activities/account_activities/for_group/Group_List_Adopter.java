package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_group;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.GroupDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Group;
import com.moodsapp.prestein.moodsapp.model.ListFriend;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.ui.fragment.FragmentGroup;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Prestein on 9/29/2017.
 */

public class Group_List_Adopter extends RecyclerView.Adapter<Group_List_Adopter.ViewHolder> {
    private ArrayList<Group> GroupListItems;
    private Context context;
    private Bitmap src;
    public static ListFriend listFriend = null;
    private Activity fragment;

    public Group_List_Adopter( Context context,ArrayList<Group> GroupListItems,Activity fragmentGroup) {
        this.GroupListItems = GroupListItems;
        this.context = context;
        this.fragment=fragmentGroup;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_group_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Group group_list_item=GroupListItems.get(position);
        final String id=group_list_item.GroupId;
        final String name=group_list_item.GroupName;
        String image=group_list_item.GroupImage;
        String members=getGroupMembers(id);
        holder.group_name.setText(name);
        holder.members_list.setText(members);
        try {
            if (image.equals("default")) {
                src = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_default);
            } else {
                byte[] imageBytes = Base64.decode(image, Base64.DEFAULT);
                src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
            Drawable Image_drawable=new BitmapDrawable(context.getResources(),src);
            holder.group_image_view.setImageDrawable(Image_drawable);
            holder.ripplerBackground.startRippleAnimation();
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            MemberImagesAdapter memberImagesAdapter=new MemberImagesAdapter(context,GroupDB.getInstance(context,id).getListGroup(id));
            holder.group_members_images_list.setLayoutManager(linearLayoutManager);
            holder.group_members_images_list.setAdapter(memberImagesAdapter);
        }catch (Exception e){
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Group created ", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(context, Chat_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, id);
                    bundle.putString(ExtractedStrings.INTENT_ROOM_TYPE, ExtractedStrings.GROUP_ROOM_TYPE);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, name);
                    ArrayList<CharSequence> idFriends = getGroupMembersIds(id);
                    bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriends);
                    intent.putExtras(bundle);
                    Chat_Activity.bitmapAvataFriend = new HashMap<>();
                    // byte[] decodedString = Base64.decode(friendImage, Base64.DEFAULT);
                    //Chat_Activity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                    // mapMark.put(id, null);
                    context.startActivity(intent);
                    fragment.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }catch (Exception e){
                    Toast.makeText(context, "In Home Onclick "+"\n"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private ArrayList<CharSequence> getGroupMembersIds(String id) {
        ArrayList<CharSequence> list=new ArrayList<>();
        String members="";
        for (Group memberIfo: GroupDB.getInstance(context,id).getListGroup(id)){
            members=members+", "+memberIfo.UserName;
            list.add(memberIfo.UserId);
        }
        return list;
    }
    private String getGroupMembers(String id) {
        String members="";
        for (Group memberIfo: GroupDB.getInstance(context,id).getListGroup(id)){
            String sep= members.length()==0?"":", ";
            members=members+sep+memberIfo.UserName;
        }
        return members;
    }

    @Override
    public int getItemCount() {
        return GroupListItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        RecyclerView group_members_images_list;
        RippleBackground ripplerBackground;
        CircleImageView group_image_view;
        EmojiconTextView group_name;
        EmojiconTextView members_list;
        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            group_image_view=(CircleImageView)itemView.findViewById(R.id.item_group_image);
            group_name=(EmojiconTextView)itemView.findViewById(R.id.item_group_name);
            members_list=(EmojiconTextView)itemView.findViewById(R.id.item_group_member_list);
            ripplerBackground=(RippleBackground)itemView.findViewById(R.id.group_item_content_background_online_status);
            group_members_images_list=(RecyclerView)itemView.findViewById(R.id.item_group_members_images_list);
        }
    }

}

 class MemberImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context x;
    ArrayList<Group> imagesMembers;


    public MemberImagesAdapter(Context x, ArrayList<Group> images) {
        this.x = x;
        this.imagesMembers = images;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(x).inflate(R.layout.row_small_cicler_image_view,parent,false);
        return new ViewHolderMemberImages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String imageMemberList=imagesMembers.get(position).UserImage;
        Bitmap srca;
        try {
            if (imageMemberList.length()<150) {
                srca = BitmapFactory.decodeResource(x.getResources(), R.drawable.avatar_default);
           /*     Toast.makeText(x, imageMemberList, Toast.LENGTH_SHORT).show();
                srca=ExtractedStrings.mProfileImage;*/
            } else {
                byte[] imageBytes = Base64.decode(imageMemberList, Base64.DEFAULT);
                srca = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
             ((ViewHolderMemberImages) holder).group_images_members_view.setImageBitmap(srca);
        }catch (Exception e){
            Toast.makeText(x, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return imagesMembers.size();
    }
     public class ViewHolderMemberImages extends RecyclerView.ViewHolder {
         View mView;

         public CircleImageView group_images_members_view;
         public ViewHolderMemberImages(View itemView) {
             super(itemView);
             mView=itemView;
             group_images_members_view=(CircleImageView)itemView.findViewById(R.id.item_small_circle_image_view);
         }
     }
}