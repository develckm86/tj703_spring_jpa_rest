package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.entity.DeptEmpId;
import com.tj703.l08_spring_jpa_rest.repository.DeptEmpRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DeptServiceImp implements DeptService {
    private final DeptEmpRepository deptEmpRepository;
    private final EntityManager entityManager; //영속성 컨텍스트 관리
    //영속성 컨텍스트 : 조회 했던 데이터를 저장하는 캐시공간
    //1. 불필요한 데이터베이스 접속을 막기위해
    //2. 트랜잭션 관리를 안전하고 효율적으로 하기 위해

    @Override
    //@Transactional //:지연조회일때 view 에서 조회가 끝나기 때문
    public List<DeptEmp> readByEmpNo(int empNo) {
        return deptEmpRepository.findByEmpNo(empNo);
    }

    @Override
    @Transactional //영속성 컨텍스를 사용할때는 꼭 트랜잭션 단위로 실행해야한다.
    public void register(DeptEmp deptEmp) {
        DeptEmpId deptEmpId = new DeptEmpId();
        deptEmpId.setEmpNo(deptEmp.getEmpNo());
        deptEmpId.setDeptNo(deptEmp.getDeptNo());
        DeptEmp existDeptEmp=entityManager.find(DeptEmp.class,deptEmpId);
        //System.out.println(existDeptEmp);
        if (existDeptEmp!=null) {
            //이미 존재하기 때문에 저장할 수 없다.
            throw new IllegalArgumentException("이미 부서이동 내역이 존재합니다.");
            //throw 에러발생, IllegalArgumentException : 객체(생성,파라미터)의 문제가 있어서 발생하는 오류
        }
        entityManager.persist(deptEmp);//매개변수로 새로운 객체를 참조해야함!!
        //entityManager.persist : 영속성 컨텍스트에 새로운 객체를 저장하면 트랜젝션이 종료되었을때 db 에 insert 로 저장
        //db에 매개변수 데이터를 저장하라!!
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
