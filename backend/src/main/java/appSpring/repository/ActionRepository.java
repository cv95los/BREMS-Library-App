package appSpring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import appSpring.model.Action;
import appSpring.model.User;

public interface ActionRepository extends JpaRepository<Action, Integer> {

	Action findByDateLoanInit(Date dateLoanInit);

	List<Action> findByUserId(Integer id);

	List<Action> findByUser(User user);

	Page<Action> findAll(Pageable page);

	Page<Action> findByUser(User user, Pageable page);

	Page<Action> findByDateLoanReturnAndUser(Date dateLoanReturn, User user, Pageable page);

	Page<Action> findByUserAndDateLoanReturnNot(User user, Date dateLoanReturn, Pageable page);

}
