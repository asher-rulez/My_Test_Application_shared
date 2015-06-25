package com.example.asher.mytestapplication;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Asher on 12.06.2015.
 */
public interface IChildFragmentDelegate {
    void setText(CharSequence sequence);
    void setMarkerPosition(LatLng latLng);
}


