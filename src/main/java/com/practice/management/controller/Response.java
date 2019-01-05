package com.practice.management.controller;

import java.io.Serializable;

public class Response<T> implements Serializable {
    String errMsg = "";
    T wrapper;
    public Response() {}
    public Response(T wrapper) {
        this.wrapper = wrapper;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getWrapper() {
        return wrapper;
    }

    public void setWrapper(T wrapper) {
        this.wrapper = wrapper;
    }
}
