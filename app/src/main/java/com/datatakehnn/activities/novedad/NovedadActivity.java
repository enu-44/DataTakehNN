package com.datatakehnn.activities.novedad;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.datatakehnn.R;
import com.datatakehnn.activities.poste.PosteActivity;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.NovedadController;
import com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.novedad_model.Novedad;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NovedadActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinnerNovedad)
    MaterialBetterSpinner spinnerNovedad;
    @BindView(R.id.edtNovedad)
    EditText edtNovedad;

    //Tipo de novedad
    String Detalle_Tipo_Novedad_Nombre;
    String Tipo_Novedad;
    long Detalle_Tipo_Novedad_Id;
    //Arrays
    List<Detalle_Tipo_Novedad> listDetalleTipoNovedad = new ArrayList<>();
    boolean perdida = false;

    //Instances
    NovedadController novedadController;
    ElementoController elementoController;

    long Elemento_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedad);
        ButterKnife.bind(this);

        setupToolbarInjection();
        setupInjection();
        Tipo_Novedad = getIntent().getExtras().getString("Nombre");
        if (getIntent().getExtras().getString("perdida") != null) {
            perdida = true;
        }
        loadDetailsNovedad();


        //TODO Recibir parámetro de qué tipo de novedad es (Si es de código de apoyo, de placa, etc)

    }

    private void loadDetailsNovedad() {

        listDetalleTipoNovedad = novedadController.getListNovedades(Tipo_Novedad);

        spinnerNovedad.setAdapter(null);
        ArrayAdapter<Detalle_Tipo_Novedad> detalle_tipo_novedadArrayAdapter =
                new ArrayAdapter<Detalle_Tipo_Novedad>(this, android.R.layout.simple_spinner_dropdown_item, listDetalleTipoNovedad);
        spinnerNovedad.setAdapter(detalle_tipo_novedadArrayAdapter);
        spinnerNovedad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Detalle_Tipo_Novedad_Nombre = listDetalleTipoNovedad.get(position).getNombre();
                Detalle_Tipo_Novedad_Id = listDetalleTipoNovedad.get(position).getDetalle_Tipo_Novedad_Id();
            }
        });

    }

    //region SETUP INJECTION
    private void setupInjection() {
        this.novedadController = NovedadController.getInstance(this);
        this.elementoController = elementoController.getInstance(this);
        Elemento_Id = getIntent().getExtras().getLong("Elemento_Id");

    }

    private void setupToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getSupportActionBar() != null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Registrar Novedad");
    }

    //endregion


    //region METHODS

    private void registerNovedad() {
        Novedad novedad = new Novedad();
        Elemento elemento;
        long elemento_id = 0;
        elemento = elementoController.getLast();
        if (elemento == null) {
            elemento_id = 1;
        } else if (perdida == true) {
            elemento_id = Elemento_Id;
        } else {
            elemento_id = elemento.getElemento_Id() + 1;
        }
        Detalle_Tipo_Novedad detalle_tipo_novedad = novedadController.getDetalleById(Detalle_Tipo_Novedad_Id);
        long tipo_novedad_id = detalle_tipo_novedad.getTipo_Novedad_Id();
        String Nombre_Tipo_Novedad = novedadController.getTipoNovedadById(tipo_novedad_id).getNombre();

        Novedad hayNovedad = novedadController.getNovedadByTipoAndElementoId(tipo_novedad_id, elemento_id);
        if (hayNovedad == null) {
            novedad.setElemento_Id(elemento_id);
            novedad.setDetalle_Tipo_Novedad_Id(Detalle_Tipo_Novedad_Id);
            novedad.setDescripcion(edtNovedad.getText().toString());
            novedad.setDetalle_Tipo_Novedad_Nombre(Detalle_Tipo_Novedad_Nombre);
            novedad.setTipo_Novedad_Id(tipo_novedad_id);
            novedad.setNombre_Tipo_Novedad(Nombre_Tipo_Novedad);
            novedadController.register(novedad);
        } else {
            hayNovedad.setDetalle_Tipo_Novedad_Id(Detalle_Tipo_Novedad_Id);
            hayNovedad.setDescripcion(edtNovedad.getText().toString());
            hayNovedad.setDetalle_Tipo_Novedad_Nombre(Detalle_Tipo_Novedad_Nombre);
            hayNovedad.setNombre_Tipo_Novedad(Nombre_Tipo_Novedad);
            novedadController.update(hayNovedad);
        }
        setResult(RESULT_OK);
        finish();
    }

    //enregion

    //region MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_novedad, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_done:
                if (spinnerNovedad.getText().toString().isEmpty()) {
                    boolean cancel = false;
                    View focusView = null;
                    spinnerNovedad.setError(getString(R.string.error_field_required));
                    focusView = spinnerNovedad;
                    cancel = true;
                } else {
                    registerNovedad();
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


    //region OVERRIDES METHODS


    //endregion
}
