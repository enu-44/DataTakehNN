package com.datatakehnn.activities.poste;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.activities.novedad.NovedadActivity;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.NovedadController;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.longitud_elemento_model.Longitud_Elemento;
import com.datatakehnn.models.material_model.Material;
import com.datatakehnn.models.nivel_tension_elemento_model.Nivel_Tension_Elemento;
import com.datatakehnn.models.retenidas_model.Cantidad_Retenidas;
import com.datatakehnn.models.tipo_direccion_model.Detalle_Tipo_Direccion;
import com.datatakehnn.models.tipo_direccion_model.Tipo_Direccion;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.data_arrays.Cantidad_Retenidas_List;
import com.datatakehnn.services.data_arrays.Detalle_Tipo_Cable_List;
import com.datatakehnn.services.data_arrays.Detalle_Tipo_Direccion_List;
import com.datatakehnn.services.data_arrays.Tipo_Direccion_List;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class PosteActivity extends AppCompatActivity {

    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radioButtonNoCodigoApoyo)
    RadioButton radioButtonNoCodigoApoyo;
    @BindView(R.id.radioButtonSiCodigoApoyo)
    RadioButton radioButtonSiCodigoApoyo;
    @BindView(R.id.edtCodigoApoyo)
    EditText edtCodigoApoyo;
    @BindView(R.id.container)
    RelativeLayout container;


    @BindView(R.id.radioButtonNoPlaca)
    RadioButton radioButtonNoPlaca;
    @BindView(R.id.radioButtonSiPlaca)
    RadioButton radioButtonSiPlaca;
    @BindView(R.id.edtResistenciaMecanica)
    EditText edtResistenciaMecanica;
    @BindView(R.id.textInputLayoutCodigoApoyo)
    TextInputLayout textInputLayoutCodigoApoyo;
    @BindView(R.id.textInputLayoutResistenciaMecanica)
    TextInputLayout textInputLayoutResistenciaMecanica;

    @BindView(R.id.spinnerMaterial)
    MaterialBetterSpinner spinnerMaterial;
    @BindView(R.id.spinnerEstado)
    MaterialBetterSpinner spinnerEstado;
    @BindView(R.id.spinnerCantidadRetenidas)
    MaterialBetterSpinner spinnerCantidadRetenidas;
    @BindView(R.id.spinnerNivelTension)
    MaterialBetterSpinner spinnerNivelTension;
    @BindView(R.id.spinnerLongitudPoste)
    MaterialBetterSpinner spinnerLongitudPoste;

    ///Direcciones
    @BindView(R.id.spinnerTipoDireccion)
    MaterialBetterSpinner spinnerTipoDireccion;
    @BindView(R.id.spinnerDetalleTipoDireccion)
    MaterialBetterSpinner spinnerDetalleTipoDireccion;

    @BindView(R.id.edtTipoDireccion)
    EditText edtTipoDireccion;
    @BindView(R.id.edtDetalleTipoDireccion)
    EditText edtDetalleTipoDireccion;
    @BindView(R.id.edtReferencia)
    EditText edtReferencia;






    //Declaracion Arrays
    List<Estado> listEstado = new ArrayList<>();
    List<Longitud_Elemento> longitud_elementos = new ArrayList<>();
    List<Material> materials = new ArrayList<>();
    List<Nivel_Tension_Elemento> nivel_tension_elementos = new ArrayList<>();
    List<Cantidad_Retenidas> cantidad_retenidas = new ArrayList<>();
    List<Tipo_Direccion> tipo_direccions = new ArrayList<>();
    List<Detalle_Tipo_Direccion> detalle_tipo_direccions = new ArrayList<>();

    //Variables Globals
    long Material_Id;
    long Nivel_Tension_Elemento_Id;
    long Longitud_Elemento_Id;
    long Cantidad_Retenidas;
    long Estado_Id;
    String fecha;
    Date dateFecha;
    String hora;
    Double Altura_Disponible;
    //Direccion

    String Nombre_Tipo_Direccion;
    String Nombre_Detalle_Tipo_Direccion;

    private static int RESULT_ACTIVITY = 1;

    //Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;
    NovedadController novedadController;
    ElementoController elementoController;
    UsuarioController usuarioController;
    @BindView(R.id.edtAlturaDisponible)
    EditText edtAlturaDisponible;
    @BindView(R.id.ibCalcularAltura)
    ImageButton ibCalcularAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste);
        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();
        loadInformacionMaster();
        obtenerFechayHora();
    }

    //region METHODS


    private void obtenerFechayHora() {
        //Obtener Fecha
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        fecha = dateFormat.format(date);


        //Obtener Hora
        Calendar cal = Calendar.getInstance();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        hora = timeFormat.format(cal.getTime());

    }

    private void loadInformacionMaster() {

        //Listas
        listEstado = sincronizacionGetInformacionController.getListEstados();
        longitud_elementos = sincronizacionGetInformacionController.getListLongitudElemento();
        materials = sincronizacionGetInformacionController.getListMaterial();
        nivel_tension_elementos = sincronizacionGetInformacionController.getListNivel_Tension_Elemento();
        cantidad_retenidas = Cantidad_Retenidas_List.getListCantidadRetenidas();
        tipo_direccions= Tipo_Direccion_List.getListTipo_Direccion();
        detalle_tipo_direccions= Detalle_Tipo_Direccion_List.getListDetalle_Tipo_Direccion();
        //Direcciones


        ///Adapaters
        spinnerEstado.setAdapter(null);
        ArrayAdapter<Estado> estadoArrayAdapter =
                new ArrayAdapter<Estado>(this, android.R.layout.simple_spinner_dropdown_item, listEstado);
        spinnerEstado.setAdapter(estadoArrayAdapter);
        spinnerEstado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Estado_Id = listEstado.get(position).getEstado_Id();
                String nombre = listEstado.get(position).getNombre();
                if (nombre.equals("Malo")) {
                    Intent i = new Intent(PosteActivity.this, NovedadActivity.class);
                    i.putExtra("Nombre", "Estado");
                    //TODO pasar parámetro de que es novedad de código de Apoyo.
                    startActivityForResult(i, 300);
                }
            }
        });


        //Longitud Poste
         /*--------------------------------------------------------------------------------------------*/
        spinnerLongitudPoste.setAdapter(null);
        ArrayAdapter<Longitud_Elemento> longitud_elementoArrayAdapter =
                new ArrayAdapter<Longitud_Elemento>(this, android.R.layout.simple_spinner_dropdown_item, longitud_elementos);
        spinnerLongitudPoste.setAdapter(longitud_elementoArrayAdapter);
        spinnerLongitudPoste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Longitud_Elemento_Id = longitud_elementos.get(position).getLongitud_Elemento_Id();
            }
        });

        //Material
        /*--------------------------------------------------------------------------------------------*/
        spinnerMaterial.setAdapter(null);
        ArrayAdapter<Material> materialArrayAdapter =
                new ArrayAdapter<Material>(this, android.R.layout.simple_spinner_dropdown_item, materials);
        spinnerMaterial.setAdapter(materialArrayAdapter);
        spinnerMaterial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Material_Id = materials.get(position).getMaterial_Id();
            }
        });

        //Nivel de tension
        /*--------------------------------------------------------------------------------------------*/
        spinnerNivelTension.setAdapter(null);
        ArrayAdapter<Nivel_Tension_Elemento> nivel_tension_elementoArrayAdapter =
                new ArrayAdapter<Nivel_Tension_Elemento>(this, android.R.layout.simple_spinner_dropdown_item, nivel_tension_elementos);
        spinnerNivelTension.setAdapter(nivel_tension_elementoArrayAdapter);
        spinnerNivelTension.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Nivel_Tension_Elemento_Id = nivel_tension_elementos.get(position).getNivel_Tension_Elemento_Id();
            }
        });


        //Cantidad retenidas
        /*--------------------------------------------------------------------------------------------*/
        spinnerCantidadRetenidas.setAdapter(null);
        ArrayAdapter<Cantidad_Retenidas> cantidad_retenidasArrayAdapter =
                new ArrayAdapter<Cantidad_Retenidas>(this, android.R.layout.simple_spinner_dropdown_item, cantidad_retenidas);
        spinnerCantidadRetenidas.setAdapter(cantidad_retenidasArrayAdapter);
        spinnerCantidadRetenidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cantidad_Retenidas = cantidad_retenidas.get(position).getCantidad_Retenidas();
            }
        });


        //Direccion
        /*--------------------------------------------------------------------------------------------*/
        spinnerTipoDireccion.setAdapter(null);
        ArrayAdapter<Tipo_Direccion> tipo_direccionArrayAdapter =
                new ArrayAdapter<Tipo_Direccion>(this, android.R.layout.simple_spinner_dropdown_item, tipo_direccions);
        spinnerTipoDireccion.setAdapter(tipo_direccionArrayAdapter);
        spinnerTipoDireccion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Nombre_Tipo_Direccion = tipo_direccions.get(position).getNombre();
            }
        });

        spinnerDetalleTipoDireccion.setAdapter(null);
        ArrayAdapter<Detalle_Tipo_Direccion> detalle_tipo_direccionArrayAdapter =
                new ArrayAdapter<Detalle_Tipo_Direccion>(this, android.R.layout.simple_spinner_dropdown_item, detalle_tipo_direccions);
        spinnerDetalleTipoDireccion.setAdapter(detalle_tipo_direccionArrayAdapter);
        spinnerDetalleTipoDireccion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Nombre_Detalle_Tipo_Direccion = detalle_tipo_direccions.get(position).getNombre();
            }
        });

    }


    private void validacionRegisterElement() {
        if (edtCodigoApoyo.isEnabled() == true) {
            String codigo_apoyo = edtCodigoApoyo.getText().toString();
            boolean cancel = false;
            View focusView = null;
            if (TextUtils.isEmpty(codigo_apoyo)) {
                edtCodigoApoyo.setError(getString(R.string.error_field_required));
                focusView = edtCodigoApoyo;
                cancel = true;
            }
        }
        if (spinnerTipoDireccion.getText().toString().isEmpty()) {
            boolean cancel = false;
            View focusView = null;
            spinnerTipoDireccion.setError(getString(R.string.error_field_required));
            focusView = spinnerTipoDireccion;
            cancel = true;
        }
        if (edtTipoDireccion.getText().toString().isEmpty()) {
            boolean cancel = false;
            View focusView = null;
            edtTipoDireccion.setError(getString(R.string.error_field_required));
            focusView = edtTipoDireccion;
            cancel = true;
        }

        if (edtResistenciaMecanica.isEnabled() == true) {
            String resistencia_mecanica = edtResistenciaMecanica.getText().toString();
            boolean cancel = false;
            View focusView = null;
            if (TextUtils.isEmpty(resistencia_mecanica)) {
                edtResistenciaMecanica.setError(getString(R.string.error_field_required));
                focusView = edtResistenciaMecanica;
                cancel = true;
            }
        }
        if (spinnerMaterial.getText().toString().isEmpty()) {
            boolean cancel = false;
            View focusView = null;
            spinnerMaterial.setError(getString(R.string.error_field_required));
            focusView = spinnerMaterial;
            cancel = true;
        }
        if (spinnerLongitudPoste.getText().toString().isEmpty()) {
            boolean cancel = false;
            View focusView = null;
            spinnerLongitudPoste.setError(getString(R.string.error_field_required));
            focusView = spinnerLongitudPoste;
            cancel = true;
        }
        if (spinnerEstado.getText().toString().isEmpty()) {
            boolean cancel = false;
            View focusView = null;
            spinnerEstado.setError(getString(R.string.error_field_required));
            focusView = spinnerEstado;
            cancel = true;
        }
        if (spinnerCantidadRetenidas.getText().toString().isEmpty()) {
            boolean cancel = false;
            View focusView = null;
            spinnerCantidadRetenidas.setError(getString(R.string.error_field_required));
            focusView = spinnerCantidadRetenidas;
            cancel = true;
        }
        if (spinnerNivelTension.getText().toString().isEmpty()) {
            boolean cancel = false;
            View focusView = null;
            spinnerNivelTension.setError(getString(R.string.error_field_required));
            focusView = spinnerNivelTension;
            cancel = true;
        }
        if (edtAlturaDisponible.getText().toString().isEmpty()) {
            boolean cancel = false;
            View focusView = null;
            edtAlturaDisponible.setError(getString(R.string.error_field_required));
            focusView = edtAlturaDisponible;
            cancel = true;
        } else {
            registerElemento();
        }

    }


    //endregion

    //region SET INJECTION

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Datos Básicos Poste");
    }

    private void setupInjection() {
        this.sincronizacionGetInformacionController = SincronizacionGetInformacionController.getInstance(this);
        this.novedadController = NovedadController.getInstance(this);
        this.elementoController = ElementoController.getInstance(this);
        this.usuarioController = UsuarioController.getInstance(this);
    }
    //endregion

    //region EVENTS
    @OnClick({R.id.radioButtonNoCodigoApoyo, R.id.radioButtonSiCodigoApoyo, R.id.radioButtonNoPlaca, R.id.radioButtonSiPlaca, R.id.ibCalcularAltura})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioButtonNoCodigoApoyo:
                edtCodigoApoyo.setEnabled(false);
                Intent i = new Intent(this, NovedadActivity.class);
                i.putExtra("Nombre", "Codigo Apoyo");
                startActivityForResult(i, 100);
                edtCodigoApoyo.getText().clear();
                break;
            case R.id.radioButtonSiCodigoApoyo:
                edtCodigoApoyo.setEnabled(true);
                textInputLayoutCodigoApoyo.setVisibility(View.VISIBLE);
                break;
            case R.id.radioButtonNoPlaca:
                edtResistenciaMecanica.setEnabled(false);
                Intent j = new Intent(this, NovedadActivity.class);
                j.putExtra("Nombre", "Resistencia Mecanica");
                startActivityForResult(j, 200);
                edtResistenciaMecanica.getText().clear();
                break;
            case R.id.radioButtonSiPlaca:
                textInputLayoutResistenciaMecanica.setVisibility(View.VISIBLE);
                edtResistenciaMecanica.setEnabled(true);
                break;
            case R.id.ibCalcularAltura:
                String packageName = "com.nfa.distancemeter";
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(container, "No puede acceder a la app", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }


    //endregion

    //region ON ACTIVITY RESULT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 100) && (resultCode == RESULT_OK)) {
            Snackbar.make(container, getString(R.string.message_novedad), Snackbar.LENGTH_SHORT).show();
            textInputLayoutCodigoApoyo.setVisibility(View.GONE);
        }
        if ((requestCode == 200) && (resultCode == RESULT_OK)) {
            Snackbar.make(container, getString(R.string.message_novedad), Snackbar.LENGTH_SHORT).show();
            textInputLayoutResistenciaMecanica.setVisibility(View.GONE);
        }
        if ((requestCode == 300) && (resultCode == RESULT_OK)) {
            Snackbar.make(container, getString(R.string.message_novedad), Snackbar.LENGTH_SHORT).show();
        }
    }


    //endregion


    //region METHODS
    public void registerElemento() {
        //TODO Registrar el poste
        Elemento elemento = new Elemento();
        long elemento_id = 0;
        elemento = elementoController.getLast();
        if (elemento == null) {
            elemento = new Elemento();
            elemento_id = 1;
        } else {
            elemento_id = elemento.getElemento_Id() + 1;
        }
        Altura_Disponible = Double.parseDouble(edtAlturaDisponible.getText().toString());

        Usuario usuario = new Usuario();
        usuario = usuarioController.getLoggedUser();
        long id_usuario = usuario.getUsuario_Id();
        elemento.setElemento_Id(elemento_id);
        elemento.setUsuario_Id(id_usuario);
        elemento.setFecha_Levantamiento(fecha);
        elemento.setHora_Inicio(hora);
        elemento.setCodigo_Apoyo(edtCodigoApoyo.getText().toString());
        elemento.setMaterial_Id(Material_Id);
        elemento.setLongitud_Elemento_Id(Longitud_Elemento_Id);
        elemento.setResistencia_Mecanica(edtResistenciaMecanica.getText().toString());
        elemento.setEstado_Id(Estado_Id);
        elemento.setRetenidas(Cantidad_Retenidas);
        elemento.setNivel_Tension_Elemento_Id(Nivel_Tension_Elemento_Id);
        elemento.setAltura_Disponible(Altura_Disponible);
        elemento.setIs_Sync(false);
        elemento.setDireccion(Nombre_Tipo_Direccion+" "+edtTipoDireccion.getText().toString()+" "+Nombre_Detalle_Tipo_Direccion+" "+edtDetalleTipoDireccion.getText().toString());
        elemento.setReferencia_Localizacion(edtReferencia.getText().toString());
        elementoController.register(elemento);
        //Snackbar.make(container, "Poste registrado", Snackbar.LENGTH_SHORT).show();
        Intent i = new Intent(this, CablesElementoActivity.class);
        startActivity(i);
    }

    //endregion

    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poste, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            validacionRegisterElement();
        }
        return super.onOptionsItemSelected(item);
    }


    //endregion

    //region OVERRIDE METHODS
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}






