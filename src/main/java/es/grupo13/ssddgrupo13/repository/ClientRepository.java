package es.grupo13.ssddgrupo13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import es.grupo13.ssddgrupo13.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

}
