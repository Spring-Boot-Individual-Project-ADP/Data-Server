package com.data.api;

import com.data.repository.EventRepository;
import com.data.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.data.domain.Event;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventAPI {
    @Autowired
    EventRepository repo;

    @Autowired
    RegistrationRepository registrationRepository;

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        Iterable<Event> events = repo.findAll();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") long id) {
        Optional<Event> event = repo.findById(id);

        if(event.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        if(event.getCode() == null || event.getTitle() == null || event.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        Event savedEvent = repo.save(event);

        URI location =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedEvent.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putEvent(@RequestBody Event event, @PathVariable("id") long id) {
        if(event.getId() != id || event.getCode() == null || event.getTitle() == null || event.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        repo.save(event);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") long id) {
        Optional<Event> exisitingEvent = repo.findById(id);

        if(exisitingEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // check whether any registrations exist for the event
        if(registrationRepository.existsByEventId(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete event with registrations");
        }

        repo.delete(exisitingEvent.get());
        return ResponseEntity.ok().build();
    }
}
