package com.example.prasannakumara.mapsample;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    LocationManager locationManager;
    LocationListener locationListener;
    private Marker tempMarkerName = null;
    private Marker tempchangedMarker = null;
    private Polyline templine=null;
    private int startval=1;
    LatLng destloc;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<LatLng> locations;
        locations = new ArrayList();
        locations.add(new LatLng(17.40233,  78.54556));
        locations.add(new LatLng(17.40273,  78.54587));
        locations.add(new LatLng(17.40224,  78.54672));
        locations.add(new LatLng(17.40232,  78.54722));




        final SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        map = mapFragment.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.setMyLocationEnabled(true);


       /* locations.add(new LatLng(17.40192,  78.54632));
        locations.add(new LatLng(17.42578,  78.47236));
        locations.add(new LatLng(17.36182,  78.46784));
        locations.add(new LatLng(17.41325,  78.52827));
        locations.add(new LatLng(17.24176,  78.43722));*/


    /*    MapFragment fm = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        map = fm.getMap();
        map.setMyLocationEnabled(true);*/

      /*  MapFragment fm = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
//        final SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fm.getMapAsync(this);
        map = fm.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.setMyLocationEnabled(true);
*/

/*
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(51.50550, -0.07520)) // Sets the new camera position
                .zoom(17) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);
*/



               CameraUpdate center=
                CameraUpdateFactory.newLatLng(locations.get(0));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        map.moveCamera(center);
        map.animateCamera(zoom);



//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);



        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if(tempchangedMarker!=null)
                {
                    tempchangedMarker.remove();
                }
                if(templine!=null)
                {
                   templine.remove();
                }

                if (tempMarkerName != null)
                    tempMarkerName.remove();



                if(startval==1)
                {
                    destloc=locations.get(0);
                }

                else if(startval==2)
                {
                    destloc=locations.get(1);
                }
                else if(startval==3)
                {
                    destloc=locations.get(2);
                }
                else if(startval==4)
                {
                    destloc=locations.get(3);
                }
                else if(startval==5)
                {
                    destloc=locations.get(4);
                }


                    // TODO: 10/31/2016 need to update map...
                    MarkerOptions newMarker = (new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .title(String.valueOf(location.getLatitude())+","+ String.valueOf(location.getLongitude()))
                    );


                    double platval1 = Math.round( location.getLatitude() * 100000.0 ) / 100000.0;
                    double plongval1= Math.round( location.getLongitude() * 100000.0 ) / 100000.0;

//                double platval2 = Math.round( 17.42578 * 100000.0 ) / 100000.0;
//                double plongval2= Math.round( 78.47236 * 100000.0 ) / 100000.0;


                LatLng location1 = new LatLng(platval1, plongval1);
                LatLng location2 = locations.get(0);
                CalculationByDistance(destloc,location1);

                    Polyline line = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(platval1,plongval1), destloc)
                            .width(5)
                            .color(Color.RED));



                    float[] results = new float[1];
                    Location.distanceBetween(platval1, plongval1,
                            17.42578,  78.47236,
                            results);


                    templine=line;

                    MarkerOptions plinemarker= (new MarkerOptions()
                            .position(destloc)
                    );

                    Marker plinemarkername=map.addMarker(plinemarker);



                    Marker markerName = map.addMarker(newMarker);
                    tempMarkerName = markerName;

                    double e = Math.round( location.getLatitude() * 100000.0 ) / 100000.0;
                    double f= Math.round( location.getLongitude() * 100000.0 ) / 100000.0;
                    LatLng a = new LatLng(e,f);
                    if(locations.contains(a))
                    {
                        startval=startval+1;
                    }


                if (locations.contains(a)) {
//                    newMarker.icom
                    Toast.makeText(getApplicationContext(), "location", Toast.LENGTH_SHORT).show();
                    alertDialogBuilder.setTitle("+10 pts");
                    alertDialogBuilder.setPositiveButton("OK",null);
//                    alertDialogBuilder.show();
                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    dialog.show();
                    dialog.getWindow().setLayout(600, 400);

                    MarkerOptions newMarker1= (new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_icon))
                    );

                    Marker changedMarker=map.addMarker(newMarker1);

                    startval=startval+1;
                    tempMarkerName.remove();
                    tempchangedMarker = changedMarker;
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        locationManager.requestLocationUpdates("gps", 2000, 0, locationListener);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }






    }
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));


        Toast.makeText(getApplicationContext(),"Radius Value " + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec,Toast.LENGTH_SHORT).show();

        //log to print distance between two points
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final LinearInterpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[10] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getApplicationContext(), "Accepted/", Toast.LENGTH_SHORT).show();
                return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


//        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}