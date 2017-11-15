package com.datatakehnn.activities.novedad;

import android.content.Intent;
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
    String Tipo_Novedad;
    long Detalle_Tipo_Novedad_Id;
    //Arrays
    List<Detalle_Tipo_Novedad> listDetalleTipoNovedad = new ArrayList<>();
    //Instances
    NovedadController novedadController;
    ElementoController elementoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedad);
        ButterKnife.bind(this);

        setupToolbarInjection();
        setupInjection();
        Tipo_Novedad = getIntent().getExtras().getString("Nombre");
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
                Detalle_Tipo_Novedad_Id = listDetalleTipoNovedad.get(position).getDetalle_Tipo_Novedad_Id();
            }
        });

    }

    //region SETUP INJECTION
    private void setupInjection() {
        this.novedadController = NovedadController.getInstance(this);
        this.elementoController = elementoController.getInstance(this);

    }

    private void setupToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Registrar Novedad");
    }

    //endregion


    //region METHODS

    private void registerNovedad() {
        Novedad novedad = new Novedad();
        Elemento elemento = new Elemento();
        long elemento_id = 0;
        elemento = elementoController.getLast();
        if (elemento == null) {
            elemento_id = 1;
        } else {
            elemento_id = elemento.getElemento_Id() + 1;
        }
        Novedad hayNovedad = novedadController.getByDetalleAndElemento(Detalle_Tipo_Novedad_Id, elemento_id);
        if (hayNovedad == null) {
            novedad.setElemento_Id(elemento_id);
            novedad.setDetalle_Tipo_Novedad_Id(Detalle_Tipo_Novedad_Id);
            novedad.setDescripcion(edtNovedad.getText().toString());
            novedadController.register(novedad);
        } else {
            novedad.setElemento_Id(elemento_id);
            novedad.setDetalle_Tipo_Novedad_Id(Detalle_Tipo_Novedad_Id);
            novedad.setDescripcion(edtNovedad.getText().toString());
            novedadController.update(novedad);
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
        int id = item.getItemId();
        if (id == R.id.action_done) {
            if (spinnerNovedad.getText().toString().isEmpty()) {
                boolean cancel = false;
                View focusView = null;
                spinnerNovedad.setError(getString(R.string.error_field_required));
                focusView = spinnerNovedad;
                cancel = true;
            } else {

                registerNovedad();


            }
        }
        return super.onOptionsItemSelected(item);
    }



    //endregion


    //region OVERRIDES METHODS
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //endregion
}
