package appSpring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import appSpring.model.Genre;
import appSpring.model.Resource;
import appSpring.model.ResourceType;
import appSpring.repository.ResourceRepository;
import appSpring.repository.ResourceTypeRepository;

@Service
public class ResourceService {

	@Autowired
	private ResourceRepository repository;
	@Autowired
	private ResourceTypeRepository rtrepository;

	public Resource findOne(Integer id) {
		return repository.findOne(id);
	}

	public List<Resource> findAll() {
		return repository.findAll();
	}

	public Page<Resource> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Resource findByTitle(String title) {
		return repository.findByTitle(title);
	}

	public Resource findByTitleLikeIgnoreCase(String title) {
		return repository.findByTitleLikeIgnoreCase(title);
	}

	public Page<Resource> findByResourceType(ResourceType resourceType, Pageable page) {
		return repository.findByResourceType(resourceType, page);
	}

	public List<Resource> findByGenreAndIdNot(Genre genre, Integer id) {
		return repository.findByGenreAndIdNot(genre, id);
	}

	public List<Resource> findByGenreNameLikeIgnoreCase(String genreName) {
		return repository.findByGenreNameLikeIgnoreCase(genreName);
	}

	public List<Resource> findByResourceTypeName(String resourceTypeName) {
		return repository.findByResourceTypeName(resourceTypeName);
	}

	public List<Resource> findByAuthor(String author) {
		return repository.findByAuthor(author);
	}

	public Page<Resource> findByTitleLikeIgnoreCaseOrGenreNameLikeIgnoreCaseOrAuthorLikeIgnoreCaseOrEditorialLikeIgnoreCase(
			String title, String genreName, String author, String editorial, Pageable page) {
		return repository
				.findByTitleLikeIgnoreCaseOrGenreNameLikeIgnoreCaseOrAuthorLikeIgnoreCaseOrEditorialLikeIgnoreCase(
						title, genreName, author, editorial, page);
	}

	public List<Resource> findByGenreAndTypeAllIgnoreCase(String genre, String type) {
		if (genre != null && type == null) {
			return repository.findByGenreNameLikeIgnoreCase(genre);
		} else if (genre != null && type != null) {
			ResourceType resourceType = rtrepository.findByNameLikeIgnoreCase(type);
			return repository.findByGenreNameLikeIgnoreCaseAndResourceType(genre, resourceType);
		} else if (genre == null && type == null) {
			return repository.findAll();
		} else {
			ResourceType resourceType = rtrepository.findByNameLikeIgnoreCase(type);
			return repository.findByResourceType(resourceType);
		}
	}
	
	public Page<Resource> findByGenreAndNameAndTypeAllIgnoreCase(String genre, String type, String name, int page) {
		if (name != null){
			return repository.findByTitleLikeIgnoreCaseOrGenreNameLikeIgnoreCaseOrAuthorLikeIgnoreCaseOrEditorialLikeIgnoreCase("%" + name + "%", "%" + name + "%", "%" + name + "%", "%" + name + "%", new PageRequest(page,3));
		}
		if (genre != null && type == null) {
			return repository.findByGenreNameLikeIgnoreCase(genre, new PageRequest(page,3));
		} else if (genre != null && type != null) {
			ResourceType resourceType = rtrepository.findByNameLikeIgnoreCase(type);
			return repository.findByGenreNameLikeIgnoreCaseAndResourceType(genre, resourceType, new PageRequest(page,3));
		} else if (genre == null && type == null) {
			return repository.findAll(new PageRequest(page,3));
		} else {
			ResourceType resourceType = rtrepository.findByNameLikeIgnoreCase(type);
			return repository.findByResourceType(resourceType, new PageRequest(page,3));
		}
	}

	public void save(Resource resource) {
		repository.save(resource);
	}

	public void delete(Integer id) {
		repository.delete(id);
	}

	public void delete(Resource resource) {
		repository.delete(resource);
	}

}
