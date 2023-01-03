package com.example.authencation.Project.Controller;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class OtpGenerator {

    private static final String DEFAULT_ALGORITHM = "SHA1PRNG";
    private static final int LOWER_BOUND = 100000;
    private static final int UPPER_BOUND = 900000;

    public int generateOtp() {
        try {
            Random random = SecureRandom.getInstance(DEFAULT_ALGORITHM);
            return LOWER_BOUND + random.nextInt(UPPER_BOUND);
        } catch (NoSuchAlgorithmException e) {
            throw new OTPServiceException("Invalid algorithm for generating otp", e);
        }
    }
}