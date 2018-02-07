package com.datatakehnn.activities.configuration;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.sync.SyncActivity;
import com.datatakehnn.controllers.SettingController;
import com.datatakehnn.models.configuracion_model.Setting;
import com.datatakehnn.models.storage_model.Storage;
import com.datatakehnn.services.api_client.retrofit.ApiClient;
import com.datatakehnn.services.data_arrays.Storage_List;
import com.google.android.gms.common.api.Api;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.spinner_almacenamiento)
    Spinner spinner_almacenamiento;


    @BindView(R.id.switchWifi)
    Switch switchWifi;

    @BindView(R.id.switchDatos)
    Switch switchDatos;

    @BindView(R.id.txtFechaUpdate)
    TextView txtFechaUpdate;

    @BindView(R.id.container)
    RelativeLayout container;


    SettingController settingController;
    ApiClient apiClient;

    Setting setting = new Setting();

    //Variables
    String Sigla_Storage = "";
    String Descripcion_Storage = "";
    boolean Available_Wifi;
    boolean Available_Datos;

    String ip_servicio;

    private final static String NOMBRE_DIRECTORIO = "/INMUNIZADOR";
    @BindView(R.id.edtIpServicios)
    EditText edtIpServicios;
    @BindView(R.id.edtContrasenaServicios)
    EditText edtContrasenaServicios;
    @BindView(R.id.btnHabilitarServicio)
    Button btnHabilitarServicio;
    @BindView(R.id.btnValidarContrasena)
    Button btnValidarContrasena;

    @BindView(R.id.textInputLayoutIpServicios)
    TextInputLayout textInputLayoutIpServicios;
    @BindView(R.id.textInputLayoutContrasenaServicios)
    TextInputLayout textInputLayoutContrasenaServicios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();
    }

    private void setupInjection() {

        this.settingController = SettingController.getInstance(this);
        this.apiClient = ApiClient.getInstance(this);

        spinner_almacenamiento.setEnabled(false);
        spinner_almacenamiento.setClickable(false);

        final List<Storage> list_spinner_almacenamiento = Storage_List.getListStorage();
        ArrayAdapter<Storage> arrayAdapter;
        spinner_almacenamiento.setAdapter(null);
        arrayAdapter =
                new ArrayAdapter<Storage>(this, android.R.layout.simple_spinner_dropdown_item, list_spinner_almacenamiento);
        spinner_almacenamiento.setAdapter(arrayAdapter);


        setting = settingController.getFirst();

        if (setting != null) {
            switchWifi.setChecked(setting.isAvailable_Wifi());
            switchDatos.setChecked(setting.isAvailable_Mobile_Data());
            txtFechaUpdate.setText(setting.getFecha());
            Available_Wifi = setting.isAvailable_Wifi();
            Available_Datos = setting.isAvailable_Mobile_Data();
            //Sigla_Storage = setting.getSigla_Storage();
            //Descripcion_Storage = setting.getDescripcion_Storage_Phone();
            //spinner_almacenamiento.setText(setting.getDescripcion_Storage_Phone());
        }

        //Spinner
        /*
        spinner_almacenamiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (list_spinner_almacenamiento.get(position).getSigla().equals("Externo")) {
                    Sigla_Storage = list_spinner_almacenamiento.get(position).getSigla();
                    Descripcion_Storage = list_spinner_almacenamiento.get(position).getDescripcion();



                    if (isExternalStorageWritable()) {
                        //grabar();
                        startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), 42);
                    }

                    // boolean sdDisponible = false;
                    //  boolean sdAccesoEscritura = false;
                    //Comprobamos el estado de la memoria externa (tarjeta SD)
                    // String estado = Environment.getExternalStorageState();


                    ////  boolean sd= hasStorage(true);
                    //  triggerStorageAccessFramework();


                    /// handleExternalStorageState(mExternalStorageAvailable,mExternalStorageWriteable);

                   // if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))   {
                       // Log.i("SDCARDINFO","SD Card se encuentra presente.");

                    //} else {
                        //Log.i("SDCARDINFO","No se encuentra SD Card.");
                    //}



                    //String extStore = System.getenv("EXTERNAL_STORAGE");
                    //File f_exts = new File(extStore);

                    //String strSDCardPathgg = System.getenv("EXTERNAL_SDCARD_STORAGE");


                    //String secStore = System.getenv("SECONDARY_STORAGE");
                    //File f_secs = new File(secStore);

                   if (estado.equals(Environment.MEDIA_MOUNTED))
                    {
                        sdDisponible = true;
                        sdAccesoEscritura = true;
                        Sigla_Storage= list_spinner_almacenamiento.get(position).getSigla();
                        Descripcion_Storage=list_spinner_almacenamiento.get(position).getDescripcion();


                    }
                    else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
                    {
                        sdDisponible = true;
                        sdAccesoEscritura = false;
                        onMessageOk(R.color.orange,"Tarjeta SD, no admite escritura");
                        spinner_almacenamiento.setText("Almacenamiento Interno");
                        Sigla_Storage= "Interno";
                        Descripcion_Storage="Almacenamiento Interno";
                    }
                    else
                    {
                        sdDisponible = false;
                        sdAccesoEscritura = false;
                        onMessageOk(R.color.orange,"Tarjeta SD, no disponible");
                        spinner_almacenamiento.setText("Almacenamiento Interno");
                        Sigla_Storage= "Interno";
                        Descripcion_Storage="Interno";
                    }

                } else {
                    Sigla_Storage = list_spinner_almacenamiento.get(position).getSigla();
                    Descripcion_Storage = list_spinner_almacenamiento.get(position).getDescripcion();
                }
            }
        }); */
    }

    /* Checks if external storage is available for read and write */
    /*
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    } */

    /* Checks if external storage is available to at least read */
    /*
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    } */


