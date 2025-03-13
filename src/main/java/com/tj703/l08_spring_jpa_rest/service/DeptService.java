package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.entity.DeptEmpId;

import java.util.List;
import java.util.Optional;

public interface DeptService {
    //사원 상세에세 비동기식으로 조회 :deptEmp.findByEmpNo(int empNo)
    List<DeptEmp> readByEmpNo(int empNo);
    //등록 수정 삭제
    void register(DeptEmp deptEmp);
   // void modify(DeptEmp deptEmp);
    DeptEmp save(DeptEmp deptEmp);
    void remove(DeptEmpId deptEmpId);

    Optional<DeptEmp> readOne(DeptEmpId deptEmpId);
}
