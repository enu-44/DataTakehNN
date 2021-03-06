package com.datatakehnn.activities.equipos_elemento;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.activities.equipos_elemento.adapter.AdapterEquipo;
import com.datatakehnn.activities.equipos_elemento.adapter.OnItemClickListenerEquipo;
import com.datatakehnn.activities.fotos.FotosActivity;
import com.datatakehnn.activities.sync.SyncActivity;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.EquipoController;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.ciudad_empresa.Ciudad_Empresa;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.reponse_generic.data_async.Response_Request_Data_Sync;
import com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.api_services.data_async_services.IDataAsync;
import com.datatakehnn.services.aplication.DataTakeApp;
import com.datatakehnn.services.connection_internet.ConnectivityReceiver;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquipoActivity extends AppCompatActivity implements IDataAsync, MainViewEquipo, OnItemClickListenerEquipo, SwipeRefreshLayout.OnRefreshListener, ConnectivityReceiver.ConnectivityReceiverListener {

    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.txtResults)
    TextView txtResults;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    //Spinner
    @BindView(R.id.spinnerOperador)
    MaterialBetterSpinner spinnerOperador;
    @BindView(R.id.spinnerTipo)
    MaterialBetterSpinner spinnerTipo;

    //Otros
    @BindView(R.id.textInputLayoutDescripcionOtro)
    TextInputLayout textInputLayoutDescripcionOtro;
    @BindView(R.id.edtDescripcionOtro)
    EditText edtDescripcionOtro;
    @BindView(R.id.edtCodigoEquipo)
    EditText edtCodigoEquipo;


    //Variables Gloabals
    private boolean Conectado_Red_Electrica = true;
    private boolean Medidor_Red = true;
    private long Empresa_Id;
    private long Ciudad_Empresa_Id;
    private long Ciudad_Id;

    private long Tipo_Equipo_Id;
    private String descripcion_register, codigo_equipo;

    public String Nombre_Tipo_Equipo;
    public String Nombre_Empresa;

    //Declaracion Arrays
    List<Ciudad_Empresa> empresaList = new ArrayList<>();
    List<Tipo_Equipo> tipo_equipos = new ArrayList<>();

    //Listas
    List<Equipo_Elemento> equipo_elementoList = new ArrayList<>();

    //Adapters
    ArrayAdapter<Ciudad_Empresa> empresaArrayAdapter;
    ArrayAdapter<Tipo_Equipo> tipo_equipoArrayAdapter;

    //Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;
    EquipoController equipoController;
    ElementoController elementoController;
    UsuarioController usuarioController;
    SyncActivity syncActivity;

    //Adapter
    AdapterEquipo adapter;

    //Accion
    boolean ACCION_ADD;
    boolean ACCION_UPDATE;

    //Variables
    long Elemento_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupInjection();
        setToolbarInjection();
        initAdapter();
        initRecyclerView();
        showProgresss();
        loadListSpinners();
        loadListEquipos();

    }

    //region SETUP INJECTION
    private void setupInjection() {
        //Actualizar o Eliminar
        ACCION_ADD = getIntent().getExtras().getBoolean("ACCION_ADD");
        ACCION_UPDATE = getIntent().getExtras().getBoolean("ACCION_UPDATE");
        Elemento_Id = getIntent().getExtras().getLong("Elemento_Id");
        this.sincronizacionGetInformacionController = SincronizacionGetInformacionController.getInstance(this);
        this.equipoController = EquipoController.getInstance(this);
        this.elementoController = ElementoController.getInstance(this);
        this.usuarioController = UsuarioController.getInstance(this);
        this.syncActivity = SyncActivity.getInstance(this);

    }

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (ACCION_UPDATE) {
            if (getSupportActionBar() != null)// Habilitar Up Button
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);//devolver
        }
        toolbar.setTitle(getString(R.string.title_equipos));
    }

    //endregion

    //region METHODS

    private void initAdapter() {
        if (adapter == null) {
            adapter = new AdapterEquipo(this, new ArrayList<Equipo_Elemento>(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void validarRegistrarEquipo() {
        //VALIDACION
        boolean cancel = false;
        View focusView = null;
        if (spinnerOperador.getText().toString().isEmpty()) {
            spinnerOperador.setError(getString(R.string.error_field_required));
            focusView = spinnerOperador;
            cancel = true;
        } else if (spinnerTipo.getText().toString().isEmpty()) {
            spinnerTipo.setError(getString(R.string.error_field_required));
            focusView = spinnerTipo;
            cancel = true;
        } else if (Nombre_Tipo_Equipo.equals("Otros") && edtDescripcionOtro.getText().toString().isEmpty()) {
            edtDescripcionOtro.setError(getString(R.string.error_field_required));
            focusView = edtDescripcionOtro;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            registerEquipos();
        }
    }

    private void registerEquipos() {

        if (Nombre_Tipo_Equipo.equals("Otros") && !edtDescripcionOtro.getText().toString().isEmpty()) {
            descripcion_register = edtDescripcionOtro.getText().toString();
        } else {
            descripcion_register = "";
        }

        if (!edtCodigoEquipo.getText().toString().isEmpty()) {
            codigo_equipo = edtCodigoEquipo.getText().toString();
        } else {
            codigo_equipo = "";
        }

        Equipo_Elemento equipo_elemento = new Equipo_Elemento(
                codigo_equipo,
                descripcion_register,
                1,
                Empresa_Id,
                Ciudad_Id,
                Ciudad_Empresa_Id,
                Conectado_Red_Electrica,
                Medidor_Red,
                0,
                "",
                Tipo_Equipo_Id,
                Elemento_Id,
                Nombre_Tipo_Equipo,
                Nombre_Empresa
        );

        equipoController.register(equipo_elemento);
        limpiarCampos();
        onMessageOk(R.color.colorAccent, "Equipo Registrado");
        loadListEquipos();
        hideKeyboard();
    }


    //Ocultar teclado
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }
    }

    private void loadListEquipos() {
        adapter.clear();
        equipo_elementoList.clear();
        equipo_elementoList = equipoController.getListEquipoElement(Elemento_Id);
        setContent(equipo_elementoList);
        resultsList(equipo_elementoList);
        hideProgress();
    }


    private void loadListSpinners() {


        Usuario userLogued = usuarioController.getLoggedUser();

        //Listas
        empresaList = sincronizacionGetInformacionController.getListEmpresasByCiudad(userLogued.getCiudad_Id(), true);
        tipo_equipos = sincronizacionGetInformacionController.getListTipoEquipo();

        //Spinner
        //Empresas Operadoras
         /*--------------------------------------------------------------------------------------------*/
        spinnerOperador.setAdapter(null);
        empresaArrayAdapter =
                new ArrayAdapter<Ciudad_Empresa>(this, android.R.layout.simple_spinner_dropdown_item, empresaList);
        spinnerOperador.setAdapter(empresaArrayAdapter);
        spinnerOperador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Empresa_Id = empresaList.get(position).getEmpresa_Id();
                Nombre_Empresa = empresaList.get(position).getNombre_Empresa();
                Ciudad_Empresa_Id = empresaList.get(position).getCiudad_Empresa_Id();
                Ciudad_Id = empresaList.get(position).getCiudad_Id();
            }
        });

        //Tipo Equipo
         /*--------------------------------------------------------------------------------------------*/
        spinnerTipo.setAdapter(null);
        tipo_equipoArrayAdapter =
                new ArrayAdapter<Tipo_Equipo>(this, android.R.layout.simple_spinner_dropdown_item, tipo_equipos);
        spinnerTipo.setAdapter(tipo_equipoArrayAdapter);
        spinnerTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Tipo_Equipo_Id = tipo_equipos.get(position).getTipo_Equipo_Id();
                Nombre_Tipo_Equipo = tipo_equipos.get(position).getNombre();
                if (Nombre_Tipo_Equipo.equals("Otros")) {
                    textInputLayoutDescripcionOtro.setVisibility(View.VISIBLE);
                } else {
                    textInputLayoutDescripcionOtro.setVisibility(View.GONE);
                }
            }
        });
    }


    private void limpiarCampos() {
        String title_tipo_cable = String.format(getString(R.string.title_tipo_equipo));
        spinnerTipo.setText("");
        spinnerTipo.setHint(title_tipo_cable);

        String title_empresa = String.format(getString(R.string.title_empresa));
        spinnerOperador.setText("");
        spinnerOperador.setHint(title_empresa);
        if (!edtDescripcionOtro.getText().toString().isEmpty()) {
            edtDescripcionOtro.setText("");
        }
        edtCodigoEquipo.setText("");

    }
    //endregion

    //region IMPLEMENTS METHODS MAINVIEWEQUIPO
    @Override
    public void showProgresss() {
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showUIElements() {

    }

    @Override
    public void hideUIElements() {

    }

    @Override
    public void onMessageOk(int colorPrimary, String message) {
        int color = Color.WHITE;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done, 0, 0, 0);
        // textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin));
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onMessageError(int colorPrimary, String message) {
        onMessageOk(colorPrimary, message);
    }

    @Override
    public void resultsList(List<Equipo_Elemento> listResult) {
        String results = String.format(getString(R.string.results_global_search),
                listResult.size());
        txtResults.setText(results);
    }


    @Override
    public void setContent(List<Equipo_Elemento> items) {
        adapter.setItems(items);
    }

    //endregion

    //region METHODS OVERRIDES
    @Override
    public void onRefresh() {
        showProgresss();
        loadListEquipos();
    }

    //No permite devolverse
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //endregion

    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /// getMenuInflater().inflate(R.menu.menu_equipos, menu);
        /// return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_equipos, menu);
        if (ACCION_UPDATE) {
            MenuItem item = menu.findItem(R.id.action_done);
            item.setVisible(false);
        } else {
            MenuItem item = menu.findItem(R.id.action_done);
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_done:
                final AlertDialog.Builder builder = new AlertDialog.Builder(EquipoActivity.this);
                builder.setTitle("Notificación");
                builder.setMessage("¿Confirma todos los datos?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), FotosActivity.class);
                        i.putExtra("ACCION_ADD", true);
                        i.putExtra("ACCION_UPDATE", false);
                        i.putExtra("Elemento_Id", Elemento_Id);
                        startActivity(i);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_update:

                if (checkConnection()) {
                    showProgresss();
                    syncActivity.loadDataAsync(this);

                } else {
                    onMessageError(R.color.orange, getString(R.string.message_not_connection));
                }
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

    //region METHODS OnItemClickLitener

    @Override
    public void onItemClick(Equipo_Elemento equipo_elemento) {

    }

    @Override
    public void onClickDelete(Equipo_Elemento equipo_elemento) {
        Response response = equipoController.DeleteEquipoByElemento(equipo_elemento.getEquipo_Elemento_Id());
        onMessageOk(R.color.orange, getString(R.string.message_delete_global));
        loadListEquipos();
    }
    //endregion

    //region EVENTS

    @OnClick({R.id.radioButtonSiConectadoRedElectrica, R.id.radioButtonNoConectadoRedElectrica, R.id.radioButtonNoMedidor, R.id.radioButtonSiMedidor, R.id.btnAddEquipos})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioButtonSiMedidor:
                Medidor_Red = true;
                break;
            case R.id.radioButtonNoMedidor:
                Medidor_Red = false;
                break;
            case R.id.radioButtonSiConectadoRedElectrica:
                Conectado_Red_Electrica = true;
                break;
            case R.id.radioButtonNoConectadoRedElectrica:
                Conectado_Red_Electrica = false;
                break;
            case R.id.btnAddEquipos:
                validarRegistrarEquipo();
                break;
        }
    }


    //endregion

    //region IMPLEMENT METHODS API SERVICE IDATASYNC
    @Override
    public void processFinishGetDataAsync(Response_Request_Data_Sync response) {
        syncActivity.processFinishGetDataAsync(response);
        hideProgress();
        onMessageOk(R.color.orange, "Datos actualizados");
        loadListSpinners();
        loadListEquipos();
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
            onMessageOk(R.color.colorAccent, getString(R.string.message_connection));
        } else {
            onMessageError(R.color.colorAccent, getString(R.string.message_not_connection));
        }
    }


    //endregion
}
