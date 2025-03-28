package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import com.tj703.l08_spring_jpa_rest.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/employ")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class EmpRestController {
    private final EmpService empService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //employ/rest   ?page=1&size=20 전체조회
    //employ/1001/rest  조회
    //employ/rest DELETE 삭제
    //employ/rest POST 등록
    //employ/rest PUT 수정
    @GetMapping("/rest")
    public ResponseEntity<Page<Employee>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Employee> employeePage=empService.readAll(pageRequest);
        return ResponseEntity.ok(employeePage);
    }
    @GetMapping("/{empNo}/rest")
    public ResponseEntity<Employee> findByEmpNo(@PathVariable int empNo) {
        Optional<Employee> empOpt=empService.readOne(empNo);

        return empOpt.isPresent() ? ResponseEntity.ok(empOpt.get()) : ResponseEntity.notFound().build();
    }
    @PutMapping(value = "/rest")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        logger.info(employee.toString());
        try {
            empService.modify(employee);
        }catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build(); //404
        }catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(204).build();
    }
    @DeleteMapping("/{empNo}/rest")
    public ResponseEntity<Void> delete(@PathVariable int empNo) {
        logger.info(empNo+"");
        try {
            empService.remove(empNo);
            return ResponseEntity.status(202).build();
        }catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/{empNo}/exist")
    public ResponseEntity<Boolean> exist(@PathVariable int empNo) {
        boolean exist=empService.exists(empNo);
        return ResponseEntity.ok(exist);
    }
}
