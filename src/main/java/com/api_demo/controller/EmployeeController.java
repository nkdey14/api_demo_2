package com.api_demo.controller;

import com.api_demo.entity.Employee;
import com.api_demo.payload.EmployeeDto;
import com.api_demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private EmployeeService empService;

    public EmployeeController(EmployeeService empService) {
        this.empService = empService;
    }

    //http://localhost:9090/api/v1/employee/saveEmployee
    @PostMapping("/saveEmployee")
    public ResponseEntity<Map<String, Object>> saveEmployeeData(@RequestBody EmployeeDto empDto){
        EmployeeDto employeeDto = empService.saveEmployeeInfo(empDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee Details Saved successfully!!");
        response.put("data", employeeDto);
        response.put("status", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //http://localhost:9090/api/v1/employee/allEmployees?pageNo=0&pageSize=3&sortBy=id&sortDir=asc
    @GetMapping("/allEmployees")
    public ResponseEntity<List<EmployeeDto>> listAllEmployees(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "3", required = false) int pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir
    ){
        List<EmployeeDto> empDtos = empService.getAllEmployees(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(empDtos, HttpStatus.OK);
    }

    //http://localhost:9090/api/v1/employee/updateEmployee/1
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable long id, @RequestBody EmployeeDto empDto){
        EmployeeDto employeeDto = empService.updateEmployeeInfo(id, empDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee Details Updated successfully!!");
        response.put("data", employeeDto);
        response.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //http://localhost:9090/api/v1/employee/deleteEmployee?id=1
    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<String> deleteEmployee(@RequestParam long id){
        empService.deleteEmployeeInfo(id);
        return new ResponseEntity<>("Employee Details Deleted Successfully!!", HttpStatus.OK);
    }

    //http://localhost:9090/api/v1/employee/getEmployee?id=2
    @GetMapping("/getEmployee")
    public ResponseEntity<EmployeeDto> getEmployeeDetails(@RequestParam long id){
        EmployeeDto dto = empService.getEmployeeById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee Details fetched successfully!!");
        response.put("data", dto);
        response.put("status", HttpStatus.OK.value());
        return new ResponseEntity(dto, HttpStatus.OK);
    }
}
