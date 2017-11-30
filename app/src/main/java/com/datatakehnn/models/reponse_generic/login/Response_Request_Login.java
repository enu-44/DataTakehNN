package com.datatakehnn.models.reponse_generic.login;

import com.datatakehnn.models.usuario_model.Usuario;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 30/11/2017.
 */

public class Response_Request_Login {

    @SerializedName("IsSuccess")
    private boolean IsSuccess;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Result")
    private Usuario Result;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Usuario getResult() {
        return Result;
    }

    public void setResult(Usuario result) {
        Result = result;
    }

    public Response_Request_Login() {

    }
}
