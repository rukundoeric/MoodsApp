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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.Application.MoodsApp;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.adapter.BaseFragmentAdapter;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.component.PhoneMediaVideoController;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.component.VideoThumbleLoader;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.EditImageActivity;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage;

import java.util.ArrayList;

public class VideoGalleryActivity extends AppCompatActivity implements PhoneMediaVideoController.loadAllVideoMediaInterface {
    private TextView emptyView;
    private GridView mView;
    private Context mContext;
    private int itemWidth = 100;
    private ListAdapter listAdapter;

    private String fromContext;
    private ArrayList<CharSequence> idFriends;
    private String User_id;
    private String nameFriend;
    private String statusFriend;

    private SparseBooleanArray mSparseBooleanArray;//Variable to store selected Images

    private ArrayList<PhoneMediaVideoController.VideoDetails> arrayVideoDetails = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        mContext=this;
        mSparseBooleanArray = new SparseBooleanArray();
        mView=(GridView)findViewById(R.id.Video_Gallery_View_List);
        mView.setAdapter(listAdapter = new ListAdapter(mContext));
        int position = mView.getFirstVisiblePosition();
        int columnsCount = 2;
        mView.setNumColumns(columnsCount);
        itemWidth = (MoodsApp.displaySize.x - ((columnsCount + 1) * MoodsApp.dp(1))) / columnsCount;
        mView.setColumnWidth(itemWidth);
        listAdapter.notifyDataSetChanged();
        mView.setSelection(position);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.video_image_fab_send_button);
        fab.setVisibility(View.GONE);
        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout selectedItem=(RelativeLayout) view.findViewById(R.id.row_video_gallery_layout_selected_video);
                ImageView imageSelected=(ImageView)view.findViewById(R.id.video_gallery_album_item_media_photo_image);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedItems = listAdapter.getCheckedItems();
                Intent intent = new Intent(VideoGalleryActivity.this, EditImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fromContext", fromContext);
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, nameFriend);
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, statusFriend);

                bundle.putStringArrayList("images", selectedItems);
                bundle.putString("type","video");

                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriends);
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });
        loadData();
    }
    private void loadData() {
        PhoneMediaVideoController mPhoneMediaVideoController = new PhoneMediaVideoController();
        mPhoneMediaVideoController.setLoadallvideomediainterface(this);
        mPhoneMediaVideoController.loadAllVideoMedia(mContext);
    }
    @Override
    public void loadVideo(ArrayList<PhoneMediaVideoController.VideoDetails> arrVideoDetails) {
        arrayVideoDetails=arrVideoDetails;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    private class ListAdapter extends BaseFragmentAdapter {
        private Context mContext;
        private VideoThumbleLoader thumbleLoader;
        private LayoutInflater inflater;

        public ListAdapter(Context context) {
            this.mContext = context;
            this.thumbleLoader=new VideoThumbleLoader(mContext);
            this.inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        //Method to return selected Images
        public ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArry = new ArrayList<String>();

            for (int i = 0; i < arrayVideoDetails.size(); i++) {
                Log.d("log", "i = " + mSparseBooleanArray.get(i));
                if (mSparseBooleanArray.get(i)) {
                    PhoneMediaVideoController.VideoDetails mVideoDetails = arrayVideoDetails.get(i);
                    String path = mVideoDetails.path;
                    if (path != null && !path.equals("")) {
                        mTempArry.add(new CompressingImage(mContext).getRealPathFromURIVideo("file://" + path));
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
            return arrayVideoDetails != null ? arrayVideoDetails.size() : 0;
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
            ViewHolder mViewHolder ;
            if (view == null) {
                mViewHolder=new ViewHolder();
                view = inflater.inflate(R.layout.row_video_picker_album_item,viewGroup, false);
                mViewHolder.img = (ImageView) view.findViewById(R.id.video_gallery_album_item_media_photo_image);
                mViewHolder.txtTitle = (TextView) view.findViewById(R.id.video_gallery_album_item_album_name);
                mViewHolder.selectedItem=(RelativeLayout)view.findViewById(R.id.row_video_gallery_layout_selected_video);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = itemWidth;
                params.height = itemWidth;
                view.setLayoutParams(params);
                view.setTag(mViewHolder);
            }else {
                mViewHolder = (ViewHolder) view.getTag();
            }

            PhoneMediaVideoController.VideoDetails mVideoDetails = arrayVideoDetails.get(i);
            final String videoPath=mVideoDetails.path;
            thumbleLoader.DisplayImage(""+mVideoDetails.imageId, mContext, mViewHolder.img, null);
            mViewHolder.txtTitle.setText(mVideoDetails.displayname);
            if(mSparseBooleanArray.get(i)) {
                mViewHolder.selectedItem.setVisibility(View.VISIBLE);
                mViewHolder.img.setAlpha(0.5f);
                mSparseBooleanArray.put(i, true);
            } else {
                mViewHolder.selectedItem.setVisibility(View.GONE);
                mViewHolder.img.setAlpha(1f);
                mSparseBooleanArray.put(i, false);
            }
            return view;
        }
        private class ViewHolder{
            ImageView img;
            TextView txtTitle;
            RelativeLayout selectedItem;
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
        initializeView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSparseBooleanArray.clear();
    }
}
