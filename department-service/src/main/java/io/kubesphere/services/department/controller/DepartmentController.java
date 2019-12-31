package io.kubesphere.services.department.controller;

import java.util.List;

import io.kubesphere.services.Department;
import io.kubesphere.services.EmployeeService;
import io.kubesphere.services.department.repository.DepartmentRepository;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.kubesphere.services.department.client.EmployeeClient;

@RestController
public class DepartmentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
    DepartmentRepository repository;
	@Autowired
	EmployeeClient employeeClient;

	@Reference(version = "1.0.0" , protocol = "dubbo")
	EmployeeService employeeService;
	
	@PostMapping("/")
	public Department add(@RequestBody Department department) {
		LOGGER.info("Department add: {}", department);
		return repository.add(department);
	}
	
	@GetMapping("/{id}")
	public Department findById(@PathVariable("id") Long id) {
		LOGGER.info("Department find: id={}", id);
		return repository.findById(id);
	}
	
	@GetMapping("/")
	public List<Department> findAll() {
		LOGGER.info("Department find");
		return repository.findAll();
	}
	
	@GetMapping("/organization/{organizationId}")
	public List<Department> findByOrganization(@PathVariable("organizationId") Long organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		return repository.findByOrganization(organizationId);
	}
	
	@GetMapping("/organization/{organizationId}/with-employees")
	public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		List<Department> departments = repository.findByOrganization(organizationId);
		departments.forEach(d -> d.setEmployees(employeeService.findByDepartment(d.getId())));
		return departments;
	}
	
}
