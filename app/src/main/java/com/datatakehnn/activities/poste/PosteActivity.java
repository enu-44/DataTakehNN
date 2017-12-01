package com.datatakehnn.activities.poste;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.CoordsActivity;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.activities.novedad.NovedadActivity;
import com.datatakehnn.activities.perdida.PerdidaActivity;
import com.datatakehnn.activities.sync.SyncActivity;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.NovedadController;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.localizcion_model.Localizacion;
import com.datatakehnn.models.longitud_elemento_model.Longitud_Elemento;
import com.datatakehnn.models.material_model.Material;
import com.datatakehnn.models.nivel_tension_elemento_model.Nivel_Tension_Elemento;
import com.datatakehnn.models.novedad_model.Novedad;
import com.datatakehnn.models.retenidas_model.Cantidad_Retenidas;
import com.datatakehnn.models.tipo_direccion_model.Detalle_Tipo_Direccion;
import com.datatakehnn.models.tipo_direccion_model.Tipo_Direccion;
import com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.apps_integrator.IntentIntegrator;
import com.datatakehnn.services.coords.CoordsService;
import com.datatakehnn.services.data_arrays.Cantidad_Retenidas_List;
import com.datatakehnn.services.data_arrays.Detalle_Tipo_Cable_List;
import com.datatakehnn.services.data_arrays.Detalle_Tipo_Direccion_List;
import com.datatakehnn.services.data_arrays.Tipo_Direccion_List;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PosteActivity extends AppCompatActivity {

    private static final String TAG = "";
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
    @BindView(R.id.tvCodigoApoyo)
    TextView tvCodigoApoyo;
    @BindView(R.id.radioGroupCodigoApoyo)
    RadioGroup radioGroupCodigoApoyo;


    @BindView(R.id.radioGroupPlaca)
    RadioGroup radioGroupPlaca;
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

    @BindView(R.id.tvCoords)
    TextView tvCoords;
    @BindView(R.id.titleCoords)
    TextView titleCoords;
    @BindView(R.id.tvPlaca)
    TextView tvPlaca;

    @BindView(R.id.edtAlturaDisponible)
    EditText edtAlturaDisponible;
    @BindView(R.id.ibCalcularAltura)
    ImageButton ibCalcularAltura;

    //Location
    Location location = new Location("Localizacion");

    //Medidor de diatancias
    private String PACKAGE_NAME = "com.nfa.distancemeter";
    IntentIntegrator intentIntegrator;

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
    long Elemento_Id;
    long Nivel_Tension_Elemento_Id;
    long Longitud_Elemento_Id;
    long Cantidad_Retenidas;
    long Estado_Id;
    String fecha;
    Date dateFecha;
    String hora;
    Double Altura_Disponible;
    //Accion
    boolean ACCION_ADD;
    boolean ACCION_UPDATE;


    //Direccion

    String Nombre_Tipo_Direccion;
    String Nombre_Detalle_Tipo_Direccion;

    private static int RESULT_ACTIVITY = 1;

    //Instances
    SincronizacionGetInformacionController sincronizacionGetInformacionController;
    NovedadController novedadController;
    ElementoController elementoController;
    UsuarioController usuarioController;
    SyncActivity syncActivity;
    public CoordsService coordsService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste);
        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();
        loadInformacionMaster();
        obtenerFechayHora();
        loadDatosPosteUpdate();
    }

    //region METHODS
    private void loadDatosPosteUpdate() {
        if (ACCION_UPDATE) {
            Elemento elementoUpdate = elementoController.getElementoById(Elemento_Id);
            titleCoords.setVisibility(View.GONE);
            tvCoords.setVisibility(View.GONE);
            tvCodigoApoyo.setVisibility(View.GONE);
            radioGroupCodigoApoyo.setVisibility(View.GONE);
            /*tvPlaca.setVisibility(View.GONE);
            radioGroupCodigoApoyo.setVisibility(View.GONE);
            spinnerEstado.setEnabled(false);
            spinnerEstado.setVisibility(View.GONE);*/

            if (!elementoUpdate.getCodigo_Apoyo().equals("")) {
                edtCodigoApoyo.setText(elementoUpdate.getCodigo_Apoyo());
                edtCodigoApoyo.setEnabled(true);
            } else {
                edtCodigoApoyo.setVisibility(View.GONE);
                edtCodigoApoyo.setEnabled(false);
                tvCodigoApoyo.setVisibility(View.VISIBLE);
                tvCodigoApoyo.setText("No tiene Código de Apoyo");
            }

            //Dirección
            Nombre_Tipo_Direccion = elementoUpdate.getNombre_Direccion();
            spinnerTipoDireccion.setText(Nombre_Tipo_Direccion);
            String via = elementoUpdate.getVia();
            edtTipoDireccion.setText(via);
            Nombre_Detalle_Tipo_Direccion = elementoUpdate.getCon();
            spinnerDetalleTipoDireccion.setText(Nombre_Detalle_Tipo_Direccion);
            String descripcion_direccion = elementoUpdate.getDescripcion_Direccion();
            edtDetalleTipoDireccion.setText(descripcion_direccion);
            edtReferencia.setText(elementoUpdate.getReferencia_Localizacion());

            //Material
            Material_Id = elementoUpdate.getMaterial_Id();
            int material_id_integer = (int) (long) Material_Id;
            int positionMaterial = material_id_integer - 1;
            Material material = materials.get(positionMaterial);
            spinnerMaterial.setText(material.getNombre());

            //Longitud Poste
            Longitud_Elemento_Id = elementoUpdate.getLongitud_Elemento_Id();
            int longitud_elemento_id_integer = (int) (long) Longitud_Elemento_Id;
            int positionLongitudElemento = longitud_elemento_id_integer - 1;
            Longitud_Elemento longitud_elemento = longitud_elementos.get(positionLongitudElemento);
            spinnerLongitudPoste.setText(String.valueOf(longitud_elemento.getValor()));


            //Resistencia Mecánica
            tvPlaca.setVisibility(View.GONE);
            radioGroupPlaca.setVisibility(View.GONE);
            if (!elementoUpdate.getResistencia_Mecanica().equals("")) {
                edtResistenciaMecanica.setText(elementoUpdate.getResistencia_Mecanica());
                edtResistenciaMecanica.setEnabled(true);
            } else {
                edtResistenciaMecanica.setVisibility(View.GONE);
                edtResistenciaMecanica.setEnabled(false);
                tvPlaca.setVisibility(View.VISIBLE);
                tvPlaca.setText("No tiene Resistencia Mecánica");
            }

            //Estado
            spinnerEstado.setEnabled(false);
            spinnerEstado.setVisibility(View.GONE);

            //Retenidas
            Cantidad_Retenidas = elementoUpdate.getRetenidas();
            spinnerCantidadRetenidas.setText(String.valueOf(Cantidad_Retenidas));

            //Nivel de Tensión
            Nivel_Tension_Elemento_Id = elementoUpdate.getNivel_Tension_Elemento_Id();
            int nivel_tension_id_integer = (int) (long) Nivel_Tension_Elemento_Id;
            int positionNivelTension = nivel_tension_id_integer - 1;
            Nivel_Tension_Elemento nivel_tension_elemento = nivel_tension_elementos.get(positionNivelTension);
            spinnerNivelTension.setText(nivel_tension_elemento.getNombre());

            //Altura Disponible
            Double altura_disponible = elementoUpdate.getAltura_Disponible();
            edtAlturaDisponible.setText(String.valueOf(altura_disponible));

        }
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


    private void getElementoId() {
        Elemento elemento = elementoController.getLast();
        if (elemento == null) {
            Elemento_Id = 1;
        } else {
            Elemento_Id = elemento.getElemento_Id() + 1;
        }
    }

    private void borrarNovedad(Novedad hayNovedad) {
        final long idHayNovedad = hayNovedad.getNovedad_Id();
        if (hayNovedad != null) {
            novedadController.deleteNovedad(idHayNovedad);
            Snackbar.make(container, "Novedad Borrada", Snackbar.LENGTH_SHORT).show();
        }
    }

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
        tipo_direccions = Tipo_Direccion_List.getListTipo_Direccion();
        detalle_tipo_direccions = Detalle_Tipo_Direccion_List.getListDetalle_Tipo_Direccion();
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
                    startActivityForResult(i, 300);
                } else if (nombre.equals("Bueno")) {
                    getElementoId();
                    Novedad hayNovedadPlaca = novedadController.getNovedadByTipoNombreAndElementoId("Estado", Elemento_Id);
                    if (hayNovedadPlaca != null) {
                        borrarNovedad(hayNovedadPlaca);
                    }
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
        boolean cancel = false;
        View focusView = null;
        if (edtCodigoApoyo.isEnabled() == true && edtCodigoApoyo.getText().toString().isEmpty()) {
            edtCodigoApoyo.setError(getString(R.string.error_field_required));
            focusView = edtCodigoApoyo;
            cancel = true;
        } else if (spinnerTipoDireccion.getText().toString().isEmpty()) {
            spinnerTipoDireccion.setError(getString(R.string.error_field_required));
            focusView = spinnerTipoDireccion;
            cancel = true;
        } else if (edtTipoDireccion.getText().toString().isEmpty()) {
            edtTipoDireccion.setError(getString(R.string.error_field_required));
            focusView = edtTipoDireccion;
            cancel = true;
        } else if (edtTipoDireccion.getText().toString().isEmpty()) {
            edtTipoDireccion.setError(getString(R.string.error_field_required));
            focusView = edtTipoDireccion;
            cancel = true;
        } else if (spinnerMaterial.getText().toString().isEmpty()) {
            spinnerMaterial.setError(getString(R.string.error_field_required));
            focusView = spinnerMaterial;
            cancel = true;
        } else if (spinnerLongitudPoste.getText().toString().isEmpty()) {
            spinnerLongitudPoste.setError(getString(R.string.error_field_required));
            focusView = spinnerLongitudPoste;
            cancel = true;
        } else if (edtResistenciaMecanica.isEnabled() == true && edtResistenciaMecanica.getText().toString().isEmpty()) {
            edtResistenciaMecanica.setError(getString(R.string.error_field_required));
            focusView = edtResistenciaMecanica;
            cancel = true;
        } else if (spinnerEstado.getText().toString().isEmpty() && spinnerEstado.isEnabled()) {
            spinnerEstado.setError(getString(R.string.error_field_required));
            focusView = spinnerEstado;
            cancel = true;
        } else if (spinnerCantidadRetenidas.getText().toString().isEmpty()) {
            spinnerCantidadRetenidas.setError(getString(R.string.error_field_required));
            focusView = spinnerCantidadRetenidas;
            cancel = true;
        } else if (spinnerNivelTension.getText().toString().isEmpty()) {
            spinnerNivelTension.setError(getString(R.string.error_field_required));
            focusView = spinnerNivelTension;
            cancel = true;
        } else if (edtAlturaDisponible.getText().toString().isEmpty()) {
            edtAlturaDisponible.setError(getString(R.string.error_field_required));
            focusView = edtAlturaDisponible;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            registerElemento();
        }
    }


    //Register
    public void registerElemento() {
        //TODO Registrar el poste
        Elemento elemento = new Elemento();
        long elemento_id = 0;
        if (ACCION_ADD) {
            elemento = elementoController.getLast();
            if (elemento == null) {
                elemento = new Elemento();
                elemento_id = 1;
            } else {
                elemento_id = elemento.getElemento_Id() + 1;
            }
        } else if (ACCION_UPDATE) {
            elemento_id = Elemento_Id;
            elemento = elementoController.getElementoById(elemento_id);
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
        elemento.setNumero_Apoyo(elemento_id);
        elemento.setMaterial_Id(Material_Id);
        elemento.setLongitud_Elemento_Id(Longitud_Elemento_Id);
        elemento.setResistencia_Mecanica(edtResistenciaMecanica.getText().toString());
        if (ACCION_ADD) {
            elemento.setEstado_Id(Estado_Id);
        }
        elemento.setRetenidas(Cantidad_Retenidas);
        elemento.setNivel_Tension_Elemento_Id(Nivel_Tension_Elemento_Id);
        elemento.setAltura_Disponible(Altura_Disponible);
        elemento.setIs_Sync(false);
        elemento.setDireccion(Nombre_Tipo_Direccion + " " + edtTipoDireccion.getText().toString() + " " + Nombre_Detalle_Tipo_Direccion + " " + edtDetalleTipoDireccion.getText().toString());
        elemento.setNombre_Direccion(Nombre_Tipo_Direccion);
        elemento.setVia(edtTipoDireccion.getText().toString());
        elemento.setCon(Nombre_Detalle_Tipo_Direccion);
        elemento.setDescripcion_Direccion(edtDetalleTipoDireccion.getText().toString());
        elemento.setReferencia_Localizacion(edtReferencia.getText().toString());
        elemento.setLongitud(location.getLongitude());
        elemento.setLatitud(location.getLatitude());
        elemento.setDepartamento_Id(usuario.getDepartamento_Id());
        elemento.setCiudad_Id(usuario.getCiudad_Id());
        elemento.setNombre_Ciudad(usuario.getNombre_Ciudad());
        elemento.setNombre_Departamento(usuario.getNombre_Departamento());
        elemento.setProyecto_Id(usuario.getProyecto_Id());
        elementoController.register(elemento);
        //Snackbar.make(container, "Poste registrado", Snackbar.LENGTH_SHORT).show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(PosteActivity.this);
        builder.setTitle("Notificación");
        builder.setMessage("¿Confirma todos los datos?");
        final long finalElemento_id = elemento_id;
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((SyncActivity) syncActivity).coordsService.closeService();
                if (ACCION_ADD) {
                    Intent i = new Intent(getApplicationContext(), PerdidaActivity.class);
                    i.putExtra("ACCION_ADD", true);
                    i.putExtra("ACCION_UPDATE", false);
                    i.putExtra("Elemento_Id", finalElemento_id);
                    startActivity(i);
                } else if (ACCION_UPDATE) {
                    onReturnActivity();
                }
            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    //endregion

    //region SET INJECTION

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getSupportActionBar() != null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Datos Básicos Poste");
    }

    private void setupInjection() {
        ACCION_ADD = getIntent().getExtras().getBoolean("ACCION_ADD");
        ACCION_UPDATE = getIntent().getExtras().getBoolean("ACCION_UPDATE");
        Elemento_Id = getIntent().getExtras().getLong("Elemento_Id");
        this.syncActivity = SyncActivity.getInstance(this);
        //Llama la instancia del servicio
        this.intentIntegrator = new IntentIntegrator(this, PACKAGE_NAME);
        //Guarda en un location la ubicación
        //location = servicioUbicacion.getUbicacion();
        /*
        if(((SyncActivity) syncActivity).coordsService.getUbicacion() != null){
            location = ((SyncActivity) syncActivity).coordsService.getUbicacion();
        }else{
            ((SyncActivity) syncActivity).iniciarServicioUbicacion();
            location = ((SyncActivity) syncActivity).coordsService.getUbicacion();
        }*/

        //Si el servicio de ubicacion se ha detenido se arranca nuevamente


        if (((SyncActivity) syncActivity).coordsService.isServiceLocalizacionRun() == false) {

            if (((SyncActivity) syncActivity).coordsService != null) {
                ((SyncActivity) syncActivity).coordsService.closeService();
                Toast.makeText(this, "Cerrar Servicio", Toast.LENGTH_LONG).show();
            }


            this.coordsService = new CoordsService(this);
            Toast.makeText(this, "Iniciando Servicio de Ubicacion", Toast.LENGTH_LONG).show();
        }


        /*
        try {
            latitud = location.getLatitude();
            longitud = location.getLongitude();
        } catch (Exception ex) {
        }
        */
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
                getElementoId();
                Novedad hayNovedadCodigoApoyo = novedadController.getNovedadByTipoNombreAndElementoId("Codigo Apoyo", Elemento_Id);
                if (hayNovedadCodigoApoyo != null) {
                    borrarNovedad(hayNovedadCodigoApoyo);
                }
                /*
                Tipo_Novedad tipo_novedad = novedadController.getTipoNovedadIdByNombre("Codigo Apoyo");
                long id_tipo_novedad = tipo_novedad.getTipo_Novedad_Id();
                Novedad novedad = novedadController.getNovedadByTipoAndElementoId(id_tipo_novedad,)*/
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
                getElementoId();
                Novedad hayNovedadPlaca = novedadController.getNovedadByTipoNombreAndElementoId("Resistencia Mecanica", Elemento_Id);
                if (hayNovedadPlaca != null) {
                    borrarNovedad(hayNovedadPlaca);
                }
                break;
            case R.id.ibCalcularAltura:
                String packageName = PACKAGE_NAME;
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                if (intent != null) {
                    startActivity(intent);
                } else {
                    intentIntegrator.showDownloadDialog();
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


    //region OVERRIDES METHODS


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        unregisterReceiver(mNotificationReceiver);
    }

    //endregion

    //region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poste, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //region MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_done:
                validacionRegisterElement();
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
    //endregion

    //region BroadcastReceiver LOCALIZACION
    ///Escucha Los valores enviados por Serial Port desde Menu Activity
    private BroadcastReceiver mNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Localizacion localizacion = intent.getExtras().getParcelable("localizacion");
            location.setLatitude(localizacion.getLatitud());
            location.setLongitude(localizacion.getLongitud());
            tvCoords.setText(String.valueOf(location.getLatitude()) + " , " + String.valueOf(location.getLongitude()));
            ////   Toast.makeText(PosteActivity.this,""+localizacion.getLatitud()+" , "+localizacion.getLongitud(),Toast.LENGTH_LONG).show();
        }
    };

    /*----------------------------------------------------------------------------------------------------------------------*/
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mNotificationReceiver, new IntentFilter("LOCATION"));

    }
    /*----------------------------------------------------------------------------------------------------------------------*/


    //endregion
}



