package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageApkType;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MainChat.Chat_Activity;

import java.util.ArrayList;
import java.util.List;

public class ApkListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private GridLayoutManager recyclerViewLayoutManager;
    private AppsAdapter adapter;
    private String UserId;
    private ArrayList<CharSequence> idFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ApkListToolbar);
        setSupportActionBar(toolbar);
        try {
            Bundle bundle = getIntent().getExtras();
            UserId = bundle.getString(ExtractedStrings.INTENT_USER_ID);
            idFriend = bundle.getCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID);
            recyclerView = (RecyclerView) findViewById(R.id.apk_list_recycler_view);
            // Passing the column number 1 to show online one column in each row.
            recyclerViewLayoutManager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
            adapter = new AppsAdapter(this,getApplicationContext(), new ApkInfoExtractor(this).GetAllInstalledApkInfo(),UserId);
            recyclerView.setAdapter(adapter);
            toolbar.setTitle(String.valueOf(new ApkInfoExtractor(this).GetAllInstalledApkInfo().size()+" Application"));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.apkfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, UserId);
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, FriendDB.getInstance(getApplicationContext()).getInfoByIdUser(2,UserId,getApplicationContext()));
                bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND,FriendDB.getInstance(getApplicationContext()).getInfoByIdUser(1,UserId,getApplicationContext()));
                Intent intent=new Intent(getApplicationContext(), Chat_Activity.class);
                ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
                idFriend.add(UserId);
                bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    public void UpdateAdapter(ApkListActivity apkListActivity, Context context,List<String> list,String userId) {
        try {
            recyclerView = (RecyclerView) findViewById(R.id.apk_list_recycler_view);
            recyclerViewLayoutManager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
            adapter = new AppsAdapter(apkListActivity,context,list,userId);
            recyclerView.setAdapter(adapter);
        }catch (Exception r){
            Toast.makeText(apkListActivity, "In Update "+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND_ID, UserId);
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_STATUS, FriendDB.getInstance(getApplicationContext()).getInfoByIdUser(2,UserId,getApplicationContext()));
        bundle.putString(ExtractedStrings.INTENT_KEY_CHAT_FRIEND,FriendDB.getInstance(getApplicationContext()).getInfoByIdUser(1,UserId,getApplicationContext()));
        Intent intent=new Intent(getApplicationContext(), Chat_Activity.class);
        ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
        idFriend.add(UserId);
        bundle.putCharSequenceArrayList(ExtractedStrings.INTENT_KEY_CHAT_ID, idFriend);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}

/*
class ApkListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<PInfo> appn;
    Activity activity;
    public ApkListAdapter(Activity activity) {
        appn=getInstalledApps(true,activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.row_test, parent, false);
        return new ApkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        for(int i=0;i<appn.size();i++){
            ((ApkViewHolder) holder).appname.setText(appn.get(i).pname);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    private ArrayList<PInfo> getPackages(Activity activity) {
        ArrayList<PInfo> apps = getInstalledApps(false,activity); */
/* false = no system packages *//*

        final int max = apps.size();
        for (int i=0; i<max; i++) {
            apps.get(i).getAppName();
        }
        return apps;
    }

    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages, Activity activity) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();
        List<PackageInfo> packs = activity.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(activity.getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(activity.getPackageManager());
            res.add(newInfo);
        }
        return res;
    }
}

class ApkViewHolder extends RecyclerView.ViewHolder{
    TextView appname;
    public ApkViewHolder(View v) {
        super(v);
        appname = (TextView) v.findViewById(R.id.secondLine);
    }
}*/
