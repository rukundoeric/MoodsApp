package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.MyProfileDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.Firebase_data_path;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;
import com.moodsapp.prestein.moodsapp.model.RecentChatRoom;
import com.moodsapp.prestein.moodsapp.service.BackgroundProcess.ContactWatchService;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType.ApkListActivity;

import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact.SelectContactsToSend;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.EditImageActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_more_info_about_app.About;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_more_info_about_app.Help;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_my_profile.Account_profile;
import com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step.FinishRegisterActivity;
import com.moodsapp.prestein.moodsapp.ui.fragment.FragmentBusiness;
import com.moodsapp.prestein.moodsapp.ui.fragment.FragmentGroup;
import com.moodsapp.prestein.moodsapp.ui.fragment.FragmentHome;
import com.moodsapp.prestein.moodsapp.ui.fragment.FragmentMyContacts;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageCaching.ImageCache;
import com.moodsapp.prestein.moodsapp.util.StringsUtils.SubstringUtils;
import com.moodsapp.prestein.moodsapp.util.UserStatusUtils.UpdatedUserOnline;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


public class Home_Activity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private CircleImageView mCircleImageView;
	private CircleImageView mSet_cleImageView;


	private String getName;
	private String getImage;
	private TextView mProfileName;
	private ProgressBar progressBar;

	private static final int GALLERY_REQUEST=1;
	private DatabaseReference user_name;
	private DatabaseReference user_pic;
	ConnectionDetector connectionDetector;
	private String dayOfTheWeek;
	private String t;
	private DatabaseReference small_pic;
	private Bitmap src;
	private Toolbar Main_toolbar;
	public  static Resources resources;
	private Typeface cooljazz;
	private DatabaseReference status;
	private static final int CustomGallerySelectId = 1;//Set Intent Id

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_home:
					FragmentHome fragmentHome=new FragmentHome();
					FragmentManager manager_home=getFragmentManager();
					manager_home.beginTransaction().replace(R.id.contentLayout,fragmentHome,fragmentHome.getTag()).commit();

					return true;
				case R.id.navigation_group:
					FragmentGroup fragmentGroup=new FragmentGroup();
					FragmentManager manager_group=getFragmentManager();
					manager_group.beginTransaction().replace(R.id.contentLayout,fragmentGroup,fragmentGroup.getTag()).commit();

					return true;
				case R.id.navigation_take_pic:
                    Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                    startActivity(intent);
				/*	if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
						if (!PermissionRequestCode.hasPremissions(getApplicationContext(), PermissionRequestCode.IO_CAMERA_PERMISSIONS)) {
							ActivityCompat.requestPermissions(Home_Activity.this, PermissionRequestCode.IO_CAMERA_PERMISSIONS, PermissionRequestCode.IO_CA_RQST);
						} else {
							startActivity(new Intent(Home_Activity.this, CameraActivity.class));
						}
					}else{
						startActivity(new Intent(Home_Activity.this, CameraActivity.class));
					}*/
					return true;
				case R.id.navigation_business_center:
					FragmentBusiness fragmentBusiness=new FragmentBusiness();
					FragmentManager manager_business=getFragmentManager();
					manager_business.beginTransaction().replace(R.id.contentLayout,fragmentBusiness,fragmentBusiness.getTag()).commit();

					return true;
				case R.id.navigation_all_members:
					FragmentMyContacts fragmentMyContacts=new FragmentMyContacts();
					FragmentManager manager_myContacts=getFragmentManager();
					manager_myContacts.beginTransaction().replace(R.id.contentLayout,fragmentMyContacts,fragmentMyContacts.getTag()).commit();
