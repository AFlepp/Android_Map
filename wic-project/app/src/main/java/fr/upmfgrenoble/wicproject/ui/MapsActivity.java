package fr.upmfgrenoble.wicproject.ui;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import fr.upmfgrenoble.wicproject.R;
import fr.upmfgrenoble.wicproject.model.GPX;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng grenoble = new LatLng(45.184375, 5.727720);
        LatLng pointT = new LatLng(45.189, 5.704);
        LatLng pointT2 = new LatLng(45.188, 5.725);
        LatLng pointT3 = new LatLng(45.191, 5.733);
        mMap.addMarker(new MarkerOptions().position(grenoble).title("Marker in Grenoble"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(grenoble));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(pointT, pointT2, pointT3)
                .width(5)
                .color(Color.RED));



            try {
                InputStream is = getAssets().open("simple.gpx");
                GPX hey = GPX.parse(is);
                for (GPX.Track track : hey) {
                    for (GPX.TrackSegment segment : track) {
                        PolylineOptions polylineOptions = new PolylineOptions().width(5).color(Color.rgb((int)(255*Math.random()),(int)(255*Math.random()), (int)(255*Math.random())));
                        for (GPX.TrackPoint trackPoint : segment) {
                            polylineOptions.add(trackPoint.position);
                        }
                        mMap.addPolyline(polylineOptions);
                    }
                }
            } catch (IOException e) {
                ;
            }
    }

}
