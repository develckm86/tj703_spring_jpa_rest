package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.DeptEmp;
import com.tj703.l08_spring_jpa_rest.entity.DeptEmpId;
import com.tj703.l08_spring_jpa_rest.service.DeptService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

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
    //ResponseEntity : 응답코드(status code)를 생성하고 dto를 같이 반환할 수 있는 객체
    @PostMapping("/deptEmp.do")
    @ResponseBody
    public ResponseEntity<DeptEmp> deptEmpRegister(@RequestBody DeptEmp deptEmp){
        //@ModelAttribute : 쿼리스트링을 객체로 파싱
        //@RequestBody : json을 객체로 파싱
        try {
            deptService.register(deptEmp);
            return ResponseEntity.status(201).body(deptEmp);//status:200  응답 {dept:{}}
        }catch (IllegalArgumentException e){//이미 부서이동 내역이 존재하면 발생
            return ResponseEntity.status(409).body(null);
            //저장하려는 데이터가 이미존재 : 클라이언트 잘못 (400~)
        }catch (DataIntegrityViolationException e){//참조할 데이터가 없을 때 오류
            return ResponseEntity.status(507).body(null);
            //507 : 저장실패 (참조할 키가 없음)
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null); //예상하지 못한 오류
        }
    }
    //GET:리소스 주세요, POST: 리소스 저장 (동기식 통신에서 가능)
    //DELETE :리소스 삭제해 주세요
    //PUT
    //FETCH
    @DeleteMapping("/deptEmp.do")
    public ResponseEntity<Void> removeDeptEmp(@RequestBody DeptEmpId deptEmpId) {
        logger.info(deptEmpId.getEmpNo().toString() );
        logger.info(deptEmpId.getDeptNo());
        try {
            deptService.remove(deptEmpId);
            //202 : 비동기식으로 데이터가 처리되었습니다. : =>데이터가 비동기식으로 삭제됨
            return ResponseEntity.status(202).build(); //build() :객체를 생성 (빌더패턴)
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(404).build();
            //404 : 동적리소스,db 리소스가 없는 것
        }catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    //REST API 쓰는 방식만 제공 -> 스프링은 REST API를 지원합니다.
    //get 방식에서 파라미터를 url에 포함하는 이유?? : url을 공유하기 위해

    @GetMapping("/{empNo}/{deptNo}/deptEmp.do")
    @ResponseBody
    public ResponseEntity<DeptEmp> readDeptEmp(
            @PathVariable int empNo,
            @PathVariable String deptNo
    ) {
        DeptEmpId deptEmpId=new DeptEmpId();
        deptEmpId.setEmpNo(empNo);
        deptEmpId.setDeptNo(deptNo);
        Optional<DeptEmp> deptEmpOpt=deptService.readOne(deptEmpId);
        //http://localhost:8888/dept/10001/d001/deptEmp.do
        return deptEmpOpt
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);

//        return deptEmpOpt
//                .map((deptEmp) -> {return ResponseEntity.ok(deptEmp);})
//                .orElseGet(()->{return ResponseEntity.notFound().build();});

//        if(deptEmpOpt.isPresent()){
//            return ResponseEntity.status(200).body(deptEmpOpt.get());
//        }else{
//            return ResponseEntity.status(404).body(null);
//        }
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
