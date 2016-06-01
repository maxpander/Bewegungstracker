package com.example.dagri.googlemapsapi;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.util.Date;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // GOOGLE MAP OBJEKT ERSTELLEN, DAS IM WEITEREN VERLAUF VERWENDET WIRD
    private GoogleMap mMap;

    // GOOGLE API CLIENT OBJEKT ERSTELLEN, DAS IM WEITEREN VERLAUF VERWENDET WIRD
    GoogleApiClient mGoogleApiClient;

    // The current location.
    Location mCurrentLocation;

    // The last update time as String.
    String mLastUpdateTime;

    // The object to store the location data inside.
    LocationStore locStore = new LocationStore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // GOOGLE API CLIENT BAUEN
        // TODO : WOFUER WIRD DER GOOGLE API CLIENT BENOETIGT?
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

        // EINE POSITION IN LATITUDE UND LONGITUDE SPEICHERN
        double lat = 51.4477;
        double lon = 7.2711;

        // LATLNG-ONJEKT ERSTELLEN UND HOCHSCHULE BENENNEN
        LatLng hochschule = new LatLng(lat, lon);

        // KAMERA (ANSICHT) AUF DIE STARTPOSITION AUSRICHTEN
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hochschule));

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

        // KREIS ERSTELLEN
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(51.77, 7.33)) // KOORDINATE DES ZENTRUMS
                .radius(500) // RADIUS IN METERN
                .fillColor(Color.BLUE); // FUELLFARBE

        // KREIS ZUR KARTE HINZUFUEGEN
        Circle circle = mMap.addCircle(circleOptions);

        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptionsPolyline = new PolylineOptions()
                .add(new LatLng(51.4566, 7.2626))
                .add(new LatLng(51.4511, 7.2626)) // North of the previous point, but at the same longitude
                //.add(new LatLng(-33.45, 152.2)) // Same latitude, and 30km to the west
                //.add(new LatLng(-33.35, 152.2)) // Same longitude, and 16km to the south
                .color(Color.BLACK);
        // Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptionsPolyline);

        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptionsPolyline1 = new PolylineOptions()
                .add(new LatLng(51.4566, 7.2778))
                .add(new LatLng(51.4511, 7.2778)) // North of the previous point, but at the same longitude
                //.add(new LatLng(-33.45, 152.2)) // Same latitude, and 30km to the west
                //.add(new LatLng(-33.35, 152.2)) // Same longitude, and 16km to the south
                .color(Color.BLACK);
        Polyline polyline1 = mMap.addPolyline(rectOptionsPolyline1);

        PolylineOptions rectOptionsPolyline2 = new PolylineOptions()
                .add(new LatLng(51.4461, 7.2606))
                .add(new LatLng(51.4425, 7.2634))
                .add(new LatLng(51.4415, 7.2677))
                .add(new LatLng(51.4412, 7.2732))
                .add(new LatLng(51.4415, 7.2775))
                .add(new LatLng(51.4427, 7.2804))
                .add(new LatLng(51.4471, 7.2817))

                .color(Color.BLACK);
        Polyline polyline2 = mMap.addPolyline(rectOptionsPolyline2);




        // Instantiates a new Polygon object and adds points to define a rectangle
        PolygonOptions rectOptionsPolygon = new PolygonOptions()
                .add(new LatLng(-33.35, 153.0), new LatLng(-33.45, 153.0),
                        new LatLng(-33.45, 153.2),
                        new LatLng(-33.35, 153.2),
                        // DIE LETZTE KOORDINATE MUSS NICHT GLEICH DER ERSTEN SEIN
                        new LatLng(-33.35, 153.0));

        // Get back the mutable Polygon
        Polygon polygon = mMap.addPolygon(rectOptionsPolygon);





        // MARKER KREISFOERMIG ERSTELLEN
        // FARBEN UND TRANSPARENZ WERDEN ZUFAELLIG ERSTELLEN
        double radius = 0.02;

        for (double a = 0; a < 2 * Math.PI; a = a + 0.1) {
            double actLon = lon + Math.cos(a) * radius;
            double actLat = lat + Math.sin(a) * radius;

            mMap.addMarker(new MarkerOptions()
                    // POSITION SETZTEN
                    .position(new LatLng(actLat, actLon))
                    // FARBE SETZTEN
                    .icon(BitmapDescriptorFactory.defaultMarker((float) Math.random() * 255))
                    // TITEL BEIM ANKLICKEN SETZTEN
                    .title("Nutzloser Punkt in der Landschaft!")
                    // TITEL UNTERSCHRIFT SETZTEN
                    .snippet("Dies ist zusaetzlicher nutzloser Text unter dem Titel!")
                    // ALPHA WERT DES PUNKTES SETZTEN
                    .alpha((float) Math.random())
                    // PUNKT VERSCHIEBBAR SCHALTEN
                    .draggable(true)
                    // MARKER FLAT
                    // .flat(true)
            );
        }

        // UEBERLAGERUNG ERSTELLEN
        // KOORDINATE ERSTELLEN
        LatLngBounds ueberlagerungKoordinate = new LatLngBounds(
                new LatLng(51.8, 7.5),       // SUED WESTLICHE ECKE
                new LatLng(52.0, 8.8));      // NORD OESTLICHE ECKE
        // GROUND OVERLAY OBJEKT ERSTELLEN
        GroundOverlayOptions ueberlagerung = new GroundOverlayOptions()
                // BILD-DATEI VERLINKEN
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.androidlogo))
                // POSITION ZUWEISEN
                .positionFromBounds(ueberlagerungKoordinate);
        // UEBERLAGERUNG ZUR KARTE HINZUFUEGEN
        mMap.addGroundOverlay(ueberlagerung);

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // An unresolvable error has occurred and a connection to Google APIs
        // could not be established. Display an error message, or handle
        // the failure silently
    }

    protected void onStart() {
        // VERBINDEN MIT DEM GOOGLE API CLIENT
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        // TRENNEN VON DEM GOOGLE API CLIENT
        // SPART STROM
        mGoogleApiClient.disconnect();
        this.locStore.toXML();
        super.onStop();
    }

    // ERFASST DIE POSITION ETWA ALLE 100 M
    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        // ALLE 10 SEKUNDEN
        mLocationRequest.setInterval(10000);
        // MAXIMAL ALLE 5 SEKUNDEN
        mLocationRequest.setFastestInterval(5000);
        // HOHE PRAEZISION
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getDateTimeInstance().format(new Date());
        this.locStore.addLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), mLastUpdateTime);
    }
}

