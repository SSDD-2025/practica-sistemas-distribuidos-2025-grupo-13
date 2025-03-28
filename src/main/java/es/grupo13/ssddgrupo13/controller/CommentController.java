/**
 * CommentController handles the creation and deletion of comments by authenticated users.
 * Comments are associated with both clients and events. Only the author of a comment
 * can delete it.
 *
 * Responsibilities:
 * - Add a comment to an event
 * - Delete a comment (only if user is the author)
 */

 package es.grupo13.ssddgrupo13.controller;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestParam;

 import es.grupo13.ssddgrupo13.model.Client;
 import es.grupo13.ssddgrupo13.model.Comment;
 import es.grupo13.ssddgrupo13.model.Event;
 import es.grupo13.ssddgrupo13.services.ClientService;
import es.grupo13.ssddgrupo13.services.CommentService;
import es.grupo13.ssddgrupo13.services.EventService;
import jakarta.servlet.http.HttpServletRequest;
 
 @Controller
 public class CommentController {
 
     @Autowired
     private ClientService clientService;
 
     @Autowired
     private EventService eventService;
 
     @Autowired
     private CommentService commentService;
 
     /**
      * Adds a new comment to the specified event.
      */
     @PostMapping("/comment_in")
     public String addComment(HttpServletRequest request,
                               @RequestParam String text,
                               @RequestParam(required = false, defaultValue = "0") String rating,
                               @RequestParam Long eventID,
                               Model model) {
 
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (!isAuthenticatedUser(auth)) {
            model.addAttribute("title", "Acceso denegado");
            model.addAttribute("message", "Necesitas iniciar sesión para comentar.");
            model.addAttribute("linkText", "Aceptar");
            model.addAttribute("linkUrl", "/");
             return "notification";
         }
 
         String email = extractEmailFromPrincipal(auth.getPrincipal());
         if (email == null) return "/error";
 
         Event event = eventService.findById(eventID).orElse(null);
         if (event == null) return "/error";
 
         Client client = clientService.findByEmail(email).orElse(null);
         if (client == null) return "/error";
         
         
         Comment comment = new Comment(client.getName(), text, Integer.parseInt(rating), event.getTitle());
         comment.setEvent(event);
 
         // Associate comment with event and client
         event.getComments().add(comment);
         client.getComments().add(comment);
 
         // Persist data
         commentService.save(comment);
         eventService.save(event);
         clientService.save(client);
 
         return "redirect:/ticket/" + eventID;
     }
 
     /**
      * Deletes a comment if the user is the author.
      */
     @PostMapping("/comment_out/{commentId}")
     public String deleteComment(Model model,
                                 HttpServletRequest request,
                                 @PathVariable Long commentId) {
         
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         boolean isUserLogged = isAuthenticatedUser(auth);
         model.addAttribute("isUserLogged", isUserLogged);

         if (!isUserLogged) return "/error";
 
         String email = extractEmailFromPrincipal(auth.getPrincipal());
         if (email == null) return "/error";
 
         Client client = clientService.findByEmail(email).orElse(null);
         if (client == null) return "/error";
        
         model.addAttribute("userLogged", client);
         Comment comment = commentService.findById(commentId).orElse(null);
         if (comment == null || !comment.getAutor().equals(client.getName())) {
             return "/error";
         }
         
         Event event = comment.getEvent();
         if (event == null) return "/error";
 
         // Remove associations
         client.getComments().remove(comment);
         event.getComments().remove(comment);
         commentService.delete(comment);
         
         // Persist changes
         clientService.save(client);
         eventService.save(event);
         
         model.addAttribute("title", "Comentario borrado");
         model.addAttribute("message", "¡Has borrado este comentario correctamente!");
         model.addAttribute("linkText", "Aceptar");
         model.addAttribute("linkUrl", "/profilePage");
         return "notification";
     }
 
     /**
      * Utility method to verify user authentication.
      */
     private boolean isAuthenticatedUser(Authentication auth) {
         return auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String);
     }
 
     /**
      * Utility method to extract email from the principal object.
      */
     private String extractEmailFromPrincipal(Object principal) {
         if (principal instanceof Client client) {
             return client.getEmail();
         } else if (principal instanceof UserDetails userDetails) {
             return userDetails.getUsername();
         }
         return null;
     }
 }
 