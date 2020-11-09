package com.mvl.se2020.web.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mvl.se2020.web.dto.UserInquiryDTO;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Role;
import com.mvl.se2020.web.models.User;
import com.mvl.se2020.web.repository.RoleRepository;
import com.mvl.se2020.web.repository.UserRepository;

//zmh
@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@SuppressWarnings("unused")
	@Autowired
	private RoleRepository roleRepo;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(RoleRepository myRoleRepo, UserRepository myUserRepo,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.roleRepo = myRoleRepo;
		this.userRepo = myUserRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/*
	 * public Boolean isExistUser(String email) { Boolean isExist = false;
	 * List<User> ulist = userRepo.findAllUsers(Status.ENABLE.toString()); for (User
	 * u : ulist) { if (u.getEmail().equals(email)) { isExist = true; break; } }
	 * return isExist; }
	 */

	public User findByName(String username) {

		User u = userRepo.findByName(username);

		return u;
	}

	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		/* Role userRole = roleRepo.findByName("ADMIN"); */
		Role r = (Role) user.getRoles().toArray()[0];
		user.setRoles(new HashSet<Role>(Arrays.asList(r)));
		return userRepo.save(user);
	}

	public User findById(Long id) {
		User u = userRepo.findById(id).orElseThrow();
		return u;
	}

	public User checkUser(String name, String password) {
		User u = userRepo.checkUser(name, password);
		return u;
	}

	public List<User> findAllUsers(String string) {
		List<User> ulist = userRepo.findAllUsers(string);
		return ulist;
	}

	public List<User> inquery(UserInquiryDTO userDTO) {

		List<User> userList;

		if (!userDTO.getName().isEmpty() && userDTO.getRole() != null) {
			userList = userRepo.getUsersByNameAndRoleId(userDTO.getName().toLowerCase(), userDTO.getRole().getId());
		} else if (!userDTO.getName().isEmpty() && userDTO.getRole() == null) {
			userList = userRepo.getUsersByName(userDTO.getName().toLowerCase());
		} else if (userDTO.getName().isEmpty() && userDTO.getRole() != null) {
			userList = userRepo.getUsersByRoleId(userDTO.getRole().getId());
		} else {
			userList = userRepo.findAllUsers(Status.ENABLE.toString());
		}

		return userList;
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}
}
