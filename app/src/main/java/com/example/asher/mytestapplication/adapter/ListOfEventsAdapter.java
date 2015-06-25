package com.example.asher.mytestapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asher.mytestapplication.R;
import com.example.asher.mytestapplication.model.FooDoNetEvent;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Asher on 20-Jun-15.
 */
public class ListOfEventsAdapter extends BaseAdapter {

    ArrayList<FooDoNetEvent> eventCollection;
    Context context;

    public ListOfEventsAdapter(ArrayList<FooDoNetEvent> eventCollection, Context context){
        this.eventCollection = eventCollection;
        this.context = context;
    }

    @Override
    public int getCount() {
        return eventCollection.size();
    }

    @Override
    public Object getItem(int position) {
        return eventCollection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.foodonet_event_list_item, null);
        }

        ImageView icon = (ImageView)convertView.findViewById(R.id.iv_event_list_item_icon);
        TextView title = (TextView)convertView.findViewById(R.id.tv_event_list_item_title);
        TextView description = (TextView)convertView.findViewById(R.id.tv_event_list_item_description);
        LinearLayout background = (LinearLayout)convertView.findViewById(R.id.ll_event_item);

        icon.setImageResource(eventCollection.get(position).getIcon());
        title.setText(eventCollection.get(position).getTitle());
        description.setText(eventCollection.get(position).getDescription());
        background.setBackgroundColor(eventCollection.get(position).getEndDate().after(new Date())
                ? context.getResources().getColor(R.color.event_list_item_background_onair)
                : context.getResources().getColor(R.color.event_list_item_background_expired));

        return convertView;
    }
}
