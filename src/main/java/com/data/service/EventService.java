package com.data.service;

import com.data.domain.Event;
import com.data.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public interface EventService  {
    public void saveEvent(Event event);
    public Iterable<Event> findAllEvents();
    public Optional<Event> findEventById(long id);
}
