package com.moodsapp.prestein.moodsapp.util.ImageUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Eric prestein on 12/19/2017.
 */

public class ConvertImage {
    public BitmapDrawable Convert_Image_To_Bitmap_Drawable(String imagePro, Context context) {
        BitmapDrawable Image_drawable = null;
        Bitmap bitmap;
        Resources res = context.getResources();
        try {
            if (imagePro.equals("default")) {
                return new BitmapDrawable(res ,BitmapFactory.decodeResource(res, R.drawable.avatar_default));
            } else {
            byte[] imageBytes = Base64.decode(imagePro, Base64.DEFAULT);
            return new BitmapDrawable(res,BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
            }
        } catch (Exception e) {
            Toast.makeText(context, "In Image Convert Bitmap \n" + e.getMessage() + "\n" + e.getCause(), Toast.LENGTH_SHORT).show();
        }
        return new BitmapDrawable(res ,BitmapFactory.decodeResource(res, R.drawable.avatar_default));
    }
    public String[] getImageStringFromFirebase(String path, Context c) {
        final String ji = null;
        try {
            DatabaseReference d = FirebaseDatabase.getInstance().getReference().child(path);
            d.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int le = dataSnapshot.getValue(String.class).length();
                    String Image = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception r) {
            Toast.makeText(c, "In GetImage From Firebase \n" + r.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public static String From_Bitmap_to_String80px(Context context, Uri image) {
        String imageBase64 = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(image);
            Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
            imgBitmap = ImageUtils80px.cropToSquare(imgBitmap);
            InputStream is = ImageUtils80px.convertBitmapToInputStream(imgBitmap);
            final Bitmap liteImage = ImageUtils80px.makeImageLite(is,
                    imgBitmap.getWidth(), imgBitmap.getHeight(),
                    ImageUtils80px.AVATAR_WIDTH, ImageUtils80px.AVATAR_HEIGHT);

            imageBase64 = ImageUtils80px.encodeBase64(liteImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imageBase64;
    }
    public static String From_Bitmap_to_String600px(Context context, Uri image) {
        String imageBase64 = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(image);
            Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
            imgBitmap = ImageUtils600px.cropToSquare(imgBitmap);
            InputStream is = ImageUtils80px.convertBitmapToInputStream(imgBitmap);
            final Bitmap liteImage = ImageUtils80px.makeImageLite(is,
                    imgBitmap.getWidth(), imgBitmap.getHeight(),
                    ImageUtils80px.AVATAR_WIDTH, ImageUtils80px.AVATAR_HEIGHT);

            imageBase64 = ImageUtils80px.encodeBase64(liteImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imageBase64;
    }
    public String Decrease_String_Base(Context context,String base){
        String imageBase64="";
        try {
            byte[] imageBytes=Base64.decode(base, Base64.DEFAULT);
            Bitmap imgBitmap= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgBitmap =ImageUtilsDefault.cropToSquare(imgBitmap);
            InputStream is = ImageUtilsDefault.convertBitmapToInputStream(imgBitmap);
            final Bitmap liteImage = ImageUtilsDefault.makeImageLite(is,
                    imgBitmap.getWidth(), imgBitmap.getHeight(),
                    100, 100);

            imageBase64 = ImageUtilsDefault.encodeBase64(liteImage);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
        return imageBase64;
    }
    public static String From_Bitmap_to_StringDefault1(Context context, Bitmap image,int width,int height) {
        String imageBase64 = null;

        Bitmap imgBitmap = image;
        imgBitmap =ImageUtilsDefault.cropToSquare(imgBitmap);
        InputStream is = ImageUtilsDefault.convertBitmapToInputStream(imgBitmap);
        final Bitmap liteImage = ImageUtilsDefault.makeImageLite(is,
                imgBitmap.getWidth(), imgBitmap.getHeight(),
                width, height);

        imageBase64 = ImageUtilsDefault.encodeBase64(liteImage);
        return imageBase64;
    }
    public static String From_Bitmap_to_StringDefault(Context context, Uri image,int width,int height) {
        String imageBase64 = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(image);
            Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
            imgBitmap =ImageUtilsDefault.cropToSquare(imgBitmap);
            InputStream is = ImageUtilsDefault.convertBitmapToInputStream(imgBitmap);
            final Bitmap liteImage = ImageUtilsDefault.makeImageLite(is,
                    imgBitmap.getWidth(), imgBitmap.getHeight(),
                    width, height);

            imageBase64 = ImageUtilsDefault.encodeBase64(liteImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imageBase64;
    }
    public Bitmap getCircularBitmap(Context context,Bitmap bitmap) {
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
            Toast.makeText(context, "In Bitmap crop \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public static Bitmap getSquaredBitmap(Bitmap bitmap){
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width)? height - ( height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0)? 0: cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0)? 0: cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }
}