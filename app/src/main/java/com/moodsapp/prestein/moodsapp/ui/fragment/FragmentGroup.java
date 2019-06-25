package com.moodsapp.prestein.moodsapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.GroupListDB;
import com.moodsapp.prestein.moodsapp.model.Group;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_group.AddNewGroup;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_group.Group_List_Adopter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGroup extends android.app.Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private View rootView;
    private RecyclerView mListGroup;
    public static Resources res;
    public static int Divice_with;
    private String mUserIdentifier;
    private SwipeRefreshLayout refresh;


    private RecyclerView recyclerListGroups;
    private ArrayList<Group> listGroup;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_EDIT = 2;
    public static final int CONTEXT_MENU_LEAVE = 3;
    public static final int REQUEST_EDIT_GROUP = 0;
    public static final String CONTEXT_MENU_KEY_INTENT_DATA_POS = "pos";
    private ProgressDialog mProgress;
    private FragmentActivity myContext;
    private Group_List_Adopter adapter;
    private android.app.Fragment fragment=this;

    public FragmentGroup() {
        //requere empty constractor
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fragment_group, container, false);
        mProgress=new ProgressDialog(getActivity());
        listGroup = GroupListDB.getInstance(getActivity()).getListGroups();
        recyclerListGroups = (RecyclerView) rootView.findViewById(R.id.fragment_group_all_group_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.Refresh_layout_group);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerListGroups.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new Group_List_Adopter(myContext, listGroup,getActivity());
        recyclerListGroups.setAdapter(adapter);
        //        //        //get Divice Width
        DisplayMetrics metrics=getActivity().getApplicationContext().getResources().getDisplayMetrics();
        Divice_with=metrics.widthPixels;
        //end
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.add_new_group_fab);
        //fab.setBackgroundColor(getResources().getColor(R.color.body_baground));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateGroup();
            }
        });
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_EDIT_GROUP && resultCode == Activity.RESULT_OK) {
            listGroup.clear();
            //ListGroupsAdapter.listFriend = null;
            GroupListDB.getInstance(getActivity()).dropDB();
           // getListGroup();
        }
    }

    private void CreateGroup() {
        Intent intent=new Intent(getActivity(), AddNewGroup.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
/*        listGroup.clear();
        GroupListDB.getInstance(getActivity()).dropDB();
       // adapter.notifyDataSetChanged();
  //      getListGroup();*/
    }


}
