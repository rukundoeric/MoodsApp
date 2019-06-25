
package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageImageType.SendImage;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageVideo.SendVideo;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.base.BaseActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.filters.FilterListener;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.tools.EditingToolsAdapter;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_image_editor.tools.ToolType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.ViewType;

public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener,
        SelectedListener,
        View.OnClickListener,
        PropertiesBSFragment.Properties,
        EmojiBSFragment.EmojiListener,
        StickerBSFragment.StickerListener, EditingToolsAdapter.OnItemSelected, FilterListener {

    private static final String TAG = EditImageActivity.class.getSimpleName();
    public static final String EXTRA_IMAGE_PATHS = "extra_image_paths";
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    public PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
    private PropertiesBSFragment mPropertiesBSFragment;
    private EmojiBSFragment mEmojiBSFragment;
    private StickerBSFragment mStickerBSFragment;

    private Typeface mWonderFont;
    private RecyclerView mRvFilters;
    private EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
    public ConstraintLayout mRootView;
    public ConstraintLayout mConstraintFilterView;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    private boolean mIsFilterVisible;
    private ArrayList<String> imageUrls;
    public int DeviceWidth;
    private RecyclerView mSelectedImages;
    private SelectedImageAdapter mSelectedAdapter;
    private ImageOptiionsAdapter mOptionsImages;
    private int currentImagePosition=0;
    private Apply_change_dialog mAppChangesDialog;
    private String mediaType;
    private LinearLayout mPlayButtonVideo;
    private Button mShowFilterButton;
    private Toolbar toolbar;
    private String fromContext;
    private ArrayList<CharSequence> idFriends;
    private String User_id;
    private String nameFriend;
    private String statusFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_edit_image);
        toolbar = (Toolbar) findViewById(R.id.toolbar_edit_photo_option);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        DeviceWidth=metrics.widthPixels;
        initViews();
        getExtraData();
        mShowFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRvFilters.getVisibility()==View.VISIBLE){
                    mShowFilterButton.setBackgroundResource(R.drawable.ic_show_up);
                    mRvFilters.setVisibility(View.GONE);
                }else{
                    mShowFilterButton.setBackgroundResource(R.drawable.ic_hide);
                    mRvFilters.setVisibility(View.VISIBLE);
                }
            }
        });

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.send_photos_fab_icon);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaType.equals("image")){
                    new SendImage(getApplicationContext()).seveImageMessage(User_id,imageUrls);
                    Intent intent = new Intent(getApplicationContext(), Chat_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, nameFriend);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, statusFriend);
                    bundle.putString(ExtractedStrings.INTENT_FRIENDS_POSITION, "0");
                    bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriends);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    finish();
                }else {
                    new SendVideo(getApplicationContext()).saveVideoMessage(User_id,imageUrls);
                    Intent intent = new Intent(getApplicationContext(), Chat_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, nameFriend);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, statusFriend);
                    bundle.putString(ExtractedStrings.INTENT_FRIENDS_POSITION, "0");
                    bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriends);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    finish();
                }

            }
        });
    }
    private void mediaShareVideoType() {
        String fileName=subStringByChar('.',new File(imageUrls.get(currentImagePosition)).getName());
        toolbar.setTitle(fileName);
        mShowFilterButton.setVisibility(View.GONE);
        mPlayButtonVideo.setVisibility(View.VISIBLE);
        Bitmap bitmap=getBitmapStringFromVideo(imageUrls.get(currentImagePosition));
        mPhotoEditorView.getSource().setImageBitmap(bitmap);
        mRvFilters.setVisibility(View.GONE);
        UpdateAdapterSelected(mediaType);
    }
    private String subStringByChar(Character a, String string){
        String finalString="";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == a) {
                finalString = string.substring(0, i);
                break;
            }
        }
        return  finalString;
    }
    private void getExtraData(){
        try {
            Bundle bundle = getIntent().getExtras();
            imageUrls = bundle.getStringArrayList("images");
            mediaType=bundle.getString("type");
            fromContext=bundle.getString("fromContext");
            idFriends = bundle.getCharSequenceArrayList("friendid");
            User_id = bundle.getString("UserId");
            nameFriend = bundle.getString("friendname");
            statusFriend = bundle.getString("friendstatus");
            if (bundle.isEmpty()){
                finish();
            }else {
                if(mediaType.equals("image")){
                    mPlayButtonVideo.setVisibility(View.GONE);
                    mediaShareImageType();
                }else {
                    mediaShareVideoType();
                }
            }

        }catch (Exception e){
            Toast.makeText(this, "In Chat ACTIVITY"+e.getMessage()+"\n"+e.getCause(), Toast.LENGTH_SHORT).show();
        }
    }
    private void mediaShareImageType(){
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        DeviceWidth=metrics.widthPixels;
        mWonderFont = Typeface.createFromAsset(getAssets(), "beyond_wonderland.ttf");
        mPropertiesBSFragment = new PropertiesBSFragment();
        mEmojiBSFragment = new EmojiBSFragment();
        mStickerBSFragment = new StickerBSFragment();
        mStickerBSFragment.setStickerListener(this);
        mEmojiBSFragment.setEmojiListener(this);
        mPropertiesBSFragment.setPropertiesChangeListener(this);
        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Bitmap photo = BitmapFactory.decodeFile(imageUrls.get(currentImagePosition));
        mPhotoEditorView.getSource().setImageBitmap(photo);
        mOptionsImages=new ImageOptiionsAdapter(this,this);
        mRvFilters.setLayoutManager(llmFilters);
        mRvFilters.setAdapter(mOptionsImages);
        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true) .build();
        UpdateAdapterSelected("image");
        if (imageUrls.size()<=1){
            mSelectedImages.setVisibility(View.GONE);
        }
        mPhotoEditor.setOnPhotoEditorListener(this);
    }
    private void UpdateAdapterSelected(String type) {
        mSelectedAdapter=new SelectedImageAdapter(this,imageUrls,this,type);
        LinearLayoutManager slcdLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mSelectedImages.setLayoutManager(slcdLayoutManager);
        mSelectedImages.setAdapter(mSelectedAdapter);
    }
    private void initViews() {

        mShowFilterButton=(Button)findViewById(R.id.show_firter_button);
        mPlayButtonVideo=findViewById(R.id.layout_back_for_play_video_button_home);
        mSelectedImages=findViewById(R.id.rvSelectedPhotoView);
        mPhotoEditorView = findViewById(R.id.photoEditorView);
        mRvFilters = findViewById(R.id.rvFilterView);
        mRootView = findViewById(R.id.rootView);
    }
    @Override
    public void onEditTextChangeListener(final View rootView, String text, int colorCode) {
        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show(this, text, colorCode);
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode) {
                mPhotoEditor.editText(rootView, inputText, colorCode);
            }
        });
    }
    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }
    @Override
    public void onRemoveViewListener(int numberOfAddedViews) {
        Log.d(TAG, "onRemoveViewListener() called with: numberOfAddedViews = [" + numberOfAddedViews + "]");
    }
    @Override
    public void onStartViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }
    @Override
    public void onStopViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }
    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.show_firter_button){
            showFilter(true);
        }
      /*  switch (view.getId()) {

         case R.id.imgUndo:
                mPhotoEditor.undo();
                break;

            case R.id.imgRedo:
                mPhotoEditor.redo();
                break;


      case R.id.imgSave:
                saveImage();
                break;

            case R.id.imgClose:
                onBackPressed();
                break;


   case R.id.imgCamera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.imgGallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
                break;

        }*/

    }
    @SuppressLint("MissingPermission")
    private void saveImage(final int position) {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            String IMAGE_MESSAGE_CASH="/MoodsApp/Media/Cashe";
            /*final String finalPath=Environment.getExternalStorageDirectory()
                    + IMAGE_MESSAGE_CASH
                    + System.currentTimeMillis() + ".png";*/
            final File file =new File(new CompressingImageApplyChange(getApplicationContext()).getFilename(IMAGE_MESSAGE_CASH));
            try {
                //file.createNewFile();
                mPhotoEditor.saveAsFile(file.getAbsolutePath(), new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                       String newFinalPath= new CompressingImageApplyChange(getApplicationContext()).compressImage(imagePath);
                        imageUrls.set(currentImagePosition,newFinalPath);
                        mSelectedAdapter.notifyDataSetChanged();
                        file.delete();
                        UpdateAdapterSelected(mediaType);
                        mAppChangesDialog.dismiss();
                        currentImagePosition=position;
                        mPhotoEditor.clearAllViews();
                        Bitmap bitmap=BitmapFactory.decodeFile(imageUrls.get(position));
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
                     }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        showSnackbar("Failed to save Image" +exception.getMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                hideLoading();
                showSnackbar(e.getMessage());
            }
        }else{
            mAppChangesDialog.dismiss();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    mPhotoEditor.clearAllViews();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mPhotoEditorView.getSource().setImageBitmap(photo);
                    break;
                case PICK_REQUEST:
                    try {
                        mPhotoEditor.clearAllViews();
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setBrushColor(colorCode);
        //mTxtCurrentTool.setText(R.string.label_brush);
    }
    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setOpacity(opacity);
        //mTxtCurrentTool.setText(R.string.label_brush);
    }
    @Override
    public void onBrushSizeChanged(int brushSize) {
        mPhotoEditor.setBrushSize(brushSize);
        //mTxtCurrentTool.setText(R.string.label_brush);
    }
    @Override
    public void onEmojiClick(String emojiUnicode) {
        mPhotoEditor.addEmoji(emojiUnicode);
        //mTxtCurrentTool.setText(R.string.label_emoji);

    }
    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
        //mTxtCurrentTool.setText(R.string.label_sticker);
    }
    @Override
    public void isPermissionGranted(boolean isGranted, String permission) {
        if (isGranted) {
            //saveImage();
        }
    }
    private void ApplyChanges(int position){
        try {
            mAppChangesDialog=new Apply_change_dialog(this);
            mAppChangesDialog.setCancelable(false);
            mAppChangesDialog.show();
            saveImage(position);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want to exit without saving image ?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // saveImage();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();

    }
    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }
    @Override
    public void onToolSelected(ToolType toolType) {
        switch (toolType) {
            case BRUSH:
                mPhotoEditor.setBrushDrawingMode(true);
                //mTxtCurrentTool.setText(R.string.label_brush);
                mPropertiesBSFragment.show(getSupportFragmentManager(), mPropertiesBSFragment.getTag());
                break;
            case TEXT:
                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
                textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
                    @Override
                    public void onDone(String inputText, int colorCode) {
                        mPhotoEditor.addText(inputText, colorCode);
                       // mTxtCurrentTool.setText(R.string.label_text);
                    }
                });
                break;
            case ERASER:
                mPhotoEditor.brushEraser();
                //mTxtCurrentTool.setText(R.string.label_eraser);
                break;
            case FILTER:
              //  mTxtCurrentTool.setText(R.string.label_filter);
                showFilter(true);
                break;
            case EMOJI:
                mEmojiBSFragment.show(getSupportFragmentManager(), mEmojiBSFragment.getTag());
                break;
            case STICKER:
                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
                break;
        }
    }
    void showFilter(boolean isVisible) {
        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mConstraintFilterView);

        if (isVisible) {
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
        } else {
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.END);
        }

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);

        mConstraintSet.applyTo(mRootView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.edit_photo_optios, menu);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "In On Option Menu"+"\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (!mediaType.equals("image")){
            menu.findItem(R.id.id_brush_edit_photo).setEnabled(false);
            menu.findItem(R.id.id_emojicon_edit_photo).setEnabled(false);
            menu.findItem(R.id.id_redo_edit_photo).setEnabled(false);
            menu.findItem(R.id.id_text_edit_photo).setEnabled(false);
            menu.findItem(R.id.id_undo_edit_photo).setEnabled(false);
            menu.findItem(R.id.id_stickers_edit_photo).setEnabled(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id== R.id.id_brush_edit_photo){
            mPhotoEditor.setBrushDrawingMode(true);
            mPropertiesBSFragment.show(getSupportFragmentManager(), mPropertiesBSFragment.getTag());
        }else if (id== R.id.id_text_edit_photo)
        {
            TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
            textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
                @Override
                public void onDone(String inputText, int colorCode) {
                    mPhotoEditor.addText(inputText, colorCode);
                }
            });
        }else if (id== R.id.id_redo_edit_photo){
            mPhotoEditor.redo();
        }else if (id== R.id.id_undo_edit_photo){
            mPhotoEditor.undo();
        }else if (id== R.id.id_emojicon_edit_photo){
            mEmojiBSFragment.show(getSupportFragmentManager(), mEmojiBSFragment.getTag());
        }else if(id== R.id.id_stickers_edit_photo){
            mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
            super.onBackPressed();
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
    @Override
    public void onSelectedClicked(int position) {

        if (mediaType.equals("image")){
            if (!mPhotoEditor.isCacheEmpty()){
                ApplyChanges(position);
            }else
            {
                currentImagePosition=position;
                Bitmap photo = BitmapFactory.decodeFile(imageUrls.get(position));
                mPhotoEditorView.getSource().setImageBitmap(photo);
            }
        }else {
            Bitmap bitmap=getBitmapStringFromVideo(imageUrls.get(position));
            mPhotoEditorView.getSource().setImageBitmap(bitmap);
        }

    }
    public Bitmap cropToSquare(Bitmap srcBmp){
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
    private Bitmap getBitmapStringFromVideo(String VideoPath){
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(VideoPath,   MediaStore.Images.Thumbnails.MINI_KIND);
        Matrix matrix = new Matrix();
        return Bitmap.createBitmap(thumb, 0, 0,thumb.getWidth(), thumb.getHeight(), matrix, true);
    }
}

