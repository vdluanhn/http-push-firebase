package com.luanvd.client.push.httputils;

import com.luanvd.client.push.models.HttpResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HttpUserDevice {
    @POST("/api/user-device/{userId}/add-user-device")
    Call<HttpResponse> addUserDevice(@Path("userId") Long userId, @Body RequestBody object);
}
