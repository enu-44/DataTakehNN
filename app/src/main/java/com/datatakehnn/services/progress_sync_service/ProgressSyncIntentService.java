package com.datatakehnn.services.progress_sync_service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.datatakehnn.controllers.ElementoController;

/**
 * Created by usuario on 5/12/2017.
 */

public class ProgressSyncIntentService extends IntentService {
    private static final String TAG = ProgressSyncIntentService.class.getSimpleName();
    ElementoController elementoController;

    NotificationCompat.Builder builder= new NotificationCompat.Builder(this);

    public ProgressSyncIntentService() {
        super("ProgressIntentService");
        this.elementoController= ElementoController.getInstance(this);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.ACTION_RUN_ISERVICE.equals(action)) {
                handleActionRun();
            }
        }
    }


    /**
     * Maneja la acción de ejecución del servicio
     */
    private void handleActionRun() {



        try {

            // Se construye la notificación
                   builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setContentTitle("Servicio en segundo plano")
                    .setContentText("Procesando...");




            // Bucle de simulación
            for (int i = 1; i <= 20; i++) {

                Log.d(TAG, i + ""); // Logueo

                // Poner en primer plano
                builder.setProgress(20, i, false);
                startForeground(1, builder.build());

                Intent localIntent = new Intent(Constants.ACTION_RUN_ISERVICE)
                        .putExtra("hora",elementoController.getFirst().getHora_Inicio())
                        .putExtra(Constants.EXTRA_PROGRESS, i);

                // Emisión de {@code localIntent}
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

                // Retardo de 1 segundo en la iteración
                Thread.sleep(1000);
            }


            closedNotify();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closedNotify() {
        // Quitar de primer plano
        stopForeground(true);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Servicio destruido...", Toast.LENGTH_SHORT).show();

        // Emisión para avisar que se terminó el servicio
        Intent localIntent = new Intent(Constants.ACTION_PROGRESS_EXIT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

        Log.d(TAG, "Servicio destruido...");
    }
}
