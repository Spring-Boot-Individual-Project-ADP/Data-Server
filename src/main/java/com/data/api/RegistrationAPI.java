package com.data.api;

import com.data.domain.Registration;
import com.data.repository.RegistrationRepository;
import com.data.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/registrations")
public class RegistrationAPI {

    @Autowired
    RegistrationRepository repo;

    @GetMapping
    public ResponseEntity<?> getAllRegistrations() {
        Iterable<Registration> registrations = repo.findAll();
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistration(@PathVariable("id") long id) {
        Optional<Registration> registration = repo.findById(id);

        if(registration.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(registration);
    }

    @PostMapping
    public ResponseEntity<?> addRegistration(@RequestBody Registration registration) {
        if(registration.getCustomer() == null || registration.getEvent() == null || registration.getDate() == null || registration.getNotes() == null) {
            return ResponseEntity.badRequest().build();
        }

        Registration savedRegistration = repo.save(registration);
        return ResponseEntity.ok(savedRegistration);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putRegistration(@RequestBody Registration registration, @PathVariable("id") long id) {
        Optional<Registration> existingRegistration = repo.findById(id);

        if(existingRegistration.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Registration updatedRegistration = existingRegistration.get();
        updatedRegistration.setEvent(registration.getEvent());
        updatedRegistration.setCustomer(registration.getCustomer());

        repo.save(updatedRegistration);
        return ResponseEntity.ok(updatedRegistration);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable("id") long id) {
        Optional<Registration> registration = repo.findById(id);

        if(registration.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repo.delete(registration.get());
        return ResponseEntity.ok().build();
    }


}
