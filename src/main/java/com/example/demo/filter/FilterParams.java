package com.example.demo.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class FilterParams {
    private List<String> fields = new ArrayList<>();
    private String word;
    private String fromDate;
    private String toDate;
    private String age;
    private String salary;
}
