package com.tj703.l08_spring_jpa_rest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "dept_emp")
@IdClass(DeptEmpId.class)
public class DeptEmp implements Serializable {
    @Id
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;
    @Id
    @Column(name = "dept_no", nullable = false, length = 4)
    private String deptNo;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee emp;

    //@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dept_no" )
    private Department dept;


    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

}