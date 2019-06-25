package com.moodsapp.prestein.moodsapp.ui.activities.login_sinup_step;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.resourceshelper.CountryCode.Country;
import com.moodsapp.prestein.moodsapp.resourceshelper.CountryCode.CountryListAdapter;

import java.util.ArrayList;
import java.util.List;

public class Search_activity_for_country_list extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Country country;
    private ArrayList<Country> countryList;
    private RecyclerView recyclerView;
    private CountryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_country_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        countryList=new ArrayList<>();
        country=new Country();
        country.AddCountryInList(countryList);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_countryDialog);
        adapter=new CountryListAdapter(countryList, this, Search_activity_for_country_list.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_menu_toobar,menu);
        MenuItem menuItem= menu.findItem(R.id.id_search_menu_icon);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        List<Country> newList=new ArrayList<>();
        for(Country country:countryList)
        {
            String cName=country.getCountryName().toLowerCase();
            if(cName.contains(newText))
            {
                newList.add(country);
            }
        }
        adapter.setFilter(newList);
        return false;
    }
}
