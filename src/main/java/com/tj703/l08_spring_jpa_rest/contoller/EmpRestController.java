package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import com.tj703.l08_spring_jpa_rest.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.JDBCException;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.Optional;

//RestController : view 를 렌더링하지 않고 json 반환
@RestController
@RequestMapping("/rest/emp")
@CrossOrigin(value = "http://localhost:3000")
@AllArgsConstructor
public class EmpRestController {
    private final EmpService empService;
    //?page=1&size=20
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/read")
    public ResponseEntity<Page<Employee>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        //page는 0부터
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<Employee> empPage= empService.readAll(pageRequest);
        return ResponseEntity.ok(empPage);
    }
    @GetMapping("/{empNo}/read")
    public ResponseEntity<Employee> detail(@PathVariable int empNo){

        Optional<Employee> empOpt=empService.readOne(empNo);

        return empOpt.map(ResponseEntity::ok).orElseGet(ResponseEntity.notFound()::build);
    }
    //삭제 pathVariable?
    @DeleteMapping ("/{empNo}/mutate")
    public ResponseEntity<Void> remove(@PathVariable int empNo){
        try {
            empService.remove(empNo);
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build(); //404
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/mutate")
    public ResponseEntity<Void> modify(@RequestBody Employee emp){
        // birthDate :"1986-05-25"
        // Employee.birthDate<LocalDate> : 문자열을 바로 파싱할 수 없다.
        logger.info(emp.toString());
        try {
            empService.modify(emp);
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mutate")
    public ResponseEntity<Void> register(@RequestBody Employee emp){
        logger.info(emp.toString());
        try {
            empService.register(emp);
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(409).build(); //409: 리소스 충돌
        }catch (JpaSystemException e){
            //400 : badRequest(데이터를 보내지 않은건)
            //422  : Unprocessable Entity (처리할 수 없는 데이터, 숫자=>문자열, 길이가 길거나 ...)
            logger.error(e.getMessage());
            return ResponseEntity.status(422).build();
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
    //뮤턴트 돌연변인 => 유전자 데이터 조작
    // mutate => 프로그래밍의 데이터 조작 (수정,등록,삭제)
    //localhost:8888/rest/emp/11/mutate

    //응답이 boolean 은 하지말아야한다. (js에서 형변환하기 힘들기 때문)
    // Boolean (  "false" ) -> true
    // "0" or "1" Boolean(Number("0")) =>false
    //map { exists: false }
    @Getter@Setter
    class ExistsDto{ boolean exists;}

    @GetMapping("/{id}/exists")
    public ResponseEntity<Integer> exists(@PathVariable int id){
        boolean exists=empService.exists(id);
        int existsNum=exists ? 1 : 0;
        return ResponseEntity.ok(existsNum);
    }
    //http://localhost:8888/rest/emp/10001/exists


}
