package appSpring.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	@OneToMany(mappedBy="genre")
	private List<Resource> resources;
	
	
	// Constructor
	protected Genre() {} // Used by SpringData
	
	public Genre(String name){
		this.name = name;
	}
	
	/** Métodos getter/setter de los atributos **/
	// - Id
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	// - name
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// - resources list
	public List<Resource> getResources() {
		return this.resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	public void addResource(Resource resource) {
		this.resources.add(resource);
	}
}
