package com.example.demo.entity;

import com.example.demo.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("employees")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {
    @Id
    private String id;
    private User user;
    private String office;
    private Double salary;
    @JsonFormat(pattern = "dd.MM.yyyy", timezone="Asia/Almaty")
    private Date startWorkDate;
    private Roles roles = Roles.EMPLOYEE;
    private String login;
    private String password;
    private Boolean isDeleted = Boolean.FALSE;
}
