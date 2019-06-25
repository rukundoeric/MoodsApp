package com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.resourceshelper.CountryCode.Country;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.NumberFormat;

import java.util.concurrent.TimeUnit;

public class Enter_your_phone_number_activity extends AppCompatActivity {

    private ImageView imgCountryFlag;
    private TextView txtCountryCode;
    private TextView txtCountryNameCode;
    private EditText mPhoneNumber;
    private Bitmap src;
    private Resources res;
    private Bundle bundle;
    private String Name;
    private FloatingActionButton mCountine;
    private Button mButtonVerify;
    private LinearLayout mLinearEnterPhone;
    private LinearLayout mLinearConfirmPhone;
    private TextView mDidNotGetCode;
    private EditText mCodeFirstSection;
    private EditText mCodeSecondSection;
    private TextView mGetConfirmCodeInfo;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBackPhoneVerfier;
    private String mVerficationCode="";
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private long startIime;
    private Handler handler;
    private String laps;
    private boolean mVerifyStarted;

    private boolean isVerifyRequestSent;
    private Handler Infohandler;
    private ProgressDialog progressDialog;
    private boolean InformingTimerStated;
    private FirebaseAuth mAuth;
    private PhoneAuthCredential phoneAuthCredentials;
    private TextView pageTitle;
    private boolean isInternetAvailable;
    private boolean isVerfied;
    private Context myContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_your_phone_number_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_for_activit_enter_phone);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       try {
           res=getResources();
           pageTitle=(TextView)findViewById(R.id.Enter_your_phone_number_in_enter_your_number_activity);
           imgCountryFlag=(ImageView)findViewById(R.id.enter_your_phone_activity_flag);
           txtCountryCode=(TextView)findViewById(R.id.enter_your_phone_activity_country_code);
           txtCountryNameCode=(TextView)findViewById(R.id.enter_your_phone_activity_name_code);
           mPhoneNumber=(EditText)findViewById(R.id.enter_your_phone_activity_phone_number);
           mPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
           mCountine=(FloatingActionButton)findViewById(R.id.activity_enter_phone_continue_btn);
           bundle = getIntent().getExtras();
           myContext=this.getApplicationContext();
           Name= bundle.getString(ExtractedStrings.INTENT_COUNTRY_NAME);
           //PhoneNumber=bundle.getString(ExtractedStrings.INTENT_COUNTRY_CODE +" "+mPhoneNumber.getText().toString().trim());
           src = BitmapFactory.decodeResource(res, new Country().getFlagResID(bundle.getString(ExtractedStrings.INTENT_COUNTRY_NAME_CODE)));
           final BitmapDrawable Image_drawable = new BitmapDrawable(res, src);
           imgCountryFlag.setImageDrawable(Image_drawable);
           txtCountryCode.setText(bundle.getString(ExtractedStrings.INTENT_COUNTRY_CODE));
           txtCountryNameCode.setText("("+bundle.getString(ExtractedStrings.INTENT_COUNTRY_NAME_CODE).toUpperCase()+")");
           //for verfication code
           mAuth=FirebaseAuth.getInstance();
           handler=new Handler();
           Infohandler=new Handler();
           progressDialog=new ProgressDialog(Enter_your_phone_number_activity.this);
           mButtonVerify=(Button)findViewById(R.id.verify_number_button_in_enter_phonnumber_activity);
           mLinearEnterPhone=(LinearLayout)findViewById(R.id.enter_phone_number_section_in_enter_phone);
           mLinearConfirmPhone=(LinearLayout)findViewById(R.id.enter_verification_section_in_enter_phone_number);
           mDidNotGetCode=(TextView)findViewById(R.id.did_not_get_confirmation_code_in_enter_phone_number);
           mCodeFirstSection=(EditText)findViewById(R.id.confirm_code_first_section);
           mGetConfirmCodeInfo=(TextView)findViewById(R.id.get_confirm_code_info_in_enter_phone);
           mCallBackPhoneVerfier=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
               @Override
               public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                   phoneAuthCredentials=phoneAuthCredential;
                   isVerfied=true;
                   progressDialog.setMessage("Verifying...");
                   progressDialog.show();
                  signInWithPhoneAuthCredential(phoneAuthCredential);
               }

               @Override
               public void onVerificationFailed(FirebaseException e) {

               }

               @Override
               public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                   mVerficationCode=s;
                   mResendToken=forceResendingToken;
               }
           };
           mCountine.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   progressDialog.setMessage(myContext.getResources().getString(R.string.internetConnectionChecking));
                   progressDialog.show();
                   new TaskIsInternetAvailable().execute();

               }
           });
           mButtonVerify.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (isVerfied) {
                       progressDialog=new ProgressDialog(Enter_your_phone_number_activity.this);
                       progressDialog.setMessage("Verifying...");
                       progressDialog.show();
                       // signInWithPhoneAuthC
                       signInWithPhoneAuthCredential(phoneAuthCredentials);
                   }else {
                       Toast.makeText(Enter_your_phone_number_activity.this, "Not yet verified,Please wait.", Toast.LENGTH_SHORT).show();
                   }

               }
           });

       }catch (Exception e){
           Toast.makeText(this, "Error in loading: "+e.getMessage(), Toast.LENGTH_SHORT).show();
       }
    }
    private class TaskIsInternetAvailable extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... args) {
            return ConnectionDetector.isInternetAvailable(Enter_your_phone_number_activity.this.getApplicationContext());
        }
        protected void onPostExecute(Boolean result) {
            if (result){
                VerifyRequest();
                progressDialog.dismiss();
            }else {
                ToastMessage.makeText(Enter_your_phone_number_activity.this,R.drawable.icon_no_internet_connection, ExtractedStrings.NO_INTERNET_CONNECTION_MESSAGE);
                cancel(true);
                progressDialog.dismiss();
            }
        }
    }
    private void VerifyRequest() {
        //get prompoty
        LayoutInflater layout_verfy=LayoutInflater.from(Enter_your_phone_number_activity.this);
        View promptVerfy=layout_verfy.inflate(R.layout.sub_prompt_if_your_sure_about_the_number,null);

        AlertDialog.Builder alertDialogname=new AlertDialog.Builder(Enter_your_phone_number_activity.this);
        //set prompont to alert dilalo bulder
        alertDialogname.setView(promptVerfy);
        String phone="+"+bundle.getString(ExtractedStrings.INTENT_COUNTRY_CODE)+" "+mPhoneNumber.getText().toString().trim();
        final TextView PhoneNumber=(TextView) promptVerfy.findViewById(R.id.phone_number_verify_asking_if_you_are_sure);
        PhoneNumber.setText(phone);
        //sent dialod name
        alertDialogname
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            mButtonVerify.setVisibility(View.VISIBLE);
                            mLinearEnterPhone.setVisibility(View.GONE);
                            mLinearConfirmPhone.setVisibility(View.VISIBLE);
                            pageTitle.setText("Enter the confirmation code:");
                            mCountine.setVisibility(View.GONE);
                            String phone="+"+new NumberFormat().removeChar(bundle.getString(ExtractedStrings.INTENT_COUNTRY_CODE)+mPhoneNumber.getText().toString().trim());
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    phone,
                                    60,
                                    TimeUnit.SECONDS,
                                    Enter_your_phone_number_activity.this,
                                    mCallBackPhoneVerfier
                            );
                            Toast.makeText(Enter_your_phone_number_activity.this, "Started", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(Enter_your_phone_number_activity.this, "Exception e:  "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
    public void OpenFinishRegisterActivitys() {
        try {
            NumberFormat numberFormat=new NumberFormat();
            if (!mPhoneNumber.getText().toString().trim().equals("")) {
                String phoneNumber =bundle.getString(ExtractedStrings.INTENT_COUNTRY_CODE)+mPhoneNumber.getText().toString().trim();
                Intent intent = new Intent(Enter_your_phone_number_activity.this, FinishRegisterActivity.class);
                intent.putExtra(ExtractedStrings.INTENT_COUNTRY_NAME, Name);
                intent.putExtra(ExtractedStrings.INTENT_PHONE_NUMBER, numberFormat.removeChar(numberFormat.removeChar(phoneNumber)));
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Please! Enter phone number", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "In open finish Register" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        try {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try {
                                if (task.isSuccessful()) {
                                    OpenFinishRegisterActivitys();
                                } else {
                                    // Sign in failed, display a message and update the UI
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(Enter_your_phone_number_activity.this, "The verification code you entered is not correct.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext())
                                                .setMessage("verification failed, please check your internet and restart the app.")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
                                                        finish();
                                                    }
                                                });
                                    }
                                }
                            }catch (Exception e)
                            {
                                Toast.makeText(Enter_your_phone_number_activity.this, "In sign in: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }catch (Exception e){
            Toast.makeText(this, "In Auth"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
        }

    }
}
