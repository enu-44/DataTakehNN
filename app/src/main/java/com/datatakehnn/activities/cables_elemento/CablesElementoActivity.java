package com.datatakehnn.activities.cables_elemento;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.RadioButton;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.adapter.AdapterCablesElemento;
import com.datatakehnn.activities.cables_elemento.adapter.OnItemClickListenerCable;
import com.datatakehnn.activities.equipos_elemento.EquipoActivity;
import com.datatakehnn.activities.sync.SyncActivity;
import com.datatakehnn.controllers.CablesController;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.ciudad_empresa.Ciudad_Empresa;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.empresa_model.Empresa;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.reponse_generic.data_async.Response_Request_Data_Sync;
import com.datatakehnn.models.tipo_cable.Tipo_Cable;
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

public class CablesElementoActivity extends AppCompatActivity implements IDataAsync,OnItemClickListenerCable, MainViewCablesElemento, SwipeRefreshLayout.OnRefreshListener,ConnectivityReceiver.ConnectivityReceiverListener {

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
    @BindView(R.id.spinnerDetalle)
    MaterialBetterSpinner spinnerDetalle;
    @BindView(R.id.edtCantidad)
    EditText edtCantidad;
    @BindView(R.id.radioButtonNoMarquilla)
    RadioButton radioButtonNoMarquilla;
    @BindView(R.id.radioButtonSiMarquilla)
    RadioButton radioButtonSiMarquilla;


    //Declaracion Arrays
    List<Ciudad_Empresa> empresaList = new ArrayList<>();
    List<Tipo_Cable> tipo_cables = new ArrayList<>();
    List<Detalle_Tipo_Cable> detalle_tipo_cables = new ArrayList<>();
    //Variables Gloabals
    private boolean SOBRE_REDES_BT = true;
    private boolean TIENE_MARQUILLA = true;
    private long Detalle_Tipo_Cable_Id;
    private long Empresa_Id;
    private long Ciudad_Empresa_Id;
    private long Ciudad_Id;

    public String Nombre_Detalle_Tipo_Cable;
    public String Nombre_Tipo_Cable;
    public String Nombre_Empresa;


    //Listas
    List<Elemento_Cable> elemento_cables_List = new ArrayList<>();

    //Adapters
    ArrayAdapter<Detalle_Tipo_Cable> detalle_tipo_cableArrayAdapter;
    ArrayAdapter<Ciudad_Empresa> empresaArrayAdapter;
    ArrayAdapter<Tipo_Cable> tipo_cableArrayAdapter;

    //Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;
    CablesController cablesController;
    ElementoController elementoController;
    UsuarioController usuarioController;
    SyncActivity syncActivity;

    //Accion
    boolean ACCION_ADD;
    boolean ACCION_UPDATE;

    //Variables
    long Elemento_Id;

