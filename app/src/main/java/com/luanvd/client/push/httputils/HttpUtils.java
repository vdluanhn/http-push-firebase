package com.luanvd.client.push.httputils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface HttpUtils {
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.17.65:8686/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static HttpUserDevice httpUserDevice = retrofit.create(HttpUserDevice.class);
}
