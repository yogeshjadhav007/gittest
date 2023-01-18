package com.example.authencation.Project.Otp;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OtpRequest implements Serializable {
    private String contactNo;
}