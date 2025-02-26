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
        Blob ohmyclubImage = loadImage("img/OH MY CLUB.jpg");
        Blob liberataImage = loadImage("img/liberata.jpg");

        Blob madcoolImage = loadImage("img/mad cool.jpg");
        Blob blackworksImage = loadImage("img/blackworks.jpg");
        Blob madridSalvajeImage = loadImage("img/madrid salvaje.jpg");
        Blob rioBabelImage = loadImage("img/rio babel.jpg");


        Blob cruzziImage = loadImage("img/cruzzi.jpg");
        Blob natosywaorImage = loadImage("img/natos y waor.jpg");
        Blob alsafirImage = loadImage("img/al safir.jpg");
        Blob pekeImage = loadImage("img/ill pekeno & ergo pro.jpg"); ; 
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
                                "club", 24, ohmyclubImage);
        
        eventRepository.save(ohmyclub);

        Event liberata = new Event("LIBERATA", 
                                200,
                                "Música de ambienta acompañada de gente dispuesta a pasarselo bien",
                                start, 
                                finish,
                                "El Corral De Chamartin",
                                "club", 19, liberataImage);
        
        eventRepository.save(liberata);

        Event madcool = new Event("MAD COOL 2025",
                                3000,
                                "Uno de los festivales musicales más importantes del mundo se celebra en el espacio Iberdrola Music en su octava edición con grandes grupos y artistas del panorama musical",
                                start,
                                finish,
                                "Espacio Iberdrola Music",
                                "festival", 69, madcoolImage);

        eventRepository.save(madcool);

        Event blackworks = new Event("BLACKWORKS",
                                3000,
                                "Con esta edición, Blackworks propone una experiencia introspectiva que combina lo mejor de la música electrónica con un enfoque en el espíritu colectivo. ",
                                start,
                                finish,
                                "Ifema Madrid",
                                "festival", 120, blackworksImage);

        eventRepository.save(blackworks);

        Event madridSalvaje = new Event("MADRID SALVAJE",
                                3000,
                                "IFEMA MADRID se convierte en punto de encuentro de la música urbana durante dos días de festival, con artistas de rap y trap del país actuando en varios escenarios.",
                                start,
                                finish,
                                "Ifema Madrid",
                                "festival", 80, madridSalvajeImage);

        eventRepository.save(madridSalvaje);

        Event rioBabel = new Event("RIO BABEL",
                                3000,
                                "Un heterogéneo festival de música iberoamericana que contará con la presencia de grupos y artistas nacionales e internacionales.",
                                start,
                                finish,
                                "Caja Mágica de Madrid",
                                "festival", 99, rioBabelImage);

        eventRepository.save(rioBabel); 

        Event natosywaor = new Event("NATOS Y WAOR",
                                3000,
                                "",
                                start,
                                finish,
                                "Movistar Arena",
                                "concierto", 42, natosywaorImage);

        eventRepository.save(natosywaor);

        Event alsafir = new Event("AL SAFIR",
                                3000,
                                "",
                                start,
                                finish,
                                "Las Ventas",
                                "concierto", 29, alsafirImage);

        eventRepository.save(alsafir);

        Event pekeno = new Event("ILL PEKEÑO & ERGO PRO",
                                3000,
                                "",
                                start,
                                finish,
                                "Sala Changó",
                                "concierto", 15, pekeImage);

        eventRepository.save(pekeno);

        Event cruzzi = new Event("CRUZZI",
                                3000,
                                "",
                                start,
                                finish,
                                "Teatro Barceló",
                                "concierto", 58, cruzziImage);

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

    @GetMapping("/ticket/{id}")
    public String showTicket(Model model, @PathVariable long id) {
        System.out.println("ID: " + id);
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            System.out.println("Event: " + event.getTitle());
            
            model.addAttribute("ticket", event); 
            return "ticket";
        } else {
            return "error";
        }
    }
    

}
