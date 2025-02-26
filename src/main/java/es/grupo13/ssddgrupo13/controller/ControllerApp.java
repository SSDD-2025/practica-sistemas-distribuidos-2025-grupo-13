package es.grupo13.ssddgrupo13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ControllerApp {

    @GetMapping("/")
    public String homeLink() {
        return "index";
    }

    /*@GetMapping("/clubbing")
    public String clubbingLink() {
        return "section_2";
    }*/
    @GetMapping("/conciertos")
    public String conciertosLink() {
        return "section_3";
    }
    /*@GetMapping("/eventos")
    public String eventosLink() {
        return "section_4";
    }*/
    @GetMapping("/contactanos")
    public String contactanosLink() {
        return "section_5";
    }
    @GetMapping("/ticket")
    public String ticketLink() {
        return "ticket";
    }
    
    /*@GetMapping("/error")
    public String errorLink() {
        return "error";
    }*/
    
    

}