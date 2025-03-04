package es.grupo13.ssddgrupo13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ControllerApp {

    @GetMapping("/")
    public String homeLink() {
        return "index";
    }

    @GetMapping("/contact")
    public String contactanosLink() {
        return "contact";
    }

    @GetMapping("/register")
    public String registerLink() {
        return "register";
    }

    @PostMapping("/contact_recieved")
    public String contact_recievedLink() {
        return "contact_recieved";
    }
}