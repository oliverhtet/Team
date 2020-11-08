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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mvl.se2020.web.dto.UserInquiryDTO;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.User;
import com.mvl.se2020.web.repository.RoleRepository;
import com.mvl.se2020.web.repository.UserRepository;
import com.mvl.se2020.web.repository.WarehouseRepositroy;
import com.mvl.se2020.web.service.ProductService;
import com.mvl.se2020.web.service.UserService;

@Controller
public class UserController {

	ProductService service = new ProductService();

	@Autowired
	public UserRepository userRepository;
	@Autowired
	public WarehouseRepositroy wareRepo;
	@Autowired
	public UserService userService;

	@Autowired
	public RoleRepository roleRepository;

	@RequestMapping("/login")
	public ModelAndView userLogin(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(Model model, @ModelAttribute User user, HttpSession session) {

		User service_user = userRepository.checkUser(user.getEmail(), user.getPassword());

		if (service_user == null) {
			System.out.println("There is no user match with this email.");
			model.addAttribute("error","error");
			return "login";
		} else {

			return "redirect:/user_list";
			// String userRole = service_user.getRole().toString();

			/*
			 * if (service_user.getRole() == 'ADMIN') { model.addAttribute("userRole",
			 * userRole); return "redirect:/user_list";
			 * 
			 * } else { model.addAttribute("userRole", userRole); return
			 * "redirect:/product_list";
			 * 
			 * }
			 */

		}
	}

	@RequestMapping("/user_list")
	public String userList(Model model, HttpSession session) {

		List<User> userList = userRepository.findAllUsers(Status.ENABLE.toString());

		model.addAttribute("users", userList);
		model.addAttribute("userDTO", new UserInquiryDTO());
		model.addAttribute("roles", roleRepository.findAll());

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

		return "redirect:/login";

	}

	@RequestMapping("/create_user")
	public String userRegister(Model model) {
		User user = new User();

		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("user", user);

		return "create_user";
	}

	@RequestMapping(value = "/create_user", method = RequestMethod.POST)
	public String userRegisterSubmit(Model model, @ModelAttribute User user) {

		Boolean isExist = false;
		isExist = userService.isExistUser(user.getEmail());
		if (!isExist) {
			user.setStatus(Status.ENABLE);
			user.setCreateDate(new Date());
			user.setModifiedDate(new Date());
			userRepository.save(user);

		} else {
			model.addAttribute("user", user);
			model.addAttribute("errorMsg", "Exist");
			model.addAttribute("roles", roleRepository.findAll());

			return "create_user";
		}

		List<User> ulist = userRepository.findAllUsers(Status.ENABLE.toString());

		model.addAttribute("users", ulist);
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("success", "success");

		return "redirect:/user_list";
	}

	@RequestMapping("/edit_user/{id}")
	public String userEditGet(Model model, @PathVariable Long id) {

		User user = userRepository.findById(id).orElseThrow();

		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("user", user);

		return "edit_user";

	}

	@RequestMapping(value = "/edit_user", method = RequestMethod.POST)
	public String editPagePost(Model model, @ModelAttribute User user) {
		try {
			User db_user = userRepository.findById(user.getId()).orElseThrow();
			user.setStatus(db_user.getStatus());
			user.setCreateDate(db_user.getCreateDate());
			user.setModifiedDate(new Date());

			userRepository.save(user);

			List<User> userList = userRepository.findAllUsers(Status.ENABLE.toString());

			model.addAttribute("user", new User());
			model.addAttribute("users", userList);
			model.addAttribute("update", "Success");

			return "redirect:/user_list";
		} catch (Exception e) {
			return null;
		}

	}

	@PostMapping(value = "/search_user")
	public String searchUser(Model model, @ModelAttribute UserInquiryDTO userDTO) {
		List<User> userList = null;
		if (userDTO != null) {
			if (!userDTO.getName().isEmpty() && userDTO.getRole() != null) {
				userList = userRepository.getUsersByNameAndRoleId(userDTO.getName().toLowerCase(),
						userDTO.getRole().getId());
			} else if (!userDTO.getName().isEmpty() && userDTO.getRole() == null) {
				userList = userRepository.getUsersByName(userDTO.getName().toLowerCase());
			} else if (userDTO.getName().isEmpty() && userDTO.getRole() != null) {
				userList = userRepository.getUsersByRoleId(userDTO.getRole().getId());
			} else {
				userList = userRepository.findAllUsers(Status.ENABLE.toString());
			}

		} else {
			userList = userRepository.findAllUsers(Status.ENABLE.toString());
		}
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("users", userList);
		model.addAttribute("roles", roleRepository.findAll());

		return "user_list";
	}

	@RequestMapping("/delete_user/{id}")
	public String userDelete(Model model, @PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow();
		user.setStatus(Status.DISABEL);
		userRepository.save(user);
		model.addAttribute("message", "Success");
		List<User> userList = userRepository.findAllUsers(Status.ENABLE.toString());

		model.addAttribute("users", userList);
		return "redirect:/user_list";

	}

	@InitBinder("user")
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
	}
}