/*
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public final void onActivityResult(final int requestCode, final int resultCode, final Intent resultData) {
        if (requestCode == 1) {
            Uri treeUri = null;
            if (resultCode == Activity.RESULT_OK) {
                // Get Uri from Storage Access Framework.
                treeUri = resultData.getData();

                // Persist URI in shared preference so that you can use it later.
                // Use your own framework here instead of PreferenceUtil.
                //PreferenceUtil.setSharedPreferenceUri(R.string.key_internal_uri_extsdcard, treeUri);

                // Persist access permissions.
                final int takeFlags = resultData.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                this.getContentResolver().takePersistableUriPermission(treeUri, takeFlags);

                //grabar(treeUri.toString());
            }
        }
    }*/


    /* ///ALMACENAMIENTO
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode != RESULT_OK)
            return;
        Uri treeUri = resultData.getData();
        DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);
        grantUriPermission(getPackageName(), treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        writeFile(pickedDir);
    }

    public void writeFile(DocumentFile pickedDir) {
        try {
            DocumentFile file = pickedDir.createFile("image/jpeg", "try2.jpg");
            OutputStream out = getContentResolver().openOutputStream(file.getUri());
            try {

                // write the image content

            } finally {
                out.close();
            }

        } catch (IOException e) {
            throw new RuntimeException("Something went wrong : " + e.getMessage(), e);
        }
    } */


