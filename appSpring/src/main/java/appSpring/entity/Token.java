package appSpring.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String value;
	private Date expiration_date;

	// Constructor by SpringData
	protected Token() {}
		

	/** Getters and setters methods **/
	// - Id
	public Integer getID() {
		return id;
	}
	public void setID(Integer id) {
		this.id = id;
	}
	
	// - value
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	// - expiration date for the token
	public Date getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}
}
