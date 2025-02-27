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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.entities.Client;
import es.grupo13.ssddgrupo13.entities.Comment;
import es.grupo13.ssddgrupo13.entities.Event;
import es.grupo13.ssddgrupo13.entities.Ticket;
import es.grupo13.ssddgrupo13.entities.TicketStatus;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.repository.EventRepository;
import es.grupo13.ssddgrupo13.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;


@Controller
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private  TicketRepository ticketRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ClientRepository clientRepository;
    
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
        Blob shokoImage = loadImage("img/shoko.png");
        Blob ohmyclubImage = loadImage("img/OH MY CLUB.png");
        Blob liberataImage = loadImage("img/liberata.png");

        Blob madcoolImage = loadImage("img/mad cool 2025.jpg");
        Blob blackworksImage = loadImage("img/blackworks.jpg");
        Blob madridSalvajeImage = loadImage("img/madrid salvaje.jpg");
        Blob rioBabelImage = loadImage("img/rio babel.jpg");


        Blob cruzziImage = loadImage("img/cruzzi.jpg");
        Blob natosywaorImage = loadImage("img/natos y waor.jpg");
        Blob alsafirImage = loadImage("img/al safir.jpg");
        Blob pekeImage = loadImage("img/ill pekeño & ergo pro.jpg");

        LocalDateTime startClub = LocalDateTime.of(2025, 2, 26, 23, 00);
        LocalDateTime finishClub = LocalDateTime.of(2025, 2, 27, 06, 00);
        
        LocalDateTime startFestival = LocalDateTime.of(2025, 7, 15, 15, 00);
        LocalDateTime finishFestival = LocalDateTime.of(2025, 7, 17, 23, 30);

        LocalDateTime startConcert = LocalDateTime.of(2025, 8, 29, 20, 00);
        LocalDateTime finishConcert = LocalDateTime.of(2025, 8, 29, 23, 00);

        Event shoko = new Event("SHOKO", "Largas noches de música latina acompañado de buen ambiente", startClub, finishClub, "Sala Shoko Madrid", "club", 23, shokoImage);
        Event ohmyclub = new Event("OH MY CLUB", "Descubre un nuevo universo con el reservado ambiente de nustra discoteca", startClub, finishClub, "C/Rosario Pino 14", "club", 24, ohmyclubImage);
        Event liberata = new Event("LIBERATA", "Música de ambienta acompañada de gente dispuesta a pasarselo bien", startClub, finishClub, "El Corral De Chamartin", "club", 19, liberataImage);
        
        Event madcool = new Event("MAD COOL 2025", "Uno de los festivales musicales más importantes del mundo se celebra en el espacio Iberdrola Music en su octava edición con grandes grupos y artistas del panorama musical", startFestival, finishFestival, "Espacio Iberdrola Music", "festival", 69, madcoolImage);
        Event blackworks = new Event("BLACKWORKS", "Con esta edición, Blackworks propone una experiencia introspectiva que combina lo mejor de la música electrónica con un enfoque en el espíritu colectivo. ", startFestival, finishFestival, "Ifema Madrid", "festival", 120, blackworksImage);
        Event madridSalvaje = new Event("MADRID SALVAJE", "IFEMA MADRID se convierte en punto de encuentro de la música urbana durante dos días de festival, con artistas de rap y trap del país actuando en varios escenarios.", startFestival, finishFestival, "Ifema Madrid", "festival", 80, madridSalvajeImage);
        Event rioBabel = new Event("RIO BABEL", "Un heterogéneo festival de música iberoamericana que contará con la presencia de grupos y artistas nacionales e internacionales.", startFestival, finishFestival, "Caja Mágica de Madrid", "festival", 99, rioBabelImage);

        Event natosywaor = new Event("NATOS Y WAOR", "", startConcert, finishConcert, "Movistar Arena", "concierto", 42, natosywaorImage);
        Event alsafir = new Event("AL SAFIR", "", startConcert, finishConcert, "Las Ventas", "concierto", 29, alsafirImage);
        Event pekeno = new Event("ILL PEKEÑO & ERGO PRO", "", startConcert, finishConcert, "Sala Changó", "concierto", 15, pekeImage);
        Event cruzzi = new Event("CRUZZI", "", startConcert, finishConcert, "Teatro Barceló", "concierto", 58, cruzziImage);

        for (int i = 0; i < 10; i++){
            ticketRepository.save(new Ticket(shoko.getTitle(), shoko.getPrecio(), shoko.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(ohmyclub.getTitle(), ohmyclub.getPrecio(), ohmyclub.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(liberata.getTitle(), liberata.getPrecio(), liberata.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(madcool.getTitle(), madcool.getPrecio(), madcool.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(blackworks.getTitle(), blackworks.getPrecio(), blackworks.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(madridSalvaje.getTitle(), madridSalvaje.getPrecio(), madridSalvaje.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(rioBabel.getTitle(), rioBabel.getPrecio(), rioBabel.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(natosywaor.getTitle(), natosywaor.getPrecio(), natosywaor.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(alsafir.getTitle(), alsafir.getPrecio(), alsafir.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(pekeno.getTitle(), pekeno.getPrecio(), pekeno.getTimeFinish(), TicketStatus.OPEN));
            ticketRepository.save(new Ticket(cruzzi.getTitle(), cruzzi.getPrecio(), cruzzi.getTimeFinish(), TicketStatus.OPEN));
        }

        Comment comment = new Comment("Robert", "soy mariquita", 5, shoko.getTitle());
        Comment comment1 = new Comment("Robert", "soy mariquita", 4, shoko.getTitle());

        shoko.addComments(comment);
        shoko.addComments(comment1);
        
        eventRepository.save(shoko);
        eventRepository.save(ohmyclub);
        eventRepository.save(liberata);

        eventRepository.save(madcool);
        eventRepository.save(blackworks);
        eventRepository.save(madridSalvaje);
        eventRepository.save(rioBabel);

        eventRepository.save(natosywaor);
        eventRepository.save(alsafir);
        eventRepository.save(pekeno);
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
            model.addAttribute("comment", event.getComments());
            model.addAttribute("event", event);
            return "ticket";
        } else {
            return "error";
        }
    }

    @PostMapping("/comment_in")
    public String comment_in(HttpSession session, @RequestParam String text, @RequestParam String rating, @RequestParam Long eventID) {
        Client client = (Client) session.getAttribute("client");
        
        if (client == null) {
            return "/error"; // Si no hay cliente en sesión, redirigir a error
        }
        
        Event event = eventRepository.findById(eventID).orElse(null); // Obtener el evento usando el ticketID
        
        if (event == null) {
            return "/error"; // Si no se encuentra el evento, redirigir a error
        }
        
        Comment comment = new Comment(client.getName(), text, Integer.valueOf(rating), event.getTitle());
        event.getComments().add(comment);  // Asociar el comentario con el evento
        commentRepository.save(comment);   // Guardar el comentario
        eventRepository.save(event);       // Guardar el evento con el comentario agregado
        
        return "redirect:/ticket/" + eventID;  // Redirigir a la página del evento
    }


    

}
