package com.datatakehnn.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.activities.equipos_elemento.EquipoActivity;
import com.datatakehnn.activities.fotos.FotosActivity;
import com.datatakehnn.activities.perdida.PerdidaActivity;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.services.aplication.DataTakeApp;
import com.datatakehnn.services.connection_internet.ConnectivityReceiver;
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

public class CoordsActivity extends AppCompatActivity implements OnMapReadyCallback, ConnectivityReceiver.ConnectivityReceiverListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //Mapa
    Elemento elemento;


    @BindView(R.id.ivCoordPoste)
    ImageView ivCoordPoste;

    @BindView(R.id.textCoordenada)
    TextView textCoordenada;

    @BindView(R.id.txtDireccionAproximada)
    TextView txtDireccionAproximada;




    //Location
    Location location= new Location("dummyprovider");;
    CoordsService servicioUbicacion;
    double latitud;
    double longitud;


    //Maps
    private GoogleMap mMap;
    LocationManager mlocManager;
    Marker marcador;
    Circle circle;

    //Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;


    //Variables Globals
    String Nombre_Material;
    String Nombre_Estado;
    String Nivel_Tension;
    String Longitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coords);
        ButterKnife.bind(this);

        elemento= getIntent().getExtras().getParcelable("Elemento");
        setToolbarInjection();
        setupInjection();
        initMap();
        setupData();
    }

    private void setupData() {
        Nombre_Material=sincronizacionGetInformacionController.getMaterialById(elemento.getMaterial_Id()).getNombre();
        Nombre_Estado=sincronizacionGetInformacionController.getEstadoById(elemento.getEstado_Id()).getNombre();
        Nivel_Tension=sincronizacionGetInformacionController.getNivelTensionByNivel_Tension_Elemento_Id(elemento.getNivel_Tension_Elemento_Id()).getNombre();
        Longitud= String.valueOf(sincronizacionGetInformacionController.getLongitudByLongitud_Elemento_Id(elemento.Longitud_Elemento_Id).getValor());
    }


    //region METHODS
    //Mostrar Mensage Snackbar
    /*--------------------------------------------------------------------------------------------------------*/
    private void showSnakBar(int colorPrimary, String message) {
        int color = Color.WHITE;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        //textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_home_bar, 0, 0, 0);
        // textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin));
        textView.setTextColor(color);
        snackbar.show();

    }

    //endregion

    //region MENU

    /*MENU*/
    /*-------------------------------------------------------------------------------------------------------*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_coords, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.menu_cables:
                Intent i = new Intent(getApplicationContext(), CablesElementoActivity.class);
                i.putExtra("ACCION_ADD",false);
                i.putExtra("ACCION_UPDATE",true);
                i.putExtra("Elemento_Id", elemento.getElemento_Id());
                startActivity(i);
                break;
            case R.id.menu_equipos:
                Intent r = new Intent(getApplicationContext(), EquipoActivity.class);
                r.putExtra("ACCION_ADD",false);
                r.putExtra("ACCION_UPDATE",true);
                r.putExtra("Elemento_Id", elemento.getElemento_Id());
                startActivity(r);
                break;
            case R.id.menu_novedades:
                Intent t = new Intent(getApplicationContext(), FotosActivity.class);
                t.putExtra("ACCION_ADD",false);
                t.putExtra("ACCION_UPDATE",true);
                t.putExtra("Elemento_Id", elemento.getElemento_Id());
                startActivity(t);
                break;

            case R.id.menu_perdfidas:
                Intent b = new Intent(getApplicationContext(), PerdidaActivity.class);
                b.putExtra("ACCION_ADD",false);
                b.putExtra("ACCION_UPDATE",true);
                b.putExtra("Elemento_Id", elemento.getElemento_Id());
                startActivity(b);
                break;

            ///Metodo que permite no recargar la pagina al devolverse
            case android.R.id.home:
                // Obtener intent de la actividad padre
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // Comprobar si DetailActivity no se creó desde CourseActivity
                if (NavUtils.shouldUpRecreateTask(this, upIntent)
                        || this.isTaskRoot()) {

                    // Construir de nuevo la tarea para ligar ambas actividades
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        TaskStackBuilder.create(this)
                                .addNextIntentWithParentStack(upIntent)
                                .startActivities();
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Terminar con el método correspondiente para Android 5.x
                    this.finishAfterTransition();
                    return true;
                }

                //Para versiones anterios a 5.x
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    // Terminar con el método correspondiente para Android 5.x
                    onBackPressed();
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    //endregion



    //region SETUP INJECTION
    private void setupInjection() {
        textCoordenada.setText(elemento.getLatitud()+","+elemento.getLongitud());
        this.sincronizacionGetInformacionController=SincronizacionGetInformacionController.getInstance(this);
        //Guarda en un location la ubicación
        try{
            location.setLongitude(elemento.getLongitud());
            location.setLatitude(elemento.getLatitud());
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

    //endregion

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
            LatLng positionInitial = new LatLng(4.565473550710278, -74.058837890625);
            /// mMap.addMarker(new MarkerOptions().position(positionInitial).title("Ecuador"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionInitial,6));
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
            setAddress(loc);
        }
    }


    public void setAddress(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {

            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    /// mensaje2.setText("Mi direccion es: \n"
                    //      + DirCalle.getAddressLine(0));
                    //  Toast.makeText(getApplicationContext(),"Direccion GPS es:"+ DirCalle.getAddressLine(0),Toast.LENGTH_LONG).show();
                    txtDireccionAproximada.setText(DirCalle.getAddressLine(0));
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
        BitmapDescriptor bitmapMarker= BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);



        String Descripcion_Elemento = "Codigo Poste: " + elemento.getCodigo_Apoyo() +
                "\n\n" + "Direccion: " + elemento.getDireccion() +
                "\n\n" + "Material: " + Nombre_Material +
                "\n\n" + "Estado: " + Nombre_Estado +
                "\n\n" + "Retenidas: " + String.valueOf(elemento.getRetenidas()) +
                "\n\n" + "Nivel de Tension: " + Nivel_Tension +
                "\n\n" + "Altura: " + String.valueOf(elemento.getAltura_Disponible()) +
                "\n\n" + "Longitud: " + Longitud;

        marcador = drawMarker(coordenadas,"Ubicacion",Descripcion_Elemento,bitmapMarker);
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

    //endregion





    //region CHECK CONNECTION INTERNET
    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;
        //showSnack(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        DataTakeApp.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            setAddress(location);
            showSnakBar(R.color.orange,  getString(R.string.message_connection));
        } else {
            showSnakBar(R.color.orange,  getString(R.string.message_not_connection));
        }
    }

    //endregion
}
