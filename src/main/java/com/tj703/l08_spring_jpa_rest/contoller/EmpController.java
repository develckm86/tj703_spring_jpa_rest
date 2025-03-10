package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import com.tj703.l08_spring_jpa_rest.service.EmpService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/emp")
@AllArgsConstructor
public class EmpController {
    private final EmpService empService;


    @GetMapping("/findAll.do")
    public String findAll(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10")int size,
            Model model
    ) {
        Pageable pageable= PageRequest.of(page-1, size);
        Page<Employee> empPage=empService.readAll(pageable);
        model.addAttribute("empPage", empPage);
        return "emp/findAll";
    }
    @GetMapping("/{empNo}/detail.do")
    public String detail(
            @PathVariable int empNo,
            Model model
    ){
        Optional<Employee> empOpt=empService.readOne(empNo);
        Employee emp=empOpt.get();
        if(emp==null) {
            return "redirect:/emp/findAll.do";
        }else{
            model.addAttribute("emp", emp);
            return "emp/detail";
        }
    }



}
