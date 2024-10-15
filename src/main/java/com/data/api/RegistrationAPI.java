package com.data.api;

import com.data.domain.Registration;
import com.data.repository.RegistrationRepository;
import com.data.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
