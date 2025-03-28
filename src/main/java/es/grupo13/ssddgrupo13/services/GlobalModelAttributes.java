// GlobalModelAttributes.java
package es.grupo13.ssddgrupo13.services;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpServletRequest;

import es.grupo13.ssddgrupo13.model.Client;

import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private ClientService clientService;

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserLogged = auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String);
        model.addAttribute("isUserLogged", isUserLogged);

        if (isUserLogged) {
            Object principal = auth.getPrincipal();
            Client client = null;

            if (principal instanceof Client) {
                client = (Client) principal;
            } else if (principal instanceof UserDetails) {
                String email = ((UserDetails) principal).getUsername();
                client = clientService.findByEmail(email).orElse(null);
            }

            model.addAttribute("userLogged", client);
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        }
    }
}
