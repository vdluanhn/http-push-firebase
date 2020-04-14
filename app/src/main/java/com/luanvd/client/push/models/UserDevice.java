package com.luanvd.client.push.models;

import java.util.Date;

import lombok.Data;

@Data
public class UserDevice {
    private Long id;
    private Long user_id;
    private String full_name;
    private String device_token;
    private String device_version;
    private String flatform;
    private String device_name;
    private int status;
    private Long notification_id;
    private Date created_date;
}
