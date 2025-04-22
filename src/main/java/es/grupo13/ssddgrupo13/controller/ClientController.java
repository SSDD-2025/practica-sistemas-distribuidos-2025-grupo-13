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
import java.util.Arrays;
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

import es.grupo13.ssddgrupo13.controller.customExceptions.UserNotFoundException;
import es.grupo13.ssddgrupo13.dto.ClientDTO;
import es.grupo13.ssddgrupo13.model.Client;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.services.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
      * Handles user registration (sign-up).
     */
    @PostMapping("/sign-up")
    public String signUp(HttpServletRequest request, Model model, ClientDTO client) {

        if (clientRepository.findByEmail(client.email()).isPresent()) {
            return "redirect:/register?error=user_exists";
        }

        Client newUser = clientService.toDomain(client);
        newUser.setRoles(Arrays.asList("USER"));
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
    public String loginUser(ClientDTO clientDTO,
                            HttpServletRequest request,
                            Model model) {

        Client client = clientService.findByEmail(clientDTO.email())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(clientDTO.password(), client.getEncodedPassword())) {
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

    @PostMapping("/deleteAccount")
    public String deleteAccount(HttpServletRequest request, HttpServletResponse response, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = isAuthenticatedUser(auth);
        model.addAttribute("isUserLogged", isLoggedIn);

        if (isLoggedIn) {
            Client client = extractClientFromPrincipal(auth.getPrincipal());
            if (client != null) {
                clientService.deleteClient(client.getId());
                // Clear the Spring Security context and invalidate the session
                SecurityContextHolder.clearContext();
                request.getSession().invalidate();
            }
        }
        
        model.addAttribute("title", "Cuenta eliminada");
        model.addAttribute("message", "Tu cuenta ha sido eliminada correctamente.");
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
    public String editProfile(ClientDTO clientDTO, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = isAuthenticatedUser(auth);
        model.addAttribute("isUserLogged", isLoggedIn);

        if (isLoggedIn) {
            Client client = extractClientFromPrincipal(auth.getPrincipal());
            if (client != null) {
                Client managedClient = clientService.findByEmail(client.getEmail()).orElse(null);
                if (managedClient != null) {
                    managedClient.setName(clientDTO.name());
                    managedClient.setLastName(clientDTO.lastName());
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
        switch (principal) {
            case Client client -> {
                return client;
            }
            case UserDetails userDetails -> {
                return clientService.findByEmail(userDetails.getUsername()).orElse(null);
            }
            default -> {
            }
        }
        return null;
    }
}
