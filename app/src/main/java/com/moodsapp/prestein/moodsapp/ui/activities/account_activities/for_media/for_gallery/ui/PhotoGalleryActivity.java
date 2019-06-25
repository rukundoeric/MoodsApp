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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.adapter.BaseFragmentAdapter;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.component.PhoneMediaControl;

import java.util.ArrayList;


public class PhotoGalleryActivity extends AppCompatActivity {
    public static ArrayList<PhoneMediaControl.AlbumEntry> albumsSorted = null;
    public static ArrayList<PhoneMediaControl.PhotoEntry> photos = new ArrayList<PhoneMediaControl.PhotoEntry>();
    private GridView mView;
    private PhoneMediaControl.AlbumEntry selectedAlbum = null;
    private int itemWidth = 100;
    private ListAdapter listAdapter;
    private int AlbummID=0;
    private ArrayList<CharSequence> idFriends;
    private String User_id;
    private String nameFriend;
    private String statusFriend;
    private String fromContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_gallery_view_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mView=(GridView)findViewById(R.id.Gallery_Image_View_List);
        getExtraData();
        initializeView();
    }
    private void getExtraData(){
        try {
            Bundle bundle = getIntent().getExtras();
            fromContext=bundle.getString("fromContext");
            idFriends = bundle.getCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID);
            User_id = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID);
            nameFriend = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND);
            statusFriend = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_STATUS);
        }catch (Exception e){
            Toast.makeText(this, "In Chat ACTIVITY"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
        }
    }
    private void initializeView(){
        mView=(GridView)findViewById(R.id.Gallery_Image_View_List);
      /*  emptyView = (TextView)v.findViewById(R.id.searchEmptyView);
        emptyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        emptyView.setText("NoPhotos");*/
        mView.setAdapter(listAdapter = new ListAdapter(getApplicationContext()));

        int position = mView.getFirstVisiblePosition();
        int columnsCount = 2;
        mView.setNumColumns(columnsCount);
        itemWidth = (MoodsApp.displaySize.x - ((columnsCount + 1) * MoodsApp.dp(1))) / columnsCount;
        mView.setColumnWidth(itemWidth);

        listAdapter.notifyDataSetChanged();
        mView.setSelection(position);
        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent mIntent=new Intent(getApplicationContext(),PhotoAlbumActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fromContext", fromContext);
                bundle.putString("Key_ID", position+"");
                bundle.putString("Key_Name", albumsSorted.get(position).bucketName+"");
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, nameFriend);
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, statusFriend);
                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriends);
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                mIntent.putExtras(bundle);
                startActivity(mIntent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });

        LoadAllAlbum();
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
    private void LoadAllAlbum(){
        PhoneMediaControl mediaControl=new PhoneMediaControl();
        mediaControl.setLoadalbumphoto(new PhoneMediaControl.loadAlbumPhoto() {

            @Override
            public void loadPhoto(ArrayList<PhoneMediaControl.AlbumEntry> albumsSorted_) {
                albumsSorted =new ArrayList<PhoneMediaControl.AlbumEntry>(albumsSorted_);
                ExtractedStrings.albumsSorted=albumsSorted;
                if (mView != null && mView.getEmptyView() == null) {
                    mView.setEmptyView(null);
                }
                if (listAdapter != null) {
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        mediaControl.loadGalleryPhotosAlbums(getApplicationContext(),0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    private class ListAdapter extends BaseFragmentAdapter {
        private Context mContext;
        public ListAdapter(Context context) {
            mContext = context;
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
            if (selectedAlbum != null) {
                return selectedAlbum.photos.size();
            }
            return albumsSorted != null ? albumsSorted.size() : 0;
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
            if (view == null) {
                LayoutInflater li = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.row_photo_picker_album_item,
                        viewGroup, false);
            }
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = itemWidth;
            params.height = itemWidth;
            view.setLayoutParams(params);
            PhoneMediaControl.AlbumEntry albumEntry = albumsSorted.get(i);
            final ImageView imageView = (ImageView) view
                    .findViewById(R.id.gallery_album_item_media_photo_image);
            if (albumEntry.coverPhoto != null
                    && albumEntry.coverPhoto.path != null) {
                Glide.with(mContext).load("file://" + albumEntry.coverPhoto.path).apply(new RequestOptions().optionalFitCenter()).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.nophotos);
            }
            TextView textView = (TextView) view.findViewById(R.id.gallery_album_item_album_name);
            textView.setText(albumEntry.bucketName);
            textView = (TextView) view.findViewById(R.id.gallery_album_item_album_count);
            textView.setText("" + albumEntry.photos.size());
            final ImageView albumIcon= (ImageView) view
                    .findViewById(R.id.gallery_album_item_media_album_icon);
            if (albumEntry.bucketName.toLowerCase().equals("camera")){
                albumIcon.setImageResource(R.drawable.ic_camera_write);
            }else {
                albumIcon.setImageResource(R.drawable.ic_folder_write);
            }
            return view;
        }

        @Override
        public int getItemViewType(int i) {
            if (selectedAlbum != null) {
                return 1;
            }
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEmpty() {
            if (selectedAlbum != null) {
                return selectedAlbum.photos.isEmpty();
            }
            return albumsSorted == null || albumsSorted.isEmpty();
        }
    }
}


