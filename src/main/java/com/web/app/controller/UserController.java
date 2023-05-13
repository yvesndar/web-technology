package com.web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.app.model.NewUser;
import com.web.app.model.Roles;
import com.web.app.model.User;
import com.web.app.model.Zone;
import com.web.app.service.UserService;
import com.web.app.service.ZoneService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ZoneService zoneService;

	@GetMapping("/register")
	public ModelAndView registerPage() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject("user", new User());
		modelandview.setViewName("register");
		return modelandview;
	}

	@GetMapping("/error")
	public ModelAndView errorPage() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("error");
		return modelandview;
	}

	@GetMapping("/home")
	public ModelAndView homePage(HttpSession session, Model model) {
		ModelAndView modelandview = new ModelAndView();
		model.addAttribute("zone", new Zone());
		model.addAttribute("zones", zoneService.findAllZone());
		modelandview.setViewName("home");
		if (session.getAttribute("firstname") == null) {
			return registerPage();
		}
		return modelandview;
	}

	@GetMapping("/Admin")
	public ModelAndView AdminPage(HttpSession session, Model model) {
		ModelAndView modelandview = new ModelAndView();
		System.out.println("hello admin");
		model.addAttribute("zone", new Zone());
		model.addAttribute("user", new NewUser());
		model.addAttribute("zones", zoneService.findAllZone());
		modelandview.setViewName("Admin");
		if (session.getAttribute("firstname") == null || session.getAttribute("role") == null) {
			return registerPage();
		}
		if (session.getAttribute("firstname") != null && session.getAttribute("role") != null) {
			return modelandview;
		}

		return modelandview;
	}

	@PostMapping("/user/new")
	public String createUser(NewUser newUser) {
		System.out.println("hello");
		userService.createUser(newUser);
		return "redirect:/user/list";
	}

	@PostMapping("/user/login")
	public String Login(User user, HttpSession session, Model model) {
		user = userService.logUser(user);
		if (user != null && user.getRole() == Roles.ADMIN) {
			session.setAttribute("role", user.getRole());
			session.setAttribute("id", user.getId());
			session.setAttribute("firstname", user.getFirstname());
			session.setAttribute("lastname", user.getLastname());

			return "redirect:/Admin";
		} else if (user == null) {
			return "redirect:/register";
		} else {
			session.setAttribute("id", user.getId());
			session.setAttribute("role", user.getRole());
			session.setAttribute("firstname", user.getFirstname());
			session.setAttribute("lastname", user.getLastname());

			return "redirect:/home";
		}
	}

	@GetMapping("/user/list")
	public ModelAndView getAllUsers() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject("user", new User());
		modelandview.addObject("newUser", new NewUser());
		modelandview.addObject("users", userService.findAllUsers());
		modelandview.setViewName("user");
		return modelandview;
	}

	@GetMapping("user/all")
	public String getUsers() {
		System.out.println("hello");
		return "hello";
	}

	@GetMapping("user/update/{id}")
	public ModelAndView getUpdatePage(@PathVariable int id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("updateUser");
		return modelAndView;
	}

	@PostMapping("user/update/{id}")
	public String UpdateZone(@PathVariable int id, @ModelAttribute("user") User user, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		User exist = userService.getUserById(id);
		exist.setFirstname(user.getFirstname());
		exist.setLastname(user.getLastname());
		exist.setEmail(user.getEmail());
		exist.setRole(user.getRole());
		exist.setUsername(user.getUsername());

		System.out.println("here for update");
		userService.UpdateUser(exist);
		return "redirect:/user/list";
	}

	@GetMapping("user/delete/{id}")
	public String UpdateZone(@PathVariable int id) {
		userService.deleteUser(id);
		return "redirect:/user/list";
	}
}
