package com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.resourceshelper.CountryCode.Country;

import java.util.ArrayList;

public class Select_your_country extends AppCompatActivity {

    private ImageView Open_County_List;
    private TextView txtCountryName;
    private TextView txtNameCose;
    private ArrayList<Country> countryListinSelectCounry;
    private ImageView imgCountryFlag;
    private Bitmap src;
    private Resources res;
    private String CountryCode;
    private String namecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__your_country);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbr_for_select_county_name);
        setSupportActionBar(toolbar);
        res=getResources();
        countryListinSelectCounry=new ArrayList<>();
        Country country=new Country();
        country.AddCountryInList(countryListinSelectCounry);
        Open_County_List=(ImageView)findViewById(R.id.activity_select_your_country_drop_down);
        Open_County_List.setClickable(true);
        Open_County_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(Select_your_country.this, Search_activity_for_country_list.class));
            }
        });
        imgCountryFlag = (ImageView) findViewById(R.id.activity_select_your_country_flag);
        txtCountryName=(TextView)findViewById(R.id.activity_select_your_country_name);
        txtNameCose=(TextView)findViewById(R.id.activity_select_your_country_name_code);
        //String local=this.getResources().getConfiguration().locale.getISO3Country();
        try {
            Bundle bundle=getIntent().getExtras();
            if (bundle!=null) {
                src = BitmapFactory.decodeResource(res, new Country().getFlagResID(bundle.getString(ExtractedStrings.INTENT_COUNTRY_NAME_CODE.toLowerCase())));
                final BitmapDrawable Image_drawable = new BitmapDrawable(res, src);
                CountryCode=bundle.getString(ExtractedStrings.INTENT_COUNTRY_CODE);
                imgCountryFlag.setImageDrawable(Image_drawable);
                txtCountryName.setText(bundle.getString(ExtractedStrings.INTENT_COUNTRY_NAME));
                txtNameCose.setText("("+bundle.getString(ExtractedStrings.INTENT_COUNTRY_NAME_CODE).toUpperCase()+")");
                namecode =bundle.getString(ExtractedStrings.INTENT_COUNTRY_NAME_CODE.toLowerCase());

            }
            else
            {
                TelephonyManager telephonyManager=(TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
                String cCode=telephonyManager.getNetworkCountryIso();

                for(Country s:countryListinSelectCounry)
                {
                    if(s.getCountryNameCode().equals(cCode))
                    {

                        //Toast.makeText(this, s.getCountryCode(), Toast.LENGTH_SHORT).show();
                        src = BitmapFactory.decodeResource(res, new Country().getFlagResID(s.getCountryNameCode().toLowerCase()));
                        final BitmapDrawable Image_drawable = new BitmapDrawable(res, src);
                        CountryCode=s.getCountryCode();
                        imgCountryFlag.setImageDrawable(Image_drawable);
                        txtCountryName.setText(s.getCountryName());
                        txtNameCose.setText("("+cCode.toUpperCase()+")");
                        namecode =cCode;
                        break;
                    }
                }

            }
        }catch (Exception e)
        {
            Toast.makeText(this, "////"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void OpenActivityPhoneNumber(View view) {
        String name=txtCountryName.getText().toString().trim();
        Intent intent=new Intent(Select_your_country.this, Enter_your_phone_number_activity.class);
        intent.putExtra(ExtractedStrings.INTENT_COUNTRY_NAME_CODE, namecode);
        intent.putExtra(ExtractedStrings.INTENT_COUNTRY_CODE, CountryCode);
        intent.putExtra(ExtractedStrings.INTENT_COUNTRY_NAME, name);
        startActivity(intent);
        finish();
    }
}
