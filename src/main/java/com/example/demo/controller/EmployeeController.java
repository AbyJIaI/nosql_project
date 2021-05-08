package com.example.demo.controller;

import com.example.demo.Roles;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.filter.FilterParams;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping("/delete")
    public Boolean deleteEmployee(@RequestParam String id) {
        return service.deleteEmployee(id);
    }

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.createEmployee(employee);
    }

    @GetMapping("/getAll")
    public List<EmployeeDto> getAllEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable String id) {
        return service.getEmployee(id);
    }

    @PostMapping("/filter")
    public List<EmployeeDto> filter(@RequestBody  FilterParams filter) {
        return service.filterByParameters(filter);
    }

    public Employee mapDtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        if (employeeDto != null) {
            User user = new User();
            user.setName(employeeDto.getName());
            user.setSurname(employeeDto.getSurname());
            user.setPatronymic(employeeDto.getPatronymic());
            user.setAge(employeeDto.getAge());
            user.setBirthday(employeeDto.getBirthday());
            user.setEmail(employeeDto.getEmail());
            employee.setIsDeleted(false);
            employee.setRoles(Roles.EMPLOYEE);
            employee.setStartWorkDate(new Date());
            employee.setUser(user);
            employee.setLogin(employeeDto.getLogin());
            employee.setOffice(employeeDto.getOffice());
            employee.setPassword(employeeDto.getPassword());
            employee.setSalary(employeeDto.getSalary());
            return employee;
        }
        return null;
    }
}

