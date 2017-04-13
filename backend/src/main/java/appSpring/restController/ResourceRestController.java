package appSpring.restController;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import appSpring.model.Action;
import appSpring.model.Genre;
import appSpring.model.Resource;
import appSpring.model.ResourceCopy;
import appSpring.model.ResourceType;
import appSpring.service.ActionService;
import appSpring.service.ResourceCopyService;
import appSpring.service.ResourceService;

@RestController
@RequestMapping("/api/resources")
public class ResourceRestController {

	public interface ResourceDetail extends Resource.Basic, Resource.ResoType, Resource.Genr, Resource.ResoCopy, ResourceCopy.Basic, Genre.Basic, ResourceType.Basic {}
	
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ActionService actionService;
	@Autowired
	private ResourceCopyService resourceCopyService;

	@JsonView(ResourceDetail.class)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Resource> postResource(@RequestBody Resource resource, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		Resource newResource = new Resource(resource.getTitle(), resource.getAutor(), resource.getEditorial(), resource.getDescription());
		newResource.setGenre(resource.getGenre());
		newResource.setProductType(resource.getProductType());
		resourceService.save(newResource);
		for (ResourceCopy resourceCopy : resource.getResourceCopies()) {
			if (resourceCopyService.findOne(resourceCopy.getID()) == null) {
				resourceCopy.setResource(newResource);
				resourceCopyService.save(resourceCopy);
				newResource.getNoReservedCopies().add(resourceCopy.getLocationCode());
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		resourceService.save(newResource);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@JsonView(ResourceDetail.class)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Page<Resource>> getAllResource(HttpSession session,
			@RequestParam(value = "genre", required = false) String genre,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam (required=false) Integer page) {
		
		session.setMaxInactiveInterval(-1);
		if(page==null) page=0;
		Page<Resource> resources = resourceService.findByGenreAndTypeAllIgnoreCase(genre, type, page);
		if (resources.getNumberOfElements() >0) {
			return new ResponseEntity<>(resources, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@JsonView(ResourceDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Resource> getResource(@PathVariable int id, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		Resource resource = resourceService.findOne(id);
		if (resource != null) {
			return new ResponseEntity<>(resource, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(ResourceDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Resource> deleteResource(@PathVariable Integer id, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		Resource resourceSelected = resourceService.findOne(id);
		if (resourceSelected == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Action> actions = actionService.findAll();
			for (Action action : actions) {
				if ((action.getDateLoanReturn() == null) && (action.getResource().getResource() == resourceSelected)) {
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				}
			}
			resourceService.delete(resourceSelected);
			return new ResponseEntity<>(resourceSelected, HttpStatus.OK);
		}
	}

	@JsonView(ResourceDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Resource> putResource(@PathVariable Integer id, @RequestBody Resource resourceUpdated,
			HttpSession session) {

		session.setMaxInactiveInterval(-1);
		Resource resource = resourceService.findOne(id);
		if ((resource != null) && (resource.getId() == resourceUpdated.getId())) {
			resource.setTitle(resourceUpdated.getTitle());
			resource.setAutor(resourceUpdated.getAutor());
			resource.setEditorial(resourceUpdated.getEditorial());
			resource.setDescription(resourceUpdated.getDescription());
			resource.setGenre(resourceUpdated.getGenre());
			resource.setProductType(resourceUpdated.getProductType());
			resourceService.save(resource);
			return new ResponseEntity<>(resourceUpdated, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
