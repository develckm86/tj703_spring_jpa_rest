package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.service.DeptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dept")
@AllArgsConstructor
public class DeptController {
    private final DeptService deptService;
    //localhost:8888/dept/10010/readEmp.do
    @GetMapping("/{empNo}/readEmp.do")
    @ResponseBody //뷰를 렌더링하지 않고 반환하는 객체를 json 으로 변환(Jackson)
    public List<DeptEmp> readEmp(@PathVariable int empNo) {
        List<DeptEmp> deptEmpList=deptService.readByEmpNo(empNo);
        return deptEmpList;
    }

}
