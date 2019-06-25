package com.moodsapp.prestein.moodsapp.service.NotificationMessage;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Eric prestein on 1/5/2018.
 */

public class PushMessageNotificationService extends Service {
    public final IBinder mBinder = new LocalBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startActivity(new Intent(PushMessageNotificationService.this, MyFirebaseMessagingService.class));
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        startActivity(new Intent(PushMessageNotificationService.this, MyFirebaseMessagingService.class));
    }

    public class LocalBinder extends Binder {
        public PushMessageNotificationService getService() {
            // Return this instance of LocalService so clients can call public methods
            return PushMessageNotificationService.this;
        }
    }
}
