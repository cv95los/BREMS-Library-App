package appSpring.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;


@Entity
public class Action {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Date date;
	
	@ManyToMany
	private List<User> userActions;

	@ManyToMany
	private List<ResourceCopy> copiesActions;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Token token;
	
	
	// Constructor by SpringData
	protected Action() {}
	
	
	/** Getters and setters methods **/
	// - Id
	public Integer getID() {
		return id;
	}
	public void setID(Integer id) {
		this.id = id;
	}
	
	// - date
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
