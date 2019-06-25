package com.moodsapp.prestein.moodsapp.util.ColorUtils;
import android.app.Activity;

import com.moodsapp.prestein.moodsapp.R;

import java.util.ArrayList;
public class ColorCreation {
    public ArrayList<Integer> getColorList(Activity activity){
            try {
                    ArrayList<Integer> arrayList=new ArrayList<>();
                    int colo1=activity.getResources().getColor(R.color.colorDecoration1);
                    int colo2=activity.getResources().getColor(R.color.colorDecoration2);
                    int colo3=activity.getResources().getColor(R.color.colorDecoration3);
                    int colo4=activity.getResources().getColor(R.color.colorDecoration4);
                    int colo5=activity.getResources().getColor(R.color.colorDecoration5);
                    int colo6=activity.getResources().getColor(R.color.colorDecoration6);
                    int colo7=activity.getResources().getColor(R.color.colorDecoration7);
                    arrayList.add(colo1);
                    arrayList.add(colo2);
                    arrayList.add(colo3);
                    arrayList.add(colo4);
                    arrayList.add(colo5);
                    arrayList.add(colo6);
                    arrayList.add(colo7);
                    return arrayList;
            }catch (NullPointerException e){
                    e.printStackTrace();
            }
         return null;
        }
}
