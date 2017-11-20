package com.datatakehnn.services.api_client.retrofit;

import com.datatakehnn.models.reponse_generic.data_async.Response_Data_Sync;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 11/11/2017.
 */

public interface ApiClientInterFace {

      /*
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);*/

    @GET("api/ResponseDataSync")
    Call<Response_Data_Sync> getListDataSyncInitial();
}
