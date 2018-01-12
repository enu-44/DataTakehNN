package com.datatakehnn.activities.poste.lista_postes_usuario;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.CoordsActivity;
import com.datatakehnn.activities.poste.lista_postes_usuario.adapter.AdapterElemento;
import com.datatakehnn.activities.poste.lista_postes_usuario.adapter.OnItemClickListenerElemento;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.usuario_model.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class Poste_Usuario_Activity extends AppCompatActivity implements OnItemClickListenerElemento, MainViewPoste, SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.spinner_poste_usuario)
    Spinner spinnerPosteUsuario;
    @BindView(R.id.btnSeleccionarFecha)
    Button btnSeleccionarFecha;
    @BindView(R.id.textViewFecha)
    TextView textViewFecha;


    private boolean List_Is_Finished = true;


    //Instances
    UsuarioController usuarioController;
    ElementoController elementoController;

    //Adapter
    AdapterElemento adapter;

    //Listas
    List<Elemento> elementosList = new ArrayList<>();

    int positionSpinner;

    //
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste__usuario);

        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupInjection();
        setToolbarInjection();
        initAdapter();
        initRecyclerView();
        updateDate();
        loadListElementsRegisterWithFecha();
    }

    //region SETUP INJECTION
    private void setupInjection() {
        this.usuarioController = UsuarioController.getInstance(this);
        this.elementoController = ElementoController.getInstance(this);
    }

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            positionSpinner = 1;
            List<String> list_spinner_post_usuario = new ArrayList<>();
            list_spinner_post_usuario.add("Postes Totales");
            list_spinner_post_usuario.add("Postes Sincronizados");
            list_spinner_post_usuario.add("Postes Sin Sincronizar");

            ArrayAdapter<String> arrayAdapter;

            spinnerPosteUsuario.setAdapter(null);
            arrayAdapter =
                    new ArrayAdapter<String>(this, R.layout.spinner_item, list_spinner_post_usuario);
            spinnerPosteUsuario.setAdapter(arrayAdapter);
            spinnerPosteUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    positionSpinner = spinnerPosteUsuario.getSelectedItemPosition() + 1;
                    loadListElementsRegisterWithFecha();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //toolbar.setTitle("Postes Registrados");
        }
    }
    //endregion

    //region METHODS
    @OnClick(R.id.btnSeleccionarFecha)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSeleccionarFecha:
                updateDate();
                break;
            default:
                break;
        }
    }

    private void updateDate() {
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mostrarResultadosFecha();
        }
    };

    private void mostrarResultadosFecha() {
        SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
        String formatted = format1.format(dateTime.getTime());
        textViewFecha.setText(formatted);
        loadListElementsRegisterWithFecha();
    }


    private void initAdapter() {
        if (adapter == null) {
            adapter = new AdapterElemento(this, new ArrayList<Elemento>(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /*
    private void loadListElementsRegister() {
        Usuario usuarioLogued = usuarioController.getLoggedUser();
        adapter.clear();
        elementosList.clear();
        if (positionSpinner == 1) {
            elementosList = elementoController.getListElementsByUserLogued(usuarioLogued.getUsuario_Id(), List_Is_Finished);
        } else if (positionSpinner == 2) {
            elementosList = elementoController.getElementosByUserAndSync(usuarioLogued.getUsuario_Id(), true, List_Is_Finished);
        } else if (positionSpinner == 3) {
            elementosList = elementoController.getElementosByUserAndSync(usuarioLogued.getUsuario_Id(), false, List_Is_Finished);
        }
        setContent(elementosList);
        resultsList(elementosList);
        hideProgress();
    }*/

    private void loadListElementsRegisterWithFecha() {
        Usuario usuarioLogued = usuarioController.getLoggedUser();
        adapter.clear();
        elementosList.clear();
        if (positionSpinner == 1) {
            elementosList = elementoController.getListElementsByFechaWithoutSync(usuarioLogued.getUsuario_Id(), List_Is_Finished, textViewFecha.getText().toString());
        } else if (positionSpinner == 2) {
            elementosList = elementoController.getListElementsByFecha(usuarioLogued.getUsuario_Id(), true, List_Is_Finished, textViewFecha.getText().toString());
        } else if (positionSpinner == 3) {
            elementosList = elementoController.getListElementsByFecha(usuarioLogued.getUsuario_Id(), false, List_Is_Finished, textViewFecha.getText().toString());
        }
        setContent(elementosList);
        resultsList(elementosList);
        hideProgress();
    }

    //endregion

    //region EVENTS

    @OnCheckedChanged(R.id.switchElementFinished)
    public void onGenderSelectedAsistantWorkshop(CompoundButton button, boolean isChecked) {
        //do your stuff.
        if (isChecked) {
            List_Is_Finished = true;
            loadListElementsRegisterWithFecha();
            //showSnakBar(R.color.colorPrimary," Asistant: "+ String.valueOf(Is_Asistant_Workshop));
        } else {
            List_Is_Finished = false;
            loadListElementsRegisterWithFecha();
            //showSnakBar(R.color.colorPrimary," Asistant: "+ String.valueOf(Is_Asistant_Workshop));
        }
    }

    //endregion

    //region IMPLEMENTS METHODS MAINVIEWPOSTE
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
    public void resultsList(List<Elemento> listResult) {
        String results = String.format(getString(R.string.results_global_search),
                listResult.size());
        txtResults.setText(results);
    }

    @Override
    public void setContent(List<Elemento> items) {
        adapter.setItems(items);
    }
    //endregion

    //region METHODS OVERRIDES
    @Override
    public void onRefresh() {
        showProgresss();
        loadListElementsRegisterWithFecha();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListElementsRegisterWithFecha();
    }

    //endregion

    //region IMPLEMENTS OnItemClickLestenerElemento
    @Override
    public void onItemClick(Elemento elemento) {

        Intent i = new Intent(this, CoordsActivity.class);
        i.putExtra("Elemento", elemento);
        Toast.makeText(this, "Poste: " + elemento.getCodigo_Apoyo(), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
    //endregion

    //region MENU

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poste_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

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
}
