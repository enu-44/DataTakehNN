package com.datatakehnn.services.coords;

import android.app.Activity;
import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;


public class CoordsService extends Service implements LocationListener {

    private final Context context;

    public static double latitud;
    public static double longitud;

    public static Location location;
    boolean gpsActivo;
    LocationManager locationManager;

    TextView texto;

    private static CoordsService instanceCoordsService;

    public CoordsService() {
        super();
        this.context = this.getApplicationContext();
    }

    public CoordsService(Context context) {
        super();
        this.context = context;
        getLocation((Activity) context);
    }

    public Location getUbicacion() {
        return location;
    }

    public void setView(View v) {

        texto = (TextView) v;
        texto.setText("Coordenadas: " + latitud + "," + longitud);
    }

    public void getLocation(Activity activity) {
        try {
            locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
            gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {

        }

        if (gpsActivo) {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                return;
            }

            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER
                    , 1000
                    , 1
                    , this);  //cada minuto y 1 segundo se actualiza el GPS

            //Traer ultima ubicacion conocida
            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            latitud = location.getLatitude();
            longitud = location.getLongitude();

        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {
        setLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
       // texto.setText("Coordenadas: " + loc.getLatitude() + "," + loc.getLongitude());
    }

}
