package com.mvl.se2020.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.models.User;

/**
 * @author MyoGlobal Developer1
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "Select * from user where user_email=?1 and user_password=?2 and status='ENABLE'", nativeQuery = true)
	User checkUser(String email, String password);

	@Query(value = "Select * from user where status=?1", nativeQuery = true)
	List<User> findAllUsers(String string);

	@Query(value = "Select * from user where user_name like %:name% and role=:role and status='ENABLE'", nativeQuery = true)
	List<User> getUsersByNameAndRole(String name, String role);

	@Query(value = "Select * from user where user_name like %:name% and status='ENABLE'", nativeQuery = true)
	List<User> getUsersByName(String name);

	@Query(value = "Select * from user where role=:role and status='ENABLE'", nativeQuery = true)
	List<User> getUsersByRole(String role);

}
