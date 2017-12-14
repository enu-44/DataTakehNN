package com.datatakehnn.activities.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.master.DeviceMasterActivity;
import com.datatakehnn.activities.sync.SyncActivity;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.empresa_model.Empresa;
import com.datatakehnn.models.proyectos_model.Proyecto;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.reponse_generic.login.Request_Login;
import com.datatakehnn.models.reponse_generic.login.Response_Request_Login;
import com.datatakehnn.models.tipo_usuario_model.Tipo_Usuario;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.aplication.DataTakeApp;
import com.datatakehnn.services.connection_internet.ConnectivityReceiver;
import com.datatakehnn.services.data_arrays.Usuario_List;
import com.datatakehnn.services.api_services.loginservice.ILogin;
import com.datatakehnn.services.api_services.loginservice.LoginApiService;
import com.datatakehnn.services.device_information.Equipment_Identifier;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILogin, ConnectivityReceiver.ConnectivityReceiverListener {

    //UI Elements
    @BindView(R.id.edtUsuario)
    EditText edtUsuario;
    @BindView(R.id.edtContraseña)
    EditText edtContrasena;
    @BindView(R.id.fabLogin)
    FloatingActionButton fabLogin;
    @BindView(R.id.container)
    ScrollView container;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    //Instances
    UsuarioController usuarioController;
    LoginApiService loginApiService;
    Equipment_Identifier equipment_Identifier;

    //Device
    private String Imei;
    private String Phone_Type_Device;
    private String Android_Id;
    private String Software_Version;
    private String Local_Ip_Address;
    private String Android_Version;
    private String MacAddr;
    private String Device_Name;
    private String Direccion_Ip;
    private boolean Estado;

    //Permission
    //public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupInjection();
        verificateLoginIsRemembered();
    }

    //region INJECTION
    private void setupInjection() {
        this.usuarioController = UsuarioController.getInstance(this);
        this.loginApiService = LoginApiService.getInstance(this);
        this.equipment_Identifier = new Equipment_Identifier(this);
    }


    //endregion

    //region METHODS
    // Cargar Datos De prueba

    /*Login Verifique*/
    /*-------------------------------------------------------------------------------------------------------------------*/
    private void verificateLoginIsRemembered() {
        Usuario user = new Usuario();
        user = usuarioController.getFirst();
        if (user != null) {
            if (user.getUsuario_Id() > 0 && user.isRemembered() == true) {
                sendMenu(user);
            }
        } else {
            //Cargar datos de prueba
            //LoadUserFake();
        }
    }

    /*
    //Datos de prueba
    private void LoadUserFake() {
        usuarioController.deleteUsuarios();
        List<Usuario> usuarios = Usuario_List.getListUsuario();
        for (Usuario item : usuarios) {
            usuarioController.register(item);
        }
    }*/


    //Ingresar
    private void attemptLogin() {
        // Reset errors.
        edtUsuario.setError(null);
        edtContrasena.setError(null);
        //mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String usuario = edtUsuario.getText().toString();
        String password = edtContrasena.getText().toString();
        ///String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;


        // Validacion de campos
        if (TextUtils.isEmpty(usuario)) {
            edtUsuario.setError(getString(R.string.error_field_required));
            focusView = edtUsuario;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            edtContrasena.setError(getString(R.string.error_field_required));
            focusView = edtContrasena;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if (checkConnection()) {
                progressBar.setVisibility(View.VISIBLE);
                ///Verificate en servicio rest
                Usuario user = usuarioController.getUsuario(usuario, password);
                if (user != null) {
                    loginSqlite(edtUsuario.getText().toString(), edtContrasena.getText().toString());
                } else {

                    Request_Login request_login = new Request_Login(
                            usuario,
                            password,
                            Imei,
                            Phone_Type_Device,
                            Android_Id,
                            Software_Version,
                            Local_Ip_Address,
                            Android_Version,
                            MacAddr,
                            Device_Name,
                            Direccion_Ip,
                            Estado
                    );
                    loginApiService.postLoginAsync(this, request_login);
                }
                //loginSqlite(edtUsuario.getText().toString(), edtContrasena.getText().toString());
            } else {
                loginSqlite(edtUsuario.getText().toString(), edtContrasena.getText().toString());
            }
        }
    }

    //Mostrar Mensage Snackbar
    /*--------------------------------------------------------------------------------------------------------*/
    private void showSnakBar(int colorPrimary, String message) {
        int color = Color.WHITE;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        //textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_home_bar, 0, 0, 0);
        // textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin));
        textView.setTextColor(color);
        snackbar.show();

    }


    ///Login SQLITE
    private void loginSqlite(String Numero_Cedula, String Password) {
        try {
            Usuario userFirts = new Usuario();
            userFirts = usuarioController.getFirst();
            if (userFirts.getUsuario_Id() > 0) {
                Response response = usuarioController.loginUsuario(Numero_Cedula, Password);
                if (response.IsSuccess) {
                    Usuario user = (Usuario) response.getResult();
                    user.setRemembered(true);
                    usuarioController.update(user);
                    sendMenu(user);
                } else {
                    showSnakBar(R.color.colorAccent, response.getMessage());
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                showSnakBar(R.color.colorAccent, getString(R.string.message_not_connection));
                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Enviar al menu
    private void sendMenu(Usuario usuario) {

        Intent i = new Intent(this, SyncActivity.class);
        i.putExtra("FROM_LOGIN", true);
        i.putExtra("FROM_MENU", false);
        startActivity(i);
    }

    //endregion

    //region EVENTS
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.fabLogin)
    public void onViewClicked() {
        edtUsuario.setError(null);
        edtContrasena.setError(null);
        //mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String usuario = edtUsuario.getText().toString();
        String password = edtContrasena.getText().toString();
        ///String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;


        // Validacion de campos
        if (TextUtils.isEmpty(usuario)) {
            edtUsuario.setError(getString(R.string.error_field_required));
            focusView = edtUsuario;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            edtContrasena.setError(getString(R.string.error_field_required));
            focusView = edtContrasena;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            if (Build.VERSION.SDK_INT >= 23) {
                if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                    requestPermission();
                } else {
                    doPermissionGrantedStuffs();
                }
            } else {
                doPermissionGrantedStuffs();
            }
        }
    }


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
                } /* else if ((Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) ||
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

                } */ else {
                    Toast.makeText(LoginActivity.this,
                            "Permiso denegado", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

   /* private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE );
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

    } */


    /*
    private void checkPermission() {

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_PHONE_STATE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            doPermissionGrantedStuffs();
        }
    } */

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    doPermissionGrantedStuffs();
                } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
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

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permiso Denegado", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }*/

    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // Now read the desired content to a textview.
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        /// String SIMSerialNumber=tm.getSimSerialNumber();
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        /*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

        }*/
        String Device_Id = equipment_Identifier.getDeviceID(telephonyManager, LoginActivity.this);
        String AndroidId = equipment_Identifier.getDeviceUniqueID(LoginActivity.this);

        String Software_V = tm.getDeviceSoftwareVersion();

        /*
        String SIMCountryISO=tm.getSimCountryIso();*/
        String Remote_Ip_Address = equipment_Identifier.NetwordDetect();
        String Local_Ip_Addrss = equipment_Identifier.getLocalIpAddress();
        String Android_Ver = equipment_Identifier.getAndroidVersion();
        String MacAdd = equipment_Identifier.getMacAddr();
        String DeviceName = equipment_Identifier.getDeviceName();
        ////Insertar Datos Equipo Maestro (Equipment master)

        Imei = telephonyManager.getDeviceId();
        Phone_Type_Device = Device_Id;
        Android_Id = AndroidId;
        Software_Version = Software_V;
        Local_Ip_Address = Local_Ip_Addrss;
        Android_Version = Android_Ver;
        MacAddr = MacAdd;
        Device_Name = DeviceName;
        Direccion_Ip = Remote_Ip_Address;
        Estado = false;

        if (Imei.equals("") || Imei == null) {
            Snackbar.make(container, "El dispositivo no cuenta con IMEI, Contacte a su proveedor", Snackbar.LENGTH_SHORT).show();
        } else {
            attemptLogin();
        }
    }


    //endregion

    //region CHECK CONNECTION INTERNET
    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;
        //showSnack(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        DataTakeApp.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            showSnakBar(R.color.colorAccent, getString(R.string.message_connection));
        } else {
            showSnakBar(R.color.colorAccent, getString(R.string.message_not_connection));
        }
    }
    //endregion

    //region API SERVICE
    @Override
    public void processFinishGetLogin(Response_Request_Login response) {
        if (response.isSuccess()) {
            //Snackbar.make(container, "Login OK por Api", Snackbar.LENGTH_SHORT).show();
            //Tipo Usuario
            Tipo_Usuario tipo_usuario_response = response.getResult().getTipo_usuario();
            usuarioController.registerTipoUsuario(tipo_usuario_response);
            //Empresa
            Empresa empresa_response = response.getResult().getEmpresa();
            usuarioController.registerEmpresa(empresa_response);
            //Proyecto
            List<Proyecto> list_proyectos_response = response.getResult().getProyectos();
            for (Proyecto items : list_proyectos_response) {
                usuarioController.registerProyecto(items);
            }

            usuarioController.registerUpdateDevice(response.getResult().getDevice_master());

            Usuario user = response.getResult();
            user.setRemembered(true);
            user.setProyecto_Id(usuarioController.getFirstProyecto().getId());
            usuarioController.register(user);
            progressBar.setVisibility(View.GONE);
            sendMenu(user);
        } else {
            Snackbar.make(container, "Usuario o Contraseña Incorrectos", Snackbar.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
    //endregion
}
