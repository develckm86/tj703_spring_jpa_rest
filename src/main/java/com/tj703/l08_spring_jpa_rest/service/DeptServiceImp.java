package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
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
    public List<DeptEmp> readByEmpNo(int empNo) {

        return deptEmpRepository.findByEmpNo(empNo);
    }
}
