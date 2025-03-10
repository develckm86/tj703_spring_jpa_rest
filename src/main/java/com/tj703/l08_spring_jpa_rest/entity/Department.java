package com.tj703.l08_spring_jpa_rest.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "departments")
public class Department implements Serializable {
    @Id
    @Column(name = "dept_no", nullable = false, length = 4)
    private String deptNo;

    @Column(name = "dept_name", nullable = false, length = 40)
    private String deptName;

}