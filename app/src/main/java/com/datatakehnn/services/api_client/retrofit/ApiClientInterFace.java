package com.datatakehnn.services.api_client.retrofit;

import com.datatakehnn.models.material_model.Material;
import com.datatakehnn.models.reponse_generic.data_async.Response_Data_Sync;
import com.datatakehnn.models.reponse_generic.login.Response_Request_Login;
import com.datatakehnn.models.request_data_sync_model.Request_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Response_Post_Data_Sync;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by user on 11/11/2017.
 */

public interface ApiClientInterFace {

    /*
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);*/

    @GET("api/ResponseDataSync")
    Call<Response_Data_Sync> getListDataSyncInitial();

    @GET("api/Usuario/GetLoginUser/{Cedula}/{Password}")
    Call<Response_Request_Login> getLoginUser(@Path("Cedula") String cedula, @Path("Password") String password);

/*
    @Headers({
            "Content-Type: application/json",
            "User-Agent: bytearray"
    })*/
    @Headers("Content-Type: application/json")
    @POST("api/ResponseDataSync/PostDataSync")
    Call<Response_Post_Data_Sync> postDataSync(@Body Request_Post_Data_Sync body);


    @Headers("Content-Type: application/json")
    @POST("api/MasterData/PostMaterial")
    Call<Material> postMaterial(@Body Material body);



}
