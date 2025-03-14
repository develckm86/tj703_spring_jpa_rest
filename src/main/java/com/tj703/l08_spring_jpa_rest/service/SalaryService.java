package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.Salary;
import com.tj703.l08_spring_jpa_rest.entity.SalaryId;

import java.util.List;
import java.util.Optional;

public interface SalaryService {
    //리스트,상세,수정,삭제,등록

    List<Salary> readByEmpNo(int empNo);
    Optional<Salary> readOne(SalaryId salaryId);
    void modify(Salary salary);
    void register(Salary salary);
    void remove(SalaryId salaryId);

}
