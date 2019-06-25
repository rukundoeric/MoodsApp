package com.moodsapp.prestein.moodsapp.util.DateUtils;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A class with static util methods.
 */

public class DateUtils {

    // This class should not be initialized
    private DateUtils() {

    }


    /**
     * Gets timestamp in millis and converts it to HH:mm (e.g. 16:44).
     */
    public static String formatTime(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(timeInMillis);
    }

    public static String formatTimeWithMarker(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return dateFormat.format(timeInMillis);
    }

    public static int getHourOfDay(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("H", Locale.getDefault());
        return Integer.valueOf(dateFormat.format(timeInMillis));
    }

    public static int getMinute(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("m", Locale.getDefault());
        return Integer.valueOf(dateFormat.format(timeInMillis));
    }

    /**
     * If the given time is of a different date, display the date.
     * If it is of the same date, display the time.
     * @param timeInMillis  The time to convert, in milliseconds.
     * @return  The time or date.
     */
    public static String formatDateTime(long timeInMillis) {
        if(isToday(timeInMillis)) {
            return formatTime(timeInMillis);
        } else {
            return formatDate(timeInMillis);
        }
    }

    /**
     * Formats timestamp to 'date month' format (e.g. 'February 3').
     */
    public static String formatDate(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd", Locale.getDefault());
        return dateFormat.format(timeInMillis);
    }

    /**
     * Returns whether the given date is today, based on the user's current locale.
     */
    public static boolean isToday(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String date = dateFormat.format(timeInMillis);
        return date.equals(dateFormat.format(System.currentTimeMillis()));
    }
    public static long getDifferenceMin(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
    }
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String getLastSeenTimeDate(long millisFirst){
        long diffMin=getDifferenceMin(new Date(millisFirst),new Date(System.currentTimeMillis()));
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String timeRecent = myFormat.format(new Date(millisFirst));
        String thisday = myFormat.format(new Date(System.currentTimeMillis()));
        try {
            Date datetimeRecent = myFormat.parse(timeRecent);
            Date datethisday = myFormat.parse(thisday);
            long diff = datethisday.getTime() - datetimeRecent.getTime();
            long dife = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            int different = Integer.parseInt(String.valueOf(dife));

            if (different==0){
                if (diffMin>=60){
                    return "Last seen at "+String.valueOf(new SimpleDateFormat("HH:mm").format(millisFirst));
                }else {
                    if (diffMin==0){
                        return "Online";
                    }else {
                        return diffMin+" min ago";
                    }
                }
            }else
                 if (different == 1) {
                     return "Yesterday at "+String.valueOf(new SimpleDateFormat("HH:mm").format(millisFirst));
                 } else if (different > 1 && different <=7) {
                     return "Last seen on "+String.valueOf(new SimpleDateFormat("EEEE").format(millisFirst))+" at "+String.valueOf(new SimpleDateFormat("HH:mm").format(millisFirst));
                 } else {
                     return "Last seen on "+String.valueOf(new SimpleDateFormat("dd MMMM yyyy").format(millisFirst));
                 }
        } catch (Exception e) {
        }
        return null;
    }

    public static String getChatTimeDate(long millisFirst){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String timeRecent = myFormat.format(new Date(millisFirst));
        String thisday = myFormat.format(new Date(System.currentTimeMillis()));

        try {
            Date datetimeRecent = myFormat.parse(timeRecent);
            Date datethisday = myFormat.parse(thisday);
            long diff = datethisday.getTime() - datetimeRecent.getTime();
            long dife = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            int different = Integer.parseInt(String.valueOf(dife));

            if (different==0){
                return "Today";
            }else if (different==1){
                return "Yesterday";
            }else if (different>=2 && different<7){
                return String.valueOf(new SimpleDateFormat("EEEE").format(millisFirst));
            }else {
                return String.valueOf(new SimpleDateFormat("dd MMMM yyyy").format(millisFirst));
            }

        } catch (Exception e) {
        }
   return  String.valueOf(new SimpleDateFormat("dd MMMM yyyy").format(millisFirst));
    }
    /**
     * Checks if two dates are of the same day.
     * @param millisFirst   The time in milliseconds of the first date.
     * @param millisSecond  The time in milliseconds of the second date.
     * @return  Whether {@param millisFirst} and {@param millisSecond} are off the same day.
     */
    public static boolean hasSameDate(long millisFirst, long millisSecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return dateFormat.format(millisFirst).equals(dateFormat.format(millisSecond));
    }
}
