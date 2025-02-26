package es.grupo13.ssddgrupo13.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.grupo13.ssddgrupo13.entities.Event;
import es.grupo13.ssddgrupo13.entities.Ticket;
import es.grupo13.ssddgrupo13.entities.TicketStatus;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.repository.EventRepository;
import es.grupo13.ssddgrupo13.repository.TicketRepository;
import jakarta.annotation.PostConstruct;


@Controller
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private  TicketRepository ticketRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    private Blob loadImage(String path) {
        try {
            
            InputStream inputStream = new ClassPathResource("static/" + path).getInputStream();
            return BlobProxy.generateProxy(inputStream, inputStream.available());
        } catch (IOException e) {
            System.err.println("⚠ No se pudo cargar la imagen: " + path + ". Usando imagen por defecto.");
            return loadDefaultImage();
        }
    }
    
    private Blob loadDefaultImage() {
        try {
            InputStream inputStream = new ClassPathResource("img/default.jpg").getInputStream();
            return BlobProxy.generateProxy(inputStream, inputStream.available());
        } catch (IOException e) {
            System.err.println("⚠ No se pudo cargar la imagen por defecto. Se usará un Blob vacío.");
            return BlobProxy.generateProxy(new byte[0]); // ✅ Usa un Blob vacío en lugar de null
        }
    }
    

    @PostConstruct
	public void init() {
        // Create a event
        Blob shokoImage = loadImage("img/shoko.jpg");
        Blob ohmyclubImage = loadImage("ohmyclub.jpg");
        LocalDateTime start = LocalDateTime.of(2025, 02, 26, 23, 00, 00);
        LocalDateTime finish = LocalDateTime.of(2025, 02, 27, 06, 00, 00);

        Event shoko = new Event("SHOKO", 
                                200,
                                "Largas noches de música latina acompañado de buen ambiente",
                                start, 
                                finish,
                                "Sala Shoko Madrid",
                                "club", 23, shokoImage);

        for (int i = 0; i < 10; i++){
            Ticket ticketShoko = new Ticket(shoko.getTitle(), shoko.getPrecio(), finish, TicketStatus.OPEN);
            shoko.getTickets().add(ticketShoko);
            //ticketRepository.save(ticketShoko);
        }
        
        eventRepository.save(shoko);
        
        Event ohmyclub = new Event("OH MY CLUB", 
                                200,
                                "Descubre un nuevo universo con el reservado ambiente de nustra discoteca",
                                start, 
                                finish,
                                "C/Rosario Pino 14",
                                "club", 24, shokoImage);
        
        eventRepository.save(ohmyclub);

        Event liberata = new Event("LIBERATA", 
                                200,
                                "Música de ambienta acompañada de gente dispuesta a pasarselo bien",
                                start, 
                                finish,
                                "El Corral De Chamartin",
                                "club", 19, shokoImage);
        
        eventRepository.save(liberata);

        Event madcool = new Event("MAD COOL 2025",
                                3000,
                                "Uno de los festivales musicales más importantes del mundo se celebra en el espacio Iberdrola Music en su octava edición con grandes grupos y artistas del panorama musical",
                                start,
                                finish,
                                "Espacio Iberdrola Music",
                                "festival", 69, shokoImage);

        eventRepository.save(madcool);

        Event blackworks = new Event("BLACKWORKS",
                                3000,
                                "Con esta edición, Blackworks propone una experiencia introspectiva que combina lo mejor de la música electrónica con un enfoque en el espíritu colectivo. ",
                                start,
                                finish,
                                "Ifema Madrid",
                                "festival", 120, shokoImage);

        eventRepository.save(blackworks);

        Event madridSalvaje = new Event("MADRID SALVAJE",
                                3000,
                                "IFEMA MADRID se convierte en punto de encuentro de la música urbana durante dos días de festival, con artistas de rap y trap del país actuando en varios escenarios.",
                                start,
                                finish,
                                "Ifema Madrid",
                                "festival", 80, shokoImage);

        eventRepository.save(madridSalvaje);

        Event rioBabel = new Event("RIO BABEL",
                                3000,
                                "Un heterogéneo festival de música iberoamericana que contará con la presencia de grupos y artistas nacionales e internacionales.",
                                start,
                                finish,
                                "Caja Mágica de Madrid",
                                "festival", 99, shokoImage);

        eventRepository.save(rioBabel); 

        Event duki = new Event("DUKI",
                                3000,
                                "",
                                start,
                                finish,
                                "Movistar Arena",
                                "concierto", 82, shokoImage);

        eventRepository.save(duki);

        Event cro = new Event("C.R.O",
                                3000,
                                "",
                                start,
                                finish,
                                "Sala But",
                                "concierto", 24, shokoImage);

        eventRepository.save(cro);

        Event hoke = new Event("HOKE",
                                3000,
                                "",
                                start,
                                finish,
                                "Movistar Arena",
                                "concierto", 25, shokoImage);

        eventRepository.save(hoke);

        Event juiceWRLD = new Event("JUICE WRLD",
                                3000,
                                "",
                                start,
                                finish,
                                "The Heaven Club",
                                "concierto", 1000 , shokoImage
                                );

        eventRepository.save(juiceWRLD);

        Event kiddkeo = new Event("KIDD KEO",
                                3000,
                                "",
                                start,
                                finish,
                                "Movistar Arena",
                                "concierto", 36, shokoImage);

        eventRepository.save(kiddkeo);

        Event natosywaor = new Event("NATOS Y WAOR",
                                3000,
                                "",
                                start,
                                finish,
                                "Movistar Arena",
                                "concierto", 42, shokoImage);

        eventRepository.save(natosywaor);

        Event alsafir = new Event("AL SAFIR",
                                3000,
                                "",
                                start,
                                finish,
                                "Las Ventas",
                                "concierto", 29, shokoImage);

        eventRepository.save(alsafir);

        Event pekeno = new Event("ILL PEKEÑO & ERGO PRO",
                                3000,
                                "",
                                start,
                                finish,
                                "Sala Changó",
                                "concierto", 15, shokoImage);

        eventRepository.save(pekeno);

        Event cruzzi = new Event("CRUZZI",
                                3000,
                                "",
                                start,
                                finish,
                                "Teatro Barceló",
                                "concierto", 58, shokoImage);

        eventRepository.save(cruzzi);

        
    }

    
    @GetMapping("/event-image/{id}")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        Optional<Event> op = eventRepository.findById(id);
        if (op.isPresent() && op.get().getImage() != null) {
            Blob image = op.get().getImage();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
            .body(new InputStreamResource(op.get().getImage().getBinaryStream()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/clubbing")
    public String showClubs(Model model) {
        List<Event> clubs = eventRepository.findByType("club"); // Obtiene los eventos de la BD
        model.addAttribute("section_2", clubs); // Agrega la lista al modelo
        return "section_2"; // Nombre de la plantilla (sin .html)
    }

    @GetMapping("/festivales")
    public String showFestivales(Model model) {
        List<Event> events = eventRepository.findByType("festival"); // Obtiene los eventos de la BD
        model.addAttribute("section_4", events); // Agrega la lista al modelo
        return "section_4"; // Nombre de la plantilla (sin .html)
    }

    @GetMapping("/conciertos")
    public String showConciertos(Model model) {
        List<Event> events = eventRepository.findByType("concierto"); // Obtiene los eventos de la BD
        model.addAttribute("section_3", events); // Agrega la lista al modelo
        return "section_3"; // Nombre de la plantilla (sin .html)
    }  

    @GetMapping("/ticket")
    public String showTicket(Model model, @PathVariable String title) {
        List<Event> events = eventRepository.findByTitle(title);
        model.addAttribute("ticket", events.getFirst());
        return "ticket";
    }
    

}
