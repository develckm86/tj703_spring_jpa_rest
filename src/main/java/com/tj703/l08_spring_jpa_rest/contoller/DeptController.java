package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.service.DeptService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/dept")
@AllArgsConstructor
public class DeptController {
    private final DeptService deptService;
    //localhost:8888/dept/10010/readEmp.do

    private static final Logger logger= LoggerFactory.getLogger(DeptController.class);

    @GetMapping("/{empNo}/readEmp.do")
    @ResponseBody //뷰를 렌더링하지 않고 반환하는 객체를 json 으로 변환(Jackson)
    public List<DeptEmp> readEmp(@PathVariable int empNo) {
        List<DeptEmp> deptEmpList=deptService.readByEmpNo(empNo);
        return deptEmpList;
    }
    @PostMapping("/deptEmp.do")
    @ResponseBody
    public Map<String, Object> deptEmpRegister(@RequestBody DeptEmp deptEmp){
        //@ModelAttribute : 쿼리스트링을 객체로 파싱
        //@RequestBody : json을 객체로 파싱
        boolean result = true;
        Map<String,Object> resultMap=new HashMap<>();
        //System.out.println(deptEmp);
        try {
            DeptEmp dept=deptService.save(deptEmp);
            resultMap.put("deptEmp",dept);
        } catch (Exception e) {
            resultMap.put("errorMsg",e.getMessage());
            result = false;
        }
        resultMap.put("msg","성공");
        resultMap.put("result",result);

        return resultMap;
    }






    @PostMapping("/register.do")
    public String register(
            @ModelAttribute DeptEmp deptEmp,
            RedirectAttributes redirectAttributes
    ) {
        //?empNo=10001&deptNo=d002&fromDate=2024-01-01&toDate=2025-01-01
        logger.info(deptEmp.toString());
        try{
            deptService.save(deptEmp);
        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg",e.toString());
            //addFlashAttribute : session 처럼 내장 객체로 값을 전달 후 삭제 됨
            return "redirect:/emp/"+deptEmp.getEmpNo()+"/detail.do";
        }
        //redirectAttributes.addAttribute("empNo", deptEmp.getEmpNo()); => 쿼리스트링 ?empNo=10002
        redirectAttributes.addFlashAttribute("msg","success");
        return "redirect:/emp/"+deptEmp.getEmpNo()+"/detail.do";
    }

}
