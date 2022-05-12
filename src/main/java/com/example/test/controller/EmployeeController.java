package com.example.test.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.model.Employee;
import com.example.test.repository.EmployeeRepo;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepo employeerepo;
	
	@PostMapping("/saveEmployee")
	public Employee saveEmployee(@RequestBody Employee emp) {
		
		return employeerepo.save(emp);
	}
	
	@GetMapping("/getEmployeeById")
	public Optional<Employee> getEmployeeById(@RequestBody Employee emp){
		return employeerepo.findById(emp.getId());
	}
	
	@GetMapping("/getAllEmployees")
	public Iterable<Employee> getAllEmployee() {
		return employeerepo.findAll();
	}
	
	@PostMapping("/updateEmployee")
	public Employee updateEmployee(@RequestBody Employee emp) {
		Optional<Employee> emps = employeerepo.findById(emp.getId());
		emps.get().setDesignation(emp.getDesignation());
		return employeerepo.save(emps.get());
		
	}
	
	
	@PostMapping("/delete")
	public ResponseEntity<Object> deleteDataById(@RequestBody Employee emp){
		employeerepo.deleteById(emp.getId());
		Optional<Employee> emps = employeerepo.findById(emp.getId());
		if(emps.isPresent())
			return generateRespose("The EMployee is not deleted ", HttpStatus.BAD_REQUEST, emp);
		else
			return generateRespose("The Employee Deleted with Id : "+emp.getId() , HttpStatus.OK, emp);
	}
	
	public ResponseEntity<Object> generateRespose(String message, HttpStatus st , Object responseobj){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("meaasge", message);
		map.put("Status", st.value());
		map.put("data", responseobj);
		
		return new ResponseEntity<Object> (map,st);
	}
}
