package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import com.tj703.l08_spring_jpa_rest.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employ")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders ="*")
@AllArgsConstructor
public class EmpRestController {
    private final EmpService empService;
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
    @PutMapping(value = "/rest", consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        try {
            empService.modify(employee);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); //404
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(204).build();
    }
}
