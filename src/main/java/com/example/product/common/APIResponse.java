package com.example.product.common;

import lombok.Data;

@Data
public class APIResponse<T> {
    private boolean success;
    private String error;
    private T data;

    public APIResponse(){
        this.success = true;
    }

    public APIResponse(boolean success,String error) {
        this.success = success;
        this.data = null;
        this.error  = error;
    }
    public APIResponse(T data){
        this.error = null;
        this.data = data;
        this.success = true;
    }
}
