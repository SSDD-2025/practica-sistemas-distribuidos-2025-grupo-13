package es.grupo13.ssddgrupo13.services;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.model.TicketStatus;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.repository.CommentRepository;
import es.grupo13.ssddgrupo13.repository.EventRepository;
import es.grupo13.ssddgrupo13.repository.TicketRepository;
import es.grupo13.ssddgrupo13.utils.ImageUtils;
import jakarta.annotation.PostConstruct;

@Component
public class DataBaseInitializer {

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private EventRepository eventRepository;

        @Autowired
        private TicketRepository ticketRepository;

        @Autowired
        private CommentRepository commentRepository;

        @Autowired
        private ClientRepository clientRepository;

        @Autowired
        private ImageUtils imageUtils;

        @Autowired
        private TicketService ticketService;

        @PostConstruct
        public void init() {

                // Sample users
                Client admin = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"),
                                Arrays.asList("ADMIN", "USER"));

                Client user = new Client("user", "user", "user@user.com", passwordEncoder.encode("user"),
                                Arrays.asList("USER"));

                if (clientRepository.findByName(admin.getName()).isEmpty()
                                && clientRepository.findByName(user.getName()).isEmpty()) {

                        clientRepository.save(admin);
                        clientRepository.save(user);

                } else {
                        user = clientRepository.findByName("user").orElse(user); // Recuperar usuario si ya existe
                }

                // Load images to the database
                Blob shokoImage = imageUtils.loadImage("img/shoko.jpg");
                Blob ohmyclubImage = imageUtils.loadImage("img/OH MY CLUB.jpg");
                Blob liberataImage = imageUtils.loadImage("img/liberata.png");

                Blob madcoolImage = imageUtils.loadImage("img/mad cool 2025.jpg");
                Blob blackworksImage = imageUtils.loadImage("img/blackworks.jpg");
                Blob madridSalvajeImage = imageUtils.loadImage("img/madrid salvaje.jpg");
                Blob rioBabelImage = imageUtils.loadImage("img/rio babel.jpg");

                Blob cruzziImage = imageUtils.loadImage("img/cruzzi.jpg");
                Blob natosywaorImage = imageUtils.loadImage("img/natos y waor.jpg");
                Blob alsafirImage = imageUtils.loadImage("img/al safir.jpg");
                Blob pekeImage = imageUtils.loadImage("img/ill pekeño & ergo pro.jpg");

                LocalDateTime startClub = LocalDateTime.of(2025, 2, 26, 23, 00);
                LocalDateTime finishClub = LocalDateTime.of(2025, 2, 27, 06, 00);

                LocalDateTime startFestival = LocalDateTime.of(2025, 7, 15, 15, 00);
                LocalDateTime finishFestival = LocalDateTime.of(2025, 7, 17, 23, 30);

                LocalDateTime startConcert = LocalDateTime.of(2025, 8, 29, 20, 00);
                LocalDateTime finishConcert = LocalDateTime.of(2025, 8, 29, 23, 00);

                Event shoko = new Event("SHOKO", "Largas noches de música latina acompañado de buen ambiente",
                                startClub,
                                finishClub, "Sala Shoko Madrid", "club", 23, shokoImage);
                Event ohmyclub = new Event("OH MY CLUB",
                                "Descubre un nuevo universo con el reservado ambiente de nustra discoteca", startClub,
                                finishClub,
                                "C/Rosario Pino 14", "club", 24, ohmyclubImage);
                Event liberata = new Event("LIBERATA",
                                "Música de ambienta acompañada de gente dispuesta a pasarselo bien",
                                startClub, finishClub, "El Corral De Chamartin", "club", 19, liberataImage);

                Event madcool = new Event("MAD COOL 2025",
                                "Uno de los festivales musicales más importantes del mundo se celebra en el espacio Iberdrola Music en su octava edición con grandes grupos y artistas del panorama musical",
                                startFestival, finishFestival, "Espacio Iberdrola Music", "festival", 69, madcoolImage);
                Event blackworks = new Event("BLACKWORKS",
                                "Con esta edición, Blackworks propone una experiencia introspectiva que combina lo mejor de la música electrónica con un enfoque en el espíritu colectivo. ",
                                startFestival, finishFestival, "Ifema Madrid", "festival", 120, blackworksImage);
                Event madridSalvaje = new Event("MADRID SALVAJE",
                                "IFEMA MADRID se convierte en punto de encuentro de la música urbana durante dos días de festival, con artistas de rap y trap del país actuando en varios escenarios.",
                                startFestival, finishFestival, "Ifema Madrid", "festival", 80, madridSalvajeImage);
                Event rioBabel = new Event("RIO BABEL",
                                "Un heterogéneo festival de música iberoamericana que contará con la presencia de grupos y artistas nacionales e internacionales.",
                                startFestival, finishFestival, "Caja Mágica de Madrid", "festival", 99, rioBabelImage);

