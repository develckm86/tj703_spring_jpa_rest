package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.entity.DeptEmpId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DeptServiceImpTest {
    @Autowired
    DeptService deptService;
    @Test
    void save() {
        DeptEmp deptEmp = new DeptEmp();
        deptEmp.setEmpNo(10001);
        deptEmp.setDeptNo("d003");
        deptEmp.setFromDate(LocalDate.parse("2024-02-01"));
        deptEmp.setToDate(LocalDate.parse("2024-04-01"));
        deptService.save(deptEmp);
    }

    @Test
    void remove() {
        DeptEmpId deptEmpId = new DeptEmpId();
        deptEmpId.setEmpNo(10001);
        deptEmpId.setDeptNo("d003");
        deptService.remove(deptEmpId);
    }
}