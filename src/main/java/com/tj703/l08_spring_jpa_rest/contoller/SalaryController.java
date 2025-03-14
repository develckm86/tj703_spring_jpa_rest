package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.Salary;
import com.tj703.l08_spring_jpa_rest.entity.SalaryId;
import com.tj703.l08_spring_jpa_rest.service.SalaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@Controller  : 기본값으로 뷰를 렌더링하도록 설정됨 return String or ModelAndView,
// 예외로 @ResponseBody 를 작성하면 뷰를 렌더링하지 않고 반환하는 객체를 json으로 변환
@RestController //기본값이 @ResponseBody, 뷰를 렌더링하지 못함
@RequestMapping("/salary")
@AllArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    //리스트조회 GET "/salary/empNo/read.do"
    //상세조회 GET "/salary/empNo<=10001/fromDate<=2000-01-01/read.do"
    //수정  PUT  "/salary/action.do"  json
    //등록  POST  "/salary/action.do"  json
    //삭제  DELETE  "/salary/action.do" json

    //http://localhost:8888/salary/10001/read.do
    @GetMapping("/{empNo}/read.do")
    public List<Salary> readList(@PathVariable int empNo) {
        List<Salary> readList=null;
        readList=salaryService.readByEmpNo(empNo);
        return readList;
    }
    //http://localhost:8888/salary/10004/1986-12-01/read.do
    @GetMapping("/{empNo}/{fromDate}/read.do")
    public ResponseEntity<Salary> read(
            @PathVariable int empNo,
            @PathVariable LocalDate fromDate ) {
        SalaryId salaryId=new SalaryId();
        salaryId.setEmpNo(empNo);
        salaryId.setFromDate(fromDate);
        Optional<Salary> salaryOpt=salaryService.readOne(salaryId);
        //??null이면?? 404
        return salaryOpt
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }





}
