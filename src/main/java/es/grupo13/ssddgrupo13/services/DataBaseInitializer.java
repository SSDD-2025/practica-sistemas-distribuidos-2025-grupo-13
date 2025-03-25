package es.grupo13.ssddgrupo13.services;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        private JdbcTemplate jdbcTemplate;

        @Autowired
        private ImageUtils imageUtils;


        
        @PostConstruct
        public void init() {

                Client admin = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"),
                                Arrays.asList("ADMIN", "USER"));

                Client user = new Client("user", "user", "user@user.com", passwordEncoder.encode("user"),
                                Arrays.asList("USER"));
                // SAMPLE USERS
                if (clientRepository.findByName(admin.getName()).isEmpty()
                                && clientRepository.findByName(user.getName()).isEmpty()) {
                        clientRepository.save(admin);

                }


                // Load images to the database
                Blob shokoImage = imageUtils.loadImage("img/shoko.png");
                Blob ohmyclubImage = imageUtils.loadImage("img/OH MY CLUB.png");
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
                Event cruzzi = new Event("CRUZZI", "", startConcert, finishConcert, "Teatro Barceló", "concierto", 58,
                                cruzziImage);

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
                                

                // Add tickets to the database
                for (int i = 0; i < 5; i++) {
                        Ticket ts = new Ticket(shoko.getTitle(), shoko.getPrecio().floatValue(), shoko.getTimeFinish(), TicketStatus.OPEN);
                        
                        shoko.getTickets().add(ts);
                        
                        Ticket to = new Ticket(ohmyclub.getTitle(), ohmyclub.getPrecio().floatValue(), ohmyclub.getTimeFinish(),TicketStatus.OPEN);
                        
                        ohmyclub.getTickets().add(to);
                        
                        Ticket tl = new Ticket(liberata.getTitle(), liberata.getPrecio().floatValue(), liberata.getTimeFinish(),TicketStatus.OPEN);
                        
                        liberata.getTickets().add(tl);
                        
                        Ticket tm = new Ticket(madcool.getTitle(), madcool.getPrecio().floatValue(), madcool.getTimeFinish(),TicketStatus.OPEN);
                        
                        madcool.getTickets().add(tm);
                        
                        Ticket tb = new Ticket(blackworks.getTitle(), blackworks.getPrecio(),blackworks.getTimeFinish(),TicketStatus.OPEN);
                        
                        blackworks.getTickets().add(tb);
                        
                        Ticket tms = new Ticket(madridSalvaje.getTitle(), madridSalvaje.getPrecio(),madridSalvaje.getTimeFinish(), TicketStatus.OPEN);
                        
                        madridSalvaje.getTickets().add(tms);
                        Ticket trb = new Ticket(rioBabel.getTitle(), rioBabel.getPrecio().floatValue(), rioBabel.getTimeFinish(), TicketStatus.OPEN);
                        
                        rioBabel.getTickets().add(trb);

                        Ticket tnw = new Ticket(natosywaor.getTitle(), natosywaor.getPrecio().floatValue(),natosywaor.getTimeFinish(),TicketStatus.OPEN);
                        
                        natosywaor.getTickets().add(tnw);

                        Ticket tas = new Ticket(alsafir.getTitle(), alsafir.getPrecio().floatValue(), alsafir.getTimeFinish(),TicketStatus.OPEN);
                        
                        alsafir.getTickets().add(tas);

                        Ticket tpk = new Ticket(pekeno.getTitle(), pekeno.getPrecio().floatValue(), pekeno.getTimeFinish(),TicketStatus.OPEN);
                        
                        pekeno.getTickets().add(tpk);

                        Ticket tcz = new Ticket(cruzzi.getTitle(), cruzzi.getPrecio().floatValue(), cruzzi.getTimeFinish(),TicketStatus.OPEN);
                        
                        cruzzi.getTickets().add(tcz);

                }
                eventRepository.save(shoko);
                eventRepository.save(ohmyclub);
                eventRepository.save(liberata);
                eventRepository.save(madcool);
                eventRepository.save(blackworks);


                Ticket tcktejmpl = new Ticket(shoko.getTitle(), shoko.getPrecio().floatValue(), shoko.getTimeFinish(),TicketStatus.OPEN);
                ticketRepository.save(tcktejmpl);
                clientRepository.save(user);
                Comment cmmntejmpl = new Comment(user.getName(), "Este evento es muy divertido", 4,natosywaor.getTitle());

                Ticket tcktejmpl2 = new Ticket(natosywaor.getTitle(), natosywaor.getPrecio(), natosywaor.getTimeFinish(), TicketStatus.OPEN);
                ticketRepository.save(tcktejmpl2);
                user.getTickets().add(tcktejmpl2);
                user.getTickets().add(tcktejmpl);
                user.getComments().add(cmmntejmpl);
                clientRepository.save(user);

                Comment comment = new Comment("Robert", "Muy guay", 5, shoko.getTitle());
                Comment comment1 = new Comment("Mar", "Increible", 4, shoko.getTitle());
                Comment comment2 = new Comment("Juan", "Impresionante", 3, ohmyclub.getTitle());
                Comment comment3 = new Comment("Jose", "Increible", 5, liberata.getTitle());
                Comment comment4 = new Comment("Robert", "Nada del otro mundo", 1, madcool.getTitle());
                Comment comment5 = new Comment("Martin", "Super interesante", 4, blackworks.getTitle());
                Comment comment6 = new Comment("Ruben", "Muy sorpresivo", 3, blackworks.getTitle());
                Comment comment7 = new Comment("Mar", "Normalito", 2, rioBabel.getTitle());
                Comment comment8 = new Comment("Alvaro", "Me esperaba más", 2, natosywaor.getTitle());
                Comment comment9 = new Comment("Mario", "Increiblemente ", 4, alsafir.getTitle());
                Comment comment10 = new Comment("Harry", "Super magico", 4, alsafir.getTitle());
                Comment comment11 = new Comment("Ruslana", "Muy malo", 0, pekeno.getTitle());
                Comment comment12 = new Comment("Daniel", "Espectacular!!!!", 5, cruzzi.getTitle());

                // Add comments to the database
                shoko.addComments(comment);
                shoko.addComments(comment1);
                ohmyclub.addComments(comment2);
                liberata.addComments(comment3);
                madcool.addComments(comment4);
                blackworks.addComments(comment5);
                blackworks.addComments(comment6);
                rioBabel.addComments(comment7);
                natosywaor.addComments(comment8);
                alsafir.addComments(comment9);
                alsafir.addComments(comment10);
                pekeno.addComments(comment11);
                cruzzi.addComments(comment12);

                // Add events to the database
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

                natosywaor.addComments(cmmntejmpl);
                eventRepository.save(natosywaor);


                
        }

}
