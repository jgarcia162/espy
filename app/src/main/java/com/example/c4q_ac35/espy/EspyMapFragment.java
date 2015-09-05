package com.example.c4q_ac35.espy;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.example.c4q_ac35.espy.foursquare.FourSquareAPI;
import com.example.c4q_ac35.espy.foursquare.ResponseAPI;
import com.example.c4q_ac35.espy.foursquare.Venue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * Created by c4q-ac35 on 8/12/15.
 */

public class EspyMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleMap googleMap;
    Location myLocation;
    GoogleApiClient mapGoogleApiClient;
    List<Geofence> mGeofenceList;
    FourSquareAPI servicesFoursquare;
    float GEOFENCE_RADIUS_IN_METERS = 1000;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mapGoogleApiClient.connect();
//
//        mGeofenceList = new ArrayList<Geofence>();

        googleMap = getMap(); // loads map
        googleMap.setMyLocationEnabled(true); //finds current location

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();


        String provider = locationManager.getBestProvider(criteria, true);

        myLocation = locationManager.getLastKnownLocation(provider);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Choose type of map, normal, terrain, satellite, none

        double lat = 40.722695;
        double lon = -73.996545;

        //Adding a null check
        if(myLocation==null){
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000*60*10);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        } else {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11)); // choose default zoom of map

//        ResponseAPI responseAPI = new ResponseAPI();
//        List<Venue> venueList = responseAPI.getResponse().getVenues();
//
//        for(int i =0;i<venueList.size();i++) {
//            com.example.c4q_ac35.espy.foursquare.Location location = venueList.get(i).getLocation();
//            final double venueLat = location.getLat();
//            final double venueLong = location.getLng();
//
//            Marker locationMarker = googleMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(venueLat, venueLong))
//                    .title(venueList.get(i).getName()));
//            locationMarker.setSnippet("Phone Number: " + venueList.get(i).getContact().getPhone());
//            locationMarker.isInfoWindowShown();
//            Log.i(venueList.get(i).getContact().getPhone(), venueList.get(i).getName());
//        }
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lon))
                .title("Rice To Riches"));
        marker.setSnippet("Phone Number: (212) 274-0008");
        marker.isInfoWindowShown();


        // Calls location service within context

        //Loop for setting markers and geofences for each location in list

    }

    @Override
    public void onConnected(Bundle bundle) {


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



}
