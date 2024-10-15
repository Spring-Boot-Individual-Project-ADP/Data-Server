package com.data.service;

import com.data.domain.Event;
import com.data.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl {
    @Autowired
    private EventRepository eventRepository;

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public Iterable<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> findEventById(long id) {
        return eventRepository.findById(id);
    }
}
