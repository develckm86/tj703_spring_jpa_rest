package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.Salary;
import com.tj703.l08_spring_jpa_rest.entity.SalaryId;
import com.tj703.l08_spring_jpa_rest.service.SalaryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@CrossOrigin(value = "http://localhost:3000")
//리소스 쉐어를 허용하는 서버 추가
@AllArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    //{"empNo":10001} ->@RequestBody
    //?empNo=10001 ->@ModelAttribute
    @PutMapping("/action.do")
    public ResponseEntity<Void> modifyAction(
            @RequestBody Salary salary
    ){
        //logger.info(salary.toString());
        try {
            salaryService.modify(salary);
        }catch (IllegalArgumentException e){ //수정할 리소스가 없다.
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/action.do")
    public ResponseEntity<Void> deleteAction(
            @RequestBody SalaryId salaryId
    ){
        try {
            salaryService.remove( salaryId );
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(202).build();
    }

    @PostMapping("/action.do")
    public ResponseEntity<Void> createAction(
            @RequestBody Salary salary
    ){
        try {
            salaryService.register(salary);
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(409).build();
            //409: 충돌오류(이미 존재하는 리소스를 다시 등록하려할때)
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(201).build();
    }



}
