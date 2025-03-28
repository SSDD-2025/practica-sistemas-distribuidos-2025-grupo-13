/**
 * ClientController handles all client-related actions such as registration,
 * login, profile editing, and data retrieval (tickets and comments).
 * It integrates with Spring Security to manage authentication and session context.
 *
 * Responsibilities:
 * - Sign up new users
 * - Authenticate and login users
 * - Display personal data (tickets, comments)
 * - Handle logout
 * - Render and process profile edit functionality
 */

package es.grupo13.ssddgrupo13.controller;

import java.util.ArrayList;
import java.util.List;
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
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.services.ClientService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Handles client registration, authentication, profile editing, and session management.
 */
@Controller
public class ClientController {

    private final ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
      * Handles user registration (sign-up).
     */
    @PostMapping("/sign-up")
    public String signUp(@RequestParam String name,
                         @RequestParam String lastName,
                         @RequestParam String email,
                         @RequestParam String password,
                         HttpServletRequest request,
                         Model model) {

        if (clientRepository.findByName(email).isPresent()) {
            return "redirect:/register?error=user_exists";
        }

        Client newUser = new Client(name, lastName, email, passwordEncoder.encode(password), List.of("USER"));
        clientRepository.save(newUser);

        authenticateUser(newUser, request);

        model.addAttribute("title", "Registrado");
        model.addAttribute("message", "¡Te has registrado correctamente!");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/");
        return "notification";
    }

    /**
      * Authenticates the user and sets Spring Security context.
     */
    private void authenticateUser(Client user, HttpServletRequest request) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }

    /**
     * Authenticates user through login form.
     */
    @PostMapping("/authenticate")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpServletRequest request,
                            Model model) {

        Client client = clientService.findByEmail(email)
                 .orElseThrow(() -> new RuntimeException("User not found")); // TODO: Replace with custom exception

        if (!passwordEncoder.matches(password, client.getEncodedPassword())) {
            return "redirect:/login?error=true";
        }

        authenticateUser(client, request);
 
        model.addAttribute("title", "Iniciado sesión");
        model.addAttribute("message", "¡Has iniciado sesión correctamente!");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/");
        return "notification";
    }

    /**
     * Handles user logout and displays a success message.
     */
    @GetMapping("/logoutSuccess")
    public String logoutSuccess(Model model) {
        model.addAttribute("title", "Cerrado sesión");
        model.addAttribute("message", "Has cerrado sesión correctamente.");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/");
        return "notification";
    }


    /**
      * Loads the profile editing page with current user info.
     */
    @GetMapping("/editprofilepage")
    public String editProfilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = isAuthenticatedUser(auth);
        model.addAttribute("isUserLogged", isLoggedIn);

        if (isLoggedIn) {
            Client client = extractClientFromPrincipal(auth.getPrincipal());
            model.addAttribute("userLogged", client);
        }
        return "editprofile";
    }

    /**
      * Handles profile updates (name and last name).
     */
    @PostMapping("/edit-profile")
    public String editProfile(@RequestParam String name,
                              @RequestParam String lastName,
                              Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = isAuthenticatedUser(auth);
        model.addAttribute("isUserLogged", isLoggedIn);

        if (isLoggedIn) {
            Client client = extractClientFromPrincipal(auth.getPrincipal());
            if (client != null) {
                Client managedClient = clientService.findByEmail(client.getEmail()).orElse(null);
                if (managedClient != null) {
                    managedClient.setName(name);
                    managedClient.setLastName(lastName);
                    clientService.save(managedClient);
                    model.addAttribute("userLogged", managedClient);
                }
            }
        }
        
        model.addAttribute("title", "Perfil editado");
        model.addAttribute("message", "¡Perfil editado con éxito!");
        model.addAttribute("linkText", "Aceptar");
        model.addAttribute("linkUrl", "/");
        return "notification";
    }

    /**
      * Utility method to check if the user is authenticated.
     */
    private boolean isAuthenticatedUser(Authentication auth) {
        return auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String);
    }

    /**
      * Utility method to extract a Client object from the authentication principal.
     */
    private Client extractClientFromPrincipal(Object principal) {
        if (principal instanceof Client client) {
            return client;
        } else if (principal instanceof UserDetails userDetails) {
            return clientService.findByEmail(userDetails.getUsername()).orElse(null);
        }
        return null;
    }
}
