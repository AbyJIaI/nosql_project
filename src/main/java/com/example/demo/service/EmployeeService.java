package com.example.demo.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.filter.FilterParams;
import com.example.demo.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;
    private final MongoTemplate mongoTemplate;

    public Boolean deleteEmployee(String id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    public List<EmployeeDto> getAllEmployees() {
        ArrayList<EmployeeDto> dtos = new ArrayList<>();
        for (Employee document : repository.findAll()) {
            EmployeeDto dto = new EmployeeDto();
            dto.setId(document.getId());
            dto.setIsDeleted(document.getIsDeleted());
            dto.setLogin(document.getLogin());
            dto.setOffice(document.getOffice());
            dto.setPassword(document.getPassword());
            dto.setRoles(document.getRoles());
            dto.setSalary(document.getSalary());
            dto.setStartWorkDate(document.getStartWorkDate());
            User user = document.getUser();
            if (user != null) {
                dto.setAge(user.getAge());
                dto.setBirthday(user.getBirthday());
                dto.setEmail(user.getEmail());
                dto.setName(user.getName());
                dto.setPatronymic(user.getPatronymic());
                dto.setSurname(user.getSurname());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public Employee getEmployee(String id) {
        return repository.findById(id).orElse(new Employee());
    }

    public List<EmployeeDto> filterByParameters(FilterParams filter) {
        String coll_name = mongoTemplate.getCollectionName(Employee.class);
        List<EmployeeDto> all = getAllEmployees();
        if (filter != null) {
            MatchOperation m1 = null;
            ArrayList<Criteria> listOfCriteria = new ArrayList<>();
            ProjectionOperation projection = null;
            Criteria criteria = new Criteria();
            if (filter.getWord() != null && !filter.getWord().trim().isEmpty()) {
                String word = filter.getWord();
                Criteria matchInNameLike = criteriaByRegex("user.name", word);
                Criteria matchInSurnameLike = criteriaByRegex("user.surname", word);
                Criteria matchInPatronymicLike = criteriaByRegex("user.patronymic", word);
                Criteria matchInEmailLike = criteriaByRegex("user.email", word);
                Criteria matchInOfficeLike = criteriaByRegex("office", word);
                Criteria matchInLoginLike = criteriaByRegex("login", word);
                listOfCriteria.add(new Criteria().orOperator(matchInEmailLike, matchInNameLike, matchInLoginLike,
                        matchInSurnameLike, matchInPatronymicLike, matchInOfficeLike));
            }
            if (filter.getAge() != null && !filter.getAge().trim().isEmpty()) {
                try {
                    int age = Integer.parseInt(filter.getAge());
                    listOfCriteria.add(new Criteria("user.age").is(age));
                } catch (NumberFormatException n) {
                    n.printStackTrace();
                }
            }
            if (filter.getSalary() != null && !filter.getSalary().trim().isEmpty()) {
                try {
                    double salary = Double.parseDouble(filter.getSalary());
                    listOfCriteria.add(new Criteria("salary").is(salary));
                } catch (NumberFormatException n) {
                    n.printStackTrace();
                }
            }
            Date from = null, to = null;
            SimpleDateFormat fromParse = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat toParse = new SimpleDateFormat("dd.MM.yyyy");
            try {
                from = fromParse.parse(filter.getFromDate());
            } catch (Exception e) {
                System.out.println("Not a date");
            }
            try {
                to = toParse.parse(filter.getToDate());
            } catch (Exception e){
                System.out.println("Not a date");
            }
            System.out.println(from + " " + to);
            Criteria between = null;
            if (from != null) {
                between = new Criteria("startWorkDate").gte(from);
            }
            if (to != null) {
                if (between != null) {
                    between = between.lt(to);
                } else {
                    between = new Criteria("startWorkDate").lt(to);
                }
            }
            if (between != null) {
                listOfCriteria.add(between);
            }
            if (filter.getFields() != null && !filter.getFields().isEmpty() && !filter.getFields().contains("all")) {
                String[] fields = filter.getFields().toArray(new String[0]);
                projection = project(fields);
            }
            if (listOfCriteria.size() != 0) {
                criteria = new Criteria().andOperator(listOfCriteria.toArray(new Criteria[0]));
            }
            m1 = new MatchOperation(criteria);
            Aggregation aggregation;
            if (projection != null) {
                aggregation = newAggregation(m1, projection);
            } else {
                aggregation = newAggregation(m1);
            }
            AggregationResults<EmployeeDto> results = mongoTemplate.aggregate(aggregation, "employees", EmployeeDto.class);
            return results.getMappedResults();
        }
        return all;
    }

    public String regexFormat(String word) {
        return ".*" + word + ".*";
    }

    public Criteria criteriaByRegex(String field, String word) {
        return new Criteria(field).regex(regexFormat(word), "i");
    }
}
