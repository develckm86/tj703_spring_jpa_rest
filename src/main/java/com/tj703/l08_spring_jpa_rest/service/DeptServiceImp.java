package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.entity.DeptEmpId;
import com.tj703.l08_spring_jpa_rest.repository.DeptEmpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DeptServiceImp implements DeptService {
    private final DeptEmpRepository deptEmpRepository;
    @Override
    //@Transactional //:지연조회일때 view 에서 조회가 끝나기 때문
    public List<DeptEmp> readByEmpNo(int empNo) {
        return deptEmpRepository.findByEmpNo(empNo);
    }

    @Override
    @Transactional
    public DeptEmp save(DeptEmp deptEmp) {
        return deptEmpRepository.save(deptEmp);
    }

    @Override
    @Transactional
    public void remove(DeptEmpId deptEmpId) {
        deptEmpRepository.deleteById(deptEmpId);
    }
}
