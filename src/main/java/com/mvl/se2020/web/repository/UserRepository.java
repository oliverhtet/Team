package com.mvl.se2020.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "Select * from user where user_email=?1 and user_password=?2", nativeQuery = true)
	User checkUser(String email, String password);

}
