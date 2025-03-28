package com.tj703.l08_spring_jpa_rest.contoller;

import com.tj703.l08_spring_jpa_rest.entity.Employee;
import com.tj703.l08_spring_jpa_rest.service.EmpService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//RestController : view 를 렌더링하지 않고 json 반환
@RestController
@RequestMapping("/rest/emp")
@CrossOrigin(value = "http://localhost:3000")
@AllArgsConstructor
public class EmpRestController {
    private final EmpService empService;
    //?page=1&size=20
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
}
