package appSpring.restController;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import appSpring.model.Action;
import appSpring.model.Resource;
import appSpring.model.ResourceCopy;
import appSpring.model.User;
import appSpring.service.ActionService;
import appSpring.service.LogicService;
import appSpring.service.ResourceCopyService;
import appSpring.service.ResourceService;
import appSpring.service.UserService;

@RestController
@RequestMapping("/api/loans")
public class ActionRestController {

	public interface LoanDetail extends Action.Basic, Action.ResoCopy, ResourceCopy.Basic, ResourceCopy.Reso,
			Resource.Basic, Action.Usr, User.ActionInt {
	}

	@Autowired
	private ActionService actionService;
	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ResourceCopyService resourceCopyService;
	@Autowired
	private LogicService logicService;

	@JsonView(LoanDetail.class)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Action> postAction(@RequestBody Action loan, Authentication authentication,
			HttpSession session, HttpServletRequest request) {

		session.setMaxInactiveInterval(-1);
		if ((authentication.getName().equals(userService.findOne(loan.getUser().getId()).getName()))
				|| (request.isUserInRole("ADMIN"))) {
			Date date = new Date();
			Resource resource = resourceService.findOne(loan.getResource().getResource().getId());
			ResourceCopy resourceCopy = resourceCopyService.findOne(loan.getResource().getID());
			int status = logicService.reserveAResource(userService.findOne(loan.getUser().getId()), date, resource,
					resourceCopy);
			if (status == 0) {
				return new ResponseEntity<>(logicService.getAction(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@JsonView(LoanDetail.class)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Page<Action>> getAllAction(Authentication authentication, HttpSession session,
			HttpServletRequest request, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) String finished) {

		session.setMaxInactiveInterval(-1);
		Page<Action> loans;
		if (page == null)
			page = 0;
		if (request.isUserInRole("ADMIN")) {
			loans = actionService.findAll(page);
		} else {
			if (finished == null) {
				loans = actionService.findByDateLoanReturnAndUser(userService.findByName(authentication.getName()),
						page, new Date(0));
			} else {
				if (finished.equals("true")) {
					loans = actionService.findByUserAndDateLoanReturnNot(
							userService.findByName(authentication.getName()), page, new Date(0));
				} else {
					loans = actionService.findByDateLoanReturnAndUser(userService.findByName(authentication.getName()),
							page, new Date(0));
				}
			}
		}
		return new ResponseEntity<>(loans, HttpStatus.OK);
	}

	@JsonView(LoanDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Action> getAction(@PathVariable int id, Authentication authentication, HttpSession session,
			HttpServletRequest request) {

		session.setMaxInactiveInterval(-1);
		Action loan = actionService.findOne(id);
		if (loan != null) {
			if ((authentication.getName().equals(loan.getUser().getName())) || (request.isUserInRole("ADMIN"))) {
				return new ResponseEntity<>(loan, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(LoanDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Action> deleteAction(@PathVariable Integer id) {

		Action loan = actionService.findOne(id);
		if (loan != null) {
			int status = logicService.deleteALoan(loan);
			if (status == 0) {
				return new ResponseEntity<>(loan, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(LoanDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Action> putAction(@PathVariable Integer id, @RequestBody Action loanUpdated,
			@RequestParam(value = "action", required = false) String action) {

		Action loan = actionService.findOne(id);
		Date date = new Date();
		if ((loan != null) && (loan.getID() == loanUpdated.getID())) {
			int status;
			if (action != null) {
				switch (action) {
				case "give":
					status = logicService.addGiveDate(loan, date);
					if (status == 0)
						return new ResponseEntity<>(loan, HttpStatus.OK);
					else
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				case "return":
					status = logicService.addReturnDate(loan, date);
					if (status == 0)
						return new ResponseEntity<>(loan, HttpStatus.OK);
					else
						return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
				default:
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}