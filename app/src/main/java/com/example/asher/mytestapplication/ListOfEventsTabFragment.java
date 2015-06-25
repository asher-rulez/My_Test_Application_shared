package com.example.asher.mytestapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asher.mytestapplication.adapter.ListOfEventsAdapter;
import com.example.asher.mytestapplication.model.FooDoNetEvent;

import java.util.ArrayList;

/**
 * Created by Asher on 20-Jun-15.
 */
public class ListOfEventsTabFragment extends Fragment {

    ArrayList<FooDoNetEvent> itemsList;
    ListOfEventsAdapter adapter;
    Context context;
    ListView lv_events_list;

    public void SetListOfEvents(ArrayList<FooDoNetEvent> itemsList){
        this.itemsList = itemsList;
    }
    public void SetContext(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_of_foodonet_events, container, false);
        adapter = new ListOfEventsAdapter(itemsList, context);
        lv_events_list = (ListView)view.findViewById(R.id.lv_list_of_events);
        lv_events_list.setAdapter(adapter);
        return view;
    }

}
