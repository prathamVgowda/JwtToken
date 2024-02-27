package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;


@RestController
@RequestMapping("/api/v1/")
public class EmployeeController 
{
	
	private static final Logger log=LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/create")
	// @PreAuthorize("hasAuthority('ROLE_USER')")
	public Employee registerEmployee(@RequestBody Employee employee) {

		return employeeService.save(employee);
	}

	@GetMapping("/hai")
	public String hai() 
	{
		log.info("info method working perfectly using in hai");
		log.debug("debug method working perfectly using in hai");
		log.error("error method working perfectly using in hai");
		log.warn("war message also working perfectly using in hai");
		return "hai";
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Employee> getAll() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/get/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Employee getById(@PathVariable Integer id) {
		return employeeService.getByEmployee(id);
	}
}