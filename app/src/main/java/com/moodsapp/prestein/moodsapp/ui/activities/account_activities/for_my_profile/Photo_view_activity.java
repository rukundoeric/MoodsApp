package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_my_profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageUtils80px;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Photo_view_activity extends AppCompatActivity {


    private static final int GALLERY_REQUEST=1;
    private Uri viewPhotoUri;
    private ImageView mViewProfile_picture;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUserPhoto;
    private StorageReference mStorageUserPhoto;
    private ImageView mGetImageFromGallery;
    private ProgressDialog mProgress;
    private Uri resultUri;
    private String mUserEmail;
    private String mUserIdentifier;
    private Point ptSize=new Point();
    private LinearLayout mLayoutImage;
    private byte[] imageBytes;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity_photo_view_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_image_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgress=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mGetImageFromGallery=(ImageView)findViewById(R.id.view_image_profile_picture);
        mStorageUserPhoto= FirebaseStorage.getInstance().getReference().child("profileImages");
        mDatabaseUserPhoto= FirebaseDatabase.getInstance().getReference().child("Users");

        DatabaseReference getImage=mDatabaseUserPhoto.child(ExtractedStrings.UID).child("Profile").child("small_profile_image");
        getImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String Image_path=dataSnapshot.getValue(String.class);

                try {
                    Resources res = getResources();
                    //Nếu chưa có avatar thì để hình mặc định
                    Bitmap src;
                    if (ExtractedStrings.MY_PROFILE_PICTURE.equals("default")) {
                        src = BitmapFactory.decodeResource(res, R.mipmap.beforeprofile);
                    } else {
                        byte[] imageBytes = Base64.decode(Image_path, Base64.DEFAULT);
                        src = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    }
                    Drawable Image_drawable=new BitmapDrawable(getResources(),src);
                    mGetImageFromGallery.setImageDrawable(Image_drawable);
                }catch (Exception e){
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
             Toast.makeText(Photo_view_activity.this, "Your connection is not working property, check your connection and try again.",Toast.LENGTH_LONG).show();
            }
        });


        mViewProfile_picture=(ImageView)findViewById(R.id.view_image_profile_picture);
        mViewProfile_picture.setClickable(true);
        mViewProfile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Photo_view_activity
                        .this, "Click on the edit icon, to load the picture", Toast.LENGTH_LONG)
                        .show();
            }
        });
        mUserEmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Character a='@';
        for(int i=0;i<mUserEmail.length();i++){
            if (mUserEmail.charAt(i)==a){
                mUserIdentifier=mUserEmail.substring(0,i);
                //   Toast.makeText(this, mGetUserIdentifier, Toast.LENGTH_SHORT).show();
                break;
            }
        }
        increaseImage();
    }
    private void increaseImage() {
        getWindowManager().getDefaultDisplay().getSize(ptSize);
        int width=ptSize.x;
        int height=ptSize.y;
        mLayoutImage=(LinearLayout)findViewById(R.id.view_my_profile_picture_edit);
        ViewGroup.LayoutParams params=mLayoutImage.getLayoutParams();
        params.height=width;
        mLayoutImage.setLayoutParams(params);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu view_picture_menu) {

        getMenuInflater().inflate(R.menu.view_photo_menu, view_picture_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.change_profile_picture_icon){
         changeProfilePicture();
        }
        else if (id==R.id.share_profile_picture_icon){

        }
        else if(id==android.R.id.home){

            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj={MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader=new CursorLoader(this,contentUri,proj,null,null,null);
        Cursor cursor=cursorLoader.loadInBackground();
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                mProgress.setMessage("Uploading image...");
                InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                imgBitmap = ImageUtils80px.cropToSquare(imgBitmap);
                InputStream is = ImageUtils80px.convertBitmapToInputStream(imgBitmap);
                final Bitmap liteImage = ImageUtils80px.makeImageLite(is,
                        imgBitmap.getWidth(), imgBitmap.getHeight(),
                        ImageUtils80px.AVATAR_WIDTH, ImageUtils80px.AVATAR_HEIGHT);

                final String imageBase64 = ImageUtils80px.encodeBase64(liteImage);

                StorageReference filepath=mStorageUserPhoto.child(data.getData().getLastPathSegment());
                filepath.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String downloadUrl=taskSnapshot.getDownloadUrl().toString();
                        // String user_id=mAuth.getCurrentUser().getUid();
                        mDatabaseUserPhoto.child(mUserIdentifier).child("Profile").child("profile_image").setValue(downloadUrl);
                        mDatabaseUserPhoto.child(mUserIdentifier).child("Profile").child("small_profile_image").setValue(imageBase64);
                        mProgress.dismiss();
                        mViewProfile_picture.setImageURI(data.getData());
                        Toast.makeText(Photo_view_activity.this,"Uploaded successful...",Toast.LENGTH_LONG).show();
                        //NavUtils.navigateUpFromSameTask(Photo_view_activity.this);

                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


    //WHEN THE USER CLICK ON CHANGE PICTURE METHOD--------------
    private void changeProfilePicture() {

        Intent changeProfilepic=new Intent(Intent.ACTION_PICK);
        changeProfilepic.setType("image/*");
        startActivityForResult(changeProfilepic, GALLERY_REQUEST);
    }





}
