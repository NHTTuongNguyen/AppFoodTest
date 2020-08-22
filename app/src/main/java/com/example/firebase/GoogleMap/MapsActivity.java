package com.example.firebase.GoogleMap;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase.Activity.MarkerInfoWindowAdapter;
import com.example.firebase.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    String categoryId = "";
    Button bt;
    TextView tvtitle;
    ImageView img;
    LatLngBounds.Builder builder;
    CameraUpdate cu;
    private static ImageView imageView;


    private Marker myMarker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<Marker> markersList = new ArrayList<Marker>();

        final String title = "LandMard 81";
        String subTitle = "045455454";

        String title2 = "Nhà Thờ Đức Bà";
        String subTitle2 = "222222";

        String title3 = "Phố Đi Bộ";
        String subTitle3 = "332432423";


        String title4 = "Hồ Bơi";
        String subTitle4 = "554545";


        img = findViewById(R.drawable.nature);
        Marker land81 = mMap.addMarker(new MarkerOptions().position(new LatLng(
                10.7951612, 106.7195944))
                .snippet(subTitle)
                .title(title));
        Marker nhathoducba = mMap.addMarker(new MarkerOptions().position(new LatLng(
                10.779785, 106.699018))
                .snippet(subTitle2)
                .title(title2));
        Marker phodibo = mMap.addMarker(new MarkerOptions().position(new LatLng(
                10.774114, 106.703623))
                .snippet(subTitle3)
                .title(title3));
        Marker hoboi = mMap.addMarker(new MarkerOptions().position(new LatLng(
                10.797979, 106.714926))
                .snippet(subTitle4)
                .title(title4));

        mMap.setOnMarkerClickListener(this);


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Toast.makeText(MapsActivity.this, ""+marker.getTitle(), Toast.LENGTH_SHORT).show();
                String title = marker.getTitle();
//
//                Intent i = new Intent(MapsActivity.this,LocationDetail.class);
//                i.putExtra("STRING",title);
//                startActivity(i);

            }
        });
        MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                mMap.clear();
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                Marker marker = mMap.addMarker(markerOptions);
//                marker.showInfoWindow();
//            }
//        });



        /**Put all the markers into arraylist*/
        markersList.add(land81);
        markersList.add(nhathoducba);
        markersList.add(phodibo);
        markersList.add(hoboi);

        builder = new LatLngBounds.Builder();
        for (Marker m : markersList) {
            builder.include(m.getPosition());
        }
        /**initialize the padding for map boundary*/
        int padding = 50;
        /**create the bounds from latlngBuilder to set into map camera*/
        LatLngBounds bounds = builder.build();
        /**create the camera with bounds and padding to set into map*/
        cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(cu);
            }
        });



    }




    public boolean onMarkerClick(Marker marker) {
//        if (marker.equals(marker))
//        {
//
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
//            LayoutInflater inflater = this.getLayoutInflater();
//            View add_menu_layout = inflater.inflate(R.layout.dialog_add,null);
//            alertDialog.setView(add_menu_layout);
//            alertDialog.show();
//            bt = add_menu_layout.findViewById(R.id.bt_takealook);
//            bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(MapsActivity.this,LocationDetail.class);
//                    startActivity(i);
//                }
//            });
//        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(this, "xx", Toast.LENGTH_SHORT).show();
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "xin duoc quyen roi", Toast.LENGTH_SHORT).show();
            xulyQuyen();
        }
    }
    public void xulyQuyen()
    {
        mMap.setMyLocationEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {//bang M
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
            {
                xulyQuyen();
                Toast.makeText(this, "e", Toast.LENGTH_SHORT).show();
            } else
            {
                ActivityCompat.requestPermissions(MapsActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
                Toast.makeText(this, "d", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            xulyQuyen();
            Toast.makeText(this, "f", Toast.LENGTH_SHORT).show();
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }
}
