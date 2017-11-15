package com.datatakehnn.models.reponse_generic;

/**
 * Created by user on 11/11/2017.
 */

public class Response {
    public String Message;
    public Object Result;
    public  boolean IsSuccess;


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getResult() {
        return Result;
    }

    public void setResult(Object result) {
        Result = result;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}
