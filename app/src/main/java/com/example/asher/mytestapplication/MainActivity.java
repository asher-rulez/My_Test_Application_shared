package com.example.asher.mytestapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asher.mytestapplication.adapter.MainViewPagerAdapter;
import com.example.asher.mytestapplication.adapter.NavDrawerListAdapter;
import com.example.asher.mytestapplication.model.FooDoNetEvent;
import com.example.asher.mytestapplication.model.NavDrawerItem;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;


public class MainActivity
        extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMyLocationChangeListener,
        AdapterView.OnItemClickListener,
        IParentActivityDelegate,
        ViewPager.OnPageChangeListener {

    GoogleMap googleMap;
    double myLatitude, myLongtitude;
    ArrayList<Marker> myMarkers;
    LatLng southWest, northEast, average, myLocation;
    double maxDistance;
    MarkerPopupFragment popup;
    FrameLayout popupFrame;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private ArrayList<NavDrawerItem> itemsList;
    private NavDrawerListAdapter adapter;

    private ViewPager mainPager;
    private MainViewPagerAdapter mainPagerAdapter;
    private ArrayList<FooDoNetEvent> eventArrayList;

    private final int MAP_PAGE_INDEX = 0;
    private final int LIST_PAGE_INDEX = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        popupFrame = (FrameLayout)findViewById(R.id.popup_fragment);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.list_slidermenu);
        drawerList.setOnItemClickListener(this);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
