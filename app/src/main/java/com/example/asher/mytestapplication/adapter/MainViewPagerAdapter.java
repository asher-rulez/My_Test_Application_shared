package com.example.asher.mytestapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asher.mytestapplication.ListOfEventsTabFragment;
import com.example.asher.mytestapplication.model.FooDoNetEvent;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

/**
 * Created by Asher on 20.06.2015.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    SupportMapFragment mapFragment;
    ListOfEventsTabFragment eventsTabFragment;
    final int NUM_ITEMS = 2;

    public MainViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        eventsTabFragment = new ListOfEventsTabFragment();
    }

    public void SetMapFragment(OnMapReadyCallback mapReadyCallback){
        mapFragment = new SupportMapFragment();
        mapFragment.getMapAsync(mapReadyCallback);
    }

    public void SetListFragment(ArrayList<FooDoNetEvent> items, Context context){
        if(eventsTabFragment != null){
            eventsTabFragment.SetListOfEvents(items);
            eventsTabFragment.SetContext(context);
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mapFragment;
            case 1:
                return eventsTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "Map";
        if(position == 1)
            return "Events";
        return "";
    }
}
