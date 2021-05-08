package com.example.demo.dto;

import com.example.demo.Roles;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

//import javax.validation.constraints.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDto {
    @NotEmpty(message = "Name can not be empty!!!")
    private String name;
    private String surname;
    private String patronymic;
    @NotEmpty(message = "Login can not be empty!!!")
    private String login;
    @NotEmpty(message = "Password can not be empty!!!")
    @Pattern(regexp = "^(?=.*[0-9]).{8,}$", message = "Not a valid password!!!")
    private String password;
    @Min(18)
    private Integer age;
    @JsonFormat(pattern = "dd.MM.yyyy", timezone="Asia/Almaty")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthday;
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
    message = "Not a valid email address!!!")
    private String email;
    private String id;
    @NotEmpty(message = "Office can not be empty!!!")
    private String office;
    @Range(max = 10000000, message = "Too high value!!!")
    private Double salary;
    @JsonFormat(pattern = "dd.MM.yyyy", timezone="Asia/Almaty")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date startWorkDate;
    private Roles roles;
    private Boolean isDeleted;
}
