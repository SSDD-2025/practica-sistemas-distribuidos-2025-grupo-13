package es.grupo13.ssddgrupo13.controller;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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
import es.grupo13.ssddgrupo13.services.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public Collection<ClientDTO> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable long id){
        return clientService.getEvent(id);
    }

    @PostMapping("/")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        clientDTO = clientService.createClient(clientDTO);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(clientDTO.id()).toUri();
		return ResponseEntity.created(location).body(clientDTO);
    }
    
    @PutMapping("/{id}")
    public ClientDTO replaceClient(@PathVariable long id, @RequestBody ClientDTO updatedClientDTO) {
        return clientService.replaceClient(id, updatedClientDTO);
    }

    @DeleteMapping("/{id}")
    public ClientDTO deletePost(@PathVariable long id) {
        return clientService.deleteClient(id);
    }

}
