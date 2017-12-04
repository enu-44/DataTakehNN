package com.datatakehnn.services.api_client.retrofit;

import com.datatakehnn.services.api_client.routes.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 11/11/2017.
 */

public class ApiClient {

    public static final String BASE_URL_ = Const.URL_RouteBaseAddress;

    private static Retrofit retrofitAmazon = null;
    private static Gson gson=null;

    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.SECONDS) //Tiempo de respuesta del servicio
            .connectTimeout(30000, TimeUnit.SECONDS)

            .build();

    public static Retrofit getClientAmazon() {
        if (retrofitAmazon==null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm")
                    .create();

            retrofitAmazon = new Retrofit.Builder()
                    .baseUrl(BASE_URL_)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }


        return retrofitAmazon;
    }
}
