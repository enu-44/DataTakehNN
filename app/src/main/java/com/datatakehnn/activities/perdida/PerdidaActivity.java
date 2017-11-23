package com.datatakehnn.activities.perdida;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.activities.novedad.NovedadActivity;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.PerdidaController;
import com.datatakehnn.models.perdida_model.Perdida;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerdidaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radioButtonNoLamparaAdicional)
    RadioButton radioButtonNoLamparaAdicional;
    @BindView(R.id.radioButtonSiLamparaAdicional)
    RadioButton radioButtonSiLamparaAdicional;
    /*
    @BindView(R.id.edtLamparaAdicional)
    EditText edtLamparaAdicional;
    @BindView(R.id.textInputLayoutCantidadLamparaAdicional)
    TextInputLayout textInputLayoutCantidadLamparaAdicional;*/
    @BindView(R.id.radioButtonNoLamparaEncendidaDia)
    RadioButton radioButtonNoLamparaEncendidaDia;
    @BindView(R.id.radioButtonSiLamparaEncendidaDia)
    RadioButton radioButtonSiLamparaEncendidaDia;
    @BindView(R.id.radioButtonNoConexionIlicita)
    RadioButton radioButtonNoConexionIlicita;
    @BindView(R.id.radioButtonSiConexionIlicita)
    RadioButton radioButtonSiConexionIlicita;
    @BindView(R.id.radioButtonNoPoda)
    RadioButton radioButtonNoPoda;
    @BindView(R.id.radioButtonSiPoda)
    RadioButton radioButtonSiPoda;
    @BindView(R.id.container)
    RelativeLayout container;

    //Instancias
    ElementoController elementoController;
    PerdidaController perdidaController;

    //Accion
    boolean ACCION_ADD;
    boolean ACCION_UPDATE;

    //Variables globales
    long Elemento_Id;
    //long cantidad_lampara_adicional = 0;
    boolean estado_lampara_adicional = false;
    boolean lampara_encendida_dia = false;
    boolean conexion_ilicita = false;
    boolean poda = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdida);
        ButterKnife.bind(this);
        setupInjection();
        setToolbarInjection();
    }


    //region CLICKS EN ITEMS
    @OnClick({R.id.radioButtonNoLamparaAdicional, R.id.radioButtonSiLamparaAdicional, R.id.radioButtonNoLamparaEncendidaDia, R.id.radioButtonSiLamparaEncendidaDia, R.id.radioButtonNoConexionIlicita, R.id.radioButtonSiConexionIlicita, R.id.radioButtonNoPoda, R.id.radioButtonSiPoda})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.radioButtonNoLamparaAdicional:
                estado_lampara_adicional = false;
                //cantidad_lampara_adicional = 0;
                //textInputLayoutCantidadLamparaAdicional.setVisibility(View.GONE);
                break;
            case R.id.radioButtonSiLamparaAdicional:
                estado_lampara_adicional = true;
                //cantidad_lampara_adicional = Long.parseLong(edtLamparaAdicional.getText().toString());
                //textInputLayoutCantidadLamparaAdicional.setVisibility(View.VISIBLE);
                Intent h = new Intent(this, NovedadActivity.class);
                h.putExtra("Nombre", "Lampara Adicional");
                h.putExtra("perdida", "1");
                startActivityForResult(h, 100);
                break;
            case R.id.radioButtonNoLamparaEncendidaDia:
                lampara_encendida_dia = false;
                break;
            case R.id.radioButtonSiLamparaEncendidaDia:
                lampara_encendida_dia = true;
                Intent i = new Intent(this, NovedadActivity.class);
                i.putExtra("Nombre", "Lampara Encendida");
                i.putExtra("perdida", "1");
                startActivityForResult(i, 100);
                break;
            case R.id.radioButtonNoConexionIlicita:
                conexion_ilicita = false;
                break;
            case R.id.radioButtonSiConexionIlicita:
                conexion_ilicita = true;
                Intent j = new Intent(this, NovedadActivity.class);
                j.putExtra("Nombre", "Conexion Ilicita");
                j.putExtra("perdida", "1");
                startActivityForResult(j, 100);
                break;
            case R.id.radioButtonNoPoda:
                poda = false;
                break;
            case R.id.radioButtonSiPoda:
                poda = true;
                Intent k = new Intent(this, NovedadActivity.class);
                k.putExtra("Nombre", "Poda");
                k.putExtra("perdida", "1");
                startActivityForResult(k, 100);
                break;
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

    //region SETUP INJECTION
    private void setupInjection() {
        ACCION_ADD = getIntent().getExtras().getBoolean("ACCION_ADD");
        ACCION_UPDATE = getIntent().getExtras().getBoolean("ACCION_UPDATE");
        Elemento_Id = getIntent().getExtras().getLong("Elemento_Id");

        this.elementoController = elementoController.getInstance(this);
        this.perdidaController = perdidaController.getInstance(this);
    }

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Pérdidas");
        if (ACCION_UPDATE) {
            if (getSupportActionBar() != null)// Habilitar Up Button
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);//devolver
        }
    }

    //endregion

    //region MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perdidas, menu);
        if (ACCION_UPDATE) {
            MenuItem item = menu.findItem(R.id.action_done);
            item.setVisible(false);
        } else {
            MenuItem item = menu.findItem(R.id.action_done);
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            validarCampos();
            ///Metodo que permite no recargar la pagina al devolverse
        } else if (id == android.R.id.home) {
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
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region VALIDACIÓN DE CAMPOS y REGISTRO DE PÉRDIDA
    public void validarCampos() {
       /* boolean cancel = false;
        View focusView = null;
        if (estado_lampara_adicional == true && edtLamparaAdicional.getText().toString().isEmpty()) {
            //cantidad_lampara_adicional = Long.parseLong(edtLamparaAdicional.getText().toString());
            edtLamparaAdicional.setError(getString(R.string.error_field_required));
            focusView = edtLamparaAdicional;
            cancel = true;
        } else {*/
        registrarPerdida();
        //}
    }

    private void registrarPerdida() {
        /*
        if (estado_lampara_adicional == true) {
            cantidad_lampara_adicional = Long.parseLong(edtLamparaAdicional.getText().toString());
        }*/
        Perdida perdida = new Perdida();
        perdida.setElemento_Id(Elemento_Id);
        perdida.setIs_Lampara_Adicional(estado_lampara_adicional);
        //perdida.setCantidad_Lampara_Adicional(cantidad_lampara_adicional);
        perdida.setIs_Lampara_Encendida_Dia(lampara_encendida_dia);
        perdida.setIs_Conexion_Ilicita(conexion_ilicita);
        perdida.setIs_Poda(poda);
        perdidaController.register(perdida);

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
    }

    //endregion
}
