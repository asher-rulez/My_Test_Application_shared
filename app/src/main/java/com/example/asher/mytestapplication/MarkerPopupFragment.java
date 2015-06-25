package com.example.asher.mytestapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Asher on 12.06.2015.
 */
public class MarkerPopupFragment extends android.support.v4.app.Fragment implements IChildFragmentDelegate, View.OnClickListener {

    Point screen_size;
    Button wazeButton;
    LinearLayout popupBody;
    Activity parent;
    TextView tvtext;
    LatLng currentMarkerPosition;
    CharSequence title;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parent = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.marker_popup, container, false);
        Log.i("myTag", "view " + (v == null));
        popupBody = (LinearLayout)v.findViewById(R.id.rl_popup_border);
        tvtext = (TextView)v.findViewById(R.id.tv_popup_text);
        tvtext.setText("Title: " + title + ". Position: " + currentMarkerPosition.toString());
        wazeButton = (Button)v.findViewById(R.id.btn_popup_wazeit);

/*
        WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
*/


        wazeButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        boolean b =  (tvtext == null);
        Log.i("myTag", "tvtext: " + (b?"null":"not null"));
        Log.i("myTag", "currentMarkerPosition: " + (currentMarkerPosition==null?"null":currentMarkerPosition.toString()));
/*
        if(tvtext != null) {
            tvtext.setText("");
            tvtext.setText("Clicked" + currentMarkerPosition.toString());
        }
*/

        if(currentMarkerPosition != null) {

            try {
                String url = "waze://?ll=" + currentMarkerPosition.latitude + "," + currentMarkerPosition.longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                parent.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Intent intent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                parent.startActivity(intent);
            }
        }
        //((IParentActivityDelegate)parent).activate();
    }

    public void setScreenSize(Point p)
    {
        screen_size = p;
    }

    @Override
    public void setText(CharSequence sequence) {
        title = sequence;
    }

    @Override
    public void setMarkerPosition(LatLng latLng) {
        currentMarkerPosition = latLng;
        Log.i("myTag", "setMarkerPosition - " + latLng.toString());
/*
        if(tvtext == null)
            tvtext = (TextView)getView().findViewById(R.id.tv_popup_text);
        tvtext.setText("setMarkerPosition - " + latLng.toString());
*/

    }



    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("myTag", "onDetach popup");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("myTag", "onStop popup");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("myTag", "onPause popup");
    }
}
