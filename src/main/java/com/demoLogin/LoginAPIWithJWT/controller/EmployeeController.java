package com.demoLogin.LoginAPIWithJWT.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.demoLogin.LoginAPIWithJWT.customResponse.ApiResponse;
import com.mycomp.demoapp.entity.EmployeeEntity;
import com.mycomp.demoapp.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService es;
	
	//@PostMapping("/save")
//	public ApiResponse saveDetails(@RequestBody EmployeeEntity en)
//	{
//		
////		es.saveDetails(en.getEmpName(), en.getEmail(), en.getDepartment());
////		return new ApiResponse("Employee Created Successfully");
////		
//	}
	
	@PostMapping("/save")
	public ResponseEntity<ApiResponse> addEmployee( @Valid @RequestBody EmployeeEntity emp)
	{
		try {
			es.addEmployee(emp);
			return ResponseEntity.ok(new ApiResponse ("Employee created successfully"));
		}
		catch(RuntimeException ex)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(ex.getMessage()));
		}
	}
	
	@GetMapping("/allEmployee")
	public List<EmployeeEntity> getDetails()
	{
		return es.getDetails();
	}
	
	@GetMapping("/singleEmployee/{id}")
	public Optional<EmployeeEntity> getEmployeeByid(@PathVariable Long id)
	{
		return es.getEmployeeByid(id);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse> updateEmployee(@Valid @PathVariable Long id ,@RequestBody EmployeeEntity emp)
	{
		try {
		es.updateEmployee(id, emp);
		return ResponseEntity.ok(new ApiResponse("Update details Successfully"));
		} catch(RuntimeException ex)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ex.getMessage()));
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteByid(@PathVariable Long id)
	{
		try {
			es.deleteByid(id);
			return ResponseEntity.ok(new ApiResponse("Deleted successfully"));
			
		} catch (RuntimeException ex)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ex.getMessage()));
		}
	}

}
