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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import appSpring.model.Action;
import appSpring.model.Fine;
import appSpring.model.Resource;
import appSpring.model.ResourceCopy;
import appSpring.service.ActionService;
import appSpring.service.ResourceCopyService;
import appSpring.service.ResourceService;

@RestController
@RequestMapping("/api/resourcecopies")
public class ResourceCopyRestController {

	public interface ResourceCopyDetail extends ResourceCopy.Basic, ResourceCopy.Fin, ResourceCopy.Reso, Fine.Basic, Resource.Basic {}

	@Autowired
	private ResourceCopyService resourceCopyService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ActionService actionService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResourceCopy postResourceCopy(@RequestBody ResourceCopy resourceCopy, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		Resource resource = resourceService.findOne(resourceCopy.getResource().getId());
		resourceCopyService.save(resourceCopy);
		resource.getNoReservedCopies().add(resourceCopy.getLocationCode());
		resource.getResourceCopies().add(resourceCopy);
		resourceService.save(resource);
		return resourceCopy;
	}

	@JsonView(ResourceCopyDetail.class)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Page<ResourceCopy>> getResourceCopies(HttpSession session, @RequestParam (required=false) Integer page) {

		session.setMaxInactiveInterval(-1);
		if(page==null) page=0;
		Page<ResourceCopy> resourceCopies = resourceCopyService.findAll(page);
		if (resourceCopies.getNumberOfElements() > 0) {
			return new ResponseEntity<>(resourceCopies, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(ResourceCopyDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ResourceCopy> getResourceCopy(@PathVariable Integer id, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		ResourceCopy resourceCopy = resourceCopyService.findOne(id);
		if (resourceCopy != null) {
			return new ResponseEntity<>(resourceCopy, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResourceCopy> deleteResourceCopy(@PathVariable Integer id, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		ResourceCopy resourceCopySelected = resourceCopyService.findOne(id);
		if (resourceCopySelected == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Action> actions = actionService.findAll();
			for (Action action : actions) {
				if ((action.getResource() == resourceCopySelected) && (action.getDateLoanReturn() == null)) {
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				}
			}
			Resource resource = resourceCopySelected.getResource();
			resource.getResourceCopies().remove(resourceCopySelected);
			resource.getNoReservedCopies().remove(resourceCopySelected.getLocationCode());
			resourceCopyService.delete(resourceCopySelected);
			resourceService.save(resource);
			return new ResponseEntity<>(resourceCopySelected, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResourceCopy> putResourceCopy(@PathVariable Integer id, @RequestBody ResourceCopy resourceCopyUpdated,
			HttpSession session) {

		session.setMaxInactiveInterval(-1);
		ResourceCopy resourceCopy = resourceCopyService.findOne(id);
		if ((resourceCopy != null) && (resourceCopy.getID() == resourceCopyUpdated.getID())) {
			resourceCopy.setLocationCode(resourceCopyUpdated.getLocationCode());
			resourceCopyService.save(resourceCopy);
			return new ResponseEntity<>(resourceCopyUpdated, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
