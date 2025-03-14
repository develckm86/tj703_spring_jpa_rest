package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.Salary;
import com.tj703.l08_spring_jpa_rest.entity.SalaryId;
import com.tj703.l08_spring_jpa_rest.repository.SalaryRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SalaryServiceImp implements SalaryService{
    private final SalaryRepository salaryRepository;
    private final EntityManager entityManager;

    @Override
    public List<Salary> readByEmpNo(int empNo) {
        return salaryRepository.findByEmpNo(empNo);
    }

    @Override
    public Optional<Salary> readOne(SalaryId salaryId) {
        return salaryRepository.findById(salaryId);
    }

    @Override
    @Transactional
    public void modify(Salary salary) {
        //있으면 수정, 없으면 오류
        //jpa 영속성컨텍스를 트랜잭션관리를 잘함 -> 영속성 컨텍스트로 수정 진행
        SalaryId salaryId=new SalaryId();
        salaryId.setEmpNo(salary.getEmpNo());
        salaryId.setFromDate(salary.getFromDate());
        Salary existSalary=entityManager.find(Salary.class,salaryId);
        if(existSalary==null){
            throw new IllegalArgumentException("수정할 급여가 없습니다.");
        }
        //existSalary.setFromDate(salary.getFromDate());
        //복합키로 급여를 받은날을 포함했기 때문에 급여 받은 날은 수정 불가!!
        existSalary.setToDate(salary.getToDate());
        existSalary.setSalary(salary.getSalary());
        entityManager.merge(existSalary);
    }

    @Override
    @Transactional
    public void register(Salary salary) {
        //없으면 등록, 있으면 오류(이미 존재합니다.)
        SalaryId salaryId=new SalaryId();
        salaryId.setEmpNo(salary.getEmpNo());
        salaryId.setFromDate(salary.getFromDate());
        Salary existSalary=entityManager.find(Salary.class,salaryId);
        if(existSalary!=null){
            throw new IllegalArgumentException("이미 존재하는 급여입니다.");
        }
        //영속성 컨텍스트가 저장하려면 완전히 새로운 객체
        entityManager.persist(salary);
    }

    @Override
    @Transactional
    public void remove(SalaryId salaryId) {
        //없으면 삭제 불가, 있으면 삭제
        Salary existSalary=entityManager.find(Salary.class,salaryId);
        if(existSalary==null){
            throw new IllegalArgumentException("이미 삭제된 급여입니다.");
        }
        entityManager.remove(existSalary);
    }
}
