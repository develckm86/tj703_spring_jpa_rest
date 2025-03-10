package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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
}