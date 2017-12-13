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
import com.datatakehnn.services.coords.CoordsService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.ivLogo)
    ImageView ivLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        loadAnimation();
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

        }else{
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }



    }
}
