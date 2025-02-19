package es.grupo13.ssddgrupo13;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerApp {

    @GetMapping("/section_1")
    public String homeLink() {
        return "section_1";
    }


}