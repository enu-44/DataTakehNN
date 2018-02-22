package com.datatakehnn.services.api_services.elemento_service;

import android.content.Context;
import android.util.Log;

import com.datatakehnn.models.reponse_generic.data_async.Response_Data_Sync;
import com.datatakehnn.models.reponse_generic.login.Response_Request_Login;
import com.datatakehnn.models.request_data_sync_model.Request_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Response_Post_Data_Sync;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.api_client.retrofit.ApiClient;
import com.datatakehnn.services.api_client.retrofit.ApiClientInterFace;
import com.datatakehnn.services.api_services.loginservice.ILogin;
import com.datatakehnn.services.api_services.loginservice.LoginApiService;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by usuario on 22/02/2018.
 */

public class GetElementoService {

    private static final String TAG = "";
    private static Context ourcontext;
    private static GetElementoService _instance;

    ApiClientInterFace apiService = ApiClient.getClientAmazon().create(ApiClientInterFace.class);
    public IElementoService IElementoService;

    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public GetElementoService() {
        _instance = this;
    }

    public static GetElementoService getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new GetElementoService();
        }
        return _instance;
    }



    public void getElementoAsync(IElementoService iElementoService, String codigoapoyo, long ciudad) {
        this.IElementoService = iElementoService;
        final Response_Post_Data_Sync response_Post_Data_Sync = new Response_Post_Data_Sync();
        //final Response_Login responseLoginNull = new Response_Login();
        final Request_Post_Data_Sync Null = new Request_Post_Data_Sync();
        Call<Response_Post_Data_Sync> call = apiService.getElementoByCodigoAndCiudad(codigoapoyo, ciudad);
        call.enqueue(new Callback<Response_Post_Data_Sync>() {
            @Override
            public void onResponse(Call<Response_Post_Data_Sync> call, retrofit2.Response<Response_Post_Data_Sync> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body().isSuccess()) {
                        response_Post_Data_Sync.setResult(response.body().getResult());
                        response_Post_Data_Sync.setSuccess(response.body().isSuccess());
                        response_Post_Data_Sync.setMessage(response.body().getMessage());
                        IElementoService.processFinishGetElemento(response_Post_Data_Sync);
                    } else {
                        //TODO VALIDAR SI puede obtener un getResutl Null
                        response_Post_Data_Sync.setResult(response.body().getResult());
                        response_Post_Data_Sync.setSuccess(response.body().isSuccess());
                        response_Post_Data_Sync.setMessage(response.body().getMessage());
                        IElementoService.processFinishGetElemento(response_Post_Data_Sync);
                    }
                } else {
                    response_Post_Data_Sync.setSuccess(false);
                    response_Post_Data_Sync.setMessage("Error en la peticion");
                    response_Post_Data_Sync.setResult(Null);
                    IElementoService.processFinishGetElemento(response_Post_Data_Sync);
                }
            }

            @Override
            public void onFailure(Call<Response_Post_Data_Sync> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                response_Post_Data_Sync.setSuccess(false);
                //Suele llegar null y deteniene la app
                //responsePlanMantenance.setMessage(t.getMessage().toString());
                response_Post_Data_Sync.setMessage(t.toString());
                response_Post_Data_Sync.setResult(Null);
                IElementoService.processFinishGetElemento(response_Post_Data_Sync);
            }
        });
    }

}
