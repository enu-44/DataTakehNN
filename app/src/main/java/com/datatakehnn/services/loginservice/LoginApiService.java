package com.datatakehnn.services.loginservice;

import android.content.Context;
import android.util.Log;

import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.reponse_generic.login.Response_Login;
import com.datatakehnn.models.reponse_generic.login.Response_Request_Login;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.api_client.retrofit.ApiClient;
import com.datatakehnn.services.api_client.retrofit.ApiClientInterFace;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by user on 30/11/2017.
 */

public class LoginApiService {

    private static final String TAG = "";
    private static Context ourcontext;
    private static LoginApiService _instance;

    ApiClientInterFace apiService = ApiClient.getClientAmazon().create(ApiClientInterFace.class);
    public ILogin ILogin;

    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public LoginApiService() {
        _instance = this;
    }

    public static LoginApiService getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new LoginApiService();
        }
        return _instance;
    }

    public void getLoginAsync(ILogin iLogin, String cedula, String contraseña) {
        this.ILogin = iLogin;
        final Response_Request_Login response_request_login = new Response_Request_Login();
        //final Response_Login responseLoginNull = new Response_Login();
        final Usuario usuarioNull = new Usuario();
        Call<Response_Request_Login> call = apiService.getLoginUser(cedula, contraseña);
        call.enqueue(new Callback<Response_Request_Login>() {
            @Override
            public void onResponse(Call<Response_Request_Login> call, retrofit2.Response<Response_Request_Login> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body().isSuccess()) {
                        response_request_login.setResult(response.body().getResult());
                        response_request_login.setSuccess(response.body().isSuccess());
                        response_request_login.setMessage(response.body().getMessage());
                        ILogin.processFinishGetLogin(response_request_login);
                    } else {
                        //TODO VALIDAR SI puede obtener un getResutl Null
                        response_request_login.setResult(response.body().getResult());
                        response_request_login.setSuccess(response.body().isSuccess());
                        response_request_login.setMessage(response.body().getMessage());
                        ILogin.processFinishGetLogin(response_request_login);
                    }
                } else {
                    response_request_login.setSuccess(false);
                    response_request_login.setMessage("Error en la peticion");
                    response_request_login.setResult(usuarioNull);
                    ILogin.processFinishGetLogin(response_request_login);
                }
            }

            @Override
            public void onFailure(Call<Response_Request_Login> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                response_request_login.setSuccess(false);
                //Suele llegar null y deteniene la app
                //responsePlanMantenance.setMessage(t.getMessage().toString());
                response_request_login.setMessage(t.toString());
                response_request_login.setResult(usuarioNull);
                ILogin.processFinishGetLogin(response_request_login);
            }
        });


    }

}
