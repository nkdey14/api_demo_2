package com.api_demo.service;

import com.api_demo.entity.Employee;
import com.api_demo.exception.ResourceNotFoundException;
import com.api_demo.payload.EmployeeDto;
import com.api_demo.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository empRepo;

    public EmployeeService(EmployeeRepository empRepo) {
        this.empRepo = empRepo;
    }

    Employee convertToEmployee(EmployeeDto empDto){
        Employee emp = new Employee();
        emp.setFirstName(empDto.getFirstName());
        emp.setLastName(empDto.getLastName());
        emp.setEmail(empDto.getEmail());
        emp.setMobile(empDto.getMobile());
        emp.setCity(empDto.getCity());
        return emp;
    }

    EmployeeDto convertToEmployeeDto(Employee emp){
        EmployeeDto empDto = new EmployeeDto();
        empDto.setFirstName(emp.getFirstName());
        empDto.setLastName(emp.getLastName());
        empDto.setEmail(emp.getEmail());
        empDto.setMobile(emp.getMobile());
        empDto.setCity(emp.getCity());
        empDto.setId(emp.getId());
        return empDto;
    }


    public EmployeeDto saveEmployeeInfo(EmployeeDto empDto) {
        Employee emp = convertToEmployee(empDto);
        Employee savedEmployee = empRepo.save(emp);
        return convertToEmployeeDto(savedEmployee);
    }

    public List<EmployeeDto> getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        List<Employee> employees = empRepo.findAll(pageable).getContent();
        return employees.stream()
                        .map(this::convertToEmployeeDto)
                        .collect(Collectors.toList());
    }

    public EmployeeDto updateEmployeeInfo(long id, EmployeeDto empDto) {
        Employee existingEmp = empRepo.findById(id)
                                      .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        existingEmp.setFirstName(empDto.getFirstName());
        existingEmp.setLastName(empDto.getLastName());
        existingEmp.setEmail(empDto.getEmail());
        existingEmp.setMobile(empDto.getMobile());
        existingEmp.setCity(empDto.getCity());
        Employee updatedEmployee = empRepo.save(existingEmp);
        return convertToEmployeeDto(updatedEmployee);
    }

    public void deleteEmployeeInfo(long id) {
        empRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        empRepo.deleteById(id);
    }

    public EmployeeDto getEmployeeById(long id) {
        Employee employee = empRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        EmployeeDto employeeDto = convertToEmployeeDto(employee);
        return employeeDto;

    }
}
