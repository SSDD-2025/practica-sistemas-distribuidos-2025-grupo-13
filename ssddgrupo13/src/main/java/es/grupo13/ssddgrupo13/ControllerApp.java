package es.grupo13.ssddgrupo13;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ControllerApp {

    @PostMapping("/home")
    public String homeLink() {
        return "../Static/index";
    }
    @PostMapping("/clubbing")
    public String clubbingLink() {
        return "section_2";
    }
    @PostMapping("/conciertos")
    public String conciertosLink() {
        return "section_3";
    }
    @PostMapping("/eventos")
    public String eventosLink() {
        return "section_4";
    }
    @PostMapping("/contactanos")
    public String contactanosLink() {
        return "section_5";
    }
    @PostMapping("/error")
    public String getMethodName(@RequestParam String param) {
        return "error";
    }
    
    

}