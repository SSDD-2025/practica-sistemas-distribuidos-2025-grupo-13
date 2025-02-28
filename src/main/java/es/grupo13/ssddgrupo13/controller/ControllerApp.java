package es.grupo13.ssddgrupo13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ControllerApp {

    @GetMapping("/")
    public String homeLink() {
        return "index";
    }

    @GetMapping("/contactanos")
    public String contactanosLink() {
        return "contactanos";
    }

    @GetMapping("/data")
    public String misDatosLink() {
        return "misdatos";
    }
    
    
    /*@GetMapping("/error")
    public String errorLink() {
        return "error";
    }*/

}