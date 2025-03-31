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
    private final EntityManager entityManager; //영속성컨텍스트 조회

    @Override
    public Optional<Employee> readOne(int empNo) {
        return empRepository.findById(empNo);
    }

    @Override
    public Page<Employee> readAll(Pageable pageable) {
        return empRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void modify(Employee employee) {
        Employee existsEmp=entityManager.find(Employee.class, employee.getId());
        if(existsEmp==null) throw new IllegalArgumentException("수정할 emp가 없습니다.");
        existsEmp.setFirstName(employee.getFirstName());
        existsEmp.setLastName(employee.getLastName());
        existsEmp.setBirthDate(employee.getBirthDate());
        existsEmp.setHireDate(employee.getHireDate());
        existsEmp.setGender(employee.getGender());
        entityManager.merge(existsEmp);
    }

    @Override
    @Transactional
    public void register(Employee employee) {
        Employee existsEmp=entityManager.find(Employee.class, employee.getId());
        if(existsEmp!=null) throw new IllegalArgumentException("이미 존재하는 사원입니다.");
        entityManager.persist(employee);
    }

    @Override
    @Transactional
    public void remove(int empNo) {
        Employee existsEmp=entityManager.find(Employee.class, empNo);
        if(existsEmp==null) throw new IllegalArgumentException("삭제할 emp가 없습니다.");
        entityManager.remove(existsEmp);
    }

    @Override
    public boolean exists(int empNo) {
        return empRepository.existsById(empNo);
    }

}
