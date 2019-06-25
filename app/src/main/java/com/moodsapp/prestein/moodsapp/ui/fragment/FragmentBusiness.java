package com.moodsapp.prestein.moodsapp.ui.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.connection.ConnectionDetector;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Database.MyProfileDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.Data_Storage_Path;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.model.Friend;
import com.moodsapp.prestein.moodsapp.model.MyProfile;
import com.moodsapp.prestein.moodsapp.model.Profile;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home.Home_Activity;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_gallery.adapter.BaseFragmentAdapter;
import com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step.FinishRegisterActivity;
import com.moodsapp.prestein.moodsapp.util.ColorUtils.ColorCreation;
import com.moodsapp.prestein.moodsapp.util.ImageUtils.CompressingImage5kb;
import com.moodsapp.prestein.moodsapp.util.InputOutputStream.getFileName;
import com.moodsapp.prestein.moodsapp.util.PopupMessages.ToastMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBusiness extends android.app.Fragment{
    private View rootView;
    private FragmentActivity myContext;
    private RecyclerView mListContacts;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fragment_business, container, false);
        mListContacts=(RecyclerView)rootView.findViewById(R.id.fragment_contacts_information);

        return rootView;
    }
}

