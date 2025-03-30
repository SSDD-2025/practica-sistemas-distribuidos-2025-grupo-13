package es.grupo13.ssddgrupo13.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import es.grupo13.ssddgrupo13.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
ClientDTO ToDTO(Client client);
List<ClientDTO> ToDTOs(Collection<Client> clients);
Client ToDomain(ClientDTO clientDTO);
List<Client> ToDomains(Collection<ClientDTO> clientDTOs);
}

