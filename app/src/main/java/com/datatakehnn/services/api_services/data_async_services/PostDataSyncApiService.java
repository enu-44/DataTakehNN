package com.datatakehnn.services.api_services.data_async_services;

import android.content.Context;
import android.util.Log;

import com.datatakehnn.models.reponse_generic.data_async.Response_Data_Sync;
import com.datatakehnn.models.reponse_generic.data_async.Response_Request_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Request_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Response_Post_Data_Sync;
import com.datatakehnn.services.api_client.retrofit.ApiClient;
import com.datatakehnn.services.api_client.retrofit.ApiClientInterFace;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by usuario on 30/11/2017.
 */

public class PostDataSyncApiService {
    private static final String TAG ="" ;
    private static Context ourcontext;
    private static PostDataSyncApiService _instance;

    ApiClientInterFace apiService = ApiClient.getClientAmazon().create(ApiClientInterFace.class);
    public IPostDataSync IPostDataSync;

    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public PostDataSyncApiService() {
        _instance = this;
    }
    public static PostDataSyncApiService getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new PostDataSyncApiService();
        }
        return _instance;
    }



    //Methods
    //POST
    public void  postDataAsync(final IPostDataSync dataAsync, Request_Post_Data_Sync request_post_data_sync){
        this.IPostDataSync=dataAsync;

        final Response_Post_Data_Sync response_request_data_sync= new Response_Post_Data_Sync();
        final Response_Post_Data_Sync responseDataSyncNull= new Response_Post_Data_Sync();


        Call<Response_Post_Data_Sync> call = apiService.postDataSync(request_post_data_sync);
        call.enqueue(new Callback<Response_Post_Data_Sync>() {
            @Override
            public void onResponse(Call<Response_Post_Data_Sync> call, retrofit2.Response<Response_Post_Data_Sync> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    if(response.body().isSuccess()){
                        response_request_data_sync.setSuccess(true);
                        response_request_data_sync.setMessage("Ok");
                        response_request_data_sync.setResult(response.body().getResult());
                        IPostDataSync.processFinishPostDataAsync(response_request_data_sync);
                    }else{
                        response_request_data_sync.setSuccess(false);
                        response_request_data_sync.setMessage("Nulo");
                        response_request_data_sync.setResult(response.body().getResult());
                        IPostDataSync.processFinishPostDataAsync(response_request_data_sync);
                    }

                }else{
                    response_request_data_sync.setSuccess(false);
                    response_request_data_sync.setMessage("Error en la peticion");
                    response_request_data_sync.setResult(responseDataSyncNull.getResult());
                    IPostDataSync.processFinishPostDataAsync(response_request_data_sync);
                }
            }

            @Override
            public void onFailure(Call<Response_Post_Data_Sync> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                response_request_data_sync.setSuccess(false);
                //Suele llegar null y deteniene la app
                //responsePlanMantenance.setMessage(t.getMessage().toString());
                response_request_data_sync.setMessage(t.toString());
                response_request_data_sync.setResult(responseDataSyncNull.getResult());
                IPostDataSync.processFinishPostDataAsync(response_request_data_sync);
            }
        });
    }


}