//					AllMembersFragment allMembersFragment=new AllMembersFragment();
//					FragmentManager manager_allmembers=getFragmentManager();
//					manager_allmembers.beginTransaction().replace(R.id.contentLayout,allMembersFragment,allMembersFragment.getTag()).commit();

					return true;
			}
			return false;
		}

	};
	private FirebaseAuth.AuthStateListener mAuthListener;
	private FirebaseAuth mAuth;
	private String UserIdentifier;
	private RecentChatRoom recentChatRoom;
	private boolean isRefleshStarted=false;
	private String MyProfileImageBase;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mAuth= FirebaseAuth.getInstance();
		FragmentHome fragmentHome=new FragmentHome();
		FragmentManager manager_home=getFragmentManager();
		manager_home.beginTransaction().replace(R.id.contentLayout,fragmentHome,fragmentHome.getTag()).commit();
		MoodsApp.FragmentHomeVisibility=true;
		//**************************************************************************************
			mAuthListener=new FirebaseAuth.AuthStateListener() {
				@Override
				public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

					if (firebaseAuth.getCurrentUser()==null){
						//startActivity(new Intent(Home_Activity.this,WelcomeActivity.class));
					}

				}
			};
			//define drawable content
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();
		//****************************************************************************************

		//open navigation view
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		final View header=navigationView.getHeaderView(0);

		mCircleImageView=(CircleImageView)header.findViewById(R.id.set_profile_image_home);
		mCircleImageView.setClickable(true);
		mCircleImageView.setOnClickListener(
				new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				set_image_profile_home_method();
			}
		});
		//************************************************************************************
		//-----call views---------
		mSet_cleImageView=(CircleImageView)findViewById(R.id.select_image_from_view_image);
		//-----set imase profile name------
		// String user_Id=mAuth.getCurrentUser().getUid();
			if ( ExtractedStrings.UID.equals("")){
				UserIdentifier= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(0,getApplicationContext());
				ExtractedStrings.UID=UserIdentifier;
			}
			if ( ExtractedStrings.NAME.equals("")){
				ExtractedStrings.NAME= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(1,getApplicationContext());
			}
			if ( ExtractedStrings.MY_COUNTRY.equals("")){
				ExtractedStrings.MY_COUNTRY= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(3,getApplicationContext());
			}
			if ( ExtractedStrings.MY_STATUS.equals("")){
				ExtractedStrings.MY_STATUS= MyProfileDB.getInstance(getApplicationContext()).getInfoUser(2,getApplicationContext());
			}

			mProfileName=(TextView)header.findViewById(R.id.get_profile_name);
			mProfileName.setText(ExtractedStrings.NAME);
			getSupportActionBar().setTitle(ExtractedStrings.NAME);
			toolbar.setTitle(ExtractedStrings.NAME);
			toolbar.setSubtitle(ExtractedStrings.MY_STATUS);
			ExtractedStrings.MY_COUNTRY_CODE= SubstringUtils.getSubString(ExtractedStrings.UID,0,3);
			//define Navigation bottom
			if (ExtractedStrings.DeviceWidth==0){
			DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
			ExtractedStrings.DeviceWidth=metrics.widthPixels;
			}
			if (ExtractedStrings.MY_PROFILE_PICTURE_PATH.equals("")){
				ExtractedStrings.MY_PROFILE_PICTURE_PATH= new CompressingImage(getApplicationContext()).getRealPathFromURI(MyProfileDB.getInstance(getApplicationContext()).getInfoUser(5,getApplicationContext()));
			}
			if (ExtractedStrings.mProfileImage==null){
			    new Handler().post(new Runnable() {
                @Override
                public void run() {
                	if (new File(ExtractedStrings.MY_PROFILE_PICTURE_PATH).exists()){
                		if (ExtractedStrings.MY_PROFILE_PICTURE_PATH.equals("default")){
							mCircleImageView.setImageResource(R.drawable.avatar_default);
						}else {
							ExtractedStrings.mProfileImage=BitmapFactory.decodeFile(ExtractedStrings.MY_PROFILE_PICTURE_PATH);
							Glide.with(getApplicationContext()).load(ExtractedStrings.MY_PROFILE_PICTURE_PATH).into(mCircleImageView);
						}
					}else {
						FirebaseDatabase.getInstance().getReference().child(Firebase_data_path.SmallImagePath(ExtractedStrings.UID)).addValueEventListener(new ValueEventListener() {
							@Override
							public void onDataChange(DataSnapshot dataSnapshot) {
								String imagePath=dataSnapshot.getValue(String.class);
								Glide.with(getApplicationContext()).load(imagePath).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mCircleImageView);
							}

							@Override
							public void onCancelled(DatabaseError databaseError) {

							}
						});
					}
			/*		Bitmap bitmap= new ImageCache(getApplicationContext()).getImageFromDiskCache(ExtractedStrings.UID);
					if (bitmap == null) {
						Toast.makeText(Home_Activity.this, "cache image is null", Toast.LENGTH_SHORT).show();
						MyProfileImageBase = MyProfileDB.getInstance(getApplicationContext()).getInfoUser(4, getApplicationContext());
						if (new File(MyProfileImageBase).exists()) {
							Bitmap bitmap1=BitmapFactory.decodeFile(MyProfileImageBase);
							new ImageCache(getApplicationContext()).addBitmapToCache(ExtractedStrings.UID,bitmap1);

							ExtractedStrings.mProfileImage=new ImageCache(getApplicationContext()).getImageFromDiskCache(ExtractedStrings.UID);
							mCircleImageView.setImageBitmap(new ImageCache(getApplicationContext()).getImageFromDiskCache(ExtractedStrings.UID));
						}else {
							mCircleImageView.setImageResource(R.drawable.avatar_default);
							Glide.with(getApplicationContext()).load(MyProfileImageBase).apply(new RequestOptions().placeholder(R.drawable.avatar_default)).into(mCircleImageView);
						}

					}else {
						Toast.makeText(Home_Activity.this, "cache image is not null", Toast.LENGTH_SHORT).show();
						ExtractedStrings.mProfileImage=bitmap;
						mCircleImageView.setImageBitmap(bitmap);
					}*/
                }
            });
			}else {
				Toast.makeText(Home_Activity.this, "mprogile image is not null", Toast.LENGTH_SHORT).show();
				Bitmap bitmap = ExtractedStrings.mProfileImage;
				mCircleImageView.setImageBitmap(bitmap);
			}
			//loadNewMessages(ExtractedStrings.UID);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
			//new refleshContacts(getApplicationContext());
           if (!isMyServiceRunning(ContactWatchService.class)){
               startContactLookService();
           }
	}

	private void set_image_profile_home_method() {
		//get data and display it in next activity
		Intent intent=new Intent(this, Account_profile.class);
		startActivity(intent);
	}
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
			this.finish();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		try {
			if (ExtractedStrings.mProfileImage!=null){
				Bitmap bitmap= ExtractedStrings.mProfileImage;
				RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), bitmap);
				roundedBitmapDrawable.setCircular(true);
				menu.findItem(R.id.home_small_profile_icon).setIcon(roundedBitmapDrawable);
			}
		}
		catch (Exception e){
		}
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.home_small_profile_icon) {
			Intent profileIntent=new Intent(Home_Activity.this, Account_profile.class);
			startActivity(profileIntent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_chat) {
			//  set_image_profile_home_method();
			startActivity(new Intent(Home_Activity.this, FinishRegisterActivity.class));
			// Handle the camera action
		} else if (id == R.id.nav_contact) {
			startActivity(new Intent(Home_Activity.this,SelectContactsToSend.class));

		} else if (id == R.id.nav_setting) {
			startActivity(new Intent(Home_Activity.this, ApkListActivity.class));
		} else if (id == R.id.nav_share_your_activity) {
			//checkIfbusinessExist();
		} else if (id == R.id.nav_open_shared_activity) {


		} else if (id == R.id.nav_share) {


			Intent intent=new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			String title="MoodsApp";
			String description="Downloude MoodsApp to day";

			intent.putExtra(Intent.EXTRA_SUBJECT,title);
			intent.putExtra(Intent.EXTRA_TEXT,description);
			startActivity(Intent.createChooser(intent,"Share with.."));

		} else if (id == R.id.nav_send) {
			startActivity(new Intent(Home_Activity.this, EditImageActivity.class));
		} else if (id == R.id.nav_About) {
			Intent intent=new Intent(Home_Activity.this, About.class);
			startActivity(intent);

		} else if (id == R.id.nav_help) {
			Intent intent=new Intent(Home_Activity.this, Help.class);
			startActivity(intent);

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	@SuppressLint("StaticFieldLeak")
	private class ConvertingImageTask extends AsyncTask<String,Integer, String> {
		@Override
		protected String doInBackground(String... strings) {
			try {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}finally {

			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
	    }

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			if (s != null) {
				Toast.makeText(getApplicationContext(), "Error in loading profile picture: "+s, Toast.LENGTH_SHORT).show();
			}else {
				if (ExtractedStrings.mProfileImage!=null){
					Bitmap bitmap= ExtractedStrings.mProfileImage;
					mCircleImageView.setImageBitmap(bitmap);
				}
			}
		}
	}
	@Override
	protected void onStart() {
		super.onStart();
		MoodsApp.HomeActivityStarted();
		FragmentHome fragmentHome=new FragmentHome();
		FragmentManager manager_home=getFragmentManager();
		manager_home.beginTransaction().replace(R.id.contentLayout,fragmentHome,fragmentHome.getTag()).commit();
		MoodsApp.FragmentHomeVisibility=true;
		mAuth.addAuthStateListener(mAuthListener);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		try {
			switch (requestCode) {
				case PermissionRequestCode.IO_CA_RQST: {
					// If request is cancelled, the result arrays are empty.
					if (grantResults.length > 0
							&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					} else {
						ActivityCompat.requestPermissions(this, PermissionRequestCode.IO_CAMERA_PERMISSIONS, PermissionRequestCode.IO_CA_RQST);
					}
					return;
				}

			}
		}catch (Exception e){
			//Toast.makeText(myContext,e.getMessage(),Toast.LENGTH_LONG);
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		if(mAuthListener!=null){
			mAuth.removeAuthStateListener(mAuthListener);
			MoodsApp.HomeActivityStoped();
			if (ExtractedStrings.UpdateOnlineStatus!=null){
				ExtractedStrings.UpdateOnlineStatus.cancel();
			}
		}
		UpdatedUserOnline.stopOnlineDetector();
		/*stopService(new Intent(Home_Activity.this, detectOnlineUsers.class));
		if (isMyServiceRunning(refleshContacts.class)){
			stopService(new Intent(Home_Activity.this, refleshContacts.class));
		}*/
	}
    private void startContactLookService() {
        try {
            if (ActivityCompat.checkSelfPermission(Home_Activity.this,
                    Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {//Checking permission
                //Starting service for registering ContactObserver
                Intent intent = new Intent(Home_Activity.this, ContactWatchService.class);
                startService(intent);
            } else {
                //Ask for READ_CONTACTS permission
                ActivityCompat.requestPermissions(Home_Activity.this, new String[]{Manifest.permission.READ_CONTACTS}, PermissionRequestCode.READ_CCT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				Log.i ("isMyServiceRunning?", true+"");
				return true;
			}
		}
		Log.i ("isMyServiceRunning?", false+"");
		return false;
	}
	@Override
	protected void onPause() {
		super.onPause();
		MoodsApp.HomeActivityPaused();
		if (ExtractedStrings.UpdateOnlineStatus!=null) {
			ExtractedStrings.UpdateOnlineStatus.cancel();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
/*		MoodsApp.HomeActivityResumed();
		FragmentHome fragmentHome=new FragmentHome();
		FragmentManager manager_home=getFragmentManager();
		manager_home.beginTransaction().replace(R.id.contentLayout,fragmentHome,fragmentHome.getTag()).commit();
	*/}
}








