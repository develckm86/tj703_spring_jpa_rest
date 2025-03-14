package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.Salary;
import com.tj703.l08_spring_jpa_rest.entity.SalaryId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SalaryServiceImpTest {
    @Autowired
    private SalaryService salaryService;
    @Test
    void readOne() {
        SalaryId salaryId = new SalaryId();
        salaryId.setEmpNo(10002);
        salaryId.setFromDate(LocalDate.parse("1996-08-03"));
        Optional<Salary> salaryOpt=salaryService.readOne(salaryId);
        System.out.println(salaryOpt.get());
    }
    @Test
    void modify() {
        Salary salary=new Salary();
        salary.setEmpNo(10002);
        salary.setFromDate(LocalDate.parse("1996-08-03"));

        salary.setSalary(2000000000);
        salary.setToDate(LocalDate.parse("2025-01-01"));
        salaryService.modify(salary);

    }

    @Test
    void register() {
        Salary salary=new Salary();
        salary.setEmpNo(10002);
        salary.setFromDate(LocalDate.parse("2000-01-01"));
        salary.setSalary(1000000000);
        salary.setToDate(LocalDate.parse("2001-01-01"));
        salaryService.register(salary);

    }

    @Test
    void remove() {
        SalaryId salaryId=new SalaryId();
        salaryId.setEmpNo(10002);
        salaryId.setFromDate(LocalDate.parse("2000-01-01"));
        salaryService.remove(salaryId);
    }
}