package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.filters.FilterListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class ImageOptiionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static Camera.Parameters params;
    private FilterListener filterListerner;
    private ViewGroup.LayoutParams Lparams;

    private EditImageActivity activity;
    //private FilterListener mFilterListener;
    private List<Pair<String, PhotoFilter>> mPairList = new ArrayList<>();
    private ArrayList<String> listitems;
    private PhotoEditor mPhotoEditor;

    public ImageOptiionsAdapter(EditImageActivity activity, FilterListener filterListener) {
        this.filterListerner=filterListener;
        this.activity=activity;
        setupFilters();
    }
    private void setupFilters() {
        mPairList.add(new Pair<>("filters/original.jpg", PhotoFilter.NONE));
        mPairList.add(new Pair<>("filters/auto_fix.png", PhotoFilter.AUTO_FIX));
        mPairList.add(new Pair<>("filters/brightness.png", PhotoFilter.BRIGHTNESS));
        mPairList.add(new Pair<>("filters/contrast.png", PhotoFilter.CONTRAST));
        mPairList.add(new Pair<>("filters/documentary.png", PhotoFilter.DOCUMENTARY));
        mPairList.add(new Pair<>("filters/dual_tone.png", PhotoFilter.DUE_TONE));
        mPairList.add(new Pair<>("filters/fill_light.png", PhotoFilter.FILL_LIGHT));
        mPairList.add(new Pair<>("filters/fish_eye.png", PhotoFilter.FISH_EYE));
        mPairList.add(new Pair<>("filters/grain.png", PhotoFilter.GRAIN));
        mPairList.add(new Pair<>("filters/gray_scale.png", PhotoFilter.GRAY_SCALE));
        mPairList.add(new Pair<>("filters/lomish.png", PhotoFilter.LOMISH));
        mPairList.add(new Pair<>("filters/negative.png", PhotoFilter.NEGATIVE));
        mPairList.add(new Pair<>("filters/posterize.png", PhotoFilter.POSTERIZE));
        mPairList.add(new Pair<>("filters/saturate.png", PhotoFilter.SATURATE));
        mPairList.add(new Pair<>("filters/sepia.png", PhotoFilter.SEPIA));
        mPairList.add(new Pair<>("filters/sharpen.png", PhotoFilter.SHARPEN));
        mPairList.add(new Pair<>("filters/temprature.png", PhotoFilter.TEMPERATURE));
        mPairList.add(new Pair<>("filters/tint.png", PhotoFilter.TINT));
        mPairList.add(new Pair<>("filters/vignette.png", PhotoFilter.VIGNETTE));
        mPairList.add(new Pair<>("filters/cross_process.png", PhotoFilter.CROSS_PROCESS));
        mPairList.add(new Pair<>("filters/b_n_w.png", PhotoFilter.BLACK_WHITE));
        mPairList.add(new Pair<>("filters/flip_horizental.png", PhotoFilter.FLIP_HORIZONTAL));
        mPairList.add(new Pair<>("filters/flip_vertical.png", PhotoFilter.FLIP_VERTICAL));
        mPairList.add(new Pair<>("filters/rotate.png", PhotoFilter.ROTATE));
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_filter_options, parent, false);
        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Lparams=((ViewHolder2) holder).mImage.getLayoutParams();
        int width=activity.DeviceWidth/12;
        Lparams.height=width;
        Lparams.width=width;
        Pair<String, PhotoFilter> filterPair = mPairList.get(position);
        Bitmap fromAsset = getBitmapFromAsset(holder.itemView.getContext(), filterPair.first);
        ((ViewHolder2) holder).mImage.setImageBitmap(fromAsset);
    }

    private Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
            return BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {

        return mPairList.size();
    }
   private class ViewHolder2 extends RecyclerView.ViewHolder{
        public ImageView mImage;
        ViewHolder2(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.imgFilterOptionItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterListerner.onFilterSelected(mPairList.get(getLayoutPosition()).second);
                }
            });
        }
    }
}


