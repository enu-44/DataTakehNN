package com.datatakehnn.activities.perdida;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.controllers.ElementoController;

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
    @BindView(R.id.edtLamparaAdicional)
    EditText edtLamparaAdicional;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdida);
        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();
    }


    //region CLICKS EN ITEMS
    @OnClick({R.id.radioButtonNoLamparaAdicional, R.id.radioButtonSiLamparaAdicional, R.id.radioButtonNoLamparaEncendidaDia, R.id.radioButtonSiLamparaEncendidaDia, R.id.radioButtonNoConexionIlicita, R.id.radioButtonSiConexionIlicita, R.id.radioButtonNoPoda, R.id.radioButtonSiPoda})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioButtonNoLamparaAdicional:
                break;
            case R.id.radioButtonSiLamparaAdicional:
                break;
            case R.id.radioButtonNoLamparaEncendidaDia:
                break;
            case R.id.radioButtonSiLamparaEncendidaDia:
                break;
            case R.id.radioButtonNoConexionIlicita:
                break;
            case R.id.radioButtonSiConexionIlicita:
                break;
            case R.id.radioButtonNoPoda:
                break;
            case R.id.radioButtonSiPoda:
                break;
        }
    }

    //endregion

    //region SETUP INJECTION
    private void setupInjection() {
        this.elementoController = elementoController.getInstance(this);
    }

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Pérdidas");
    }

    //endregion

    //region MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perdidas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            //TODO
            Intent i = new Intent(this, CablesElementoActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
