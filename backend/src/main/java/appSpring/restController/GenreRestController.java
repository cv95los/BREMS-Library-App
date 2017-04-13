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

import appSpring.model.Genre;
import appSpring.model.Resource;
import appSpring.service.GenreService;
import appSpring.service.ResourceService;

@RestController
@RequestMapping("/api/genres")
public class GenreRestController {

	public interface GenreDetail extends Genre.Basic, Genre.Reso, Resource.Basic {}

	@Autowired
	private GenreService genreService;
	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Genre postGenre(@RequestBody Genre genre, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		genreService.save(genre);
		return genre;
	}

	@JsonView(GenreDetail.class)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Page<Genre>> getAllGenres(HttpSession session, @RequestParam (required=false) Integer page) {

		session.setMaxInactiveInterval(-1);
		if(page==null) page = 0;
		Page<Genre> genres = genreService.findAll(page);
		if (genres.getNumberOfElements() > 0) {
			return new ResponseEntity<>(genres, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(GenreDetail.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Genre> getGenre(@PathVariable int id, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		Genre genre = genreService.findOne(id);
		if (genre != null) {
			return new ResponseEntity<>(genre, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Genre> deleteGenre(@PathVariable Integer id, HttpSession session) {

		session.setMaxInactiveInterval(-1);
		Genre genreSelected = genreService.findOne(id);
		if (genreSelected == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (genreSelected.getName().equals("Defecto"))
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			else {
				Genre genre = genreService.findByName("Defecto");
				List<Resource> resources = resourceService.findByGenreNameLikeIgnoreCase(genreSelected.getName());
				for (Resource resource : resources) {
					resource.setGenre(genre);
					resourceService.save(resource);
				}
				genreService.delete(genreSelected);
				return new ResponseEntity<>(genreSelected, HttpStatus.OK);
			}
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Genre> putGenre(@PathVariable Integer id, @RequestBody Genre genreUpdated,
			HttpSession session) {

		session.setMaxInactiveInterval(-1);
		Genre genre = genreService.findOne(id);
		if ((genre != null) && (genre.getId() == genreUpdated.getId())) {
			genre.setName(genreUpdated.getName());
			genreService.save(genre);
			return new ResponseEntity<>(genreUpdated, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
