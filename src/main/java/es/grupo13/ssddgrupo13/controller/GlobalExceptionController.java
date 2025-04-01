package es.grupo13.ssddgrupo13.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import es.grupo13.ssddgrupo13.controller.CustomExceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionController {
    
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
        model.addAttribute("title", "Error de inicio de sesión");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("linkText", "Volver al inicio");
        model.addAttribute("linkUrl", "/");
        return "notification";
    }

}
