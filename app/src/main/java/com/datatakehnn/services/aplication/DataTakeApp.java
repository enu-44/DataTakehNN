package com.datatakehnn.services.aplication;

import android.app.Application;
import android.os.StrictMode;

import com.datatakehnn.services.connection_internet.ConnectivityReceiver;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by user on 11/11/2017.
 */

public class DataTakeApp extends Application {

    private static DataTakeApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        /// Timber.plant(new Timber.DebugTree());
        FlowManager.init(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        FlowManager.destroy();
    }

    public static synchronized DataTakeApp getInstance() {
        return mInstance;
    }

    ///Conection Internet
    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
