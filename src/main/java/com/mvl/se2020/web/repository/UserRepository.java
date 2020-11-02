package com.mvl.se2020.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "Select * from user where user_email=?1 and user_password=?2", nativeQuery = true)
	User checkUser(String email, String password);

	@Query(value = "Select * from user where status=?1", nativeQuery = true)
	List<User> findAllUserWithStatus(Status enable);

	@Query(value = "Select * from user where status=?1", nativeQuery = true)
	List<User> findAllUsers(String string);

}
