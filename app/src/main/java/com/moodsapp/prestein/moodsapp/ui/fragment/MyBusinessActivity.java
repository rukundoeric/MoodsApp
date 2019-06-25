package com.moodsapp.prestein.moodsapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Database.FriendDB;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_contact.ListFriendsAdapter;
import com.moodsapp.prestein.moodsapp.util.ColorUtils.ColorCreation;

import java.util.ArrayList;


public class MyBusinessActivity extends android.app.Fragment {

    private View rootView;
    private RecyclerView mBusinessRecycleView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseBusiness;
    private FragmentActivity myContext;
    private ListFriendsAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_my_business, container, false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(myContext,LinearLayoutManager.VERTICAL,false);
       // mBusinessRecycleView=(RecyclerView)rootView.findViewById(R.id.my_business_RecycleView);
       // mBusinessRecycleView.setLayoutManager(linearLayoutManager);
       // adapter = new ListFriendsAdapter(myContext, FriendDB.getInstance(myContext).getListFriendArray(),colors());
        //mBusinessRecycleView.setAdapter(adapter);
        return rootView;
    }

    private ArrayList<Integer> colors(){
        ArrayList<Integer> arrayList=new ArrayList<>();
        int a=0;
        for(int i=0;i<FriendDB.getInstance(myContext).getListFriendArray().size();i++){
            if (a == 6) {
                a=0;
            }
            int color=new ColorCreation().getColorList(getActivity()).get(a);
            arrayList.add(color);
            a++;
        }
        return arrayList;
    }

}
