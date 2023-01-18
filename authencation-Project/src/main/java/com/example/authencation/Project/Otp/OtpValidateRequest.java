package com.example.authencation.Project.Otp;

import lombok.Getter;

@Getter
public class OtpValidateRequest {

    private String key;
    private String otp;

}