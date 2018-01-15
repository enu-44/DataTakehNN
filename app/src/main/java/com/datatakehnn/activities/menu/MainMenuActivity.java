package com.datatakehnn.activities.menu;

import android.app.Dialog;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.usuario_model.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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


    //region MENU

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

    //region Binding Drawer Layout
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
