package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.moodsapp.prestein.moodsapp.R;

import java.io.File;
import java.util.ArrayList;

class SelectedImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SelectedListener selectedListerner;
    private EditImageActivity context;
    private ArrayList<String> listitems;
    public static ViewGroup.LayoutParams params;
    private ViewGroup.LayoutParams Lparams;
    private String type;

    public SelectedImageAdapter(EditImageActivity context, ArrayList<String> list, SelectedListener selectedListener, String type) {
        this.listitems = list;
        this.selectedListerner=selectedListener;
        this.context = context;
        this.type=type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_selected_view, parent, false);
        return new ViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Lparams=((ViewHolder) holder).mImage.getLayoutParams();
        int width=context.DeviceWidth/7;
        Lparams.height=width;
        Lparams.width=width;
        ((ViewHolder) holder).mImage.setLayoutParams(Lparams);
        if(new File(listitems.get(position)).exists()){
           if (type.equals("image")){
                ((ViewHolder) holder).mPlayButton.setVisibility(View.GONE);
               Glide.with(context).load(listitems.get(position)).apply(new RequestOptions().optionalFitCenter()).into(((ViewHolder) holder).mImage);
            }else {
                ((ViewHolder) holder).mPlayButton.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mImage.setImageBitmap(getBitmapStringFromVideo(listitems.get(position)));
            }
        }else {
           listitems.remove(listitems.get(position));
        }
    }
    private Bitmap getBitmapStringFromVideo(String VideoPath){
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(VideoPath,   MediaStore.Images.Thumbnails.MINI_KIND);
        Matrix matrix = new Matrix();
        Bitmap bmThumbnail = Bitmap.createBitmap(thumb, 0, 0,thumb.getWidth(), thumb.getHeight(), matrix, true);
        bmThumbnail= cropToSquare(bmThumbnail);
        return bmThumbnail;
    }
    public static Bitmap cropToSquare(Bitmap srcBmp){
        Bitmap dstBmp = null;
        if (srcBmp.getWidth() >= srcBmp.getHeight()){

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        }else{
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }

        return dstBmp;
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mPlayButton;
        public ImageView mImage;
        ViewHolder(Context context, View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.imgSelectedImagesView);
            mPlayButton=(LinearLayout) itemView.findViewById(R.id.layout_back_for_play_video_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedListerner.onSelectedClicked(getLayoutPosition());
                }
            });
        }
    }
}


/*
package com.moodsapp.photo_editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class SelectedImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

//    private ViewGroup.LayoutParams Lparams;
//
//    private EditImageActivity context;
//    private ArrayList<String> listitems;
//
//    public SelectedImageAdapter(EditImageActivity context, ArrayList<String> listitems) {
//        this.context = context;
//        this.listitems = listitems;
//        Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
//
//    }
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Toast.makeText(context, "On created view", Toast.LENGTH_SHORT).show();
//        View view=LayoutInflater.from(context).inflate(R.layout.row_filter_view,parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Lparams=((ViewHolder) holder).mImage.getLayoutParams();
//        Toast.makeText(context, "Image "+position, Toast.LENGTH_SHORT).show();
//        int width=context.DeviceWidth-((context.DeviceWidth/5));
//        Lparams.height=width;
//        Lparams.width=width;
//        ((ViewHolder) holder).mImage.setLayoutParams(Lparams);
//        if(new File(listitems.get(position)).exists()){
//            Bitmap bitmap= BitmapFactory.decodeFile(listitems.get(position));
//            ((ViewHolder) holder).mImage.setImageBitmap(bitmap);
//        }else {
//            listitems.remove(listitems.get(position));
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}
class  ViewHolder extends RecyclerView.ViewHolder{

    public ImageView mImage;

    public ViewHolder(View itemView) {
        super(itemView);
        mImage=(ImageView)itemView.findViewById(R.id.rvSelectedPhotoView);
    }
}*/
