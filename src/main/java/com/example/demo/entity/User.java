package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private String name;
    private String surname;
    private String patronymic;
    private Integer age;
    @JsonFormat(pattern = "dd.MM.yyyy", timezone="Asia/Almaty")
    private Date birthday;
    private String email;
}
