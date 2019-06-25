package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_more_info_about_app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.RecentNotificationDb;
import com.moodsapp.prestein.moodsapp.service.RecentNotification.RecentNotificationListClass;

public class Help extends AppCompatActivity {
    private RecyclerView NotificationView;
    private ListingNotificationTest adapter;
    private Context context=this;
    private static RecentNotificationListClass recentNotificationMessage=null;
    private Button btn;
    private ImageView layoutLi;
    private boolean isAnimatedIn=false;
    private RadioGroup mRadioGroup;
    private int nCheckedItem=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        btn=(Button)findViewById(R.id.button_start_animation);
        layoutLi=(ImageView)findViewById(R.id.layout_test_animation);
        mRadioGroup=(RadioGroup)findViewById(R.id.radioButtonsTest);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nCheckedItem=checkedId;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int animation=getSelectedAnimation(nCheckedItem);
                if (animation!=0){
                    Animation an = AnimationUtils.loadAnimation(getBaseContext(), animation);
                    layoutLi.startAnimation(an);
                    isAnimatedIn=true;
                    an.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }else {
                    Toast.makeText(context, "No selected Animation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private int getSelectedAnimation(int selectedRadioId){
        if (selectedRadioId==R.id.radioButton1){
            return R.anim.abc_slide_in_bottom;
        }else if (selectedRadioId==R.id.radioButton2){
            return R.anim.abc_slide_in_top;
        }else if (selectedRadioId==R.id.radioButton3){
            return R.anim.abc_slide_out_bottom;
        }else if (selectedRadioId==R.id.radioButton4){
            return R.anim.yobounce;
        }else if (selectedRadioId==R.id.radioButton5){
            return R.anim.up;
        }else if (selectedRadioId==R.id.radioButton6){
            return R.anim.down;
        }else {
            return 0;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RecentNotificationDb.getInstance(context).removeAllNotification(context);
    }
}
