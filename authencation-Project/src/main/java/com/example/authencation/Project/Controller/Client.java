package com.example.authencation.Project.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Component
public class Client {
    @Id
    private Integer id;
    private String name;
    private String emailid;
    private String password;
    private String contactNo;
}