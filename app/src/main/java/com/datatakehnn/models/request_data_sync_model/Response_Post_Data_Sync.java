package com.datatakehnn.models.request_data_sync_model;

/**
 * Created by usuario on 30/11/2017.
 */

public class Response_Post_Data_Sync {
    public String Message;
    public Request_Post_Data_Sync Result;
    public  boolean IsSuccess;

    //Methods
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Request_Post_Data_Sync getResult() {
        return Result;
    }

    public void setResult(Request_Post_Data_Sync result) {
        Result = result;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}
