package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.adapter.BaseFragmentAdapter;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.component.PhoneMediaControl;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.EditImageActivity;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;

import java.util.ArrayList;


public class OnePicSelectorAlbumActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView mView;
    private Context mContext;
    public static ArrayList<PhoneMediaControl.AlbumEntry> albumsSorted = null;
    public static ArrayList<PhoneMediaControl.PhotoEntry> photos = new ArrayList<PhoneMediaControl.PhotoEntry>();

    private int itemWidth = 100;
    private ListAdapter listAdapter;
    private int AlbummID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pic_selector_album);
        toolbar = (Toolbar) findViewById(R.id.one_pic_selector_in_album_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeActionBar();
        initializeView();
    }
    private void initializeActionBar() {
        Bundle mBundle=getIntent().getExtras();
        String nameAlbum = mBundle.getString("Key_Name");
        AlbummID =Integer.parseInt(mBundle.getString("Key_ID")) ;
        albumsSorted= ExtractedStrings.albumsSorted;
        photos=albumsSorted.get(AlbummID).photos;
        toolbar.setTitle(nameAlbum+" ("+photos.size()+")");
    }
    private void initializeView(){
        mView=(GridView)findViewById(R.id.Gallery_in_album_Image_View_List);
        mView.setAdapter(listAdapter = new ListAdapter(this));

        int position = mView.getFirstVisiblePosition();
        int columnsCount = 3;
        mView.setNumColumns(columnsCount);
        itemWidth = (MoodsApp.displaySize.x - ((columnsCount + 1) * MoodsApp.dp(4))) / columnsCount;
        // mView.setColumnWidth(itemWidth);

        listAdapter.notifyDataSetChanged();
        mView.setSelection(position);
        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                PhoneMediaControl.PhotoEntry mPhotoEntry = photos.get(position);
                String path = mPhotoEntry.path;
                if (path != null && !path.equals("")) {
                    String imagePath=new CompressingImage(mContext).getRealPathFromURI("file://" + path);
                    ArrayList<String> selectedItems = new ArrayList<>();
                    selectedItems.add(imagePath);
                    Intent intent = new Intent(OnePicSelectorAlbumActivity.this, EditImageActivity.class);
                    intent.putExtra("images", selectedItems);
                    intent.putExtra("type","image");
                    startActivity(intent);
                }
            }
        });
        LoadAllAlbum();
    }
    private void LoadAllAlbum(){
        if (mView != null && mView.getEmptyView() == null) {
            mView.setEmptyView(null);
        }
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    private class ListAdapter extends BaseFragmentAdapter {
        private Context mContext;
        private LayoutInflater layoutInflater;
        public ListAdapter(Context context) {
            this.mContext = context;
            this.layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int i) {
            return true;
        }

        @Override
        public int getCount() {
            return photos != null ? photos.size() : 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ListAdapter.viewHolder mHolder;
            if (view == null) {
                mHolder = new ListAdapter.viewHolder();
                view = layoutInflater.inflate(R.layout.row_gallery_album_image, viewGroup,false);
                mHolder.imageView = (ImageView) view.findViewById(R.id.row_gallery_album_image);
                mHolder.selectedItem=(RelativeLayout) view.findViewById(R.id.row_gallery_layout_selected_image);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = itemWidth;
                params.height = itemWidth;
                view.setLayoutParams(params);
                //mHolder.imageView.setTag(i);

                view.setTag(mHolder);
            } else {
                mHolder = (ListAdapter.viewHolder) view.getTag();
            }
            PhoneMediaControl.PhotoEntry mPhotoEntry = photos.get(i);
            String path = mPhotoEntry.path;
            if (path != null && !path.equals("")) {
                Glide.with(mContext).load("file://" + path).into(mHolder.imageView);
            }
            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEmpty() {
            return albumsSorted == null || albumsSorted.isEmpty();
        }

        class viewHolder {
            public ImageView imageView;
            public RelativeLayout selectedItem;
        }

    }
}
