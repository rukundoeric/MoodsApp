/*
 * Copyright 2016 Hani Al Momani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.moodsapp.emojis_library.Helper.EmojiconEditText;
import com.moodsapp.emojis_library.Helper.EmojiconRecentsManager;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.data.Global_String.PermissionRequestCode;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType.ApkListActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageContact.SelectContactsToSend;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.ui.SelectDocumentToSend;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.adapter.BaseFragmentAdapter;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui.PhotoGalleryActivity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.ui.VideoGalleryActivity;
import com.moodsapp.prestein.moodsapp.util.DiviceActionUtils.ScreenManager;

import java.util.ArrayList;
public class MediaFilePopup extends PopupWindow {

    private int keyBoardHeight = 0;
    private Boolean pendingOpen = false;
    private Boolean isOpened = false;
    OnSoftKeyboardOpenCloseListener onSoftKeyboardOpenCloseListener;
    View rootView;
    Activity mContext;
    View view;
    public RelativeLayout mMediaViewBack;
    public GridView mMediaTypeView;
    public GridView mMediaDocumentViewTypeDocumentView;
    public LinearLayout mLinearLayoutMediaType;
    public RelativeLayout mLinearLayoutDocumentMediaType;
    private String User_id;
    private ArrayList<CharSequence> idFriend;
    private String status;
    private String name;
    private ListDocumentTypeAdapter[] listDocumentTypeAdapter;


    public MediaFilePopup(View rootView, Activity mContext, String user_id, ArrayList<CharSequence> idFriend, String status, String name){
        super(mContext);
        this.mContext = mContext;
        this.rootView = rootView;
        View customView = createCustomView();
        setContentView(customView);
        setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setSize(LayoutParams.MATCH_PARENT, 255);
        setBackgroundDrawable(null);
        this.User_id=user_id;
        this.idFriend=idFriend;
        this.status=status;
        this.name=name;

    }
    /**
     * Set the listener for the event of keyboard opening or closing.
     */
    public void setOnSoftKeyboardOpenCloseListener(OnSoftKeyboardOpenCloseListener listener){
        this.onSoftKeyboardOpenCloseListener = listener;
    }

    public void showAtBottom(){
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    public void showAtBottomPending(){
        if(isKeyBoardOpen())
            showAtBottom();
        else
            pendingOpen = true;
    }
    public Boolean isKeyBoardOpen(){
        return isOpened;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EmojiconRecentsManager
                .getInstance(mContext).saveRecents();
    }

    /**
     * Call this function to resize the emoji popup according to your soft keyboard size
     */
    public void setSizeForSoftKeyboard(){
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);

                int screenHeight = getUsableScreenHeight();
                int heightDifference = screenHeight
                        - (r.bottom - r.top);
                int resourceId = mContext.getResources()
                        .getIdentifier("status_bar_height",
                                "dimen", "android");
                if (resourceId > 0) {
                    heightDifference -= mContext.getResources()
                            .getDimensionPixelSize(resourceId);
                }
                if (heightDifference > 100) {
                    keyBoardHeight = heightDifference;
                    setSize(LayoutParams.MATCH_PARENT, keyBoardHeight);
                    if (isOpened == false) {
                        if (onSoftKeyboardOpenCloseListener != null)
                            onSoftKeyboardOpenCloseListener.onKeyboardOpen(keyBoardHeight);
                    }
                    isOpened = true;
                    if (pendingOpen) {
                        showAtBottom();
                        pendingOpen = false;
                    }
                } else {
                    isOpened = false;
                    if (onSoftKeyboardOpenCloseListener != null)
                        onSoftKeyboardOpenCloseListener.onKeyboardClose();
                }
            }
        });
    }

    private int getUsableScreenHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();

            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);

            return metrics.heightPixels;

        } else {
            return rootView.getRootView().getHeight();
        }
    }

    public void setSize(int width, int height){
        setWidth(width);
        setHeight(height);
    }

    private View createCustomView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(com.moodsapp.prestein.moodsapp.R.layout.send_media_file_content_view, null, false);
        mMediaTypeView = (GridView) view.findViewById(com.moodsapp.prestein.moodsapp.R.id.send_media_file_media_type_grid_view);
        mLinearLayoutMediaType=(LinearLayout)view.findViewById(com.moodsapp.prestein.moodsapp.R.id.all_media_view_layout);
        mMediaDocumentViewTypeDocumentView = (GridView) view.findViewById(com.moodsapp.prestein.moodsapp.R.id.send_media_document_files_media_type_grid_view);
        mLinearLayoutDocumentMediaType=(RelativeLayout)view.findViewById(com.moodsapp.prestein.moodsapp.R.id.document_files_media_view_layout);
        final ListAdapter[] listAdapter = new ListAdapter[1];
         listDocumentTypeAdapter = new ListDocumentTypeAdapter[1];
        mMediaTypeView.setNumColumns(3);
        mMediaTypeView.setAdapter(listAdapter[0] = new ListAdapter(mContext));
        mMediaTypeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(mContext, PermissionRequestCode.IO_PERMISSIONS)) {
                        ActivityCompat.requestPermissions(mContext, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                    } else {

                    MediaTypeGallery();
                    }
                }else if (position==1){

                }else if (position==2){
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(mContext, PermissionRequestCode.IO_PERMISSIONS)) {
                        ActivityCompat.requestPermissions(mContext, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                    } else {
                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(mContext, PermissionRequestCode.IO_PERMISSIONS)) {
                            ActivityCompat.requestPermissions(mContext, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                        } else {
                    MediaTypeDocument();
                        }
                    }
                }else if (position==3){
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(mContext, PermissionRequestCode.IO_PERMISSIONS)) {
                        ActivityCompat.requestPermissions(mContext, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                    } else {
                    MediaTypeVideo();
                    }
                }else if (position==4){
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(mContext, PermissionRequestCode.IO_PERMISSIONS)) {
                        ActivityCompat.requestPermissions(mContext, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
                    } else {
                        MediaTypeContact();
                    }
                }
            }
        });

        view.findViewById(R.id.hide_documents_files_media_type).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMediaTypeDocument();
            }
        });

        return view;
    }

    private void hideMediaTypeDocument() {
        Animation an = android.view.animation.AnimationUtils.loadAnimation(mContext.getBaseContext(), com.moodsapp.prestein.moodsapp.R.anim.abc_slide_out_bottom);
        mLinearLayoutDocumentMediaType.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLinearLayoutDocumentMediaType.setVisibility(View.GONE);
                Animation an = android.view.animation.AnimationUtils.loadAnimation(mContext.getBaseContext(), com.moodsapp.prestein.moodsapp.R.anim.abc_slide_in_top);
                mLinearLayoutMediaType.startAnimation(an);
                an.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mLinearLayoutMediaType.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void MediaTypeVideo(){
        ExtractedStrings.RecentActivity=this.getClass().getName();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(mContext.getApplicationContext(), PermissionRequestCode.IO_PERMISSIONS)) {
            ActivityCompat.requestPermissions(mContext, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
        } else {
            Intent intent = new Intent(mContext, VideoGalleryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("fromContext", mContext.getClass().getName());
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, name);
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, status);
            bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
            intent.putExtras(bundle);
            //  activity.customDialogMediaShow.dismiss();
            mContext.startActivity(intent);
            mContext.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
    }
    private void MediaTypeGallery() {
        ExtractedStrings.RecentActivity=this.getClass().getName();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&!PermissionRequestCode.hasPremissions(mContext, PermissionRequestCode.IO_PERMISSIONS)) {
            ActivityCompat.requestPermissions(mContext, PermissionRequestCode.IO_PERMISSIONS, PermissionRequestCode.IO_REQUEST);
        } else {
            Intent intent = new Intent(mContext, PhotoGalleryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("fromContext", mContext.getClass().getName());
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND, name);
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, status);
            bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
            intent.putExtras(bundle);
            //    activity.customDialogMediaShow.dismiss();
            mContext.startActivity(intent);
            mContext.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
    }
    private void MediaTypeApk(){
        Bundle bundle = new Bundle();
        bundle.putString(ExtractedStrings.INTENT_USER_ID, User_id);
        Intent intent=new Intent(mContext, ApkListActivity.class);
        ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
        idFriend.add(User_id);
        bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
        mContext.finish();
    }
    @SuppressLint("ClickableViewAccessibility")
    private void MediaTypeDocument() {
        Animation an = android.view.animation.AnimationUtils.loadAnimation(mContext.getBaseContext(), com.moodsapp.prestein.moodsapp.R.anim.abc_slide_out_top);
        mLinearLayoutMediaType.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLinearLayoutMediaType.setVisibility(View.GONE);
                mLinearLayoutDocumentMediaType.setVisibility(View.VISIBLE);
                Animation an = android.view.animation.AnimationUtils.loadAnimation(mContext.getBaseContext(), com.moodsapp.prestein.moodsapp.R.anim.abc_slide_in_bottom);
                mLinearLayoutDocumentMediaType.startAnimation(an);
                an.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mMediaDocumentViewTypeDocumentView.setNumColumns(3);
                        mMediaDocumentViewTypeDocumentView.setAdapter(listDocumentTypeAdapter[0] = new ListDocumentTypeAdapter(mContext));

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mMediaDocumentViewTypeDocumentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    dismiss();
                    Intent intent=new Intent(mContext, SelectDocumentToSend.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_DOCUMENT_FILE_TYPE, ExtractedStrings.DOCUMENT_FILE_TYPE_PDF);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_ID, User_id);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
                else if (position==1){
                    dismiss();
                    Intent intent=new Intent(mContext, SelectDocumentToSend.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_DOCUMENT_FILE_TYPE, ExtractedStrings.DOCUMENT_FILE_TYPE_WORD_DOC);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_ID, User_id);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
                else if (position==2){
                    dismiss();
                    Intent intent=new Intent(mContext, SelectDocumentToSend.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_DOCUMENT_FILE_TYPE, ExtractedStrings.DOCUMENT_FILE_TYPE_EXCEL);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_ID, User_id);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
                else if (position==3){
                    dismiss();
                    Intent intent=new Intent(mContext, SelectDocumentToSend.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_DOCUMENT_FILE_TYPE, ExtractedStrings.DOCUMENT_FILE_TYPE_PW_POINT);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_ID, User_id);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
                else if (position==4){
                    dismiss();
                    Intent intent=new Intent(mContext, SelectDocumentToSend.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_DOCUMENT_FILE_TYPE, ExtractedStrings.DOCUMENT_FILE_TYPE_APK);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_ID, User_id);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
                else if (position==5){
                    dismiss();
                    Intent intent=new Intent(mContext, SelectDocumentToSend.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExtractedStrings.INTENT_DOCUMENT_FILE_TYPE, ExtractedStrings.DOCUMENT_FILE_TYPE_ZIP);
                    bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_ID, User_id);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }
        });
        mMediaDocumentViewTypeDocumentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new GestureDetector.OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        hideMediaTypeDocument();
                        return false;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {

                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        hideMediaTypeDocument();
                        return false;
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {

                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        return false;
                    }
                };
              return false;
            }
        });
    }
    private void MediaTypeContact(){
        if (User_id.length()>3){
            Bundle bundle = new Bundle();
            bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, User_id);
            Intent intent=new Intent(mContext, SelectContactsToSend.class);
            ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
            idFriend.add(User_id);
            bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }else {
            Toast.makeText(mContext, "Userid is null", Toast.LENGTH_SHORT).show();

        }

    }

    private class ListAdapter extends BaseFragmentAdapter {
        private Activity mContext;
        private ArrayList<MediaTypeItem> mMediaList;
        private LayoutInflater layoutInflater;
        public ListAdapter(Activity context) {
            this.mContext = context;
            this.layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            MediaTypeItem mediaTypeItem=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_gallery,"Gallery");
            MediaTypeItem mediaTypeItem1=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_camera, "Camera");
            MediaTypeItem mediaTypeItem2=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_document,"Document");
            MediaTypeItem mediaTypeItem3=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_video,"Video");
            MediaTypeItem mediaTypeItem4=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_contact, "Contact");
            MediaTypeItem mediaTypeItem5=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.call_voice_audio_icon, "Audio");
            mMediaList=new ArrayList<>();
            mMediaList.add(mediaTypeItem);
            mMediaList.add(mediaTypeItem1);
            mMediaList.add(mediaTypeItem2);
            mMediaList.add(mediaTypeItem3);
            mMediaList.add(mediaTypeItem4);
            mMediaList.add(mediaTypeItem5);
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
            return mMediaList != null ? mMediaList.size() : 0;
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
                view = layoutInflater.inflate(com.moodsapp.prestein.moodsapp.R.layout.send_media_file_type_item, viewGroup,false);
                mHolder.icon = (ImageView) view.findViewById(com.moodsapp.prestein.moodsapp.R.id.send_media_file_media_type_icon);
                mHolder.name=(EmojiconTextView) view.findViewById(com.moodsapp.prestein.moodsapp.R.id.send_media_file_media_type_name);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                int itemWidth= ScreenManager.getItemWidth(3,3);
                params.width = ExtractedStrings.DeviceWidth/4;
                params.height = ExtractedStrings.DeviceWidth/4;
                view.setLayoutParams(params);
                //mHolder.imageView.setTag(i);

                view.setTag(mHolder);
            } else {
                mHolder = (ListAdapter.viewHolder) view.getTag();
            }
            mHolder.icon.setImageResource(mMediaList.get(i).getIcon());
            mHolder.name.setText(mMediaList.get(i).getName());

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



        class viewHolder {
            public ImageView icon;
            public EmojiconTextView name;
        }

    }
    private class ListDocumentTypeAdapter extends BaseFragmentAdapter {
        private Activity mContext;
        private ArrayList<MediaTypeItem> mMediaList;
        private LayoutInflater layoutInflater;
        public ListDocumentTypeAdapter(Activity context) {
            this.mContext = context;
            this.layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            MediaTypeItem mediaTypeItem=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_document_pdf,"PDF doc");
            MediaTypeItem mediaTypeItem1=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_document_word, "Word doc");
            MediaTypeItem mediaTypeItem2=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_document_excel,"Excel doc");
            MediaTypeItem mediaTypeItem3=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_document_power,"PPT doc");
            MediaTypeItem mediaTypeItem4=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_document_apk, "APK");
            MediaTypeItem mediaTypeItem5=new MediaTypeItem(com.moodsapp.prestein.moodsapp.R.drawable.attach_document_zip, "ZIP file");
            mMediaList=new ArrayList<>();
            mMediaList.add(mediaTypeItem);
            mMediaList.add(mediaTypeItem1);
            mMediaList.add(mediaTypeItem2);
            mMediaList.add(mediaTypeItem3);
            mMediaList.add(mediaTypeItem4);
            mMediaList.add(mediaTypeItem5);
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
            return mMediaList != null ? mMediaList.size() : 0;
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
                view = layoutInflater.inflate(com.moodsapp.prestein.moodsapp.R.layout.send_media_file_type_item, viewGroup,false);
                mHolder.icon = (ImageView) view.findViewById(com.moodsapp.prestein.moodsapp.R.id.send_media_file_media_type_icon);
                mHolder.name=(EmojiconTextView) view.findViewById(com.moodsapp.prestein.moodsapp.R.id.send_media_file_media_type_name);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                int itemWidth= ScreenManager.getItemWidth(3,3);
                params.width = ExtractedStrings.DeviceWidth/4;
                params.height = ExtractedStrings.DeviceWidth/4;
                view.setLayoutParams(params);
                //mHolder.imageView.setTag(i);

                view.setTag(mHolder);
            } else {
                mHolder = (viewHolder) view.getTag();
            }
            mHolder.icon.setImageResource(mMediaList.get(i).getIcon());
            mHolder.name.setText(mMediaList.get(i).getName());

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



        class viewHolder {
            public ImageView icon;
            public EmojiconTextView name;
        }

    }
    public interface OnSoftKeyboardOpenCloseListener{
        void onKeyboardOpen(int keyBoardHeight);
        void onKeyboardClose();
    }




    public static class MediaKeyBoardAction{
        private boolean useSystemEmoji = false;
        Activity context;

        FloatingActionButton floatingActionButton;
        EmojiconEditText mEditInputText;
        private String User_id;
        private ArrayList<CharSequence> idFriend;
        private String status;
        private String name;

        MediaFilePopup popup;
        private MediaFilePopup.KeyboardListener keyboardListener;

        public MediaKeyBoardAction(Activity context, FloatingActionButton floatingActionButton, View rootView, EmojiconEditText mEditInputText, String user_id, ArrayList<CharSequence> idFriend, String status, String name) {
            this.context = context;
            this.floatingActionButton = floatingActionButton;
            this.mEditInputText = mEditInputText;
            User_id = user_id;
            this.idFriend = idFriend;
            this.status = status;
            this.name = name;
            this.popup=new MediaFilePopup(rootView,context,user_id,idFriend,status,name);
        }



        public void showMediaTypes() {
           //Will automatically set size according to the soft keyboard size
            popup.setSizeForSoftKeyboard();

            //If the emoji popup is dismissed, change emojiButton to smiley icon
            popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {

                  // Key board dismissed


                }
            });

            //If the text keyboard closes, also dismiss the emoji popup
            popup.setOnSoftKeyboardOpenCloseListener(new MediaFilePopup
                    .OnSoftKeyboardOpenCloseListener() {

                @Override
                public void onKeyboardOpen(int keyBoardHeight) {
                    if (keyboardListener != null)
                        keyboardListener.onKeyboardOpen();
                }

                @Override
                public void onKeyboardClose() {
                    if (keyboardListener != null)
                        keyboardListener.onKeyboardClose();
                    if (popup.isShowing())
                        popup.dismiss();
                }
            });


            // To toggle between text keyboard and emoji keyboard keyboard(Popup)
            showForEditText();
        }

        private void showForEditText() {

            floatingActionButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
               /*     if (emojiconEditText == null)
                        emojiconEditText = emojiconEditTextList.get(0);
                 */   //If popup is not showing => emoji keyboard is not visible, we need to show it
                    if (!popup.isShowing()) {

                        //If keyboard is visible, simply show the emoji popup
                        if (popup.isKeyBoardOpen()) {
                            popup.showAtBottom();
                            popup.mLinearLayoutMediaType.setVisibility(View.VISIBLE);
                            Animation an = android.view.animation.AnimationUtils.loadAnimation(context.getBaseContext(), com.moodsapp.prestein.moodsapp.R.anim.abc_slide_in_top);
                            popup.mLinearLayoutMediaType.startAnimation(an);
                            an.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                          //  changeEmojiKeyboardIcon(emojiButton, KeyBoardIcon);
                        }

                        //else, open the text keyboard first and immediately after that show the
                        // emoji popup
                        else {
                            mEditInputText.setFocusableInTouchMode(true);
                            mEditInputText.requestFocus();
                            final InputMethodManager inputMethodManager = (InputMethodManager)
                                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(mEditInputText, InputMethodManager
                                    .SHOW_IMPLICIT);
                            popup.showAtBottomPending();
                            popup.mLinearLayoutMediaType.setVisibility(View.VISIBLE);
                            Animation an = android.view.animation.AnimationUtils.loadAnimation(context.getBaseContext(), com.moodsapp.prestein.moodsapp.R.anim.abc_slide_in_top);
                            popup.mLinearLayoutMediaType.startAnimation(an);
                            an.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                           // changeEmojiKeyboardIcon(emojiButton, KeyBoardIcon);
                        }
                    }

                    //If popup is showing, simply dismiss it to show the undelying text keyboard
                    else {
                       popup.mLinearLayoutDocumentMediaType.setVisibility(View.GONE);

                        Animation an = android.view.animation.AnimationUtils.loadAnimation(context.getBaseContext(), com.moodsapp.prestein.moodsapp.R.anim.abc_slide_out_top);
                        popup.mLinearLayoutMediaType.startAnimation(an);
                        an.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                popup.dismiss();
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });



                    }


                }
            });
        }
        public void setKeyboardListener(MediaFilePopup.KeyboardListener listener) {
            this.keyboardListener = listener;
        }
    }
    public interface KeyboardListener {
        void onKeyboardOpen();

        void onKeyboardClose();
    }

}
class MediaTypeItem{
    public int icon;
    private String name;

    public MediaTypeItem(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}