                Event natosywaor = new Event("NATOS Y WAOR", "", startConcert, finishConcert, "Movistar Arena",
                                "concierto", 42,
                                natosywaorImage);
                Event alsafir = new Event("AL SAFIR", "", startConcert, finishConcert, "Las Ventas", "concierto", 29,
                                alsafirImage);
                Event pekeno = new Event("ILL PEKEÑO & ERGO PRO", "", startConcert, finishConcert, "Sala Changó",
                                "concierto",
                                15, pekeImage);
                Event cruzzi = new Event("CRUZ CAFUNE", "", startConcert, finishConcert, "Teatro Barceló", "concierto", 58,
                                cruzziImage);

                // Save events to the database
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

                setImageURL(shoko);
                setImageURL(ohmyclub);
                setImageURL(liberata);
                setImageURL(madcool);
                setImageURL(blackworks);
                setImageURL(madridSalvaje);
                setImageURL(rioBabel);
                setImageURL(natosywaor);
                setImageURL(alsafir);
                setImageURL(pekeno);
                setImageURL(cruzzi);

                // Save comments to the database
                for (Event event : Arrays.asList(shoko, madcool, ohmyclub, liberata, blackworks,
                                madridSalvaje, rioBabel, natosywaor, alsafir, pekeno, cruzzi)) {
                        Comment comment = new Comment(user.getName(), "Great event!", 5, event.getTitle());
                        comment.setClient(user);
                        comment.setEvent(event);

                        event.getComments().add(comment);
                        user.getComments().add(comment);

                        commentRepository.save(comment);
                }
                // Add tickets to the database
                for (int i = 0; i < 5; i++) {
                        Ticket ts = new Ticket(shoko.getTitle(), shoko.getPrice().floatValue(), shoko.getTimeFinish(),
                                        TicketStatus.OPEN, shoko);

                        shoko.getTickets().add(ts);
                        ticketRepository.save(ts);

                        Ticket to = new Ticket(ohmyclub.getTitle(), ohmyclub.getPrice().floatValue(),
                                        ohmyclub.getTimeFinish(), TicketStatus.OPEN, ohmyclub);

                        ohmyclub.getTickets().add(to);
                        ticketRepository.save(to);

                        Ticket tl = new Ticket(liberata.getTitle(), liberata.getPrice().floatValue(),
                                        liberata.getTimeFinish(), TicketStatus.OPEN, liberata);

                        liberata.getTickets().add(tl);
                        ticketRepository.save(tl);

                        Ticket tm = new Ticket(madcool.getTitle(), madcool.getPrice().floatValue(),
                                        madcool.getTimeFinish(), TicketStatus.OPEN, madcool);

                        madcool.getTickets().add(tm);
                        ticketRepository.save(tm);

                        Ticket tb = new Ticket(blackworks.getTitle(), blackworks.getPrice(),
                                        blackworks.getTimeFinish(), TicketStatus.OPEN, blackworks);

                        blackworks.getTickets().add(tb);
                        ticketRepository.save(tb);

                        Ticket tms = new Ticket(madridSalvaje.getTitle(), madridSalvaje.getPrice(),
                                        madridSalvaje.getTimeFinish(), TicketStatus.OPEN, madridSalvaje);

                        madridSalvaje.getTickets().add(tms);
                        ticketRepository.save(tms);

                        Ticket trb = new Ticket(rioBabel.getTitle(), rioBabel.getPrice().floatValue(),
                                        rioBabel.getTimeFinish(), TicketStatus.OPEN, rioBabel);

                        rioBabel.getTickets().add(trb);
                        ticketRepository.save(trb);

                        Ticket tnw = new Ticket(natosywaor.getTitle(), natosywaor.getPrice().floatValue(),
                                        natosywaor.getTimeFinish(), TicketStatus.OPEN, natosywaor);

                        natosywaor.getTickets().add(tnw);
                        ticketRepository.save(tnw);

                        Ticket tas = new Ticket(alsafir.getTitle(), alsafir.getPrice().floatValue(),
                                        alsafir.getTimeFinish(), TicketStatus.OPEN, alsafir);

                        alsafir.getTickets().add(tas);
                        ticketRepository.save(tas);

                        Ticket tpk = new Ticket(pekeno.getTitle(), pekeno.getPrice().floatValue(),
                                        pekeno.getTimeFinish(), TicketStatus.OPEN, pekeno);

                        pekeno.getTickets().add(tpk);
                        ticketRepository.save(tpk);

                        Ticket tcz = new Ticket(cruzzi.getTitle(), cruzzi.getPrice().floatValue(),
                                        cruzzi.getTimeFinish(), TicketStatus.OPEN, cruzzi);

                        cruzzi.getTickets().add(tcz);
                        ticketRepository.save(tcz);

                }

                //Assign tickets to user
                Ticket ticket1 = natosywaor.getTickets().getFirst();
                Ticket ticket2 = madcool.getTickets().getFirst();

                ticketService.buyTicket(user, ticket1);
                ticketService.buyTicket(user, ticket2);

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

        public void setImageURL(Event event) {
                event.setImage("https://localhost:8443/api/events/" + event.getId() + "/image");
                eventRepository.save(event);
        }

}
