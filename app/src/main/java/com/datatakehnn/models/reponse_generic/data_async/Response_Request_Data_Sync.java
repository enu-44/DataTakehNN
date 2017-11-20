package com.datatakehnn.models.reponse_generic.data_async;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 20/11/2017.
 */

public class Response_Request_Data_Sync {
    @SerializedName("IsSuccess")
    private boolean IsSuccess;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Result")
    private Response_Data_Sync Result;

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

    public Response_Data_Sync getResult() {
        return Result;
    }

    public void setResult(Response_Data_Sync result) {
        Result = result;
    }

    public  Response_Request_Data_Sync(){


    }
}
