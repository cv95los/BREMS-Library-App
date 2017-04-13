package appSpring.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import appSpring.model.Action;
import appSpring.model.User;
import appSpring.repository.ActionRepository;

@Service
public class ActionService {

	@Autowired
	private ActionRepository repository;

	public Action findOne(Integer id) {
		return repository.findOne(id);
	}

	public List<Action> findAll() {
		return repository.findAll();
	}

	public Page<Action> findAll(int page) {
		return repository.findAll(new PageRequest(page, 3));
	}

	public List<Action> findByUser(User user) {
		return repository.findByUser(user);
	}

	public Page<Action> findByUser(User user, int page) {
		return repository.findByUser(user, new PageRequest(page, 3));
	}

	public Action findByDateLoanInit(Date dateLoanInit) {
		return repository.findByDateLoanInit(dateLoanInit);
	}

	public Page<Action> findByDateLoanReturnAndUser(User user, int page, Date dateLoanReturn) {
		return repository.findByDateLoanReturnAndUser(dateLoanReturn, user, new PageRequest(page, 3));
	}

	public Page<Action> findByUserAndDateLoanReturnNot(User user, int page, Date dateLoanReturn) {
		return repository.findByUserAndDateLoanReturnNot(user, dateLoanReturn, new PageRequest(page, 3));
	}

	public void save(Action action) {
		repository.save(action);
	}

	public void delete(Integer id) {
		repository.delete(id);
	}

	public void delete(Action action) {
		repository.delete(action);
	}

}
