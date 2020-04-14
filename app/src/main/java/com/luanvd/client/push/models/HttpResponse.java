package com.luanvd.client.push.models;

import java.util.Date;

import lombok.Data;

@Data
public class HttpResponse {
    private Date timestamp;
    private int status;
    private String message;
//    private PageInfo pageInfo;
    private Object data;

    public HttpResponse(int status, String message) {
        this.status = status;
        this.message = message;
//        this.pageInfo = null;
        this.data = null;
        this.timestamp = new Date();
    }
    public HttpResponse(){
        this.timestamp = new Date();
    }
}
