package com.datatakehnn.activities.menu;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.datatakehnn.R;
import com.datatakehnn.activities.novedad.NovedadActivity;
import com.datatakehnn.activities.poste.PosteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity {

    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        setToolbarInjection();
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
    @OnClick({R.id.imgAddElement, R.id.imgListElement, R.id.imgLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAddElement:
                Intent i = new Intent(this, PosteActivity.class);
                startActivity(i);
                break;
            case R.id.imgListElement:

                break;
            case R.id.imgLogout:

                break;

        }
    }




}