    //Adapter
    AdapterCablesElemento adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cables_elemento);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupInjection();
        setToolbarInjection();
        initAdapter();
        initRecyclerView();
        showProgresss();
        loadListSpinners();
        loadListCablesElementos();
    }

    //region SETUP INJECTION

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getString(R.string.title_cables));
        if (ACCION_UPDATE) {
            if (getSupportActionBar() != null)// Habilitar Up Button
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);//devolver
        }
    }

    private void setupInjection() {
        //Actualizar o Eliminar
        ACCION_ADD = getIntent().getExtras().getBoolean("ACCION_ADD");
        ACCION_UPDATE = getIntent().getExtras().getBoolean("ACCION_UPDATE");
        Elemento_Id = getIntent().getExtras().getLong("Elemento_Id");

        this.sincronizacionGetInformacionController = SincronizacionGetInformacionController.getInstance(this);
        this.cablesController = CablesController.getInstance(this);
        this.elementoController = ElementoController.getInstance(this);
        this.usuarioController= UsuarioController.getInstance(this);
        this.syncActivity= SyncActivity.getInstance(this);

        //Elemento elemento = elementoController.getLast();
        ///Elemento_Id = elemento.getElemento_Id();

    }

    //endregion

    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_cables, menu);
        //return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cables, menu);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(CablesElementoActivity.this);
                builder.setTitle("Notificación");
                builder.setMessage("¿Confirma todos los datos?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), EquipoActivity.class);
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

                if(checkConnection()){
                    showProgresss();
                    syncActivity.loadDataAsync(this);

                }else{
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

    //region METHODS

    private void initAdapter() {
        if (adapter == null) {
            adapter = new AdapterCablesElemento(this, new ArrayList<Elemento_Cable>(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadListSpinners() {


        Usuario userLogued= usuarioController.getLoggedUser();

        //Listas
        empresaList = sincronizacionGetInformacionController.getListEmpresasByCiudad(userLogued.getCiudad_Id());
        tipo_cables = sincronizacionGetInformacionController.getListTipo_Cable();

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
                Ciudad_Empresa_Id= empresaList.get(position).getCiudad_Empresa_Id();
                Ciudad_Id= empresaList.get(position).getCiudad_Id();
            }
        });

        //Tipos de Cables
         /*--------------------------------------------------------------------------------------------*/
        spinnerTipo.setAdapter(null);
        tipo_cableArrayAdapter =
                new ArrayAdapter<Tipo_Cable>(this, android.R.layout.simple_spinner_dropdown_item, tipo_cables);
        spinnerTipo.setAdapter(tipo_cableArrayAdapter);
        spinnerTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                long tipo_Cable_Id = tipo_cables.get(position).getTipo_Cable_Id();
                Nombre_Tipo_Cable = tipo_cables.get(position).getNombre();
                detalle_tipo_cables.clear();
                detalle_tipo_cables = sincronizacionGetInformacionController.getListDetalleTipo_Cable(tipo_Cable_Id);
                notifyChangeAdapterDetalleCables(detalle_tipo_cables);
            }
        });


        notifyChangeAdapterDetalleCables(new ArrayList<Detalle_Tipo_Cable>());

    }

    //Carga el detalle de cables
    private void notifyChangeAdapterDetalleCables(List<Detalle_Tipo_Cable> arrayList) {
        spinnerDetalle.setAdapter(null);
        detalle_tipo_cableArrayAdapter =
                new ArrayAdapter<Detalle_Tipo_Cable>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        spinnerDetalle.setAdapter(detalle_tipo_cableArrayAdapter);
        String title = String.format(getString(R.string.title_detalle_cable));
        spinnerDetalle.setText("");
        spinnerDetalle.setHint(title);

        spinnerDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Detalle_Tipo_Cable_Id = detalle_tipo_cables.get(position).getDetalle_Tipo_Cable_Id();
                Nombre_Detalle_Tipo_Cable = detalle_tipo_cables.get(position).getNombre();
            }
        });
    }

    private void loadListCablesElementos() {
        adapter.clear();
        elemento_cables_List.clear();
        elemento_cables_List = cablesController.getList_Cable_Element(Elemento_Id);
        setContent(elemento_cables_List);
        resultsList(elemento_cables_List);
        hideProgress();
    }

    private void registrarCable() {

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
        } else if (spinnerDetalle.getText().toString().isEmpty()) {
            spinnerDetalle.setError(getString(R.string.error_field_required));
            focusView = spinnerDetalle;
            cancel = true;
        } else if (edtCantidad.getText().toString().isEmpty()) {
            edtCantidad.setError(getString(R.string.error_field_required));
            focusView = edtCantidad;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            registerCables();

        }
    }

    private void registerCables() {

        long cantidad = Long.parseLong(edtCantidad.getText().toString());

        Elemento_Cable elementoCable = new Elemento_Cable(
               "Codigo",
                cantidad,
                SOBRE_REDES_BT,
                TIENE_MARQUILLA,
                Empresa_Id,
                Ciudad_Id,
                Ciudad_Empresa_Id,
                Detalle_Tipo_Cable_Id,
                Elemento_Id,
                Nombre_Detalle_Tipo_Cable,
                Nombre_Tipo_Cable,
                Nombre_Empresa
        );

        cablesController.register(elementoCable);


         /*
        cablesController.register(Detalle_Tipo_Cable_Id,
                Elemento_Id,
                "Codigo",
                Empresa_Id,
                cantidad,
                SOBRE_REDES_BT,
                Nombre_Detalle_Tipo_Cable,
                Nombre_Tipo_Cable,
                Nombre_Empresa);*/

        limpiarCampos();
        onMessageOk(R.color.colorAccent, "Cable Registrado");
        loadListCablesElementos();
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

    //limpiar campos
    private void limpiarCampos() {

        String title_detalle_cable = String.format(getString(R.string.title_detalle_cable));
        spinnerDetalle.setText("");
        spinnerDetalle.setHint(title_detalle_cable);

        String title_tipo_cable = String.format(getString(R.string.title_tipo_cable));
        spinnerTipo.setText("");
        spinnerTipo.setHint(title_tipo_cable);

        String title_empresa = String.format(getString(R.string.title_empresa));
        spinnerOperador.setText("");
        spinnerOperador.setHint(title_empresa);
        edtCantidad.setText("1");

        notifyChangeAdapterDetalleCables(new ArrayList<Detalle_Tipo_Cable>());
    }


    //endregion

    //region IMPLEMENT METHODS INTERFACE VIEW
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
    public void resultsList(List<Elemento_Cable> listResult) {
        String results = String.format(getString(R.string.results_global_search),
                listResult.size());
        txtResults.setText(results);
    }

    @Override
    public void setContent(List<Elemento_Cable> items) {
        adapter.setItems(items);
    }
    //endregion

    //region IMPLEMENT METHODS OnItemClickListener
    @Override
    public void onItemClick(Elemento_Cable elemento_cable) {
        //onMessageOk(R.color.orange,"Click Item");
    }

    @Override
    public void onClickDelete(Elemento_Cable elemento_cable) {
        Response response = cablesController.DeleteCableByElemento(elemento_cable.getElemento_Cable_Id());

        onMessageOk(R.color.orange, getString(R.string.message_delete_global));
        loadListCablesElementos();
    }
    //endregion

    //region METHODS OVERRIDES
    @Override
    public void onRefresh() {
        showProgresss();
        loadListCablesElementos();
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

    //region EVENTS

    @OnClick({R.id.radioButtonSiRedesBt, R.id.radioButtonNoRedesBt, R.id.btnAddCables, R.id.radioButtonNoMarquilla, R.id.radioButtonSiMarquilla})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioButtonSiRedesBt:
                SOBRE_REDES_BT = true;
                break;
            case R.id.radioButtonNoRedesBt:
                SOBRE_REDES_BT = false;
                break;
            case R.id.radioButtonNoMarquilla:
                TIENE_MARQUILLA = false;
                break;
            case R.id.radioButtonSiMarquilla:
                TIENE_MARQUILLA = true;
                break;
            case R.id.btnAddCables:
                registrarCable();
                break;
        }
    }


    //endregion

    //region IMPLEMENT METHODS API SERVICE IDATASYNC

    @Override
    public void processFinishGetDataAsync(Response_Request_Data_Sync response) {
        syncActivity.processFinishGetDataAsync(response);
        hideProgress();
        onMessageOk(R.color.orange,"Datos actualizados");
        loadListSpinners();
        loadListCablesElementos();
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
