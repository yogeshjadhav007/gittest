package com.example.authencation.Project.Controller;

import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class Controller {

    @Autowired
    private final CacheRepository cacheRepository;
    @Autowired
    private final OtpGenerator otpGenerator;

    @Autowired
    private MongoTemplate Mrt;

    public Controller(CacheRepository cacheRepository, OtpGenerator otpGenerator, MongoTemplate Mrt){
        this.cacheRepository = cacheRepository;
        this.otpGenerator = otpGenerator;
        this.Mrt=Mrt;
    }
    @Autowired
    public Controller(CacheRepository cacheRepository, OtpGenerator otpGenerator) {
        this.cacheRepository = cacheRepository;
        this.otpGenerator = otpGenerator;
    }

    @PostMapping("/Register")
    public Client createClient(@RequestBody Client client){
        Mrt.save(client);
        return client;
    }
    @RequestMapping("/ClientLogin/{emailid}and{password}")
    public String ClientLogin(@PathVariable String emailid, @PathVariable String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where("emailid").is(emailid).and("password").is(password));
        String result = Mrt.findOne(query,String.class,"client");
        if(result!=null){
            JsonObject response = new JsonObject(result);
            return response.getJson();
        }
        return "null";
    }


    @PostMapping("/generate-otp")
    public ResponseEntity<String> generate(@RequestBody OtpRequest key) {

        int value = otpGenerator.generateOtp();
        cacheRepository.put(key.getContactNo(), value);

        return ResponseEntity.ok("Otp Generated Successfully Otp : " + value);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verify(@RequestBody VerifyOTP key) {

        if (cacheRepository.get(key.getContactNo()).isPresent() && cacheRepository.get(key.getContactNo()).get().equals(key.getOtp())) {
            System.out.println((key.getOtp()));
            return ResponseEntity.ok("Otp verified successfully....");
        }
        return ResponseEntity.ok("Invalid Otp ...");
    }

}