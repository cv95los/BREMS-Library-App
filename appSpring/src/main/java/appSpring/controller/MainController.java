package appSpring.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import appSpring.entity.Resource;
import appSpring.entity.ResourceType;
import appSpring.entity.User;
import appSpring.repository.ResourceRepository;
import appSpring.repository.ResourceTypeRepository;
import appSpring.repository.UserRepository;

@Controller
public class MainController {

	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private ResourceTypeRepository resourceTypeRepo;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String resources(Model model, HttpServletRequest request) {

		if (request.isUserInRole("ADMIN") || request.isUserInRole("USER")) {
			User loggedUser = userRepository.findByName(request.getUserPrincipal().getName());
			model.addAttribute("user", loggedUser);
			model.addAttribute("logged",true);
		}
		else model.addAttribute("unlogged",true);
		if (request.isUserInRole("ADMIN")) model.addAttribute("admin",true);

		ResourceType type;

		type = resourceTypeRepo.findOneByName("Libro");
		Page<Resource> books = resourceRepository.findByResourceType(type, new PageRequest(0,1));

		type = resourceTypeRepo.findOneByName("Revista");
		Page<Resource> magazines = resourceRepository.findByResourceType(type, new PageRequest(0,1));

		Page<Resource> allShelf = resourceRepository.findAll(new PageRequest(0,1));

		model.addAttribute("books", books);
		model.addAttribute("magazines", magazines);
		model.addAttribute("all", allShelf);
		model.addAttribute("index", true);

		return "index";
	}

	@RequestMapping("/about")
	public String about(Model model, HttpServletRequest request) {

		if (request.isUserInRole("ADMIN") || request.isUserInRole("USER")) {
			User loggedUser = userRepository.findByName(request.getUserPrincipal().getName());
			model.addAttribute("user", loggedUser);
			model.addAttribute("logged",true);
		}
		else model.addAttribute("unlogged",true);
		if (request.isUserInRole("ADMIN")) model.addAttribute("admin",true);
		model.addAttribute("about", true);

		return "about";
	}

	@RequestMapping("/contact")
	public String contact(Model model, HttpServletRequest request) {

		if (request.isUserInRole("ADMIN") || request.isUserInRole("USER")) {
			User loggedUser = userRepository.findByName(request.getUserPrincipal().getName());
			model.addAttribute("user", loggedUser);
			model.addAttribute("logged",true);
		}
		else model.addAttribute("unlogged",true);
		if (request.isUserInRole("ADMIN")) model.addAttribute("admin",true);
		model.addAttribute("contact", true);

		return "contact";
	}

}
