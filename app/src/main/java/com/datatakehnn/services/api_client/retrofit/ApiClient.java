package com.datatakehnn.services.api_client.retrofit;

import android.content.Context;

import com.datatakehnn.controllers.SettingController;
import com.datatakehnn.models.configuracion_model.Setting;
import com.datatakehnn.services.api_client.routes.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 11/11/2017.
 */

public class ApiClient {

    //public static final String BASE_URL = Const.URL_RouteBaseAddress;

    private static Context ourcontext;
    private static ApiClient _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public ApiClient() {
        _instance = this;
    }

    public static ApiClient getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new ApiClient();
        }
        return _instance;
    }


    public static String BASE_URL = SQLite.select().from(Setting.class).querySingle().getRuta_Servicio();

    public String setRoute(String url) {
        BASE_URL = url;
        return BASE_URL;
    }


    private static Retrofit retrofitAmazon = null;
    private static Gson gson = null;

    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(300000, TimeUnit.SECONDS) //Tiempo de respuesta del servicio
            .connectTimeout(30000, TimeUnit.SECONDS)

            .build();

    public static Retrofit getClientAmazon() {
        if (retrofitAmazon == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm")
                    .create();

            retrofitAmazon = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }


        return retrofitAmazon;
    }
}
