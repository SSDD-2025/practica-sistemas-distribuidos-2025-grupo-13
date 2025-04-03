package es.grupo13.ssddgrupo13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport( pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class Ssddgrupo13Application {

	public static void main(String[] args) {
		SpringApplication.run(Ssddgrupo13Application.class, args);
	}

}
