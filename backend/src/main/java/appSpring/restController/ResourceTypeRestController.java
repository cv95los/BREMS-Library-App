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

import appSpring.model.Resource;
import appSpring.model.ResourceType;
import appSpring.service.ResourceService;
import appSpring.service.ResourceTypeService;

@RestController
@RequestMapping("/api/resourcetypes")
public class ResourceTypeRestController {

	@Autowired
	private ResourceTypeService resourceTypeService;
	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResourceType postResourceType(@RequestBody ResourceType resourceType) {

		resourceTypeService.save(resourceType);

		return resourceType;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Page<ResourceType>> getResourceTypes(HttpSession session, @RequestParam (required=false) Integer page) {

		session.setMaxInactiveInterval(-1);
		if(page==null) page=0;
		Page<ResourceType> resourceTypes = resourceTypeService.findAll(page);
		if (resourceTypes.getNumberOfElements() >0) {
			return new ResponseEntity<>(resourceTypes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ResourceType> getResourceType(@PathVariable Integer id, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		ResourceType resourceType = resourceTypeService.findOne(id);
		if (resourceType != null) {
			return new ResponseEntity<>(resourceType, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResourceType> deleteResourceType(@PathVariable Integer id, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		ResourceType resourceTypeSelected = resourceTypeService.findOne(id);
		if (resourceTypeSelected == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (resourceTypeSelected.getName().equals("Defecto"))
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			else {
				ResourceType resourceType = resourceTypeService.findByName("Defecto");
				List<Resource> resources = resourceService.findByResourceTypeName(resourceTypeSelected.getName());
				for (Resource resource : resources) {
					resource.setProductType(resourceType);
					resourceService.save(resource);
				}
				resourceTypeService.delete(resourceTypeSelected);
				return new ResponseEntity<>(resourceTypeSelected, HttpStatus.OK);
			}
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResourceType> putResourceType(@PathVariable Integer id, @RequestBody ResourceType resourceTypeUpdated,
			HttpSession session) {

		session.setMaxInactiveInterval(-1);
		ResourceType resourceType = resourceTypeService.findOne(id);
		if ((resourceType != null) && (resourceType.getId() == resourceTypeUpdated.getId())) {
			resourceTypeService.save(resourceTypeUpdated);
			return new ResponseEntity<>(resourceTypeUpdated, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
