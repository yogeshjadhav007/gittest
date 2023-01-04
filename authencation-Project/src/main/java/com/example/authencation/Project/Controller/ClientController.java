package com.example.authencation.Project.Controller;

import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ClientController {

    @Autowired
    MongoTemplate mt;

    public static final FindAndModifyOptions options = FindAndModifyOptions.options().upsert(true).returnNew(true);
    public static final FindAndModifyOptions modifyOptions = FindAndModifyOptions.options().returnNew(true);

    @Autowired
    CacheRepository cacheRepository;

    @Autowired
    OtpGenerator otpGenerator;


    @PostMapping("/register")
    public String CreateClient(@RequestBody String client) {

        JSONObject clientInfo = new JSONObject(client);
        JSONObject registerResJson = new JSONObject();

        Query query = new Query();
        query.addCriteria(Criteria.where("mobileNo").is(clientInfo.getString("contactNo")));
        String result = mt.findOne(query, String.class, "customerDetails");
        JSONObject mobileNoResponse = new JSONObject(result);

        if (mobileNoResponse.getBoolean("isOtpVerified")) {
            Update update = new Update();
            update.set("signUpData", Document.parse(clientInfo.toString()));
            mt.findAndModify(query, update, options, String.class, "customerDetails");
        } else {
            registerResJson.put("status", "error");
            registerResJson.put("message", "Mobile no not verified");
            return registerResJson.toString();
        }
        registerResJson.put("status", "success");
        registerResJson.put("message", "Registration Successfully");
        return registerResJson.toString();
    }


    //Login API
    @RequestMapping("/clientLogin/{emailId}/{password}")
    public String ClientLogin(@RequestBody String loginInfo) {

        JSONObject login=new JSONObject();
        System.out.println(login);


//        Query query = new Query();
//        query.addCriteria(Criteria.where("emailId").is(emailId).and("password").is(password));
//        String result = mt.findOne(query, String.class, "customerDetails");
//        if (result != null) {
//            JsonObject response = new JsonObject(result);
//            System.out.println(response.getJson());
//            return "Login Successfully";
//        }
        return "Login Failed";
    }



    @PostMapping("/generateOtp")
    public ResponseEntity<String> addToCache(@RequestBody String key) {

        int value = otpGenerator.generateOtp();
        JSONObject object = new JSONObject(key);
        cacheRepository.put(object.getString("contactNo"), value);
        return ResponseEntity.ok("Otp Generated Successfully : " + value);
    }


    @PostMapping("/verifyOtp")
    public ResponseEntity<String> addToCache(@RequestBody VerifyOTP key) {
        JSONObject verifyOtpReqJson = new JSONObject(key);

        if (cacheRepository.get(key.getContactNo()).isPresent() && cacheRepository.get(key.getContactNo()).get().equals(key.getOtp())) {
            verifyOtpReqJson.put("isOtpVerified", true);
            verifyOtpReqJson.remove("otp");

            Query query = new Query();
            query.addCriteria(Criteria.where("mobileNo").is(verifyOtpReqJson.getString("mobileNo")));
            System.out.println(query);
            String result = mt.findOne(query, String.class, "customerDetails");
            System.out.println(result);
            if (result != null) {
                Update update = new Update();
                update.set("verifyMobileNo", Document.parse(verifyOtpReqJson.toString()));
                mt.findAndModify(query, update, options, String.class, "customerDetails");

                return ResponseEntity.ok("OTP verified Successfully");

            } else if (result == null){

                mt.insert(Document.parse(verifyOtpReqJson.toString()), "customerDetails");
                return ResponseEntity.ok("OTP verified Successfully");

            }
        }
        return ResponseEntity.ok("Invalid OTP!");
    }
}