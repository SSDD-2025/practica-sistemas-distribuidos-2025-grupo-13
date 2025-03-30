package es.grupo13.ssddgrupo13.security;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomNoSuchElementException {

@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(java.util.NoSuchElementException.class)
	public void handleNotFound() {
	}
}
