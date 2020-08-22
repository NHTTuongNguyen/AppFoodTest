package com.example.firebase.Activity;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.firebase.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    ImageView img;

    public MarkerInfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.dialog2, null);
        LatLng latLng = marker.getPosition();


        TextView tvName= view.findViewById(R.id.nametitle);
        TextView tvLat = (TextView) view.findViewById(R.id.tv_lat);
        TextView tvLng = (TextView) view.findViewById(R.id.tv_lng);
        tvName.setText(marker.getTitle());
        tvLat.setText(marker.getTitle());
        tvLng.setText(marker.getSnippet());

        img = view.findViewById(R.id.pic);




//        tvLat.setText("Latitude:" + latLng.latitude);
//        tvLng.setText("Longitude:"+ latLng.longitude);
        return view;

    }
}

