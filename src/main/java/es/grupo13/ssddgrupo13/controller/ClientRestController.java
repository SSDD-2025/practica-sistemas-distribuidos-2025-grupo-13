package es.grupo13.ssddgrupo13.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.grupo13.ssddgrupo13.dto.ClientDTO;
import es.grupo13.ssddgrupo13.repository.ClientRepository;
import es.grupo13.ssddgrupo13.services.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public Page<ClientDTO> getAllClients(Pageable pageable){
        return clientRepository.findAll(pageable).map(clientService::toDTO);
    }

    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }

    @PostMapping("/")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        clientDTO = clientService.createClient(clientDTO);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(clientDTO.id()).toUri();
		return ResponseEntity.created(location).body(clientDTO);
    }
    
    @PutMapping("/{id}")
    public ClientDTO replaceClient(@PathVariable Long id, @RequestBody ClientDTO updatedClientDTO) {
        return clientService.replaceClient(id, updatedClientDTO);
    }

    @DeleteMapping("/{id}")
    public ClientDTO deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }

}
