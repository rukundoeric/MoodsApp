package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.moodsapp.prestein.moodsapp.R;

public class Apply_change_dialog extends Dialog {
    public Apply_change_dialog(Activity activity){
        super(activity);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.apply_changes_dialog);

    }
}
