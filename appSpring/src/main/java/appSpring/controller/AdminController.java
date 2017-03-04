package appSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import appSpring.repository.PenaltyRepository;
import appSpring.repository.ResourceRepository;
import appSpring.repository.UserRepository;

@Controller
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private PenaltyRepository penaltyRepository;

	@RequestMapping("/admin/")
	public String home(Model model) {

		return "admin/home";
	}

	@RequestMapping("/admin/users")
	public String users(Model model) {

		model.addAttribute("users", userRepository.findAll());

		return "admin/user_management";
	}

	@RequestMapping("/admin/fines")
	public String fines(Model model) {

		model.addAttribute("fines", penaltyRepository.findAll());

		return "admin/fines_management";
	}

	@RequestMapping("/admin/loans")
	public String loans(Model model) {

		return "admin/loans_management";
	}

	@RequestMapping("/admin/resources")
	public String resources(Model model) {

		model.addAttribute("resources", resourceRepository.findAll());

		return "admin/resource_management";
	}

}
