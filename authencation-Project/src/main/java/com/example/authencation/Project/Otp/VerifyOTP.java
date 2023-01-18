package com.example.authencation.Project.Otp;

import lombok.Getter;

import java.io.Serializable;
@Getter
public class VerifyOTP implements Serializable {
    public String mobileNo;
    public String otp;

}