/*
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public final void onActivityResult(final int requestCode, final int resultCode, final Intent resultData) {
        if (requestCode == 1) {
            Uri treeUri = null;
            if (resultCode == Activity.RESULT_OK) {
                // Get Uri from Storage Access Framework.
                treeUri = resultData.getData();

                // Persist URI in shared preference so that you can use it later.
                // Use your own framework here instead of PreferenceUtil.
                //PreferenceUtil.setSharedPreferenceUri(R.string.key_internal_uri_extsdcard, treeUri);

                // Persist access permissions.
                final int takeFlags = resultData.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                this.getContentResolver().takePersistableUriPermission(treeUri, takeFlags);

                //grabar(treeUri.toString());
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void triggerStorageAccessFramework() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, 1);
    }


    public static String[] getStorageDirectories(Context pContext)
    {
        // Final set of paths
        final Set<String> rv = new HashSet<>();

        //Get primary & secondary external device storage (internal storage & micro SDCARD slot...)
        File[]  listExternalDirs = ContextCompat.getExternalFilesDirs(pContext, null);
        for(int i=0;i<listExternalDirs.length;i++){
            if(listExternalDirs[i] != null) {
                String path = listExternalDirs[i].getAbsolutePath();
                int indexMountRoot = path.indexOf("/Android/data/");
                if(indexMountRoot >= 0 && indexMountRoot <= path.length()){
                    //Get the root path for the external directory
                    rv.add(path.substring(0, indexMountRoot));
                }
            }
        }
        return rv.toArray(new String[rv.size()]);
    }


    /*
    public void grabar() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        String nomarchivo = "Externo2";
        String contenido = "Hola";
        try {
            //String extStore = System.getenv("EXTERNAL_STORAGE");
            //File tarjeta = new File(extStore);

            String[] rutas= getStorageDirectories(getApplicationContext());
            String SD="";
            String INTERNO="";

            for(int i =0;i<rutas.length;i++){
                if(rutas[i].equals("/storage/emulated/0")){
                    INTERNO=rutas[i];
                }else{
                    SD=rutas[i];
                }
            }

           // String secStore = System.getenv("SECONDARY_STORAGE");
            File tarjeta = new File(SD);


            //File tarjeta = Environment.getExternalStorageDirectory();
            Toast.makeText(this,tarjeta.getAbsolutePath(),Toast.LENGTH_LONG).show();
            File file = new File(tarjeta.getAbsolutePath(), nomarchivo);
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(file));
            osw.write(contenido);
            osw.flush();
            osw.close();
            Toast.makeText(this, "Los datos fueron grabados correctamente",
                    Toast.LENGTH_SHORT).show();

        } catch (IOException ioe) {
            Toast.makeText(this, "No se pudo grabar",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*


    static public boolean hasStorage(boolean requireWriteAccess) {
        //TODO: After fix the bug,  add "if (VERBOSE)" before logging errors.
        String state = Environment.getExternalStorageState();
        Log.v(TAG, "storage state is " + state);
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (requireWriteAccess) {
                boolean writable = checkFsWritable();
                Log.v(TAG, "storage writable is " + writable);
                return writable;
            } else {
                return true;
            }
        } else if (!requireWriteAccess && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }



    private static boolean checkFsWritable() {
        // Create a temporary file to see whether a volume is really writeable.
        // It's important not to put it in the root directory which may have a
        // limit on the number of files.
        String directoryName = Environment.getExternalStorageDirectory().toString() + "/SDCARD";
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        return directory.canWrite();
    }

    public boolean externalMemoryAvailable() {
        if (Environment.isExternalStorageRemovable()) {
            //device support sd card. We need to check sd card availability.
            String state = Environment.getExternalStorageState();
            return state.equals(Environment.MEDIA_MOUNTED) || state.equals(
                    Environment.MEDIA_MOUNTED_READ_ONLY);
        } else {
            //device not support sd card.
            return false;
        }
    }
 */

    @OnCheckedChanged(R.id.switchWifi)
    public void onWifiSelected(CompoundButton button, boolean isChecked) {
        //do your stuff.
        if (isChecked) {
            Available_Wifi = true;
            //showSnakBar(R.color.colorPrimary," Asistant: "+ String.valueOf(Is_Asistant_Workshop));
        } else {
            Available_Wifi = false;
            //showSnakBar(R.color.colorPrimary," Asistant: "+ String.valueOf(Is_Asistant_Workshop));
        }
    }

    @OnCheckedChanged(R.id.switchDatos)
    public void onDatosSelected(CompoundButton button, boolean isChecked) {
        //do your stuff.
        if (isChecked) {
            Available_Datos = true;
            //showSnakBar(R.color.colorPrimary," Asistant: "+ String.valueOf(Is_Asistant_Workshop));
        } else {
            Available_Datos = false;
            //showSnakBar(R.color.colorPrimary," Asistant: "+ String.valueOf(Is_Asistant_Workshop));
        }
    }


    //region SETUP INJECTION
    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getString(R.string.title_ubicacion_ciudad));
        if (getSupportActionBar() != null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_done:

                if (setting != null) {
                    setting.setDescripcion_Storage_Phone(Descripcion_Storage);
                    setting.setSigla_Storage(Sigla_Storage);
                    setting.setAvailable_Mobile_Data(Available_Datos);
                    setting.setAvailable_Wifi(Available_Wifi);
                    if (!edtIpServicios.getText().toString().isEmpty() && (edtIpServicios.getText().toString().startsWith("http://") || edtIpServicios.getText().toString().startsWith("https://"))) {
                        //ip_servicio = edtIpServicios.getText().toString();
                        setting.setRuta_Servicio(edtIpServicios.getText().toString());
                    }
                    settingController.registerUpdate(setting);
                    apiClient.setRoute(setting.getRuta_Servicio());
                    apiClient.BASE_URL = setting.getRuta_Servicio();
                    onMessageOk(R.color.orange, "Guardado Correctamente");
                    //onReturnActivity();
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);

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

    private void onReturnActivity() {
        // Obtener intent de la actividad padre
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Comprobar si DetailActivity no se creó desde CourseActivity
        if (NavUtils.shouldUpRecreateTask(this, upIntent)
                || this.isTaskRoot()) {

            // Construir de nuevo la tarea para ligar ambas actividades
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                android.support.v4.app.TaskStackBuilder.create(this)
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

    @OnClick({R.id.btnHabilitarServicio, R.id.btnValidarContrasena})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btnHabilitarServicio:
                //edtIpServicios.setEnabled(true);
                textInputLayoutContrasenaServicios.setVisibility(View.VISIBLE);
                btnValidarContrasena.setVisibility(View.VISIBLE);
                String ruta_actual = apiClient.BASE_URL;
                Toast.makeText(this, ruta_actual, Toast.LENGTH_LONG).show();
                break;
            case R.id.btnValidarContrasena:
                if (edtContrasenaServicios.getText().toString().equals("adecaso-datatake") && !edtContrasenaServicios.getText().toString().isEmpty()) {
                    edtIpServicios.setEnabled(true);
                    edtIpServicios.setText("http://");
                    btnValidarContrasena.setVisibility(View.GONE);
                    textInputLayoutContrasenaServicios.setVisibility(View.GONE);

                } else {
                    Snackbar.make(container, "Contraseña Incorrecta", Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }





        /*if (!edtIpServicios.getText().toString().isEmpty() && edtIpServicios.getText().toString().startsWith("http://") ) {
            ip_servicio = edtIpServicios.getText().toString();
        } else {
            Snackbar.make(container, "Ip incorrecta, ingresar de nuevo ", Snackbar.LENGTH_SHORT).show();
        }*/
    }
}
