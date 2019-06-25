package com.moodsapp.prestein.moodsapp.service.BackgroundProcess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class detectOnlineUsers extends Service {

    private static final long  TIME_TO_DETECT_ONLINE= 60*1000;
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
