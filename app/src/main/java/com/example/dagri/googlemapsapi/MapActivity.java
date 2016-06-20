package com.example.dagri.googlemapsapi;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    // GOOGLE MAP OBJEKT ERSTELLEN, DAS IM WEITEREN VERLAUF VERWENDET WIRD
    private GoogleMap mMap;

    // The object to store the location data inside.
    LocationStore locStore = new LocationStore();

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Kick off the process of building a GoogleApiClient and requesting the LocationServices
        // API.
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // MAP OBJEKT ERSTELLEN
        mMap = googleMap;
        // BERECHTIGUNGEN UEBERPRUEFEN FUER DIE GENAUE POSITION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // WENN BERECHTIGUNG VORHANDEN, DANN DEN BUTTON EINSCHALTEN
            mMap.setMyLocationEnabled(true);
        } else {
            // TODO : NACHFRAGE AN DEN NUTZER STELLEN
        }
        // KOMPASS EINSCHALTEN
        mMap.getUiSettings().setCompassEnabled(true);
        // ZOOM KONTROLLEN EINSCHALTEN
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // INDOOR LEVEL LEISTE EINSCHALTEN
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        // MAP TOOLBAR EINSCHALTEN
        mMap.getUiSettings().setMapToolbarEnabled(true);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        this.toEmail();
        super.onStop();
    }

    public void toEmail(){
        String adresse = "david.griegel.1991@gmail.com.de";
        String adressarray[] = { adresse };
        String nachricht = "Hier sind die aufgezeichneten Informationen: Anzahl = "+ this.locStore.getLatLng().size() +  "\n";
        for(int a=0;a<this.locStore.getLatLng().size();a++){
            nachricht = nachricht + "lat = " + this.locStore.getLatLng().get(a).toString() +" lon= " +this.locStore.getLatLng().get(a).toString() + this.locStore.getTimes().get(a).toString()+"\n";
        }
        Intent emailversand = new Intent(Intent.ACTION_SEND);
        emailversand.putExtra(Intent.EXTRA_EMAIL, adressarray);
        emailversand.putExtra(Intent.EXTRA_SUBJECT, "LocationTracker");
        emailversand.setType("plain/text");
        emailversand.putExtra(Intent.EXTRA_TEXT, nachricht);
        startActivity(emailversand);
    }
}

