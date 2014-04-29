package com.cwat.nades;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.cwat.ui.SherlockMapFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class SherlockMapActivity extends SherlockFragmentActivity {

    GoogleMap map;
    Location gLocation;
    LocationManager manager;
    Circle circle;
    ArrayList<Circle> enemyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        map = ((SherlockMapFragment) getSupportFragmentManager().findFragmentById((R.id.map))).getMap();
        populateMap();
    }

    public void populateMap() {

        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String provider = manager.getBestProvider(new Criteria(),true);
        gLocation = manager.getLastKnownLocation(provider);
        LocationListener listener = new LocationListener(){

            @Override
            public void onLocationChanged(Location location) {
                gLocation = location;


            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), "Provider Disabled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onProviderEnabled(String provider) {
                // DO nothing

            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // DO nothing

            }

        };
        manager.requestLocationUpdates(provider, 0, 0,listener);
        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(gLocation.getLatitude(), gLocation.getLongitude()));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);
        map.moveCamera(center);
        map.animateCamera(zoom);
        manager.removeUpdates(listener);
        map.setMyLocationEnabled(true);

        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(gLocation.getLatitude(), gLocation.getLongitude()))
                .fillColor(Color.GREEN)
                .strokeColor(Color.TRANSPARENT)
                .radius(100);
        circle = map.addCircle(circleOptions);

    }

    @Override
    public final boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }


}
