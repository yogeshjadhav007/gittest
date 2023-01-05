package com.example.authencation.Project.Controller;

import lombok.Getter;

import java.io.Serializable;
@Getter
public class VerifyOTP implements Serializable {


    public String mobileNo;
    public String otp;

}
