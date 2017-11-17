package com.datatakehnn.activities.equipos_elemento;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.adapter.AdapterCablesElemento;
import com.datatakehnn.activities.equipos_elemento.adapter.AdapterEquipo;
import com.datatakehnn.activities.equipos_elemento.adapter.OnItemClickListenerEquipo;
import com.datatakehnn.activities.fotos.FotosActivity;
import com.datatakehnn.controllers.CablesController;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.EquipoController;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.empresa_model.Empresa;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.tipo_cable.Tipo_Cable;
import com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquipoActivity extends AppCompatActivity implements MainViewEquipo, OnItemClickListenerEquipo, SwipeRefreshLayout.OnRefreshListener {

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

    //Variables Gloabals
    private boolean Conectado_Red_Electrica = true;
    private boolean Medidor_Red = true;
    private long Empresa_Id;
    private long Elemento_Id;
    private long Tipo_Equipo_Id;


    public String Nombre_Tipo_Equipo;
    public String Nombre_Empresa;

    //Declaracion Arrays
    List<Empresa> empresaList = new ArrayList<>();
    List<Tipo_Equipo> tipo_equipos = new ArrayList<>();

    //Listas
    List<Equipo_Elemento> equipo_elementoList = new ArrayList<>();

    //Adapters
    ArrayAdapter<Empresa> empresaArrayAdapter;
    ArrayAdapter<Tipo_Equipo> tipo_equipoArrayAdapter;

    //Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;
    EquipoController equipoController;
    ElementoController elementoController;

    //Adapter
    AdapterEquipo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        setToolbarInjection();
        setupInjection();
        initAdapter();
        initRecyclerView();
        showProgresss();
        loadListSpinners();
        loadListEquipos();
    }

    //region SETUP INJECTION
    private void setupInjection() {
        this.sincronizacionGetInformacionController = SincronizacionGetInformacionController.getInstance(this);
        this.equipoController = EquipoController.getInstance(this);
        this.elementoController = ElementoController.getInstance(this);
        Elemento elemento = elementoController.getLast();
        Elemento_Id = elemento.getElemento_Id();
    }


    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//devolver
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
        Equipo_Elemento equipo_elemento = new Equipo_Elemento(
                1,
                Tipo_Equipo_Id,
                Elemento_Id,
                Empresa_Id,
                Conectado_Red_Electrica,
                Medidor_Red,
                Nombre_Tipo_Equipo,
                Nombre_Empresa
        );

        equipoController.register(equipo_elemento);
        limpiarCampos();
        onMessageOk(R.color.colorAccent, "Cable Registrado");
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

        //Listas
        empresaList = sincronizacionGetInformacionController.getListEmpresas();
        tipo_equipos = sincronizacionGetInformacionController.getListTipoEquipo();

        //Spinner
        //Empresas Operadoras
         /*--------------------------------------------------------------------------------------------*/
        spinnerOperador.setAdapter(null);
        empresaArrayAdapter =
                new ArrayAdapter<Empresa>(this, android.R.layout.simple_spinner_dropdown_item, empresaList);
        spinnerOperador.setAdapter(empresaArrayAdapter);
        spinnerOperador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Empresa_Id = empresaList.get(position).getEmpresa_Id();
                Nombre_Empresa = empresaList.get(position).getNombre();
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
        getMenuInflater().inflate(R.menu.menu_equipos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(EquipoActivity.this);
            builder.setTitle("Notificación");
            builder.setMessage("¿Confirma todos los datos?");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(getApplicationContext(), FotosActivity.class);
                    startActivity(i);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
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
}
