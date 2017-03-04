package appSpring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import appSpring.entity.User;
import appSpring.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/user_profile")
	public String user(Model model, HttpServletRequest request){

		User loggedUser = userRepository.findByName(request.getUserPrincipal().getName());
		model.addAttribute("user", loggedUser);
		if (request.isUserInRole("ADMIN")) model.addAttribute("admin",true);
		model.addAttribute("logged",true);
		model.addAttribute("profile", true);

		return "userProfile";
	}

}
