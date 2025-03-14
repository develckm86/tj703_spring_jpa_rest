package com.tj703.l08_spring_jpa_rest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SalaryRepositoryTest {
    @Autowired
    SalaryRepository salaryRepository;
    @Test
    @Transactional
    void findByEmpNo() {
        System.out.println(salaryRepository.findByEmpNo(10001).toString());
    }
}