package com.datatakehnn.activities.menu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.CoordsActivity;
import com.datatakehnn.activities.ciudad.CiudadActivity;
import com.datatakehnn.activities.configuration.SettingsActivity;
import com.datatakehnn.activities.fotos.CamaraActivity;
import com.datatakehnn.activities.login.LoginActivity;
import com.datatakehnn.activities.master.DeviceMasterActivity;
import com.datatakehnn.activities.novedad.NovedadActivity;
import com.datatakehnn.activities.poste.PosteActivity;
import com.datatakehnn.activities.poste.lista_postes_usuario.Poste_Usuario_Activity;
import com.datatakehnn.activities.sync.SyncActivity;
import com.datatakehnn.activities.sync.post_sync_activity.UploadDataActivity;
import com.datatakehnn.config.DataSource;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.api_client.retrofit.ApiClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "";
    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;


    //Instances
    UsuarioController usuarioController;
    private Usuario usuarioLogued;

    //Permission
    //public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    //BACKUP DATABASE
    private static  String Directori_Backup_DataBase= "BackupDatatake";
    private static  String Directori_Restore_DataBase= "RestoreDatatake";

    private static final int READ_REQUEST_CODE = 42;


    private static  boolean IS_IMPORT = false;
    private static  boolean IS_EXPORT = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();
        setNavDrawerInjection();

    }

    private void setupInjection() {
        this.usuarioController = UsuarioController.getInstance(this);
        usuarioLogued = usuarioController.getLoggedUser();

    }




    //region /*MENU*/
    /*-------------------------------------------------------------------------------------------------------*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sync_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_sync:
                Intent j = new Intent(getApplicationContext(), UploadDataActivity.class);
                startActivity(j);
                break;
            case R.id.action_pdf:
                Uri uri = Uri.parse("http://34.234.94.92/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
               /* WebView webview = new WebView(this);
                setContentView(webview);
                webview.loadUrl("http://34.234.94.92/"); */
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

            case R.id.action_exportar:
                IS_IMPORT=false;
                IS_EXPORT=true;

                if (Build.VERSION.SDK_INT >= 23) {
                    if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                        requestPermission();
                    } else {
                        boolean response= doPermissionGrantedStuffs();
                        if(response){
                            startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), READ_REQUEST_CODE);
                        }

                    }
                } else {
                    boolean response= doPermissionGrantedStuffs();
                    if(response){
                        startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), READ_REQUEST_CODE);
                    }
                }
                break;

            case R.id.action_importar:

                IS_IMPORT=true;
                IS_EXPORT=false;

                if (Build.VERSION.SDK_INT >= 23) {
                    if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                        requestPermission();
                    } else {
                        boolean response= doPermissionGrantedStuffs();
                        if(response){
                            performFileSearch();
                        }

                    }
                } else {
                    boolean response= doPermissionGrantedStuffs();
                    if(response){
                        performFileSearch();
                    }
                }



               // startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), READ_REQUEST_CODE);

               // startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), READ_REQUEST_CODE);

                // performFileSearch();

                /*if (Build.VERSION.SDK_INT >= 23) {
                    if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                        requestPermission();
                    } else {
                        boolean response= doPermissionGrantedStuffs();
                        if(response){
                            importDB();
                        }
                    }
                } else {
                    boolean response= doPermissionGrantedStuffs();
                    if(response){
                        importDB();
                    }
                }*/
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_configuration) {
            // Handle the camera action
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_syncronization) {
            startActivity(new Intent(getBaseContext(), SyncActivity.class)
                    .putExtra("FROM_LOGIN", false)
                    .putExtra("FROM_MENU", true)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        } else if (id == R.id.nav_device) {

            Intent i = new Intent(this, DeviceMasterActivity.class);
            startActivity(i);
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    //endregion


    //region PERMISOS


    ///PERMISOS
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void requestPermission() {

        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doPermissionGrantedStuffs();
                }

                /* else if ((Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) ||
                        (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[1]))) {
                    //Toast.makeText(MainActivity.this, "Go to Settings and Grant the permission to use this feature.", Toast.LENGTH_SHORT).show();
                    // User selected the Never Ask Again Option
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    getApplicationContext().startActivity(i);

                } */
                else {
                    Toast.makeText(MainMenuActivity.this,
                            "Permiso denegado", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public boolean  doPermissionGrantedStuffs() {

        /// String SIMSerialNumber=tm.getSimSerialNumber();
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                boolean response= false;
                return response;
            }
        }



        boolean response= true;
        return response;
    }

    //endregion


    //region METHODS

    public void performFileSearch() {


        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.

            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());

                File file=new File(uri.getPath());

                String path= file.getAbsolutePath();

                if(IS_EXPORT){
                    exportDB(path);
                }else{
                    importDB(path);
                }


              //  Toast.makeText(this,"Uri: "+directory,Toast.LENGTH_LONG).show();
                //showImage(uri);
            }
        }
    }



    private void exportDB(String path) {
        // TODO Auto-generated method stub
        try {
            String[] posicion;
            posicion = path.split(":");

            String directory="";
            if (posicion.length > 1) {
                directory=posicion[1];
            }


            File sd = Environment.getExternalStorageDirectory();
            //File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                /*String currentDBPath = "//data//" + "com.datatakehnn"
                        + "//databases//" + "db_datatake.db";*/
                String currentDBPath = String.format("%s%s",String.valueOf(getDatabasePath(DataSource.NAME)),".db");

                /*
                File file = new File(currentDBPath);
                boolean res=false;

                if (file.exists()) {
                    res= true;

                }else{
                    res= false;
                }*/

               // createDirIfNotExists(Directori_Backup_DataBase);

                String backupDBPath = directory+"/db_datatake.db";


                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

                Toast.makeText(getBaseContext(), "Se exporto correctamente en: "+backupDBPath,
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }

        /*try {
            final String database = String.format("%s%s",String.valueOf(getDatabasePath(DataSource.NAME)),".db");
            //final String inFileName = "/data/data/com.datatakehnn/databases/db_datatake";
            File dbFile = new File(database);
            FileInputStream fis = new FileInputStream(dbFile);

            //String backupDBPath = "/BackupDataTake";


            String outFileName = Environment.getExternalStorageDirectory()  + "/db_datatake_copy.db";

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        } */

    }
    //importing database
    private void importDB(String path) {
        // TODO Auto-generated method stub

        /*
        String backupDBPath = "/BackupDatatake/db_datatake.db";
        try {

            boolean response= importDatabase(backupDBPath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/



        // TODO Auto-generated method stub

        try {

            String[] posicion;
            posicion = path.split(":");

            String directory="";
            if (posicion.length > 1) {
                directory=posicion[1];
            }

            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {

                //String currentDBPath= "/data/data/com.datatakehnn/databases/db_datatake.db";
                String  currentDBPath=  String.format("%s%s",String.valueOf(getDatabasePath(DataSource.NAME)),".db");

                File file = new File(currentDBPath);
                boolean res=false;

                if (file.exists()) {
                    res= true;

                }else{
                    res= false;
                }

                String backupDBPath = "/"+directory;
                File  backupDB= new File(currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

                Toast.makeText(getBaseContext(),"DB Imported Succesfult",
                        Toast.LENGTH_LONG).show();

                restart(this,1);


            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }


    }

    public static void restart(Context context, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        Log.e("", "restarting app");
        Intent restartIntent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName() );
        @SuppressLint("WrongConstant") PendingIntent intent = PendingIntent.getActivity(
                context, 0,
                restartIntent, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
        System.exit(2);
    }

    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }else{
            File folder = new File(Environment.getExternalStorageDirectory().toString()+path);
            folder.mkdirs();
        }
        return ret;
    }


    //endregion

    //region SETUP INJECTION
    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//devolver
        toolbar.setTitle(getString(R.string.title_menu));
    }

    private void setNavDrawerInjection() {
        long empresaId = usuarioLogued.getEmpresa_Id();
        long ciudadId = usuarioLogued.getCiudad_Id();
        long departamentoId = usuarioLogued.getDepartamento_Id();
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        mActionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        View header = navigationView.getHeaderView(0);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(header);
        headerViewHolder.tvNombreUsuario.setText(usuarioLogued.getNombre() + " " + usuarioLogued.getApellido());
        headerViewHolder.tvCCUsuario.setText("C.C " + usuarioLogued.getCedula());
        headerViewHolder.tvEmpresaUsuario.setText(usuarioController.getEmpresaById(empresaId).getNombre());
        if (ciudadId != 0 && departamentoId != 0) {
            headerViewHolder.tvCiudadUsuario.setText(usuarioController.getCiudadById(ciudadId).getNombre() + ","
                    + usuarioController.getDepartamentoById(departamentoId).getNombre());
        }
    }
    //endregion


    //region EVENTS
    @OnClick({R.id.imgAddElement, R.id.imgListElement, R.id.imgLogout, R.id.imgCiudad})
    public void onViewClicked(View view) {
        Intent i = null;
        switch (view.getId()) {
            case R.id.imgAddElement:
                usuarioLogued = usuarioController.getLoggedUser();
                ///Toast.makeText(this,usuarioLogued.getDepartamento_Id()+" - "+usuarioLogued.getCiudad_Id(),Toast.LENGTH_LONG).show();
                if (usuarioLogued.getCiudad_Id() > 0 && usuarioLogued.getDepartamento_Id() > 0) {
                    i = new Intent(this, PosteActivity.class);
                    i.putExtra("ACCION_ADD", true);
                    i.putExtra("ACCION_UPDATE", false);
                } else {
                    i = new Intent(this, CiudadActivity.class);
                }
                break;
            case R.id.imgListElement:
                i = new Intent(this, Poste_Usuario_Activity.class);
                break;
            case R.id.imgLogout:
                showExit();
                break;
            case R.id.imgCiudad:
                i = new Intent(this, CiudadActivity.class);
                break;
        }

        if (i != null) {
            startActivity(i);
        }
    }

    //endregion

    //region UI Elements

    public Dialog showExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmacion");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Dialogos", "Confirmacion Cancelada.");
            }
        });
        builder.setMessage("¿Cerrar Sesión?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                usuarioController.logoutLogin();
                startActivity(new Intent(getBaseContext(), LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        builder.setIcon(R.drawable.logo_datatakeh_nuevo);
        return builder.show();
    }

//endregion

//region  Binding Drawer Layout
protected static class HeaderViewHolder {

    @BindView(R.id.tvNombreUsuario)
    TextView tvNombreUsuario;
    @BindView(R.id.tvCCUsuario)
    TextView tvCCUsuario;
    @BindView(R.id.tvEmpresaUsuario)
    TextView tvEmpresaUsuario;
    @BindView(R.id.tvProyectoUsuario)
    TextView tvProyectoUsuario;
    @BindView(R.id.tvCiudadUsuario)
    TextView tvCiudadUsuario;

    HeaderViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}

//endregion

}
