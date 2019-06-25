package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_my_profile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageUtils80px;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.skyfishjy.library.RippleBackground;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class Account_profile extends AppCompatActivity {
    private CircleImageView mCircleImageViewSelectImage;
    private String mgetName;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;
    private ProgressDialog mProgress;

    final Context context=this;
    final Context status_context=this;
    final Context phone_context=this;
    private TextView mDescription;
    private TextView mStatus;
    private TextView mPhone;
    private byte[] imageBytes;
    private Bitmap src;
    private Typeface cooljazz;
    private String CountryCodeWithPlus="";
    private TextView mProfileName;
    private String UserIdentifier=ExtractedStrings.UID;
    private RippleBackground mProfileBackPhoto;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_activity_account_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.set_profile_image_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /*  if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(R.color.colorPrimaryDarkt);
        }*/
        mProgress=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();

        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("Users");
        mProfileName=(TextView)findViewById(R.id.profile_name_in_set_activity_account_profile);
        mPhone=(TextView)findViewById(R.id.set_phone_in_set_activity_account_profile);
        mStatus=(TextView)findViewById(R.id.set_status_in_set_activity_account_profile);
        mProfileBackPhoto=(RippleBackground)findViewById(R.id.view_my_profile_content_background_profile_picture_view);
        mProfileBackPhoto.startRippleAnimation();
        mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusMethod();
            }
        });


        //when the user click on image in set profile activity
        mCircleImageViewSelectImage=(CircleImageView)findViewById(R.id.select_image_from_view_image);
        mCircleImageViewSelectImage.setClickable(true);
        mCircleImageViewSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account_profile.this, Photo_view_activity.class));
            }
        });

        //------------LOAD PROFILE DATA---------------------------------------------------------

        loadInfomationProfile();
        //-----------------------------------------------------------------------------------------

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_take_picture);
      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    private class TaskLoadProfilePicture extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(getApplicationContext());
        }
        protected void onPostExecute(Boolean result) {
            if (result){
                try {
                    FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.ImagePath(ExtractedStrings.UID)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String s=dataSnapshot.getValue(String.class);
                            if (URLUtil.isHttpsUrl(s) || URLUtil.isHttpUrl(s)){
                                Glide.with(getApplicationContext()).load(s).into(mCircleImageViewSelectImage);
                            }else {
                                Toast.makeText(getApplicationContext(), "Error in loading profile picture "+s, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            ToastMessage.makeText(Account_profile.this,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                            Bitmap bitmap=ExtractedStrings.mProfileImage;
                            mCircleImageViewSelectImage.setImageBitmap(bitmap);
                        }
                    });
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "In glid loading"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }else {
                ToastMessage.makeText(Account_profile.this,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                Bitmap bitmap=ExtractedStrings.mProfileImage;
                mCircleImageViewSelectImage.setImageBitmap(bitmap);
            }
        }
    }
    private void loadInfomationProfile(){
        String name=ExtractedStrings.NAME;
        String status=ExtractedStrings.MY_STATUS;
        String pPic=ExtractedStrings.MY_PROFILE_PICTURE_PATH;
        mProfileName.setText(name);
        mPhone.setText("+"+ExtractedStrings.UID);
        mStatus.setText(status);
        if (new File(pPic).exists()){
            Uri uri=Uri.fromFile(new File(pPic));
            Glide.with(getApplicationContext()).load(uri).into(mCircleImageViewSelectImage);
        }else {
           new TaskLoadProfilePicture().execute();
        }

    }
    private void setStatusMethod() {

        //get prompoty
        LayoutInflater layout_status=LayoutInflater.from(status_context);
        View promptStatus=layout_status.inflate(R.layout.sub_prompt_profile_status,null);

        AlertDialog.Builder alertDialogname=new AlertDialog.Builder(status_context);

        //set prompont to alert dilalo bulder
        alertDialogname.setView(promptStatus);

        final EditText profileStatus=(EditText)promptStatus.findViewById(R.id.edit_set_profile_status);

        //sent dialod name
        alertDialogname
                .setCancelable(false)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProgress.setMessage("Updating status...");
                        mProgress.show();
                        String status=profileStatus.getText().toString();
                        DatabaseReference set_profile_status=mDatabaseUser.child(Firebase_data_path.StatusPath(ExtractedStrings.UID));
                        set_profile_status.setValue(status);
                        mStatus.setText(status);
                        mProgress.dismiss();
                        Toast.makeText(Account_profile.this,"Updated successful",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertdialod=alertDialogname.create();
        alertdialod.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_activity_menu_change_profile_name,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void ChangeProfileName(View view) {

        //get prompoty
        LayoutInflater li=LayoutInflater.from(context);
        View promptName=li.inflate(R.layout.sub_prompt_profile_name,null);

        AlertDialog.Builder alertDialogname=new AlertDialog.Builder(context);

        //set prompont to alert dilalo bulder
        alertDialogname.setView(promptName);

        final EditText profileName=(EditText)promptName.findViewById(R.id.edit_set_profile_name);

        //sent dialod name
        alertDialogname
                .setCancelable(false)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProgress.setMessage("Updating...");
                        mProgress.show();
                        String name=profileName.getText().toString();
                        DatabaseReference set_profile_name=mDatabaseUser.child(UserIdentifier).child("Profile").child("name");
                        set_profile_name.setValue(name);
                        mProfileName.setText(name);
                        mProgress.dismiss();
                        Toast.makeText(Account_profile.this,"Updated successful",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertdialod=alertDialogname.create();
        alertdialod.show();
    }
}
