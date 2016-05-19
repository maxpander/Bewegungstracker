package com.example.dagri.googlemapsapi;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();

        setContentView(R.layout.activity_map_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }


        double lat = 51.4477;
        double lon = 7.2711;
        LatLng hochschule = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hochschule));

        // https://developers.google.com/maps/documentation/android-api/shapes#polygone

        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptionsPolyline = new PolylineOptions()
                .add(new LatLng(-33.35, 152.0))
                .add(new LatLng(-33.45, 152.0)) // North of the previous point, but at the same longitude
                .add(new LatLng(-33.45, 152.2)) // Same latitude, and 30km to the west
                .add(new LatLng(-33.35, 152.2)) // Same longitude, and 16km to the south
                .color(Color.CYAN);

// Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptionsPolyline);

        // Instantiates a new Polygon object and adds points to define a rectangle
        PolygonOptions rectOptionsPolygon = new PolygonOptions()
                .add(new LatLng(-33.35, 153.0), new LatLng(-33.45, 153.0),
                        new LatLng(-33.45, 153.2),
                        new LatLng(-33.35, 153.2),
                        // DIE LETZTE KOORDINATE MUSS NICHT GLEICH DER ERSTEN SEIN
                        new LatLng(-33.35, 153.0));

// Get back the mutable Polygon
        Polygon polygon = mMap.addPolygon(rectOptionsPolygon);

        // Instantiates a new CircleOptions object and defines the center and radius
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(-34.4, 155.1))
                .radius(1000); // In meters

// Get back the mutable Circle
        Circle circle = mMap.addCircle(circleOptions);


        // KARTENTYPEN SETZTEN:
        // mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        // KOMPASS EINSCHALTEN
        mMap.getUiSettings().setCompassEnabled(true);
        // ZOOM KONTROLLEN EINSCHALTEN
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // INDOOR LEVEL LEISTE EINSCHALTEN
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        // MAP TOOLBAR EINSCHALTEN
        mMap.getUiSettings().setMapToolbarEnabled(true);
        // MEINE POSITION BUTTON EINSCHALTEN
        // https://developers.google.com/maps/documentation/android-api/location#laufzeitberechtigungen_anfordern
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        double radius = 0.02;


        for (double a = 0; a < 2 * Math.PI; a = a + 0.1) {
            double actLon = lon + Math.cos(a) * radius;
            double actLat = lat + Math.sin(a) * radius;

            mMap.addMarker(new MarkerOptions()
                    .position(
                            new LatLng(actLat, actLon)

                    )
                    .icon(BitmapDescriptorFactory.defaultMarker((float) Math.random() * 255))
                    .title("Sinnloser Punkt in der Landschaft!")
                    .snippet("Dies ist zusaetzlicher nutzloser Text unter dem Titel!")
                    .alpha((float) Math.random())
                    .draggable(true)
            );
        }

        // TODO : UEBERLAGERUNGEN

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // An unresolvable error has occurred and a connection to Google APIs
        // could not be established. Display an error message, or handle
        // the failure silently
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

}
