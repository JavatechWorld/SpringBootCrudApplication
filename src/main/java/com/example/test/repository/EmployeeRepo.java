package com.example.test.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.test.model.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Integer>{

}
