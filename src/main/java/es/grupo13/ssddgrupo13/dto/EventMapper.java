package es.grupo13.ssddgrupo13.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import es.grupo13.ssddgrupo13.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO ToDTO(Event event);
    Event ToDomain(EventDTO eventDTO);
    List<EventDTO> ToDTOs(Collection<Event> events);
    List<Event> ToDomains(Collection<EventDTO> eventDTOs);

}
