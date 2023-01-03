package com.example.authencation.Project.Controller;

public class OTPServiceException extends RuntimeException {

    /**
     * OtpCache Exception with error message
     *
     * @param errorMessage error message
     */
    public OTPServiceException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * OtpCache Exception with error message and throwable
     *
     * @param errorMessage error message
     * @param throwable    error
     */
    public OTPServiceException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

}