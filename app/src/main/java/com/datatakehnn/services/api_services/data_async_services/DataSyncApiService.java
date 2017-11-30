package com.datatakehnn.services.api_services.data_async_services;

import android.content.Context;
import android.util.Log;

import com.datatakehnn.models.reponse_generic.data_async.Response_Data_Sync;
import com.datatakehnn.models.reponse_generic.data_async.Response_Request_Data_Sync;
import com.datatakehnn.services.api_client.retrofit.ApiClient;
import com.datatakehnn.services.api_client.retrofit.ApiClientInterFace;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by usuario on 30/11/2017.
 */

public class DataSyncApiService {

    private static final String TAG ="" ;
    private static Context ourcontext;
    private static DataSyncApiService _instance;

    ApiClientInterFace apiService = ApiClient.getClientAmazon().create(ApiClientInterFace.class);
    public IDataAsync IDataAsync;

    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public DataSyncApiService() {
        _instance = this;
    }
    public static DataSyncApiService getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new DataSyncApiService();
        }
        return _instance;
    }


    public void  loadDataAsync(final IDataAsync dataAsync){
        this.IDataAsync=dataAsync;
        final Response_Request_Data_Sync response_request_data_sync= new Response_Request_Data_Sync();
        final Response_Data_Sync responseDataSyncNull= new Response_Data_Sync();
        Call<Response_Data_Sync> call = apiService.getListDataSyncInitial();
        call.enqueue(new Callback<Response_Data_Sync>() {
            @Override
            public void onResponse(Call<Response_Data_Sync> call, retrofit2.Response<Response_Data_Sync> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    if(response.body()!=null){
                        response_request_data_sync.setSuccess(true);
                        response_request_data_sync.setMessage("Ok");
                        response_request_data_sync.setResult(response.body());
                        IDataAsync.processFinishGetDataAsync(response_request_data_sync);
                    }else{
                        response_request_data_sync.setSuccess(false);
                        response_request_data_sync.setMessage("Nulo");
                        response_request_data_sync.setResult(response.body());
                        IDataAsync.processFinishGetDataAsync(response_request_data_sync);
                    }

                }else{
                    response_request_data_sync.setSuccess(false);
                    response_request_data_sync.setMessage("Error en la peticion");
                    response_request_data_sync.setResult(responseDataSyncNull);
                    IDataAsync.processFinishGetDataAsync(response_request_data_sync);
                }
            }

            @Override
            public void onFailure(Call<Response_Data_Sync> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                response_request_data_sync.setSuccess(false);
                //Suele llegar null y deteniene la app
                //responsePlanMantenance.setMessage(t.getMessage().toString());
                response_request_data_sync.setMessage(t.toString());
                response_request_data_sync.setResult(responseDataSyncNull);
                IDataAsync.processFinishGetDataAsync(response_request_data_sync);
            }
        });
    }
}