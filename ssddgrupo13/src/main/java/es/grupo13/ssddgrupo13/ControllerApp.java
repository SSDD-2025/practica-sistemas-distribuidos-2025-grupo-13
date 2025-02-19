package es.grupo13.ssddgrupo13;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerApp {

    @GetMapping("/section_1")
    public String homeLink() {
        return "section_1";
    }
    @GetMapping("/section_2")
    public String clubbingLink() {
        return "section_2";
    }
    @GetMapping("/section_3")
    public String conciertosLink() {
        return "section_3";
    }
    @GetMapping("/section_4")
    public String eventosLink() {
        return "section_4";
    }
    @GetMapping("/section_5")
    public String contactanosLink() {
        return "section_5";
    }
    

}