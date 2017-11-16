package com.datatakehnn.activities.poste.lista_postes_usuario;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.adapter.AdapterCablesElemento;
import com.datatakehnn.activities.poste.lista_postes_usuario.adapter.AdapterElemento;
import com.datatakehnn.activities.poste.lista_postes_usuario.adapter.OnItemClickListenerElemento;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.usuario_model.Usuario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Poste_Usuario_Activity extends AppCompatActivity implements OnItemClickListenerElemento,MainViewPoste,SwipeRefreshLayout.OnRefreshListener {

    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView( R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView( R.id.txtResults)
    TextView txtResults;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    //Instances
    UsuarioController usuarioController;
    ElementoController elementoController;

    //Adapter
    AdapterElemento adapter;

    //Listas
    List<Elemento>elementosList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste__usuario);

        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupInjection();
        setToolbarInjection();
        initAdapter();
        initRecyclerView();
        loadListElementsRegister();
    }

    //region SETUP INJECTION
    private void setupInjection() {
        this.usuarioController= UsuarioController.getInstance(this);
        this.elementoController= ElementoController.getInstance(this);
    }
    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getSupportActionBar() != null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Postes Registrados");
    }
    //endregion

    //region METHODS
    private void initAdapter() {
        if(adapter==null){
            adapter= new AdapterElemento(this, new ArrayList<Elemento>(),this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadListElementsRegister() {
        Usuario usuarioLogued= usuarioController.getLoggedUser();
        adapter.clear();
        elementosList.clear();
        elementosList= elementoController.getListElementsByUserLogued(usuarioLogued.getUsuario_Id());
        setContent(elementosList);
        resultsList(elementosList);
        hideProgress();
    }

    //endregion

    //region IMPLEMENTS METHODS MAINVIEWPOSTE
    @Override
    public void showProgresss() {
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void showUIElements() {

    }

    @Override
    public void hideUIElements() {

    }

    @Override
    public void onMessageOk(int colorPrimary, String message) {
        int color = Color.WHITE;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this,colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done, 0, 0, 0);
        // textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin));
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onMessageError(int colorPrimary, String message) {
        onMessageOk(colorPrimary,message);
    }

    @Override
    public void resultsList(List<Elemento> listResult) {
        String results= String.format(getString(R.string.results_global_search),
                listResult.size());
        txtResults.setText(results);
    }

    @Override
    public void setContent(List<Elemento> items) {
        adapter.setItems(items);
    }
    //endregion

    //region METHODS OVERRIDES
    @Override
    public void onRefresh() {
        showProgresss();
        loadListElementsRegister();
    }
    //endregion

    //region IMPLEMENTS OnItemClickLestenerElemento
    @Override
    public void onItemClick(Elemento elemento) {
        Toast.makeText(this, "Poste: "+elemento.getCodigo_Apoyo(), Toast.LENGTH_SHORT).show();
    }
    //endregion
}
