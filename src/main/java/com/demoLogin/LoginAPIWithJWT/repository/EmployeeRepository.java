package com.demoLogin.LoginAPIWithJWT.repository;
import com.demoLogin.LoginAPIWithJWT.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
	EmployeeEntity findByEmail(String email);
}
