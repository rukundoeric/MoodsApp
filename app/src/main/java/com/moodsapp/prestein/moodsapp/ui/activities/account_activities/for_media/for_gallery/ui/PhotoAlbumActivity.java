package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.adapter.BaseFragmentAdapter;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.component.PhoneMediaControl;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.EditImageActivity;
import com.moodsapp.prestein.moodsapp.util.DiviceActionUtils.ScreenManager;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;

import java.util.ArrayList;


public class PhotoAlbumActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView mView;
    private Context mContext;
    private SparseBooleanArray mSparseBooleanArray;//Variable to store selected Images
    public static ArrayList<PhoneMediaControl.AlbumEntry> albumsSorted = null;
    public static ArrayList<PhoneMediaControl.PhotoEntry> photos = new ArrayList<PhoneMediaControl.PhotoEntry>();

    private int itemWidth = 100;
    private ListAdapter listAdapter;
    private int AlbummID=0;
    private String fromContext;
    private ArrayList<CharSequence> idFriends;
    private String User_id;
    private String nameFriend;
    private String statusFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initializeActionBar();
        getExtraData();
        initializeView();
    }
    private void getExtraData(){
        try {
            Bundle bundle = getIntent().getExtras();
            fromContext=bundle.getString("fromContext");
            String nameAlbum = bundle.getString("Key_Name");
            AlbummID =Integer.parseInt(bundle.getString("Key_ID")) ;
            idFriends = bundle.getCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID);
            User_id = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID);
            nameFriend = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND);
            statusFriend = bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_STATUS);
            toolbar.setTitle(nameAlbum+" ("+photos.size()+")");
        }catch (Exception e){
            Toast.makeText(this, "In Chat ACTIVITY"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
        }
    }
    private void initializeActionBar() {
        toolbar = (Toolbar) findViewById(R.id.album_image_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle mBundle=getIntent().getExtras();
        albumsSorted= ExtractedStrings.albumsSorted;
        photos=albumsSorted.get(AlbummID).photos;
    }
    private void initializeView(){
        mSparseBooleanArray = new SparseBooleanArray();
        mView=(GridView)findViewById(R.id.Gallery_in_album_Image_View_List);
        mView.setAdapter(listAdapter = new ListAdapter(PhotoAlbumActivity.this));

        int position = mView.getFirstVisiblePosition();
        int columnsCount = 3;
        mView.setNumColumns(columnsCount);
        itemWidth = ScreenManager.getItemWidth(columnsCount,1);
       // mView.setColumnWidth(itemWidth);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.album_image_fab_send_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listAdapter.getCheckedItems().size()>0){
                    ArrayList<String> selectedItems = listAdapter.getCheckedItems();
                    Intent intent = new Intent(PhotoAlbumActivity.this, EditImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fromContext", fromContext);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, nameFriend);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, statusFriend);

                    bundle.putStringArrayList("images", selectedItems);
                    bundle.putString("type","image");

                    bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriends);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }
        });
        fab.setVisibility(View.GONE);
        listAdapter.notifyDataSetChanged();
        mView.setSelection(position);
        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                RelativeLayout selectedItem=(RelativeLayout) view.findViewById(R.id.row_gallery_layout_selected_image);
                ImageView imageSelected=(ImageView)view.findViewById(R.id.row_gallery_album_image);
                if(!mSparseBooleanArray.get(position)) {
                    selectedItem.setVisibility(View.VISIBLE);
                    imageSelected.setAlpha(0.5f);
                    mSparseBooleanArray.put(position, true);
                    Log.d("log", "pos = "+position);
                    if (listAdapter.getCheckedItems().size()==1){
                        Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_grow_fade_in_from_bottom);
                        fab.startAnimation(an2);
                        an2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                fab.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }
                } else {
                    selectedItem.setVisibility(View.GONE);
                    imageSelected.setAlpha(1f);
                    mSparseBooleanArray.put(position, false);
                    if (listAdapter.getCheckedItems().size()==0){
                        Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_shrink_fade_out_from_bottom);
                        fab.startAnimation(an);
                        an.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                fab.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }
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
        if (mView != null && mView.getEmptyView() == null) {
            mView.setEmptyView(null);
        }
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSparseBooleanArray.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeActionBar();
        initializeView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSparseBooleanArray.clear();
    }

    private class ListAdapter extends BaseFragmentAdapter {
        private Context mContext;
        private LayoutInflater layoutInflater;
        public ListAdapter(Context context) {
            this.mContext = context;
            this.layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        //Method to return selected Images
        public ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArry = new ArrayList<String>();

            for (int i = 0; i < photos.size(); i++) {
                Log.d("log", "i = " + mSparseBooleanArray.get(i));
                if (mSparseBooleanArray.get(i)) {
                    PhoneMediaControl.PhotoEntry mPhotoEntry = photos.get(i);
                    String path = mPhotoEntry.path;
                   if (path != null && !path.equals("")) {
                        mTempArry.add(new CompressingImage(mContext).getRealPathFromURI("file://" + path));
                     }
                }
            }
            return mTempArry;
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
            viewHolder mHolder;
            if (view == null) {
                mHolder = new viewHolder();
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
                mHolder = (viewHolder) view.getTag();
            }
            PhoneMediaControl.PhotoEntry mPhotoEntry = photos.get(i);
            String path = mPhotoEntry.path;
            if (path != null && !path.equals("")) {
                Glide.with(mContext).load("file://" + path).apply(new RequestOptions().optionalFitCenter()).into(mHolder.imageView);
            }
            if(mSparseBooleanArray.get(i)) {
                mHolder.selectedItem.setVisibility(View.VISIBLE);
                mHolder.imageView.setAlpha(0.5f);
                mSparseBooleanArray.put(i, true);
            } else {
                mHolder.selectedItem.setVisibility(View.GONE);
                mHolder.imageView.setAlpha(1f);
                mSparseBooleanArray.put(i, false);
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
