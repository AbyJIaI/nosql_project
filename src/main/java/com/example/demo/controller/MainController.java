package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.filter.FilterParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private final EmployeeController employeeController;

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id) {
        employeeController.deleteEmployee(id);
        return "redirect:/table";
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute FilterParams filter, Model model) {
        List<EmployeeDto> filtered = employeeController.filter(filter);
        model.addAttribute("title", "Tables");
        FilterParams filterParams = new FilterParams();
        model.addAttribute("filter", filterParams);
        model.addAttribute("employees", filtered);
        return "tables";
    }

    @PostMapping("/save-employee")
    public String submitStudentDetails(@Valid EmployeeDto employeeDto, Errors errors, Model model) {
        model.addAttribute("title", "Create employee");
        if (errors != null && errors.getErrorCount() > 0) {
            model.addAttribute("successMsg", "error");
            model.addAttribute("employeeDto", employeeDto);
        } else {
            EmployeeDto newEmp = new EmployeeDto();
            Employee employee = employeeController.mapDtoToEntity(employeeDto);
            employeeController.createEmployee(employee);
            model.addAttribute("employeeDto", newEmp);
            model.addAttribute("successMsg", "success");
        }
        return "create";
    }

    @RequestMapping(value = {"/"})
    public String getIndex(Model model) {
        model.addAttribute("title", "Dashboard");
        return "index";
    }

    @RequestMapping(value = "/table")
    public String getTable(Model model) {
        List<EmployeeDto> all = employeeController.getAllEmployees();
        model.addAttribute("title", "Tables");
        FilterParams filterParams = new FilterParams();
        model.addAttribute("filter", filterParams);
        model.addAttribute("employees", all);
        return "tables";
    }

    @RequestMapping(value = "/create")
    public String getCreate(Model model) {
        model.addAttribute("title", "Create employee");
        EmployeeDto employee = new EmployeeDto();
        model.addAttribute("employeeDto", employee);
        return "create";
    }

    @RequestMapping(value = "/404")
    public String get404(Model model) {
        model.addAttribute("title", "404");
        return "404";
    }

    @RequestMapping(value = "/charts")
    public String getCharts(Model model) {
        model.addAttribute("title", "charts");
        return "charts";
    }

    @RequestMapping(value = "/forgot-password")
    public String getForgotPassword(Model model) {
        model.addAttribute("title", "Forgot password");
        return "forgot-password";
    }

    @RequestMapping(value = "/login")
    public String getLogin(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @RequestMapping(value = "/register")
    public String getRegister(Model model) {
        model.addAttribute("title", "Register");
        return "register";
    }
}
