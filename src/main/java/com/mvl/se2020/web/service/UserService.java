package com.mvl.se2020.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.User;
import com.mvl.se2020.web.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public Boolean isExistUser(String email) {
		Boolean isExist = false;
		List<User> ulist = userRepo.findAllUsers(Status.ENABLE.toString());
		for (User u : ulist) {
			if (u.getEmail().equals(email)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

}
