package com.example.authencation.Project.Controller;

import lombok.Getter;

@Getter
public class OtpValidateRequest {

    private String key;
    private String otp;

}