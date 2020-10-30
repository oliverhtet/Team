package com.mvl.se2020.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mvl.se2020.web.enumerations.AccountType;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.User;
import com.mvl.se2020.web.repository.UserRepository;

@Controller

public class UserController {

	@Autowired
	public UserRepository userRepository;
	public AccountType accountType;

	// SampleDateFormat formatter = new SampleDateFormat("dd-MM-yyyy");

	@RequestMapping("/index")
	public ModelAndView userLogin(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return new ModelAndView("index");
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String userLogin(Model model, @ModelAttribute User user, HttpSession session) {

		User service_user = userRepository.checkUser(user.getEmail(), user.getPassword());
		session.setAttribute("user", service_user);

		if (service_user == null) {
			System.out.println("There is no user match with this email.");
		} else {
			model.addAttribute("accountType", accountType);
			model.addAttribute("user", service_user);

			if (session.getAttribute("user") != null) {
				System.out.println(session.getAttribute("user").toString() + " <<<<<<<session");
			}

			return "redirect:/welcome";
		}

		model.addAttribute("user", user);
		return "redirect:/index";
	}

	@RequestMapping("/user_list")
	public String userList(Model model) {
		List<User> userList = userRepository.findAll();

		System.out.println("User List Method");
		model.addAttribute("users", userList);

		return "user_list";
	}

	@RequestMapping("/welcome")
	public String userWelcome(Model model, @ModelAttribute User user, HttpSession session) {

		User u = (User) session.getAttribute("user");

		System.out.println("User Login Infomation >>>>>>" + u.toString());

		model.addAttribute("user", u);

		return "user_index";

	}

	@RequestMapping("/logout")
	public String userLogout(Model model, HttpSession session) {

		session.removeAttribute("user");
		User u = (User) session.getAttribute("user");

		if (session.getAttribute("user") != null) {
			System.out.println("Reach There >>>>>>" + u.toString());

			model.addAttribute("user", u);
		} else if (session.getAttribute("user") == null) {
			System.out.println("Session Clear Successful!");
		}

		return "redirect:/index";

	}

	@RequestMapping("/create_user")
	public String userRegister(Model model) {
		User user = new User();

		model.addAttribute("accountType", accountType);
		model.addAttribute("user", user);

		return "user_register";

	}

	@RequestMapping(value = "/create_user", method = RequestMethod.POST)
	public String userRegisterSubmit(Model model, @ModelAttribute User user) {
		user.setAccountType(AccountType.ADMIN);
		user.setStatus(Status.ENABLE);
		user.setCreateDate(new Date());
		user.setModifiedDate(new Date());

		model.addAttribute("user", user);

		userRepository.save(user);

		List<User> userList = userRepository.findAll();

		model.addAttribute("users", userList);

		return "redirect:/user_list";

	}

	@RequestMapping("/edit_user/{id}")
	public String userEditGet(Model model, @PathVariable Long id) {

		User user = userRepository.findById(id).orElseThrow();

		System.out.println("User Edit Form>>>>" + user.toString());

		model.addAttribute("accountType", accountType);
		model.addAttribute("user", user);

		return "edit_user";

	}

	@RequestMapping(value = "/edit_user", method = RequestMethod.POST)
	public String editPagePost(Model model, @ModelAttribute User user) {

		user.setDob(new Date());
		user.setModifiedDate(new Date());
		userRepository.save(user);

		List<User> userList = userRepository.findAll();
		model.addAttribute("users", userList);

		return "redirect:/user_list";
	}
	
	@RequestMapping("/delete_user/{id}")
	public String userDelete(Model model, @PathVariable Long id) {

		User user = userRepository.findById(id).orElseThrow();

		
		model.addAttribute("user", user);

		return "edit_user";

	}

	@InitBinder("user")
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
	}
}
