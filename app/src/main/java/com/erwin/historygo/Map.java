package com.erwin.historygo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.erwin.historygo.api.PlaceModel;
import com.erwin.historygo.api.PlaceRepository;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


//    https://github.com/codepath/android_guides/wiki/Retrieving-Location-with-LocationServices-API



public class Map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    public GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastLocation;
    private GoogleApiClient mGoogleApiClient;
    public ArrayList<PlaceModel> displayList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        displayList = new ArrayList<PlaceModel>();
        new task().execute();
    }


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    public void onPolygonClick(Polygon polygon) {

    }


    private void setUpMap(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {

                                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f));


                            }
                        }
                    });
        } else {

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;



        mMap.setMinZoomPreference(11f);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        setUpMap();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Your location has been updated", Toast.LENGTH_SHORT).show();
        setUpMap();
        return false;
    }


    public class task extends android.os.AsyncTask<String, String, String> {


        public String value;
        public String result;
        private PlaceRepository places;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Map.this, "Fetching places' data from server...", Toast.LENGTH_SHORT).show();

        }


        @Override
        protected String doInBackground(String... strings) {


            String urlBase = getResources().getString(R.string.app_server);
            String urlStr = urlBase + "/places/all";
            places = new PlaceRepository();

            try {
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                this.value = br.readLine();

                result = "";
                JSONArray array = new JSONArray(value);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject placeObj = array.getJSONObject(i);

                    int id = placeObj.getInt("id");
                    String placeName = placeObj.getString("name");
                    int points = placeObj.getInt("points");
                    String description = placeObj.getString("description");
                    double rating = placeObj.getDouble("rating");
                    int year = placeObj.getInt("year");
                    double latitude = placeObj.getDouble("gps_N");
                    double length = placeObj.getDouble(("gps_E"));
                 //   String country = placeObj.getString("country");

                    places.addPlace(new PlaceModel(id, placeName, points, rating, description, year, latitude, length));
                }
            } catch (Exception ex) {
            }

            return value;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);


            for (PlaceModel place : places.getPlaces()){
                   LatLng locationOfPlace = new LatLng(place.getLatitude(), place.getLength());
                    String nameOfPlace = place.getName();
                    String descriptionOfPlace = place.getDescription();

                mMap.addMarker(new MarkerOptions().position(locationOfPlace)
                        .title(nameOfPlace)
                        .snippet(descriptionOfPlace));


            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                            for (PlaceModel place : places.getPlaces()){
                                if (place.getName().equals(marker.getTitle())){

                                    Intent myIntent = new Intent(Map.this, PlaceActivity.class);
                                    String placeName = place.getName();
                                    String placeDescription = place.getDescription();
                                    String placePoints = Integer.toString(place.getPoints());
                                    //  String placeRating = Double.toString(place.getRating());
                                    String placeYear = Integer.toString(place.getYear());
                                    myIntent.putExtra("placeName", placeName);
                                    myIntent.putExtra("placeDescription", placeDescription);
                                    myIntent.putExtra("placePoints", placePoints);
                                    //  myIntent.putExtra("placeRating",placeRating);
                                    myIntent.putExtra("placeYear", placeYear);
                                    startActivity(myIntent);
                                }
                            }


                    return false;
                }
            });

        }
    }
}
