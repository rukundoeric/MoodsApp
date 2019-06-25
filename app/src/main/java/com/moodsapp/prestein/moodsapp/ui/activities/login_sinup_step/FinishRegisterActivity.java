package com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moodsapp.cropper.CropImage;
import com.moodsapp.cropper.CropImageView;
import com.moodsapp.emojis_library.Actions.EmojIconActions;
import com.moodsapp.emojis_library.Helper.EmojiconEditText;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.MyProfileDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;
import com.moodsapp.prestein.moodsapp.model.MyProfile;
import com.moodsapp.prestein.moodsapp.model.Profile;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home.Home_Activity;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage5kb;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Prestein on 5/9/2017.
 */

public class FinishRegisterActivity extends AppCompatActivity {
    private String mGetUserEmail;
    /* private String mGetUserIdentifier;*/
    private Button mbtnSave;
    private EmojiconEditText mProfileName;
    private EditText mProfilePhone;
    private CircleImageView mCircleImageView;
    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;
    private Context context = this;
    //private CountryCodePicker ccp;
    private ProgressDialog mProgress;


    //---Firebase--------
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReferance;

    private Uri resultUri;
    private DatabaseReference mDatabasContactInfo;
    private ImageView mOpenCountriesDialog;
    public TextView mCountriesNameCode;
    public ImageView mCountriesFrag;
    private Context CountryListContext = this;
    private Bundle bundle;
    private FloatingActionButton mFabFinish;
    private ProgressDialog internetCheckProgress;
    private RelativeLayout mRootView;
    private EmojIconActions emojIcon;
    private ImageView mOpenEmojis;
    private String TAG;
    private String pictureRealPath;
    private Handler mHandler = new Handler();
    private Uri outputUri;
    private String deviceId;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        mProgress = new ProgressDialog(this);
        //identifier Edite text
        mProfileName = (EmojiconEditText) findViewById(R.id.set_profile_name);
        // registerCarrierEditText();
        //**********************************************************************
        bundle = getIntent().getExtras();
        mCircleImageView = (CircleImageView) findViewById(R.id.set_profile_image);
        mCircleImageView.setClickable(true);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_profile_image_method();
            }
        });
        mFabFinish = (FloatingActionButton) findViewById(R.id.fab_finish_register_activity);
        mFabFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Connecting...");
                mProgress.show();
                new TaskIsInternetAvailable().execute();
            }
        });
        //open emojis button
        mRootView = (RelativeLayout) findViewById(R.id.setUp_profile_rootView);
        mOpenEmojis = (ImageView) findViewById(R.id.open_emojis_on_setup_profile);
        emojIcon = new EmojIconActions(this, mRootView, mProfileName, mOpenEmojis, "#214253", "#FFFFFFFF", "#FFFFFFFF");
        //emojIcon = new EmojIconActions(this, rootView, mChatMessageText, mOpenEmojis);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_key_boad_replace, R.drawable.ic_insert_emoticon_black_24dp);
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e(TAG, "Keyboard opened!");
            }

            @Override
            public void onKeyboardClose() {
                Log.e(TAG, "Keyboard closed");
            }
        });

        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (!PermissionRequestCode.hasPremissions(getApplicationContext(), PermissionRequestCode.IO_PERMISSIONS)) {
                ActivityCompat.requestPermissions(FinishRegisterActivity.this, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
            } else {
                deviceId = telephonyManager.getSubscriberId();
            }
        }else{
            deviceId = telephonyManager.getSubscriberId();
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            saveMethod();
        }
    };

    private void set_profile_image_method() {
        Intent changeProfilepic = new Intent(Intent.ACTION_PICK);
        changeProfilepic.setType("image/*");
        startActivityForResult(changeProfilepic, GALLERY_REQUEST);
    }

    //---------on Activity Result methods------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageUri = data.getData();
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            mImageUri = data.getData();
            if (data.getData() == null) {
                Toast.makeText(this, "Failed to load Image,try again", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                outputUri =new getFileName(context).getDataFilesThumbnailUriPath(String.valueOf(System.currentTimeMillis()),context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView
                            .Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setBackgroundColor(getApplicationContext().getResources().getColor(R.color.background))
                    .setActivityMenuIconColor(getApplicationContext().getResources().getColor(R.color.colorPrimary))
                    .setOutputUri(outputUri)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri =Uri.fromFile(new File(new CompressingImage5kb(String.valueOf(System.currentTimeMillis()),context).compressImage(String.valueOf(result.getUri()),"",true)));
                mCircleImageView.setImageURI(resultUri);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(context);
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                mHandler.post(mRunnable);
            } else {
                ToastMessage.makeText(FinishRegisterActivity.this, R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                cancel(true);
                //  mHandler.removeCallbacks(mRunnable);
                mProgress.dismiss();
            }
        }
    }

    public void saveMethod() {
        mProgress.setMessage("Uploading your profile..");
        try {
            final String name = mProfileName.getText().toString().trim();
            final String phone = bundle.getString(ExtractedStrings.INTENT_PHONE_NUMBER);
            final String country = bundle.getString(ExtractedStrings.INTENT_COUNTRY_NAME);
            if (name.length() > 0 && resultUri != null) {
               final String status = "Hi, Am very appreciate to use MoodsApp";
                final StorageReference filepath = FirebaseStorage.getInstance().getReference().child(Firebase_data_path.ProfilesImageStoragePath(phone,resultUri.getLastPathSegment()));
                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        Uri smallImage = Uri.fromFile(new File(new CompressingImage5kb(String.valueOf(System.currentTimeMillis()),context).compressImage(String.valueOf(resultUri), "", true)));
                         FirebaseStorage.getInstance().getReference().child(Firebase_data_path.ProfilesImageStoragePath(phone,smallImage.getLastPathSegment())).putFile(smallImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                  @Override
                                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot2) {
                                      String downloadUrl1 = taskSnapshot.getDownloadUrl().toString();
                                      String downloadUrl2=taskSnapshot2.getDownloadUrl().toString();
                                      Profile profile=new Profile();
                                      profile.DeviceId=deviceId;
                                      profile.DeviceToken=FirebaseInstanceId.getInstance().getToken();
                                      profile.UserId=phone;
                                      profile.name=name;
                                      profile.status=status;
                                      profile.phone=phone;
                                      profile.country=country;
                                      profile.profile_image=downloadUrl1;
                                      profile.small_profile_image=downloadUrl2;
                                      FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.ProfilePath(phone)).setValue(profile);
                                      MyProfile myProfile=new MyProfile();
                                      myProfile.profileId=phone;
                                      myProfile.profileName=name;
                                      myProfile.profileStatus=status;
                                      myProfile.profileCountry=country;
                                      myProfile.profileImage=downloadUrl1;
                                      myProfile.profileImagePath=resultUri.toString();
                                      MyProfileDB.getInstance(context).checkBeforeAdd(myProfile,phone,context);
                                      mProgress.dismiss();
                                      startActivity(new Intent(FinishRegisterActivity.this, Home_Activity.class));
                                      finish();
                                  }
                              });
                            }
                        });

                    }
                }catch (Exception e){
                    Toast.makeText(context, "In Save"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
                }

    }

}
