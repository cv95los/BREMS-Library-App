package appSpring.restController;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import appSpring.component.UserComponent;
import appSpring.model.User;
import appSpring.service.UserService;

@RestController
public class LoginRestController {

	public interface UserDetail extends User.LoginInt {
	}

	private static final Logger log = LoggerFactory.getLogger(LoginRestController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserComponent userComponent;

	@JsonView(UserDetail.class)
	@RequestMapping(value = "/api/logIn")
	public ResponseEntity<User> logIn() {

		if (!userComponent.isLoggedUser()) {
			log.info("Not user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			User loggedUser = userComponent.getLoggedUser();
			log.info("Logged as " + loggedUser.getName());
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);
		}
	}

	@RequestMapping("/api/logOut")
	public ResponseEntity<Boolean> logOut(HttpSession session) {

		if (!userComponent.isLoggedUser()) {
			log.info("No user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			session.invalidate();
			log.info("Logged out");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/api/register", method = RequestMethod.POST)
	public ResponseEntity<Boolean> register(HttpSession session, @RequestBody User user) {
		if (userComponent.isLoggedUser()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try {
			User newUser = new User(user.getName(), user.getPasswordHash(), user.getDni(), user.getFirstName(),
					user.getLastName1(), user.getLastName2(), user.getEmail(), user.getTelephone(), "ROLE_USER");

			userService.save(newUser);
			return new ResponseEntity<>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}
