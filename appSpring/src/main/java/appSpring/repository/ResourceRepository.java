package appSpring.repository;

import java.util.List;

import appSpring.entity.Resource;
import appSpring.entity.ResourceType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

	List<Resource> findByTitle(String title);

	List<Resource> findByAuthor(String author);

	List<Resource> findByEditorial(String editorial);

	List<Resource> findByResourceType(ResourceType resourceType);
	
	Page<Resource> findByResourceType(ResourceType resourceType, Pageable page);

}
