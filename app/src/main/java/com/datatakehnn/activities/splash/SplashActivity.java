package com.datatakehnn.activities.splash;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.login.LoginActivity;
import com.datatakehnn.controllers.SettingController;
import com.datatakehnn.models.configuracion_model.Setting;
import com.datatakehnn.services.api_client.routes.Const;
import com.datatakehnn.services.coords.CoordsService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.ivLogo)
    ImageView ivLogo;

    SettingController settingController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        this.settingController = SettingController.getInstance(this);
        loadAnimation();
        guardarIpRuta();
    }


    public void loadAnimation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_transition);
            ivLogo.startAnimation(animation);
            final Intent i = new Intent(this, LoginActivity.class);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(i);
                        finish();
                    }
                }
            };
            timer.start();

        } else {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void guardarIpRuta() {
        Setting setting = settingController.getFirst();
        if (setting == null) {
            setting = new Setting();
            setting.setRuta_Servicio(Const.URL_RouteBaseAddress);
            settingController.registerUpdate(setting);
        }
    }
}
