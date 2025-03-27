package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import com.tj703.l08_spring_jpa_rest.repository.EmployRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpServiceImp implements EmpService {
    private final EmployRepository empRepository;
    private final EntityManager entityManager;

    @Override
    public Optional<Employee> readOne(int empNo) {
        return empRepository.findById(empNo);
    }

    @Override
    public Page<Employee> readAll(Pageable pageable) {
        return empRepository.findAll(pageable);
    }

    @Override
    public void register(Employee employee) {
        Employee emp=entityManager.find(Employee.class, employee.getId());
        if(emp!=null){
            throw new IllegalArgumentException("Employee already exists");
        }
        entityManager.persist(employee);

    }

    @Override
    @Transactional
    public void modify(Employee employee) {
        Employee emp=entityManager.find(Employee.class, employee.getId());
        if(emp==null){
            throw new IllegalArgumentException("Employee not found");
        }
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setGender(employee.getGender());
        emp.setHireDate(employee.getHireDate());
        emp.setBirthDate(employee.getBirthDate());

        entityManager.merge(emp);

    }

    @Override
    @Transactional
    public void remove(int empNo) {
        Employee emp=entityManager.find(Employee.class, empNo);
        if(emp==null){
            throw new IllegalArgumentException("Employee not found");
        }
        entityManager.remove(emp);
    }
}
