package com.datatakehnn.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.services.coords.CoordsService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CoordsActivity extends AppCompatActivity implements OnMapReadyCallback {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //Mapa
    Elemento elemento;
    //Location
    Location location;
    CoordsService servicioUbicacion;

    @BindView(R.id.ivCoordPoste)
    ImageView ivCoordPoste;

    @BindView(R.id.textCoordenada)
    TextView textCoordenada;



    Unbinder unbinder;

    double latitud;
    double longitud;


    //Maps
    private GoogleMap mMap;
    LocationManager mlocManager;
    Marker marcador;
    Circle circle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coords);
        ButterKnife.bind(this);

        elemento= getIntent().getExtras().getParcelable("Elemento");
        setToolbarInjection();
        setupInjection();

        initMap();

        /*
        if (savedInstanceState == null) {
            PlaceholderFragment newFragment = new PlaceholderFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, newFragment)
                    .commit();
        }*/
    }

    private void setupInjection() {

        textCoordenada.setText(elemento.getLatitud()+","+elemento.getLongitud());
        //Llama la instancia del servicio
        this.servicioUbicacion = new CoordsService(this);
        servicioUbicacion.setView(findViewById(R.id.textCoordenada));
        //Guarda en un location la ubicación
        try{
            location = servicioUbicacion.getUbicacion();
        }catch (Exception ex){
        }
    }

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getSupportActionBar() != null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Ubicacion Poste");
    }


    //region MAPA
    /*------------------------------------------------------------------------------------------------------------*/

    public void initMap() {
        //MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        ///map.getMapAsync(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try{
            mMap = googleMap;
            // Add a marker in Sydney and move the camera
            LatLng positionInitial = new LatLng(-4.570868, -74.29733299999998);
            /// mMap.addMarker(new MarkerOptions().position(positionInitial).title("Ecuador"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionInitial,7));
            //Configuracion de InfoWindow
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    LinearLayout info = new LinearLayout(getApplicationContext());
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(getApplicationContext());
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(getApplicationContext());
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);
                    return info;
                }
            });

            ////miUbication();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            } else {
                this.setLocation(location);
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled (true);
            }
        }catch (Exception ex){
        }
    }


    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            updateLocation(loc);
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    /// mensaje2.setText("Mi direccion es: \n"
                    //      + DirCalle.getAddressLine(0));
                    Toast.makeText(getApplicationContext(),"Direccion GPS es:"+ DirCalle.getAddressLine(0),Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //Actualizar Localizacion
    private void updateLocation(Location location) {
        if (location != null) {
            addMarker(location.getLatitude(), location.getLongitude());
        }
    }


    private void addMarker(double lat, double lng) {
        //Get Zoom last(Obtener zoom anterior)
        float zoom= mMap.getCameraPosition().zoom;
        /// Toast.makeText(getApplicationContext(),"Zoom: "+String.valueOf(zoom),Toast.LENGTH_SHORT).show();
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate myposition = CameraUpdateFactory.newLatLngZoom(coordenadas, zoom);
        if(marcador != null) marcador.remove();
        //Define color del marker
        BitmapDescriptor bitmapMarker= BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

        String Descripcion_Taller = "Codigo Poste: " + elemento.getCodigo_Apoyo();
        marcador = drawMarker(coordenadas,"Ubicacion",Descripcion_Taller,bitmapMarker);
        if (circle != null) circle.remove();
        circle = drawCircle(coordenadas);

        CameraUpdate myNewPosition = CameraUpdateFactory.newLatLngZoom(coordenadas, 13);
        mMap.animateCamera(myNewPosition);
    }

    //Dibujar Mapa
    private Circle drawCircle(LatLng latLng) {
        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();
        // Specifying the center of the circle
        circleOptions.center(latLng);
        // Radius of the circle
        circleOptions.radius(4000);
        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);
        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);
        // Border width of the circle
        circleOptions.strokeWidth(2);
        // Adding the circle to the GoogleMap
        return mMap.addCircle(circleOptions);
    }

    //Dibujar Marker
    private Marker drawMarker(LatLng latLng, String title,String snippet, BitmapDescriptor bitmapDescriptor ) {
        // Instantiating CircleOptions to draw a circle around the marker
        MarkerOptions markerOption = new MarkerOptions();
        // Specifying the psition of the marker
        markerOption.position(latLng);

        // Title marker
        markerOption.title(title);
        // Fill color of the marker
        markerOption.icon(bitmapDescriptor);
        //Snip
        markerOption.snippet(snippet);

        // Adding the circle to the GoogleMap
        return mMap.addMarker(markerOption);
    }


    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
