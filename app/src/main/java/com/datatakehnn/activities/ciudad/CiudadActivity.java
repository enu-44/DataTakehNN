package com.datatakehnn.activities.ciudad;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.activities.equipos_elemento.EquipoActivity;
import com.datatakehnn.activities.login.LoginActivity;
import com.datatakehnn.activities.poste.PosteActivity;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.ciudades_model.Ciudad;
import com.datatakehnn.models.departmentos_model.Departamento;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.usuario_model.Usuario;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CiudadActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //Spinner
    @BindView(R.id.spinnerDepartament)
    MaterialBetterSpinner spinnerDepartament;
    @BindView(R.id.spinnerCiudad)
    MaterialBetterSpinner spinnerCiudad;

    @BindView(R.id.txtCiudadSelected)
    TextView txtCiudadSelected;

    //Array Adapters
    ArrayAdapter<Departamento> departamentoArrayAdapter;
    ArrayAdapter<Ciudad> ciudadArrayAdapter;

    //Listas
    List<Departamento> departamentos= new ArrayList<>();
    List<Ciudad> ciudades= new ArrayList<>();

    //Variables
    public long Ciudad_Id;
    public String Nombre_Ciudad;
    public long Departamento_Id;
    public String Nombre_Departamento;





    //Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;
    UsuarioController usuarioController;
    private Usuario usuarioLogued;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad);
        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();
        loadListDepartments();
        verficateCitySelected();
    }



    //region METHODS
    private void verficateCitySelected() {
        if(usuarioLogued.getCiudad_Id()>0 && usuarioLogued.getDepartamento_Id()>0){
            //Departamentos y Ciudades
            ciudades= sincronizacionGetInformacionController.getListCiudadByDepartamento(usuarioLogued.getDepartamento_Id());
            notifyChangeCiudaddes(ciudades);
            spinnerDepartament.setText(usuarioLogued.getNombre_Departamento());
            spinnerCiudad.setText(usuarioLogued.getNombre_Ciudad());
            txtCiudadSelected.setText(usuarioLogued.getNombre_Departamento()+" - "+usuarioLogued.getNombre_Ciudad());
            Ciudad_Id= usuarioLogued.getCiudad_Id();
            Departamento_Id= usuarioLogued.getDepartamento_Id();
            Nombre_Ciudad=usuarioLogued.getNombre_Ciudad();
            Nombre_Departamento= usuarioLogued.getNombre_Departamento();

        }
    }
    //Carga el detalle de cables
    private void loadListDepartments() {
        departamentos= new ArrayList<>();
        departamentos= sincronizacionGetInformacionController.getDepartamentos();

        spinnerDepartament.setAdapter(null);
        departamentoArrayAdapter =
                new ArrayAdapter<Departamento>(this, android.R.layout.simple_dropdown_item_1line, departamentos);
        spinnerDepartament.setAdapter(departamentoArrayAdapter);
        String title = String.format(getString(R.string.title_departamento));
        spinnerDepartament.setText("");
        spinnerDepartament.setHint(title);
        spinnerDepartament.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Departamento_Id = departamentos.get(position).getDepartamento_Id();
                Nombre_Departamento = departamentos.get(position).getNombre();
                ciudades= sincronizacionGetInformacionController.getListCiudadByDepartamento(Departamento_Id);
                notifyChangeCiudaddes(ciudades);
            }
        });

        notifyChangeCiudaddes(new ArrayList<Ciudad>());
    }

    private void notifyChangeCiudaddes(List<Ciudad> list) {
        ciudades= new ArrayList<>();
        ciudades= list;
        spinnerCiudad.setAdapter(null);
        ciudadArrayAdapter =
                new ArrayAdapter<Ciudad>(this, android.R.layout.simple_dropdown_item_1line, ciudades);
        spinnerCiudad.setAdapter(ciudadArrayAdapter);
        String title_ciudad = String.format(getString(R.string.title_ciudad));
        spinnerCiudad.setText("");
        spinnerCiudad.setHint(title_ciudad);

        spinnerCiudad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Ciudad_Id = ciudades.get(position).getCiudad_Id();
                Nombre_Ciudad = ciudades.get(position).getNombre();
                txtCiudadSelected.setText(Nombre_Departamento+" - "+Nombre_Ciudad);
            }
        });
    }

    public void validacionUpdateCiudadUser(){
        //VALIDACION
        boolean cancel = false;
        View focusView = null;
        if (spinnerDepartament.getText().toString().isEmpty()) {
            spinnerDepartament.setError(getString(R.string.error_field_required));
            focusView = spinnerDepartament;
            cancel = true;
        } else if (spinnerCiudad.getText().toString().isEmpty()) {
            spinnerCiudad.setError(getString(R.string.error_field_required));
            focusView = spinnerCiudad;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            UpdateCiudadUser();
        }
    }

    private void UpdateCiudadUser() {
        usuarioLogued.setNombre_Departamento(Nombre_Departamento);
        usuarioLogued.setNombre_Ciudad(Nombre_Ciudad);
        usuarioLogued.setCiudad_Id(Ciudad_Id);
        usuarioLogued.setDepartamento_Id(Departamento_Id);
        usuarioController.update(usuarioLogued);
        onReturnActivity();
    }

    //endregion

    //region SETUP INJECTION
    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getString(R.string.title_ubicacion_ciudad));
        if (getSupportActionBar() != null)// Habilitar Up Button
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupInjection() {
        this.sincronizacionGetInformacionController= SincronizacionGetInformacionController.getInstance(this);
        this.usuarioController= UsuarioController.getInstance(this);
        usuarioLogued= usuarioController.getLoggedUser();
    }
    //endregion

    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_generic, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                showConfirmacion();

                break;

            case android.R.id.home:
                showConfirmacion();
                return true;
        }
        return false;
    }

    private void onReturnActivity() {
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

        }

        //Para versiones anterios a 5.x
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Terminar con el método correspondiente para Android 5.x
            onBackPressed();

        }
    }

    //endregion

    //region UI Elements

    public Dialog showConfirmacion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmacion");
        builder.setNegativeButton("Descartar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Dialogos", "Confirmacion Cancelada.");
                    onReturnActivity();
            }
        });
        builder.setMessage("¿Confirmar Cambios?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                    validacionUpdateCiudadUser();
            }
        });
        builder.setIcon(R.drawable.logo_datatakeh_nuevo);
        return builder.show();
    }

    //endregion




    //region OVERRIDES METHODS

    @Override
    public void onBackPressed() {
      ///  super.onBackPressed();
        showConfirmacion();
    }


    //endregion

}
