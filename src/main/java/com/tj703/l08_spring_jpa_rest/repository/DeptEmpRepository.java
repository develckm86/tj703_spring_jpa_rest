package com.tj703.l08_spring_jpa_rest.repository;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.entity.DeptEmpId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {
    //@EntityGraph(attributePaths = "dept")
    List<DeptEmp> findByEmpNo(int empNo);
    //lazy 조회할때 프록시객체를 json 으로 변환하는 문제
}
