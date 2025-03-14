package com.tj703.l08_spring_jpa_rest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "salaries")
@IdClass(SalaryId.class)
public class Salary {
    @Id
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;
    @Id
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no",insertable = false,updatable = false)
    @ToString.Exclude
    @JsonBackReference
    private Employee emp;
}