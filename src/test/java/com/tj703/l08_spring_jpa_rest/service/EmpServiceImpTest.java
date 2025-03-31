package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmpServiceImpTest {
    @Autowired
    private EmpService empService;
    @Test
    @Transactional
    void readOne() {
        System.out.println(empService.readOne(10010).get());
    }

    @Test
    @Transactional
    void readAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> empPage=empService.readAll(pageable);
        System.out.println(empPage.getContent());
    }

    @Test
    void modify() {
        Employee employee = new Employee();
        employee.setId(10001);
        employee.setGender("F");
        employee.setHireDate(LocalDate.parse("2025-03-30"));
        employee.setBirthDate(LocalDate.parse("2025-03-31"));
        employee.setFirstName("A");
        employee.setLastName("B");
        empService.modify(employee);
    }

    @Test
    void register() {
        Employee employee = new Employee();
        employee.setId(101);
        employee.setGender("M");
        employee.setHireDate(LocalDate.parse("1925-03-30"));
        employee.setBirthDate(LocalDate.parse("1988-03-31"));
        employee.setFirstName("길동");
        employee.setLastName("홍");
        empService.register(employee);
    }

    @Test
    void remove() {
        empService.remove(101);
    }
    @Test
    void exists() {
        System.out.println(empService.exists(1));
    }
}