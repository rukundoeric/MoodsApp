package com.moodsapp.prestein.moodsapp.util.InterfaceUtils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import com.moodsapp.prestein.moodsapp.R;

/**
 * Created by Eric prestein on 1/3/2018.
 */

public class AnyCustomProgressDialog extends Dialog{
    String content;
    Activity activity;
    public AnyCustomProgressDialog(String content,Activity activity) {
        super(activity);
        this.content=content;
        this.activity=activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_custom_progress_dialog);
        TextView txtcontent=(TextView)findViewById(R.id.any_custom_progress_dialog_content);
        txtcontent.setText(content);
    }
}
