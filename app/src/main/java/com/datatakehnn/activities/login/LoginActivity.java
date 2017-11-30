package com.datatakehnn.activities.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.activities.sync.SyncActivity;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.empresa_model.Empresa;
import com.datatakehnn.models.proyectos_model.Proyecto;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.reponse_generic.login.Response_Request_Login;
import com.datatakehnn.models.tipo_usuario_model.Tipo_Usuario;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.aplication.DataTakeApp;
import com.datatakehnn.services.connection_internet.ConnectivityReceiver;
import com.datatakehnn.services.data_arrays.Usuario_List;
import com.datatakehnn.services.loginservice.ILogin;
import com.datatakehnn.services.loginservice.LoginApiService;

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
            LoadUserFake();
        }
    }

    //Datos de prueba
    private void LoadUserFake() {
        usuarioController.deleteUsuarios();
        List<Usuario> usuarios = Usuario_List.getListUsuario();
        for (Usuario item : usuarios) {
            usuarioController.register(item);
        }
    }


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
                ///Verificate en servicio rest
                Usuario user = usuarioController.getUsuario(usuario, password);
                if (user != null) {
                    loginSqlite(edtUsuario.getText().toString(), edtContrasena.getText().toString());
                } else {
                    loginApiService.getLoginAsync(this, usuario, password);
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
        attemptLogin();
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

            Usuario user = response.getResult();
            user.setRemembered(true);
            user.setProyecto_Id(usuarioController.getFirstProyecto().getId());
            usuarioController.register(user);

            sendMenu(user);
        } else {
            Snackbar.make(container, "Usuario o Contraseña Incorrectos", Snackbar.LENGTH_SHORT).show();
        }

    }
    //endregion

}
