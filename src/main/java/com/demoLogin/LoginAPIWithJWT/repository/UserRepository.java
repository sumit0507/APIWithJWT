package com.demoLogin.LoginAPIWithJWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoLogin.LoginAPIWithJWT.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity,Long> {
	 
	Optional<UserEntity> findByEmail(String email) ;
	boolean existsByEmail(String email);

}
