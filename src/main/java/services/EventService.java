package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.grupo13.ssddgrupo13.entities.Event;
import es.grupo13.ssddgrupo13.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> findByType(String type){
        return eventRepository.findByType(type);
    }
    public List<Event> findByTitle(String title){
        return eventRepository.findByTitle(title);
    }
    public Optional<Event> findById(long id){
        return eventRepository.findById(id);
    }
    public void deleteById(long id){
        eventRepository.deleteById(id);
    } 

}
