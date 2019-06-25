package com.moodsapp.prestein.moodsapp.Application;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.view.Display;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Prestein on 6/2/2017.
 */

public class MoodsApp extends MultiDexApplication {
    public static Point displaySize = new Point();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabaseUsers;
    public static volatile Handler applicationHandler = null;
    private static boolean HomeActivityVisibility;
    private static boolean ChatActivityVisibility;
    public static boolean FragmentHomeVisibility;
    public static Context applicationContext;
    public static float density = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Picasso.Builder builder=new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built=builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
        applicationContext = getApplicationContext();
        density = applicationContext.getResources().getDisplayMetrics().density;
        applicationHandler = new Handler(applicationContext.getMainLooper());
        checkDisplaySize();
    }
        public static void ChatActivityResumed(){
            ChatActivityVisibility=true;
        }
        public static void ChatActivityStarted(){
            ChatActivityVisibility=true;
        }
        public static void ChatActivityPaused(){
            ChatActivityVisibility=false;
        }
        public static void ChatActivityStoped(){
           ChatActivityVisibility=false;
       }
        public static void HomeActivityResumed(){
            HomeActivityVisibility=true;
        }
        public static void HomeActivityStarted(){
            HomeActivityVisibility=true;
        }
        public static void HomeActivityPaused(){
            HomeActivityVisibility=false;
        }
        public static void HomeActivityStoped(){
            HomeActivityVisibility=false;
        }
        public static boolean isChatActivityVisibility(){
        return ChatActivityVisibility;
        }
        public static boolean isHomeActivityVisibility(){
        return HomeActivityVisibility;
        }
        public static boolean isFragmentHomeVisibility(){
        return FragmentHomeVisibility;
        }
    public static int dp(float value) {
        return (int)Math.ceil(density * value);
    }

    public static void checkDisplaySize() {
        try {
            WindowManager manager = (WindowManager)applicationContext.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    if(android.os.Build.VERSION.SDK_INT < 13) {
                        displaySize.set(display.getWidth(), display.getHeight());
                    } else {
                        display.getSize(displaySize);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

}
