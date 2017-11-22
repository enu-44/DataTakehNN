package com.datatakehnn.activities.menu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.datatakehnn.R;
import com.datatakehnn.activities.CoordsActivity;
import com.datatakehnn.activities.ciudad.CiudadActivity;
import com.datatakehnn.activities.fotos.CamaraActivity;
import com.datatakehnn.activities.login.LoginActivity;
import com.datatakehnn.activities.novedad.NovedadActivity;
import com.datatakehnn.activities.poste.PosteActivity;
import com.datatakehnn.activities.poste.lista_postes_usuario.Poste_Usuario_Activity;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.usuario_model.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity {

    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;


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
    }

    private void setupInjection() {
        this.usuarioController= UsuarioController.getInstance(this);
        usuarioLogued= usuarioController.getLoggedUser();
    }

    //region SETUP INJECTION
    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//devolver
        toolbar.setTitle(getString(R.string.title_menu));
    }

    //endregion


    //region EVENTS
    @OnClick({R.id.imgAddElement, R.id.imgListElement, R.id.imgLogout,R.id.imgCiudad})
    public void onViewClicked(View view) {
        Intent i = null;
        switch (view.getId()) {
            case R.id.imgAddElement:
                if(usuarioLogued.getCiudad_Id()>0 && usuarioLogued.getDepartamento_Id()>0){
                    i = new Intent(this, PosteActivity.class);
                }else{
                    i=  new Intent(this, CiudadActivity.class);
                }
                break;
            case R.id.imgListElement:
               i= new Intent(this, Poste_Usuario_Activity.class);
                break;
            case R.id.imgLogout:
                showExit();
                break;
            case R.id.imgCiudad:
                i=  new Intent(this, CiudadActivity.class);
                break;
        }

        if(i!=null){
            startActivity(i);
        }
    }

    //endregion

    //region UI Elements

    public Dialog showExit(){
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
        builder.setIcon(R.drawable.logo_datatakeh);
        return builder.show();
    }

    //endregion

}
