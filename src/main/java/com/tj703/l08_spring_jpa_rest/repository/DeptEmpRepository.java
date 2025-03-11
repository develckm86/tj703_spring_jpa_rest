package com.tj703.l08_spring_jpa_rest.repository;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.entity.DeptEmpId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {
    @EntityGraph(attributePaths = "dept")
    List<DeptEmp> findByEmpNo(int empNo);
    //lazy 조회할때 프록시객체를 json 으로 변환하는 문제
    /*
    * responseBody 객체를 json 으로 변환할 때
    * 지연조회(deptEmp->dept)를 하게되면 조회하는
    * 객체가 Proxy(지연조회시 엔터티 객체가 실제로 조회하는 동안 참조 객체로 생성된 대리 객체)
    * 객체로 대체되는데 때문에 json 으로 변환되는 객체는 Proxy 객체가 된다.
    * 하지만 Proxy 객체가 직렬화가 되지 않아 json 으로 변환할 수 없다.
    * =>지연조회를 하지 않기 위해 EntityGraph 로 강제로 조인을 하게됨
    * */

}