/*
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        SupportMapFragment smf = new SupportMapFragment();
        smf.getMapAsync(this);
        ft.add(R.id.map_fragment, smf);
        ft.commit();
*/
        eventArrayList = InitNewListEvents();

        mainPager = (ViewPager)findViewById(R.id.main_Pager);
        mainPagerAdapter = new MainViewPagerAdapter(fm);
        mainPagerAdapter.SetMapFragment(this);
        mainPagerAdapter.SetListFragment(eventArrayList, this);
        mainPager.setAdapter(mainPagerAdapter);
        mainPager.addOnPageChangeListener(this);
    }

    private ArrayList<FooDoNetEvent> InitNewListEvents(){
        //BitmapDescriptor red_marker = BitmapDescriptorFactory.fromResource(R.drawable.red_marker_small);
        //BitmapDescriptor green_marker = BitmapDescriptorFactory.fromResource(R.drawable.green_marker_small);

        ArrayList<FooDoNetEvent> result = new ArrayList<FooDoNetEvent>();

        result.add(new FooDoNetEvent(R.drawable.green_marker_small, getResources().getString(R.string.marker_1_label),
                "Some free tomatoes", new LatLng(GetFloatResource(R.dimen.lat1), GetFloatResource(R.dimen.long1)),
                new Date(), AddDaysToDate(new Date(), 10), "address 1", 0));

        result.add(new FooDoNetEvent(R.drawable.red_marker_small, getResources().getString(R.string.marker_2_label),
                "Some fish", new LatLng(GetFloatResource(R.dimen.lat2), GetFloatResource(R.dimen.long2)),
                new Date(), AddDaysToDate(new Date(), 5), "address 2", 1));

        result.add(new FooDoNetEvent(R.drawable.red_marker_small, getResources().getString(R.string.marker_3_label),
                "Some sauce", new LatLng(GetFloatResource(R.dimen.lat3), GetFloatResource(R.dimen.long3)),
                new Date(), AddDaysToDate(new Date(), 5), "address 2", 1));

        return result;
    }

    private Date AddDaysToDate(Date startingDate, int numberOfDays){
        Calendar c = Calendar.getInstance();
        c.setTime(startingDate);
        c.add(Calendar.DATE, 5);
        return c.getTime();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (this.googleMap != null)
            Toast.makeText(this, "Map loaded!", Toast.LENGTH_SHORT);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMyLocationChangeListener(this);

        itemsList = new ArrayList<NavDrawerItem>();
        myMarkers = new ArrayList<Marker>();

        for(FooDoNetEvent event : eventArrayList){
            myMarkers.add(AddMarker((float)event.getPosition().latitude, (float)event.getPosition().longitude,
                    event.getTitle(), BitmapDescriptorFactory.fromResource(event.getIcon())));
            itemsList.add(new NavDrawerItem(event.getTitle(),
                    "("+ event.getPosition().latitude + ";" + event.getPosition().longitude + ")", event.getIcon()));
        }

/*
        myMarkers.add(AddMarker(GetFloatResource(R.dimen.lat1), GetFloatResource(R.dimen.long1),
                , green_marker));
        itemsList.add(new NavDrawerItem(getResources().getString(R.string.marker_1_label),
                "("+ GetFloatResource(R.dimen.lat1) + ";" + GetFloatResource(R.dimen.long1) + ")",
                R.drawable.green_marker_small));

        myMarkers.add(AddMarker(GetFloatResource(R.dimen.lat2), GetFloatResource(R.dimen.long2),
                getResources().getString(R.string.marker_2_label), red_marker));
        itemsList.add(new NavDrawerItem(getResources().getString(R.string.marker_2_label),
                "("+ GetFloatResource(R.dimen.lat2) + ";" + GetFloatResource(R.dimen.long2) + ")",
                R.drawable.red_marker_small));

        myMarkers.add(AddMarker(GetFloatResource(R.dimen.lat3), GetFloatResource(R.dimen.long3),
                getResources().getString(R.string.marker_3_label), red_marker));
        itemsList.add(new NavDrawerItem(getResources().getString(R.string.marker_3_label),
                "("+ GetFloatResource(R.dimen.lat3) + ";" + GetFloatResource(R.dimen.long3) + ")",
                R.drawable.red_marker_small));
*/

        adapter = new NavDrawerListAdapter(getApplicationContext(), itemsList);
        drawerList.setAdapter(adapter);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.green_marker_small, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle("Title 1");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle("Title 2");
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);

        SetCamera();


    }

    private void FocusOnMarkerByID(int markerID) {
        if(googleMap == null) return;
        onMarkerClick(myMarkers.get(markerID));
    }

    private void SetCamera() {
        double latitude = 0;
        double longtitude = 0;
        int counter = 0;
        if (myLocation != null) {
            if (average != null && GetDistance(average, myLocation) < maxDistance)
                return;

            latitude += myLocation.latitude;
            longtitude += myLocation.longitude;
            counter++;
        }

        for (Marker m : myMarkers) {
            latitude += m.getPosition().latitude;
            longtitude += m.getPosition().longitude;
            counter++;
        }

        average = new LatLng(latitude / counter, longtitude / counter);

        maxDistance = 0;

        if (myLocation != null)
            maxDistance = GetDistance(average, myLocation);

        for (Marker m : myMarkers) {
            if (GetDistance(average, m.getPosition()) > maxDistance)
                maxDistance = GetDistance(average, m.getPosition());
        }

/*
        southWest = new LatLng(average.latitude - 1.5 * maxDistance, average.longitude - 1.5 * maxDistance);
        northEast = new LatLng(average.latitude + 1.5 * maxDistance, average.longitude + 1.5 * maxDistance);
*/

        //CameraPosition c = googleMap.getCameraPosition();
        Point size = GetScreenSize();
        int width = size.x;
        int height = size.y;

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(GetBoundsByCenterLatLng(average), width, height, 0);// new LatLngBounds(southWest, northEast)
        googleMap.animateCamera(cu);
    }

    private Point GetScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private double GetDistance(LatLng pos1, LatLng pos2) {
        return Math.sqrt(Math.pow(pos1.latitude - pos2.latitude, 2) + Math.pow(pos1.longitude - pos2.longitude, 2));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (Marker m : myMarkers)
            m.hideInfoWindow();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), googleMap.getCameraPosition().zoom));
        marker.showInfoWindow();

