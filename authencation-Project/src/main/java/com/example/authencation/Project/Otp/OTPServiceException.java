package com.example.authencation.Project.Otp;

public class OTPServiceException extends RuntimeException {


    public OTPServiceException(String errorMessage) {
        super(errorMessage);
    }


    public OTPServiceException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

}