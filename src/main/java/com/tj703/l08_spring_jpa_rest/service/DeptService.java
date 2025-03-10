package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;

import java.util.List;

public interface DeptService {
    //사원 상세에세 비동기식으로 조회 :deptEmp.findByEmpNo(int empNo)
    List<DeptEmp> readByEmpNo(int empNo);

}
