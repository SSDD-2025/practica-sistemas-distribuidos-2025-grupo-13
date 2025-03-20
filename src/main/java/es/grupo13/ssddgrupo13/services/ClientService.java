package es.grupo13.ssddgrupo13.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.entities.Client;
import es.grupo13.ssddgrupo13.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Optional<Client> findById(long id){
        return clientRepository.findById(id);
    }

    public Optional<Client> findByEmail(String email){
        return clientRepository.findByEmail(email);
    }
    public Client save(Client client){
        return clientRepository.save(client);
    }



}
