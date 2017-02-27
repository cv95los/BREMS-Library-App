package appSpring.repository;

import java.util.List;
import entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceTypeRepository extends JpaRepository<ResourceType, Integer>{

	List<ResourceType> findByName(String name);
}