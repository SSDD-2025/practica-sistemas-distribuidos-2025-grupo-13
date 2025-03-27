package es.grupo13.ssddgrupo13.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.model.Comment;
import es.grupo13.ssddgrupo13.model.Event;
import es.grupo13.ssddgrupo13.model.Ticket;
import es.grupo13.ssddgrupo13.model.TicketStatus;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import es.grupo13.ssddgrupo13.services.TicketService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ClientController {
    private final ClientService clientService;
    private final TicketService ticketService;
    private final CommentService commentService;
    private final EventService eventService;

    @Autowired
    private ClientRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientController(ClientService clientService, TicketService ticketService, CommentService commentService, EventService eventService) {
        this.clientService = clientService;
        this.ticketService = ticketService;
        this.commentService = commentService;
        this.eventService = eventService;
    }

    private Boolean isLogged = false;
    private Boolean isAdmin = false;

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String name,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            HttpServletRequest request) {

        if (userRepository.findByName(email).isPresent()) {
            return "redirect:/register?error=user_exists"; // Redirect error page
        }
        Client user = new Client(name, lastName, email, passwordEncoder.encode(password), Arrays.asList("USER"));
        userRepository.save(user);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/"; // Go to home page
    }

    @PostMapping("/sign-in")
    public String signIn(HttpSession session, @RequestParam String email) {
        Optional<Client> clientLogin = clientService.findByEmail(email);
        System.out.println(clientLogin.get().getEmail());
        if (clientLogin.isPresent()) {
            session.setAttribute("client", clientLogin.get());
            isLogged = true;
            isAdmin = clientLogin.get().isAdmin();
            return "/signIn";
        } else {
            return "/error";
        }
    }

    @GetMapping("/data")
    public String getMyTickets(HttpSession session, Model model) {
        Client sessionclient = (Client) session.getAttribute("client");
        if (sessionclient == null) {
            return "/createsAnAccount"; // If no client is in session, redirect to error
        }
        System.out.println("Correo de la sesion del cliente" + sessionclient.getEmail());
        // Must find the client in the repository, otherwise will give an error
        Client client = clientService.findById(sessionclient.getId()).orElse(null);
        if (client == null) {
            return "/error"; // If no client is in session, redirect to error
        }
        System.out.println("Correo del cliente" + client.getEmail());

        List<Ticket> tickets = client.getTickets();
        List<Comment> comments = client.getComments();
        model.addAttribute("tickets", tickets);
        model.addAttribute("comments", comments);
        return "myData";
    }

    @PostMapping("authenticate")
    public String loginUser(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpServletRequest request) {
        Client client = clientService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")); // TODO change to custom exception
        if (!passwordEncoder.matches(password, client.getEncodedPassword())) {
            return "redirect:/login?error=true";
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : client.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(client, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/"; // TODO change to admin page
        }
        return "redirect:/";
    }

    @PostMapping("/log_out")
    public String logout(HttpSession session) {
        isLogged = false;
        isAdmin = false;
        session.invalidate();
        return "/loguedOut";
    }

    @GetMapping("/editprofilepage")
    public String editProfilePage(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = authentication.getPrincipal();
            Client client = null;

            if (principal instanceof Client) {
                client = (Client) principal;
            } else if (principal instanceof UserDetails) {
                // Buscar el Client a partir del username
                String email = ((UserDetails) principal).getUsername();
                client = clientService.findByEmail(email).orElseThrow(); // <-- Asume que tienes esto
            }

            model.addAttribute("userLogged", client);
        }

        return "editprofile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(Model model, @RequestParam("name") String name,
            @RequestParam("lastName") String lastName) {
        // Check if the user is logged
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = authentication.getPrincipal();
            Client client = null;
            String email = "";
            if (principal instanceof Client) {
                email = ((Client) principal).getEmail();
                client = (Client) principal;
            } else if (principal instanceof UserDetails) {
                // Buscar el Client a partir del username
                email = ((UserDetails) principal).getUsername();
                client = clientService.findByEmail(email).orElseThrow(); // <-- Asume que tienes esto
            }

            model.addAttribute("userLogged", client);
            Client managedClient = clientService.findByEmail(email).orElse(null);
            managedClient.setName(name);
            managedClient.setLastName(lastName);
            clientService.save(managedClient);

        }

        return "/profile_edited";
    }

}
