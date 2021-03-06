package com.datatakehnn.activities.sync;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.activities.menu.MainMenuActivity;
import com.datatakehnn.activities.poste.PosteActivity;
import com.datatakehnn.controllers.SettingController;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.models.ciudad_empresa.Ciudad_Empresa;
import com.datatakehnn.models.ciudades_model.Ciudad;
import com.datatakehnn.models.configuracion_model.Setting;
import com.datatakehnn.models.departmentos_model.Departamento;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad;
import com.datatakehnn.models.empresa_model.Empresa;
import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.longitud_elemento_model.Longitud_Elemento;
import com.datatakehnn.models.material_model.Material;
import com.datatakehnn.models.nivel_tension_elemento_model.Nivel_Tension_Elemento;
import com.datatakehnn.models.reponse_generic.data_async.Response_Request_Data_Sync;
import com.datatakehnn.models.tipo_cable.Tipo_Cable;
import com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo;
import com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad;
import com.datatakehnn.models.tipo_perdida_model.Tipo_Perdida;
import com.datatakehnn.services.api_client.retrofit.ApiClient;
import com.datatakehnn.services.api_client.routes.Const;
import com.datatakehnn.services.api_services.data_async_services.DataSyncApiService;
import com.datatakehnn.services.api_services.data_async_services.IDataAsync;
import com.datatakehnn.services.aplication.DataTakeApp;
import com.datatakehnn.services.connection_internet.ConnectivityReceiver;
import com.datatakehnn.services.coords.CoordsService;
import com.datatakehnn.services.data_arrays.Detalle_Tipo_Cable_List;
import com.datatakehnn.services.data_arrays.Detalle_Tipo_Novedad_List;
import com.datatakehnn.services.data_arrays.Empresa_List;
import com.datatakehnn.services.data_arrays.Estado_List;
import com.datatakehnn.services.data_arrays.Longitud_Elemento_List;
import com.datatakehnn.services.data_arrays.Material_List;
import com.datatakehnn.services.data_arrays.Nivel_Tension_Elemento_List;
import com.datatakehnn.services.data_arrays.Tipo_Cable_List;
import com.datatakehnn.services.data_arrays.Tipo_Equipo_List;
import com.datatakehnn.services.data_arrays.Tipo_Novedad_List;
import com.datatakehnn.services.data_arrays.Tipo_Perdida_List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyncActivity extends AppCompatActivity implements IDataAsync, ConnectivityReceiver.ConnectivityReceiverListener {

    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivSync)
    ImageView ivSync;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.txtCargando)
    TextView txtCargando;

    //Variables Globales
    private boolean sync = false;

    // Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;
    SettingController settingController;

    public CoordsService coordsService;
    private static Context ourcontext;
    private static SyncActivity _instance;
    DataSyncApiService dataSyncApiService;


    //Item Menu
    Menu menuGlobal;

    //Navigation From

    boolean FROM_LOGIN = true;
    boolean FROM_MENU = false;
    String fecha = "";
    String hora = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();
        animationButton();
        loadInformation();
    }

    private void loadInformation() {
        //Obtener Fecha
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        fecha = dateFormat.format(date);

        //Obtener Hora
        Calendar cal = Calendar.getInstance();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        hora = timeFormat.format(cal.getTime());

        if (FROM_LOGIN) {
            //Ya se sincronizo la informacion en la fecha actual
            Setting settingByDate = settingController.getByDate(fecha);
            if (settingByDate != null) {
                Intent i = new Intent(this, MainMenuActivity.class);
                startActivity(i);
            } else {
                if (checkConnection()) {
                    showUIElements();
                    loadDataAsync(this);
                }
            }
        }///FROM_MENU
        else {
            if (checkConnection()) {
                showUIElements();
                loadDataAsync(this);
            }
        }
    }


    //region API SERIVICE
    public void loadDataAsync(IDataAsync idataAsync) {
        dataSyncApiService.loadDataAsync(idataAsync);
    }

    @Override
    public void processFinishGetDataAsync(Response_Request_Data_Sync response) {
        if (response.isSuccess()) {


            //Departamentos y Ciudades
            List<Departamento> departamentoList = response.getResult().getDepartamento();
            List<Ciudad> ciudadList = new ArrayList<>();
            for (Departamento departamento : departamentoList) {
                ciudadList.addAll(departamento.getCiudades());
            }

            //Obtener Listas Poste

            ///Informacion Estatica
            /*List<Detalle_Tipo_Novedad> detalle_tipo_novedadList = Detalle_Tipo_Novedad_List.getListDetalleTipoNovedad();
            List<Estado> estado_lists = Estado_List.getListEstado();
            List<Longitud_Elemento> longitud_elementos = Longitud_Elemento_List.getListLongitudElemento();
            List<Material> materiales = Material_List.getListMaterial();
            List<Nivel_Tension_Elemento> nivel_tension_elementos = Nivel_Tension_Elemento_List.getListNivelTension();
            List<Tipo_Novedad> tipo_novedades = Tipo_Novedad_List.getListTipoNovedad();


               ///Lista Cables
            List<Tipo_Cable> tipo_cables = Tipo_Cable_List.getListTipo_Cable();
            List<Detalle_Tipo_Cable> detalle_tipo_cables = Detalle_Tipo_Cable_List.getListDetalle_Tipo_Cable();

            //Empresas
            List<Empresa> empresaList = Empresa_List.getListEmpresa();
            //Tipo Equipos
            List<Tipo_Equipo> tipo_equipos = Tipo_Equipo_List.getListTipoEquipo();
            //Tipo Perdidas
            List<Tipo_Perdida> tipo_perdidas = Tipo_Perdida_List.getListTipoPerdida();
            */

            List<Detalle_Tipo_Novedad> detalle_tipo_novedadList = response.getResult().getDetalle_Tipo_Novedad();
            List<Estado> estado_lists = response.getResult().getEstados();
            List<Longitud_Elemento> longitud_elementos = response.getResult().getLongitud_Elementos();
            List<Material> materiales = response.getResult().getMateriales();
            List<Nivel_Tension_Elemento> nivel_tension_elementos = response.getResult().getNivel_Tension_Elementos();
            List<Tipo_Novedad> tipo_novedades = response.getResult().getTipo_Novedad();


            ///Lista Cables
            List<Tipo_Cable> tipo_cables = response.getResult().getTipo_Cable();
            List<Detalle_Tipo_Cable> detalle_tipo_cables = response.getResult().getDetalle_Tipo_Cable();

            //Empresas
            List<Empresa> empresaList = response.getResult().getEmpresa();
            //Tipo Equipos
            List<Tipo_Equipo> tipo_equipos = response.getResult().getTipo_Equipo();
            //Tipo Perdidas
            List<Tipo_Perdida> tipo_perdidas = response.getResult().getTipo_Perdidas();

            List<Ciudad_Empresa> ciudad_empresas = response.getResult().getCiudad_Empresas();

            sincronizacionGetInformacionController.deleteInformacionMaster();
            sincronizacionGetInformacionController.registerDataGetInformacion(estado_lists,
                    detalle_tipo_novedadList,
                    longitud_elementos,
                    materiales,
                    nivel_tension_elementos,
                    tipo_novedades,
                    tipo_cables,
                    detalle_tipo_cables,
                    empresaList,
                    tipo_equipos,
                    departamentoList,
                    ciudadList,
                    tipo_perdidas,
                    ciudad_empresas
            );


            showSnakBar(R.color.colorAccent, getString(R.string.message_information_sync));
            MenuItem item = menuGlobal.findItem(R.id.action_done);
            item.setVisible(true);
            sync = true;
            hideUIElements();
            ivSync.setImageResource(R.drawable.ic_botonsincronizado);


            //Configuracion
            Setting setting = settingController.getFirst();
            if (setting != null) {
                setting.setFecha(fecha);
                setting.setHora(hora);
                settingController.registerUpdate(setting);
            } else {
                Setting newSetting = new Setting();
                newSetting.setFecha(fecha);
                newSetting.setHora(hora);
                newSetting.setSetting_Id(1);
                newSetting.setAvailable_Wifi(true);
                newSetting.setAvailable_Mobile_Data(true);
                newSetting.setDescripcion_Storage_Phone("Almacenamiento Interno");
                newSetting.setSigla_Storage("Interno"); //Opciones son (Interno o Externo)
                newSetting.setRuta_Servicio(Const.URL_RouteBaseAddress);
                settingController.registerUpdate(newSetting);
            }

        } else {
            showSnakBar(R.color.colorAccent, response.getMessage());
            sync = false;
            hideUIElements();
        }
    }


    //endregion

    //region INSTANCE
    public SyncActivity() {
        _instance = this;
    }

    public static SyncActivity getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new SyncActivity();
        }
        return _instance;
    }
    //endregion

    //region SET INJECTION
    private void setupInjection() {
        this.dataSyncApiService = DataSyncApiService.getInstance(this);
        this.sincronizacionGetInformacionController = SincronizacionGetInformacionController.getInstance(this);
        this.settingController = SettingController.getInstance(this);

        FROM_LOGIN = getIntent().getExtras().getBoolean("FROM_LOGIN");
        FROM_MENU = getIntent().getExtras().getBoolean("FROM_MENU");

        this.coordsService = new CoordsService(this);
    }
    /*
    public void iniciarServicioUbicacion(Context context) {
        this.coordsService= new CoordsService(context);
    }*/

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Sincronizar Información");
    }

    //endregion

    //region MENU


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuGlobal = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_workshops, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sync, menuGlobal);
        MenuItem item = menuGlobal.findItem(R.id.action_done);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            if (sync == true) {
                Intent i = new Intent(this, MainMenuActivity.class);
                startActivity(i);
            } else {

                showSnakBar(R.color.colorAccent, getString(R.string.message_sincronize_information));
            }

        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region EVENTS
    @OnClick(R.id.ivSync)
    public void onViewClicked() {
        showUIElements();
        getInformationSync();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //endregion

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

    public void showUIElements() {
        progressBar.setVisibility(View.VISIBLE);
        txtCargando.setVisibility(View.VISIBLE);
        ivSync.setEnabled(false);

    }

    public void hideUIElements() {
        progressBar.setVisibility(View.GONE);
        txtCargando.setVisibility(View.GONE);
        ivSync.setEnabled(true);
    }


    // Sincronizar Informacion
    public void getInformationSync() {
        if (checkConnection()) {

            loadDataAsync(this);

        } else {
            //Verificar si existe informacion sincronizada(si no hay conexion a internet, se validad la sincronizacion de datos)
            Longitud_Elemento longitud_elemento = sincronizacionGetInformacionController.getFirstLongitudElemento();
            if (longitud_elemento != null) {
                if (longitud_elemento.getLongitud_Elemento_Id() > 0) {
                    showSnakBar(R.color.colorAccent, getString(R.string.message_information_sync));
                    MenuItem item = menuGlobal.findItem(R.id.action_done);
                    item.setVisible(true);
                    sync = true;
                    hideUIElements();
                } else {
                    showSnakBar(R.color.colorAccent, getString(R.string.message_not_connection));
                    hideUIElements();
                    sync = false;
                }
            } else {
                showSnakBar(R.color.colorAccent, getString(R.string.message_not_connection));
                hideUIElements();
                sync = false;
            }
        }
    }

    //Enviar a actividad del Poste
    private void sendActivityToElemto() {
        Intent i = new Intent(this, PosteActivity.class);
        startActivity(i);
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
            showSnakBar(R.color.colorAccent, getString(R.string.message_connection));
        } else {
            showSnakBar(R.color.colorAccent, getString(R.string.message_not_connection));
        }
    }


    //endregion

    private void animationButton() {
        ivSync.setScaleX(0);
        ivSync.setScaleY(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getBaseContext(),
                    android.R.interpolator.fast_out_slow_in);
            ivSync.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(1000)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ivSync.animate()
                                    .scaleY(1)
                                    .scaleX(1)
                                    .setInterpolator(interpolador)
                                    .setDuration(600)
                                    .start();

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
        }
    }
}
