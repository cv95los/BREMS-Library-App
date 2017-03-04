package appSpring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import appSpring.entity.Penalty;

public interface PenaltyRepository extends JpaRepository<Penalty, Integer> {

	List<Penalty> findByInitDate(Date initDate);

}
