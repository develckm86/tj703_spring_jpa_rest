package com.tj703.l08_spring_jpa_rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class DeptEmpId implements java.io.Serializable {
    private static final long serialVersionUID = -5007790062757419121L;
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @Column(name = "dept_no", nullable = false, length = 4)
    private String deptNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeptEmpId entity = (DeptEmpId) o;
        return Objects.equals(this.empNo, entity.empNo) &&
                Objects.equals(this.deptNo, entity.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }

}