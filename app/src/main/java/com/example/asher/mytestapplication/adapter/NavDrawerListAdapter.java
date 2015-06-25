package com.example.asher.mytestapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asher.mytestapplication.R;
import com.example.asher.mytestapplication.model.NavDrawerItem;

import java.util.ArrayList;

/**
 * Created by Asher on 16-Jun-15.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> items;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> items){
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView sub_title = (TextView)convertView.findViewById(R.id.sub_title);

        icon.setImageResource(items.get(position).get_icon());
        title.setText(items.get(position).get_title());
        sub_title.setText(items.get(position).get_sub_title());

        return convertView;
    }
}
