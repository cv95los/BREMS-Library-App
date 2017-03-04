package appSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import appSpring.repository.ResourceRepository;
import appSpring.repository.ResourceTypeRepository;
import appSpring.entity.Resource;
import appSpring.entity.ResourceType;


@Controller
public class BookController {

	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private ResourceTypeRepository resourceTypeRepo;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String bookProfile(Model model, @PathVariable Integer id) {

		Resource resource = resourceRepository.findOne(id);
		model.addAttribute("resource", resource);

		return "profileBook";
	}
	
	@RequestMapping(value="/moreBooks")
	public String moreBooks(Model model, @RequestParam int page){
		
		ResourceType type = resourceTypeRepo.findOneByName("Libro");
		Page<Resource> books = resourceRepository.findByResourceType(type, new PageRequest(page,1));
		
		model.addAttribute("items", books);
		
		return "listItemsPage";
	}
	
	@RequestMapping(value="/moreMagazines")
	public String moreMagazines(Model model, @RequestParam int page){
		
		ResourceType type = resourceTypeRepo.findOneByName("Revista");
		Page<Resource> revistas = resourceRepository.findByResourceType(type, new PageRequest(page,1));
		
		model.addAttribute("items", revistas);
		
		return "listItemsPage";
	}
	
	@RequestMapping(value="/moreAllShelf")
	public String moreAllShelf(Model model, @RequestParam int page){
		
		Page<Resource> allShelf = resourceRepository.findAll(new PageRequest(page,1));
		
		model.addAttribute("items", allShelf);
		
		return "listItemsPage";
	}
}
