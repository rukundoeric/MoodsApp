package com.moodsapp.prestein.moodsapp.util.InterfaceUtils;

import android.content.Context;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.model.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Prestein on 10/20/2017.
 */

public class SoltByTimeMessages implements Comparator<Message>{

private Context context;

    public SoltByTimeMessages(Context context) {
        this.context = context;
    }

    @Override
    public int compare(Message o1, Message o2) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            Date date1 = simpleDateFormat.parse(String.valueOf(simpleDateFormat.format(o1.timestamp)));
            Date date2=simpleDateFormat.parse(String.valueOf(simpleDateFormat.format(o2.timestamp)));
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
