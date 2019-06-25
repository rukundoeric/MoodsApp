package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType;

import android.content.Context;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import android.view.View;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moodsapp.prestein.moodsapp.R;

import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder>{

    Context context1;
    List<String> stringList;
    String userId;
    ApkListActivity apkListActivity;
    public AppsAdapter(ApkListActivity apkListActivity,Context context, List<String> list,String userId){
        this.context1 = context;
        this.stringList = list;
        this.userId=userId;
        this.apkListActivity=apkListActivity;
    }

    public AppsAdapter() {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageSendApp;
        public ImageView imageView;
        public TextView textView_App_Name;
        public TextView textView_App_Package_Name;

        public ViewHolder (View view){

            super(view);
            imageView = (ImageView) view.findViewById(R.id.Installed_app_icon_image);
            textView_App_Name = (TextView) view.findViewById(R.id.Installed_app_name_textView);
            textView_App_Package_Name = (TextView) view.findViewById(R.id.Installed_app_details_textView);
            mImageSendApp=(ImageView)view.findViewById(R.id.Installed_app_send_icon);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view2 = LayoutInflater.from(context1).inflate(R.layout.row_installed_app,parent,false);
        ViewHolder viewHolder = new ViewHolder(view2);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){

        final ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(context1);

        final String ApplicationPackageName = (String) stringList.get(position);
        final String ApplicationLabelName = apkInfoExtractor.GetAppName(ApplicationPackageName);
        Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);

        viewHolder.textView_App_Name.setText(ApplicationLabelName);

        viewHolder.textView_App_Package_Name.setText(ApplicationPackageName);

        viewHolder.imageView.setImageDrawable(drawable);

        viewHolder.mImageSendApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dir=apkInfoExtractor.GetApkDirectory(context1,ApplicationPackageName);
                new SendApk(context1).saveApkMessage(userId,dir,ApplicationPackageName);
                Toast.makeText(context1, "Saved success", Toast.LENGTH_SHORT).show();
                //stringList.remove(ApplicationPackageName);
                //new AppsAdapter().notifyDataSetChanged();
               // apkListActivity.UpdateAdapter(apkListActivity,context1,stringList,userId);
            }
        });
        //Adding click listener on CardView to open clicked application directly from here .
/*        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String dir=apkInfoExtractor.GetApkDirectory(context1,ApplicationPackageName);
            apkInfoExtractor.copyApkToMoodsAppFolder(context1,ApplicationLabelName,dir);
                Toast.makeText(context1, dir, Toast.LENGTH_SHORT).show();
       *//*         Intent intent = context1.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                if(intent != null){

                    context1.startActivity(intent);

                }
                else {

                    Toast.makeText(context1,ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                }*//*
            }
        });*/
    }

    @Override
    public int getItemCount(){

        return stringList.size();
    }

}