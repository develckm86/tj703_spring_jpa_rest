package com.tj703.l08_spring_jpa_rest.service;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.entity.DeptEmpId;
import com.tj703.l08_spring_jpa_rest.repository.DeptEmpRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeptServiceImp implements DeptService {
    private final DeptEmpRepository deptEmpRepository;
    private final EntityManager entityManager;
    @Override
    //@Transactional //:지연조회일때 view 에서 조회가 끝나기 때문
    public List<DeptEmp> readByEmpNo(int empNo) {
        return deptEmpRepository.findByEmpNo(empNo);
    }


    @Override
    @Transactional
    public DeptEmp register(DeptEmp deptEmp) {
        DeptEmpId deptEmpId=new DeptEmpId();
        deptEmpId.setEmpNo(deptEmp.getEmpNo());
        deptEmpId.setDeptNo(deptEmp.getDeptNo());
        DeptEmp deptEmp2=entityManager.find(DeptEmp.class, deptEmpId);
        if(deptEmp2!=null) throw new IllegalAccessError("이미 존재하는 데이터");
        entityManager.persist(deptEmp);
        deptEmp2=entityManager.find(DeptEmp.class, deptEmpId);
        return deptEmp2;
    }

    @Override
    @Transactional
    public DeptEmp modify(DeptEmp deptEmp) {
        DeptEmpId deptEmpId=new DeptEmpId();
        deptEmpId.setEmpNo(deptEmp.getEmpNo());
        deptEmpId.setDeptNo(deptEmp.getDeptNo());

        DeptEmp deptEmp2=entityManager.find(DeptEmp.class, deptEmpId);

        if(deptEmp2==null)throw new IllegalAccessError("존재하지 않는 데이터");

        deptEmp2.setFromDate(deptEmp.getFromDate());
        deptEmp2.setToDate(deptEmp.getToDate());
        entityManager.merge(deptEmp);
        return deptEmp;
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
