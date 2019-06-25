package com.moodsapp.prestein.moodsapp.util.PopupMessages;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;

public class ToastMessage {
private Activity context;
    public static void makeText(Activity context,int NotIcon,String NotMessage){
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View layout=layoutInflater.inflate(R.layout.row_toast_message,(ViewGroup) context.findViewById(R.id.toast_layout_root_view));
        ImageView imageView=(ImageView)layout.findViewById(R.id.row_toast_icon);
        TextView textView=(TextView)layout.findViewById(R.id.row_toast_message);
        imageView.setImageResource(NotIcon);
        textView.setText(NotMessage);
        Toast toast=new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM,0,60);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
