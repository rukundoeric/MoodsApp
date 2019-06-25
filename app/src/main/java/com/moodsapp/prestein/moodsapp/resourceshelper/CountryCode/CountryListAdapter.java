package com.moodsapp.prestein.moodsapp.resourceshelper.CountryCode;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step.Search_activity_for_country_list;
import com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step.Select_your_country;

import java.util.ArrayList;
import java.util.List;
public class CountryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<Country> lictCountryItem;
    public static Context context;
    public Resources res;
    Bitmap src;
    private Search_activity_for_country_list search_activity_for_country_list;

    public CountryListAdapter(List<Country> lictCountryItem, Context context, Search_activity_for_country_list search_activity_for_country_list) {
        this.lictCountryItem = lictCountryItem;
        this.context = context;
        this.search_activity_for_country_list=search_activity_for_country_list;
        res=context.getResources();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_country_code_picker, parent, false);
        return new ItemCountryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Country countries=lictCountryItem.get(position);
        src = BitmapFactory.decodeResource(res, new Country().getFlagResID(countries.getCountryNameCode().toLowerCase()));
        final BitmapDrawable Image_drawable = new BitmapDrawable(res, src);
        try {
            ((ItemCountryViewHolder) holder).CountryImage.setImageDrawable(Image_drawable);
            ((ItemCountryViewHolder) holder).CountryName.setText(countries.getCountryName());
            ((ItemCountryViewHolder) holder).CountryCode.setText(countries.getCountryCode());
        } catch (Exception e) {
            Toast.makeText(context, "In list All countries"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        ((ItemCountryViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, Select_your_country.class);
                    intent.putExtra(ExtractedStrings.INTENT_COUNTRY_NAME, countries.getCountryName());
                    intent.putExtra(ExtractedStrings.INTENT_COUNTRY_NAME_CODE, countries.getCountryNameCode());
                    intent.putExtra(ExtractedStrings.INTENT_COUNTRY_CODE, countries.getCountryCode());
                    context.startActivity(intent);
                    search_activity_for_country_list.finish();

                }catch (Exception e){
                    Toast.makeText(context, "In on click"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return lictCountryItem.size();
    }
    public class ItemCountryViewHolder extends RecyclerView.ViewHolder{

        public View mView;
        public ImageView CountryImage;
        public TextView CountryName;
        public TextView CountryCode;

        public ItemCountryViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            CountryImage=(ImageView)mView.findViewById(R.id.img_flag_in_row_country_code_picker);
            CountryName=(TextView)mView.findViewById(R.id.txt_country_name_in_row_country_code_picker);
            CountryCode=(TextView)mView.findViewById(R.id.txt_country_code_in_row_country_code_picker);


        }

    }
    public void setFilter(List<Country> newList)
    {
        lictCountryItem=new ArrayList<>();
        lictCountryItem.addAll(newList);
        notifyDataSetChanged();
    }

}
