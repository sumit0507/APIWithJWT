package com.demoLogin.LoginAPIWithJWT.services;
import com.demoLogin.LoginAPIWithJWT.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoLogin.LoginAPIWithJWT.entity.EmployeeEntity;



@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repo;
	
//	public EmployeeEntity saveDetails(String name,String email,String department)
//	{
//		
//		EmployeeEntity en=new EmployeeEntity();
//		en.setEmpName(name);
//		en.setEmail(email);
//		en.setDepartment(department);
//		
//		
//		
//		return repo.save(en);
//				
//	}
	
	public void addEmployee(EmployeeEntity emp)
	{
		EmployeeEntity existing=repo.findByEmail(emp.getEmail());
		if(existing!=null)
		{
			throw new RuntimeException("Employee already exist with this email");
			
		}
		
		repo.save(emp);
				
	}
	
	
	
	public List<EmployeeEntity> getDetails()
	{
		return repo.findAll();
	}
	
	public Optional<EmployeeEntity> getEmployeeByid(Long id)
	{
		return repo.findById(id);
	}
	
	//update
	public void updateEmployee( Long id,EmployeeEntity newData)
	{
		Optional<EmployeeEntity> optional=repo.findById(id);
		if(optional.isPresent())
		{
			EmployeeEntity existing=optional.get();
			
			existing.setEmpName(newData.getEmpName() );
			existing.setEmail(newData.getEmail());
			existing.setDepartment(newData.getDepartment());
			
			repo.save(existing);
		}
		else
		{
			throw new RuntimeException("Employee not found with id"+id);
		}
	}

	public void deleteByid(Long id)
	{
		if(!repo.existsById(id))
		{
			throw new RuntimeException("Data not found");
		}
		repo.deleteById(id);
	}
}
