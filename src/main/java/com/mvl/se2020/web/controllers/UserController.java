package com.mvl.se2020.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.mvl.se2020.web.service.UserService;
import com.mvl.se2020.web.service.WarehouseService;

@Controller
public class UserController {

	@Autowired
	public WarehouseService wservice;
	@Autowired
	public UserService userService;

	@Autowired
	public RoleRepository roleRepository;

	@GetMapping(value = { "/", "/login" })
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping("/admin/user_list")
	public String userList(Model model, HttpSession session) {

		List<User> userList = userService.findAllUsers(Status.ENABLE.toString());

		model.addAttribute("users", userList);
		model.addAttribute("userDTO", new UserInquiryDTO());
		model.addAttribute("roles", roleRepository.findAll());

		return "admin/user_list";
	}

	@RequestMapping("/admin/create_user")
	public String userRegister(Model model) {
		User user = new User();

		model.addAttribute("user", user);
		model.addAttribute("roles", roleRepository.findAll());

		return "admin/create_user";
	}

	@RequestMapping(value = "/admin/create_user", method = RequestMethod.POST)
	public String userRegisterSubmit(Model model, @Validated User user, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();

		User userExist = userService.findByName(user.getName());
		if (userExist != null) {
			result.rejectValue("userName", "error.user",
					"There is already a user registered with the user name provided");
		}
		if (result.hasErrors()) {

			modelAndView.setViewName("admin/create_user");
		} else {
			user.setStatus(Status.ENABLE);
			user.setCreateDate(new Date());
			user.setModifiedDate(new Date());
			userService.saveUser(user);
			/*
			 * List<User> ulist = userService.findAll(); modelAndView.addObject("users",
			 * ulist); modelAndView.addObject("user", new User());
			 * modelAndView.addObject("roles", roleRepository.findAll());
			 * modelAndView.setViewName("admin/user_list");
			 */

			return "redirect:/admin/user_list";

		}
		return "admin/create_user";
		/*
		 * if (!isExist) { user.setStatus(Status.ENABLE); user.setCreateDate(new
		 * Date()); user.setModifiedDate(new Date()); userService.saveUser(user);
		 * 
		 * } else { model.addAttribute("user", user); model.addAttribute("errorMsg",
		 * "Exist"); model.addAttribute("roles", roleRepository.findAll());
		 * 
		 * return "create_user"; }
		 * 
		 * List<User> ulist = userService.findAllUsers(Status.ENABLE.toString());
		 * 
		 * model.addAttribute("users", ulist); model.addAttribute("user", new User());
		 * model.addAttribute("roles", roleRepository.findAll());
		 * model.addAttribute("success", "success");
		 * 
		 * return "redirect:/user_list";
		 */
	}

	@RequestMapping("/admin/edit_user/{id}")
	public String userEditGet(Model model, @PathVariable Long id) {

		User user = userService.findById(id);

		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("user", user);

		return "admin/edit_user";

	}

	@RequestMapping(value = "/admin/edit_user", method = RequestMethod.POST)
	public String editPagePost(Model model, @ModelAttribute User user) {
		try {
			User db_user = userService.findById(user.getId());
			user.setStatus(db_user.getStatus());
			user.setCreateDate(db_user.getCreateDate());
			user.setModifiedDate(new Date());

			userService.saveUser(user);

			/*
			 * List<User> userList = userService.findAllUsers(Status.ENABLE.toString());
			 * 
			 * model.addAttribute("user", new User()); model.addAttribute("users",
			 * userList); model.addAttribute("update", "Success");
			 */

			return "redirect:/admin/user_list";
		} catch (Exception e) {
			return null;
		}

	}

	@PostMapping(value = "/admin/search_user")
	public String searchUser(Model model, @ModelAttribute UserInquiryDTO userDTO) {
		List<User> userList = null;
		if (userDTO != null) {
			userList = userService.inquery(userDTO);

		} else {
			userList = userService.findAllUsers(Status.ENABLE.toString());
		}
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("users", userList);
		model.addAttribute("roles", roleRepository.findAll());

		return "admin/user_list";
	}

	@RequestMapping("/admin/delete_user/{id}")
	public String userDelete(Model model, @PathVariable Long id) {
		User user = userService.findById(id);
		user.setStatus(Status.DISABEL);
		userService.saveUser(user);

		return "redirect:/admin/user_list";

	}

	@InitBinder("user")
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
	}
}
