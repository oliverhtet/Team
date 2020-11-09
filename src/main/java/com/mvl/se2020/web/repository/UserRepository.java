package com.mvl.se2020.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.models.User;

/**
 * @author zmh
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "Select * from user where user_name=?1 and user_password=?2", nativeQuery = true)
	User checkUser(String name, String password);

	@Query(value = "Select * from user where status=?1", nativeQuery = true)
	List<User> findAllUsers(String string);

	@Query(value = "Select * from user where user_name like %:name% and status='ENABLE'", nativeQuery = true)
	List<User> getUsersByName(String name);

	@Query(value = "Select * from user where user_email=?1 and status='ENABLE'", nativeQuery = true)
	User findUserByEmail(String email);

	@Query(value = "Select * from user where user_name like %:name% and role_id=:id and status='ENABLE'", nativeQuery = true)
	List<User> getUsersByNameAndRoleId(String name, Long id);

	@Query(value = "Select * from user where role_id=?1 and status='ENABLE'", nativeQuery = true)
	List<User> getUsersByRoleId(Long id);

	User findByName(String username);

}
