package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.AppEventsReminder;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by USER on 3/19/2018.
 */

public class TimeMessage {
    private Context context;
    private String firstMessage;
    private String secondMessage;
    private TextView timeView;
    private long time;

   public TimeMessage(Context context, String firstMessage, String secondMessage, TextView timeView, long time) {
      this.context = context;
      this.firstMessage = firstMessage;
      this.secondMessage = secondMessage;
      this.timeView = timeView;
      this.time = time;
   }
   public void setTimeAndDate(){
      SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      try {
         Date fMessage = myFormat.parse(firstMessage);
         Date sMessage = myFormat.parse(secondMessage);
         long diff = sMessage.getTime() - fMessage.getTime();
         long dife = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
         int different = Integer.parseInt(String.valueOf(dife));

         if (different == 1) {
            timeView.setText("YESTERDAY");
         } else if (different > 1 && different <= 7) {
            timeView.setText(new SimpleDateFormat("dd MMMM").format(new Date(time)));
         } else {
            timeView.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date(time)));
         }
      } catch (Exception e) {
         Toast.makeText(context, "In Message time "+e.getMessage(), Toast.LENGTH_SHORT).show();
         e.printStackTrace();
      }
   }
}
