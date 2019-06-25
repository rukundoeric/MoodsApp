package com.moodsapp.prestein.moodsapp.util.InterfaceUtils;

import android.content.Context;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.model.ResentChats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Prestein on 10/20/2017.
 */

public class SoltByTimeRecentChats implements Comparator<ResentChats>{

private Context context;

    public SoltByTimeRecentChats(Context context) {
        this.context = context;
    }

    @Override
    public int compare(ResentChats o1, ResentChats o2) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            Date date1 = simpleDateFormat.parse(String.valueOf(simpleDateFormat.format(o1.timeStamps)));
            Date date2=simpleDateFormat.parse(String.valueOf(simpleDateFormat.format(o2.timeStamps)));


            if (date1.compareTo(date2) > 0){
                return -1;
            }else if ( date1.compareTo(date2) < 0){
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG);
        }
        return 0;
    }

}
