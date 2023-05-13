package com.web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.app.model.User;
import com.web.app.model.Zone;
import com.web.app.repository.UserRepository;
import com.web.app.service.ZoneService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ZoneController {

	@Autowired
	private ZoneService zoneService;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/zone/new")
	public String NewZone(Zone zone, HttpSession session) {
		int id = (Integer) session.getAttribute("id");
		User user = userRepository.getUserById(id);
		zone.setCreatedby(user);
		zoneService.createZone(zone);
		return "redirect:/home";
	}

	@GetMapping("zone/update/{id}")
	public ModelAndView getUpdatePage(@PathVariable int id, Model model) {
		model.addAttribute("zone", zoneService.getZoneById(id));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("update");
		return modelAndView;
	}

	@PostMapping("zone/update/{id}")
	public String UpdateZone(@PathVariable int id, @ModelAttribute("zone") Zone zone, Model model,
			HttpSession session) {
		model.addAttribute("zone", zoneService.getZoneById(id));
		Zone exist = zoneService.getZoneById(id);
		exist.setCode(zone.getCode());
		exist.setName(zone.getName());
		exist.setDescription(zone.getDescription());
		System.out.println("here for update");
		zoneService.updateZone(exist);
		System.out.println(session.getAttribute("role"));
		if (session.getAttribute("role") == "USER") {
			return "redirect:/home";
		}
		return "redirect:/Admin";

	}

	@GetMapping("zone/delete/{id}")
	public String UpdateZone(@PathVariable int id) {
		zoneService.deleteZone(id);
		return "redirect:/home";
	}
}
