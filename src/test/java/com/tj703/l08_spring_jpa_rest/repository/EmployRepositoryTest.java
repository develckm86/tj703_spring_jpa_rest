package com.tj703.l08_spring_jpa_rest.repository;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployRepositoryTest {
    @Autowired
    private EmployRepository employRepository;

    @Test
    @Transactional
    void findById(){
        Optional<Employee> empOpt=employRepository.findById(10010);
        empOpt.ifPresent(System.out::println);
    }

}