package com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home.Home_Activity;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;

import java.io.File;

public class WelcomeActivity extends AppCompatActivity {

    private TextView mTermsAndCondition;
    private ProgressDialog mProgress;

    //-----for firebase authentication--------------
    private FirebaseAuth mAuth;

    private Button mSignInButton;
    private static final String TAG="Login Activity";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ImageView mWelcomeLogo;

    //----------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        try{
            mAuth=FirebaseAuth.getInstance();
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    try{
                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                            if (!PermissionRequestCode.hasPremissions(getApplicationContext(), PermissionRequestCode.IO_PERMISSIONS)) {
                                ActivityCompat.requestPermissions(WelcomeActivity.this, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                            } else {
                                String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
                                if (new File(filePath+Data_Storage_Path.MY_PROFILE_STORAGE_PATH_DB).exists()) {
                                    startActivity(new Intent(WelcomeActivity.this, Home_Activity.class));
                                    finish();
                                }
                            }
                        }else{
                            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
                            if (new File(filePath+Data_Storage_Path.MY_PROFILE_STORAGE_PATH_DB).exists()) {
                                startActivity(new Intent(WelcomeActivity.this, Home_Activity.class));
                                finish();
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(WelcomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            };

            //*************************************************************************
            // mSignInButton=(Button) findViewById(R.id.google_Sin_in_btn);
            //----progress

            //underline Terms and condition TextView
            mTermsAndCondition=(TextView)findViewById(R.id.terms_and_condition);
            mTermsAndCondition.setPaintFlags(mTermsAndCondition.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            //*********************************************************************

            mSignInButton=(Button)findViewById(R.id.google_Sin_in_btn);
            mSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(WelcomeActivity.this, Select_your_country.class));
                    finish();
                }
            });


        }catch (Exception e){
            Toast.makeText(this, "In welcome "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == PermissionRequestCode.IO_REQUEST) {
                if (grantResults.length != 0) {
                    mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath();
                            if (new File(filePath+Data_Storage_Path.MY_PROFILE_STORAGE_PATH).exists()) {
                                startActivity(new Intent(WelcomeActivity.this, Home_Activity.class));
                                finish();
                            }
                        }
                    };
                }
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener!=null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
