package appSpring.entity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import appSpring.repository.*;

@Component
public class DataExamples {

	@Autowired
    private UserRepository userRepository;
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private ResourceTypeRepository resourceTypeRepository;

    @PostConstruct
    private void initDatabase() {

    	// Data declaration
    	Genre g1, g2, g3, g4;
    	ResourceType rt1, rt2;
    	Resource res1, res2, res3, res4;
    	User user1, user2, user3, user4;


    	// Users creation
    	user1 = new User("carlosv", "passc", "0000", "Carlos", "Vázquez", "Losada", "c.vazquezlosada@gmail.com", "656565066", "Sierra de Guadarrama", "ROLE_USER");
    	user1.getPenalties().add(new Penalty(new GregorianCalendar(2015, Calendar.FEBRUARY, 13).getTime(), new GregorianCalendar(2015, Calendar.FEBRUARY, 20).getTime(), user1));
    	user1.getPenalties().add(new Penalty(new GregorianCalendar(2015, Calendar.SEPTEMBER, 6).getTime(), new GregorianCalendar(2015, Calendar.SEPTEMBER, 13).getTime(), user1));
    	userRepository.save(user1);
    	user2 = new User("sergiob", "passs", "0001", "Sergio", "Blay", "González", "blaybleybluy@gmail.com", "606000000", "Calle de Chueca", "ROLE_ADMIN", "ROLE_USER");
    	userRepository.save(user2);
    	user3 = new User("annyc", "passa", "0002", "Anny", "Saldaña", "Cervera", "annylashula@gmail.com", "606036000", "Calle de los gangsters", "ROLE_USER");
    	user3.getPenalties().add(new Penalty(new GregorianCalendar(2014, Calendar.JULY, 4).getTime(), new GregorianCalendar(2014, Calendar.JULY, 11).getTime(), user3));
    	userRepository.save(user3);
    	user4 = new User("jherelj", "passj", "0003", "Jorge Jherel", "Córdoba", "Proaño", "omocracko@gmail.com", "606036123", "Calle de Chueca", "ROLE_USER");
    	user4.getPenalties().add(new Penalty(new GregorianCalendar(2014, Calendar.MAY, 17).getTime(), new GregorianCalendar(2014, Calendar.MAY, 24).getTime(), user4));
    	userRepository.save(user4);

    	// Data creation
		g1 = new Genre("Novela");
		g2 = new Genre("Fantasía");
		g3 = new Genre("Farándula");
		g4 = new Genre("Divulgación");
		genreRepository.save(g1);
		genreRepository.save(g2);
		genreRepository.save(g3);
		genreRepository.save(g4);

		rt1 = new ResourceType("Libro");
		rt2 = new ResourceType("Revista");
		resourceTypeRepository.save(rt1);
		resourceTypeRepository.save(rt2);

		res1 = new Resource("Cien años de soledad", "Gabriel García Márquez", "Editorial Sudamericana", "Cien años de soledad es una novela del escritor colombiano Gabriel "
				+ "García Márquez, ganador del Premio Nobel de Literatura en 1982. Es considerada una obra maestra de la literatura hispanoamericana y universal, así como una de las "
				+ "obras más traducidas y leídas en español.");
		res1.setGenre(g1);
		res1.setProductType(rt1);

		res1.getResourceCopies().add(new ResourceCopy());
		res1.getResourceCopies().add(new ResourceCopy());
		res1.getResourceCopies().add(new ResourceCopy());
		res1.getResourceCopies().add(new ResourceCopy());
		resourceRepository.save(res1);

		res2 = new Resource("El principito", "Antoine de Saint-Exupéry", "Reynal & Hitchcock", "El principito es un cuento poético que viene acompañado de ilustraciones hechas "
				+ "con acuarelas por el mismo Saint-Exupéry. En él, un piloto se encuentra perdido en el desierto del Sahara después de que su avión sufriera una avería, pero para su sorpresa, "
				+ "es allí donde conoce a un pequeño príncipe proveniente de otro planeta. La historia tiene una temática filosófica, donde se incluyen críticas sociales dirigidas a la «extrañeza» "
				+ "con la que los adultos ven las cosas. Estas críticas a las cosas «importantes» y al mundo de los adultos van apareciendo en el libro a lo largo de la narración.");
		res2.setGenre(g2);
		res2.setProductType(rt1);

		res2.getResourceCopies().add(new ResourceCopy());
		res2.getResourceCopies().add(new ResourceCopy());
		res2.getResourceCopies().add(new ResourceCopy());
		resourceRepository.save(res2);

		res3 = new Resource("Hola.com", "HOLA S.L.", "Hola S.L.", "La revista Hola es una publicación tanto impresa como electrónica, propiedad de la editora 'Hola S. L., que se dedica "
				+ "a comunicar acontecimientos recientes de la vida de la farándula principalmente, abordando también temas como moda, actualidad, belleza, mujer, hombre, cocina o decoración.");
		res3.setGenre(g3);
		res3.setProductType(rt2);

		res3.getResourceCopies().add(new ResourceCopy());
		res3.getResourceCopies().add(new ResourceCopy());
		resourceRepository.save(res3);

		res4 = new Resource("Muy Interesante", "G+J", "G+J", "Muy interesante es una revista mensual de divulgación y ciencia popular, creada y publicada por el Grupo G+J España.");
		res4.setGenre(g3);
		res4.setProductType(rt2);

		res4.getResourceCopies().add(new ResourceCopy());
		res4.getResourceCopies().add(new ResourceCopy());
		res4.getResourceCopies().add(new ResourceCopy());
		res4.getResourceCopies().add(new ResourceCopy());
		resourceRepository.save(res4);

    }
}
