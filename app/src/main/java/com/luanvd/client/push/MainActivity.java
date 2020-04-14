package com.luanvd.client.push;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.luanvd.client.push.httputils.HttpUserDevice;
import com.luanvd.client.push.httputils.HttpUtils;
import com.luanvd.client.push.models.HttpResponse;
import com.luanvd.client.push.models.UserDevice;
import com.luanvd.client.push.ultils.Utilities;

import org.json.JSONObject;

import java.util.Date;

import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

//    static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8686/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
//
//    static HttpUserDevice httpUserDevice = retrofit.create(HttpUserDevice.class);


    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("1", "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();
                                // call api dang ky thiet bi
                                Long userId = 1L;
                                String paramJson = "";
                                String deviceName = Build.MODEL;
                                String version = Build.VERSION.RELEASE+"-"+Build.VERSION.SDK_INT;
                                UserDevice userDevice = new UserDevice();
                                userDevice.setFull_name("VŨ ĐÌNH LUÂN");
                                userDevice.setUser_id(userId);
                                userDevice.setDevice_name(deviceName);
                                userDevice.setDevice_token(token);
                                userDevice.setDevice_version(version);
                                userDevice.setFlatform("ANDROID");
                                userDevice.setStatus(1);
                                final Gson gson = new Gson();
                                paramJson = gson.toJson(userDevice);
                                System.out.println("Input: "+paramJson);
                                RequestBody body = RequestBody.create(MediaType.parse("application/json"),paramJson);
                                HttpUtils.httpUserDevice.addUserDevice(userId,body)
                                        .enqueue(new Callback<HttpResponse>() {
                                            @SneakyThrows
                                            @Override
                                            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                                                //truong hop thanh cong
                                                System.out.println("\nRespon: "+response.body());
                                                if (response.body()!=null){
                                                    HttpResponse res = response.body();
                                                    if (200 == res.getStatus()){
                                                        System.out.println("data: "+res.getData());
                                                        JSONObject json = new JSONObject(res.getData().toString());
                                                        System.out.println("data json: "+json.toString());
                                                         UserDevice deviceAdded = gson.fromJson(json.toString(), UserDevice.class);
//                                                        UserDevice deviceAdded = gson.fromJson(res.getData().toString(),UserDevice.class);
                                                        tvResult.setText("Added device with token: "+deviceAdded.getDevice_token());
                                                        // Log and toast
                                                        String msg = "Dang ky thanh cong user device voi id = "+deviceAdded.getId();
                                                        Log.d("2", msg);
                                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        tvResult.setText(res.getMessage());
                                                        String msg = res.getMessage();
                                                        Log.d("2", msg);
                                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<HttpResponse> call, Throwable t) {
                                                tvResult.setText("Co loi xay ra: "+t.getMessage());
                                                String msg = "Dang ky that bai";
                                                Log.d("2", msg);
                                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }
        });
    }

    void getInfoDevice(){

        System.out.println("Local IP: "+ Utilities.getLocalIpAddress());
        Log.i("TAG", "SERIAL: " + Build.SERIAL);
        Log.i("TAG", "DEVICE: " + Build.DEVICE);
        Log.i("TAG", "DISPLAY: " + Build.DISPLAY);
        Log.i("TAG", "Version OS: " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName());
        Log.i("TAG","MODEL: " + Build.MODEL);
        Log.i("TAG","ID: " + Build.ID);
        Log.i("TAG","Manufacture: " + Build.MANUFACTURER);
        Log.i("TAG","brand: " + Build.BRAND);
        Log.i("TAG","type: " + Build.TYPE);
        Log.i("TAG","user: " + Build.USER);
        Log.i("TAG","BASE: " + Build.VERSION_CODES.BASE);
        Log.i("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.i("TAG","SDK  " + Build.VERSION.SDK);
        Log.i("TAG","BOARD: " + Build.BOARD);
        Log.i("TAG","BRAND " + Build.BRAND);
        Log.i("TAG","HOST " + Build.HOST);
        Log.i("TAG","FINGERPRINT: "+Build.FINGERPRINT);
        Log.i("TAG","Version Code: " + Build.VERSION.RELEASE);
    }
    void initView(){
        txtPassword = findViewById(R.id.txtPassword);
        txtUsername = findViewById(R.id.txtUsername);
        btnLogin = findViewById(R.id.btnLogin);
        tvResult = findViewById(R.id.tvResult);
    }
    private String getToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("1", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        //return token;

                        // Log and toast
                        String msg = "Loi get token: "+token;
                        Log.d("2", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        return null;
    }
}
