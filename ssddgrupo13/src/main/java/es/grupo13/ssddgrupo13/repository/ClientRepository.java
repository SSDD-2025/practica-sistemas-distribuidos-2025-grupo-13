package es.grupo13.ssddgrupo13.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.grupo13.ssddgrupo13.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
}
