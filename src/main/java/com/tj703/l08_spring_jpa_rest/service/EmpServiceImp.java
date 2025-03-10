package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import com.tj703.l08_spring_jpa_rest.repository.EmployRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpServiceImp implements EmpService {
    private final EmployRepository empRepository;

    @Override
    public Optional<Employee> readOne(int empNo) {
        return empRepository.findById(empNo);
    }

    @Override
    public Page<Employee> readAll(Pageable pageable) {
        return empRepository.findAll(pageable);
    }
}
