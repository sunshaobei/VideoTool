package com.sunsh.baselibrary.http.ok3.entity;

import java.io.Serializable;

/**
 * Created by sunsh on 18/5/30.
 */
public class HttpResponse<T> implements Serializable {

    private boolean result;
    private String message;
    private T data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message != null ? message : "";
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public boolean isSuccess() {
        return isResult();
//                && data != null;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
