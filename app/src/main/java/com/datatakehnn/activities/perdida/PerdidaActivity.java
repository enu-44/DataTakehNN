package com.datatakehnn.activities.perdida;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.activities.novedad.NovedadActivity;
import com.datatakehnn.activities.perdida.adapter.AdapterPerdida;
import com.datatakehnn.activities.perdida.adapter.OnItemClickListenerPerdida;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.PerdidaController;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.models.perdida_model.Perdida;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.tipo_perdida_model.Tipo_Perdida;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerdidaActivity extends AppCompatActivity implements MainViewPerdida, SwipeRefreshLayout.OnRefreshListener, OnItemClickListenerPerdida {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinnerTipoPerdida)
    MaterialBetterSpinner spinnerTipoPerdida;
    @BindView(R.id.btnAddPerdidas)
    Button btnAddPerdidas;
    @BindView(R.id.edtCantidad)
    EditText edtCantidad;
    @BindView(R.id.edtDescripcion)
    EditText edtDescripcion;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtResults)
    TextView txtResults;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.container)
    RelativeLayout container;

    //Declaracion Arrays
    List<Tipo_Perdida> tipoPerdidaList = new ArrayList<>();
    List<Perdida> perdidaList = new ArrayList<>();

    //Adapters
    ArrayAdapter<Tipo_Perdida> tipoPerdidaArrayAdapter;

    //Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;
    PerdidaController perdidaController;
    ElementoController elementoController;

    //Adapter
    AdapterPerdida adapter;

    //Accion
    boolean ACCION_ADD;
    boolean ACCION_UPDATE;

    //Variables
    long Elemento_Id;
    long Tipo_Perdida_Id;
    String Nombre_Tipo_Perdida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdida);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupInjection();
        setToolbarInjection();
        initAdapter();
        initRecyclerView();
        showProgresss();
        loadListSpinnerTipoPerdida();
        loadListPerdidas();
    }

    //region SETUP INJECTION
    private void setupInjection() {
        //Actualizar o Eliminar
        ACCION_ADD = getIntent().getExtras().getBoolean("ACCION_ADD");
        ACCION_UPDATE = getIntent().getExtras().getBoolean("ACCION_UPDATE");
        Elemento_Id = getIntent().getExtras().getLong("Elemento_Id");
        this.sincronizacionGetInformacionController = SincronizacionGetInformacionController.getInstance(this);
        this.perdidaController = PerdidaController.getInstance(this);
        this.elementoController = ElementoController.getInstance(this);

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
        toolbar.setTitle(getString(R.string.title_perdidas));
    }

    //endregion

    //region METHODS
    private void initAdapter() {
        if (adapter == null) {
            adapter = new AdapterPerdida(this, new ArrayList<Perdida>(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadListSpinnerTipoPerdida() {
        //Listas
        tipoPerdidaList = sincronizacionGetInformacionController.getListTipoPerdidas();

        spinnerTipoPerdida.setAdapter(null);
        tipoPerdidaArrayAdapter =
                new ArrayAdapter<Tipo_Perdida>(this, android.R.layout.simple_spinner_dropdown_item, tipoPerdidaList);
        spinnerTipoPerdida.setAdapter(tipoPerdidaArrayAdapter);
        spinnerTipoPerdida.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Tipo_Perdida_Id = tipoPerdidaList.get(position).getTipo_Perdida_Id();
                Nombre_Tipo_Perdida = tipoPerdidaList.get(position).getNombre();
            }
        });
    }

    private void validarRegistrarPerdida() {
        //VALIDACION
        boolean cancel = false;
        View focusView = null;
        if (spinnerTipoPerdida.getText().toString().isEmpty()) {
            spinnerTipoPerdida.setError(getString(R.string.error_field_required));
            focusView = spinnerTipoPerdida;
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
            registerPerdidas();
        }
    }

    private void registerPerdidas() {
        Perdida perdida = new Perdida(
                Nombre_Tipo_Perdida,
                Long.parseLong(edtCantidad.getText().toString()),
                edtDescripcion.getText().toString(),
                0.0,
                true,
                Elemento_Id,
                Tipo_Perdida_Id
        );

        perdidaController.register(perdida);
        limpiarCampos();
        onMessageOk(R.color.colorAccent, "Pérdida registrada");
        loadListPerdidas();
        hideKeyboard();

        //TODO Registrar Novedad.
        Intent i = new Intent(this, NovedadActivity.class);
        i.putExtra("Nombre", Nombre_Tipo_Perdida);
        i.putExtra("perdida", 1);
        i.putExtra("Elemento_Id", Elemento_Id);
        startActivityForResult(i, 100);


    }

    private void loadListPerdidas() {
        adapter.clear();
        perdidaList.clear();
        perdidaList = perdidaController.getListPerdida(Elemento_Id);
        setContent(perdidaList);
        resultsList(perdidaList);
        hideProgress();
    }

    private void limpiarCampos() {
        String title_tipo_perdida = String.format(getString(R.string.title_tipo_perdida));
        spinnerTipoPerdida.setText("");
        spinnerTipoPerdida.setHint(title_tipo_perdida);

        edtCantidad.setText("");
        edtDescripcion.setText("");

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
    //endregion

    //region ON ACTIVITY RESULT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 100) && (resultCode == RESULT_OK)) {
            Snackbar.make(container, getString(R.string.message_novedad), Snackbar.LENGTH_SHORT).show();
        }
    }

    //endregion


    //region MÉTODOS DE INTERFAZ MAINVIEWPERDIDA
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
    public void resultsList(List<Perdida> listResult) {
        String results = String.format(getString(R.string.results_global_search),
                listResult.size());
        txtResults.setText(results);
    }

    @Override
    public void setContent(List<Perdida> items) {
        adapter.setItems(items);
    }


    //endregion


    //region METHODS OVERRIDES
    @Override
    public void onRefresh() {
        showProgresss();
        loadListPerdidas();
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

    //region MÉTODOS DE INTERFAZ ON CLICK LISTENER
    @Override
    public void onItemClick(Perdida perdida) {

    }

    @Override
    public void onClickDelete(Perdida perdida) {
        Response response = perdidaController.DeletePerdidaById(perdida.getPerdida_Id());
        onMessageOk(R.color.orange, getString(R.string.message_delete_global));
        loadListPerdidas();
    }

    //endregion

    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /// getMenuInflater().inflate(R.menu.menu_equipos, menu);
        /// return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perdida, menu);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(PerdidaActivity.this);
                builder.setTitle("Notificación");
                builder.setMessage("¿Confirma todos los datos?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), CablesElementoActivity.class);
                        i.putExtra("ACCION_ADD", true);
                        i.putExtra("ACCION_UPDATE", false);
                        i.putExtra("Elemento_Id", Elemento_Id);
                        startActivity(i);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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

    @OnClick({R.id.btnAddPerdidas})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddPerdidas:
                validarRegistrarPerdida();
                break;
        }
    }


}
