package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_more_info_about_app;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.service.RecentNotification.RecentNotificationListClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Danny on 9/24/2017.
 */

public class ListingNotificationTest extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private RecentNotificationListClass recentNotificationListClass;
    public static Context context;
    public static Help fragment;
    public static final int VIEW_TYPE_NOTIFICATIONN = 0;

    public ListingNotificationTest(RecentNotificationListClass recentNotificationListClass, Context context, Help fragment) {
        this.recentNotificationListClass = recentNotificationListClass;
        this.context = context;
        this.fragment=fragment;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NOTIFICATIONN) {
            View view = LayoutInflater.from(context).inflate(R.layout.testnotificationmessga, parent, false);
            return new itemContactViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((itemContactViewHolder) holder).NotificationImage.setImageBitmap(((itemContactViewHolder) holder).NotificationIcon(recentNotificationListClass.getListRecentNotification().get(position).senderid,recentNotificationListClass.getListRecentNotification().get(position).notificationIcon));
        ((itemContactViewHolder) holder).title.setText(recentNotificationListClass.getListRecentNotification().get(position).title);
        ((itemContactViewHolder) holder).message.setText(recentNotificationListClass.getListRecentNotification().get(position).message);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NOTIFICATIONN;
    }

    @Override
    public int getItemCount() {
        return recentNotificationListClass.getListRecentNotification().size();
    }
    public class itemContactViewHolder extends RecyclerView.ViewHolder{
        public ImageView NotificationImage;
        public TextView title;
        public TextView message;
        private Bitmap bitmapBase;

        public itemContactViewHolder(View itemView) {
            super(itemView);
            NotificationImage=(ImageView) itemView.findViewById(R.id.notofication_Image_help);
            title=(TextView) itemView.findViewById(R.id.NotificationTitleMessage);
            message=(TextView) itemView.findViewById(R.id.NotificationMessageMessage);
        }
        //Simple method for image downloading
        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(fragment, "in load image URL \n"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        public Bitmap getCircularBitmap(Bitmap bitmap) {
            try {
                Bitmap output;
                if (bitmap.getWidth() > bitmap.getHeight()) {
                    output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                } else {
                    output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
                }

                Canvas canvas = new Canvas(output);

                final int color = 0xff424242;
                final Paint paint = new Paint();
                final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                float r = 0;

                if (bitmap.getWidth() > bitmap.getHeight()) {
                    r = bitmap.getHeight() / 2;
                } else {
                    r = bitmap.getWidth() / 2;
                }

                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                canvas.drawCircle(r, r, r, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, rect, rect, paint);
                return output;

            }
            catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(fragment, "In Bitmap crop \n"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        public Bitmap NotificationIcon(String userId,String urlIcon) {
            Toast.makeText(fragment,userId +"\n"+ "********************************************\n"+urlIcon, Toast.LENGTH_SHORT).show();
            try {
                if (FriendDB.getInstance(fragment).getInfoByIdUser(3,userId, fragment) != null) {
                    if (urlIcon.equals("")){
                        if (userId.startsWith("group")){
                            Bitmap bitmap1=BitmapFactory.decodeResource(fragment.getResources(), R.drawable.avatar_group);
                            return bitmap1;
                        }else {
                            Bitmap bitmap=BitmapFactory.decodeResource(fragment.getResources(), R.drawable.avatar_default);
                            return getCircularBitmap(bitmap);
                        }

                    }else {
                        FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("small_profile_image").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String imageBase=dataSnapshot.getValue(String.class);
                                byte[] image = Base64.decode(imageBase, Base64.DEFAULT);
                                bitmapBase=getCircularBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        return bitmapBase;
             /*           try {
                            AnyCustomProgressDialog progress=new AnyCustomProgressDialog("Connecting...", fragment);
                            progress.setCancelable(true);
                            progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            progress.show();

                            try {
                                URL url = new URL("https://firebasestorage.googleapis.com/v0/b/moodsapp-ad905.appspot.com/o/profileImages%2Fcropped-380929404.jpg?alt=media&token=5ff9d55e-802a-408b-8132-9706b8bb80cb");
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setDoInput(true);
                                connection.connect();
                                InputStream input = connection.getInputStream();
                                return BitmapFactory.decodeStream(input);
                            } catch (Exception e)
                            { e.printStackTrace();
                                Toast.makeText(fragment, "in load image URL \n"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
                                return null;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                    }
                    //return getCircularBitmap(getBitmapFromURL(urlIcon/*"https://firebasestorage.googleapis.com/v0/b/moodsapp-ad905.appspot.com/o/profileImages%2Fcropped-380929404.jpg?alt=media&token=5ff9d55e-802a-408b-8132-9706b8bb80cb"*/));
                } else {
                    byte[] image = Base64.decode(FriendDB.getInstance(fragment).getInfoByIdUser(3,userId, fragment), Base64.DEFAULT);
                    Bitmap bitmap=getCircularBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
                    return bitmap;
                }
            }catch (Exception r){
                Toast.makeText(fragment, "In Check if icon exist or not \n"+r.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }

    }

}