/*
        try
        {
            String url = "waze://?ll=" + marker.getPosition().latitude + "," + marker.getPosition().longitude;
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
            startActivity( intent );
        }
        catch ( ActivityNotFoundException ex  )
        {
            Intent intent =
                    new Intent( Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze") );
            startActivity(intent);
        }
*/
        ClosePopup();
        if (popup == null) {

            popup = new MarkerPopupFragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            MarkerPopupFragment popup = new MarkerPopupFragment();
            popup.setScreenSize(GetScreenSize());
            popup.setText(marker.getTitle());
            popup.setMarkerPosition(marker.getPosition());
            Log.i("myTag", "new fragment and position:" + marker.getPosition().toString());
            ft.setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
            ft.add(R.id.popup_fragment, popup);
            popupFrame.requestLayout();
            ft.addToBackStack(null);
            ft.commit();
        }

        popup.setText(marker.getTitle());
        popup.setMarkerPosition(marker.getPosition());
        Log.i("myTag", marker.getPosition().toString());


        return true;
    }

    private void ClosePopup()
    {
        Log.i("myTag", "popup not null: " + (popup != null ? "true" : "false"));
        if (popup == null) return;
        Log.i("myTag", "popup.isAdded(): " + (popup.isAdded() ? "true" : "false"));
        Log.i("myTag", "!popup.isDetached(): " + (!popup.isDetached() ? "true":"false"));
        Log.i("myTag", "!popup.isRemoving(: " + (!popup.isRemoving() ? "true":"false"));
        if(popup != null && !popup.isAdded() && !popup.isDetached() && !popup.isRemoving()) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            Log.i("myTag", "fragmentManager not null: " + (fm == null ? "false" : "true"));
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            Log.i("myTag", "transaction not null: " + (ft == null ? "false":"true"));
            Log.i("myTag", "popup not null: " + (popup == null ? "false":"true"));
            //ft.hide(popup);
            fm.popBackStackImmediate();
            ft.remove(popup);
            ft.commit();

            popup = null;
            Log.i("myTag", "close done!");

        }
    }

    private LatLngBounds GetBoundsByCenterLatLng(LatLng center) {
        return new LatLngBounds
                (new LatLng(center.latitude - maxDistance * 1.5, center.longitude - maxDistance * 1.5),
                        new LatLng(center.latitude + maxDistance * 1.5, center.longitude + maxDistance * 1.5));


    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location == null)
            return;

/*
        if(myLocation == null) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.blue_dot_circle);
            myLocation = AddMarker(((float)location.getLatitude()), (float)location.getLongitude(), getString(R.string.my_location), icon);
        }
*/
        myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if(GetDistance(myLocation, new LatLng(location.getLatitude(), location.getLongitude()) )<= maxDistance)
            return;
        if (myLocation == new LatLng(location.getLatitude(), location.getLongitude()))
            return;
        SetCamera();
    }

    private Marker AddMarker(float latitude, float longtitude, String title, BitmapDescriptor icon) {
        MarkerOptions newMarker = new MarkerOptions().position(new LatLng(latitude, longtitude)).title(title).draggable(false);
        if (icon != null)
            newMarker.icon(icon);
        return googleMap.addMarker(newMarker);
    }

    private float GetFloatResource(int identifier) {
        TypedValue outValue = new TypedValue();
        this.getResources().getValue(identifier, outValue, true);
        return outValue.getFloat();
    }

    @Override
    public void activate() {
        Toast.makeText(this, "activated", Toast.LENGTH_LONG);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(drawerLayout != null)
            drawerLayout.closeDrawer(Gravity.LEFT);

        if(mainPager != null)
            mainPager.setCurrentItem(MAP_PAGE_INDEX, true);

        FocusOnMarkerByID(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ClosePopup();
    }

    @Override
    public void onPageSelected(int position) {
        ClosePopup();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        ClosePopup();
    }
}
