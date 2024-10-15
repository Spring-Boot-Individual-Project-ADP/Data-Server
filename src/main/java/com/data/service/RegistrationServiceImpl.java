package com.data.service;

import com.data.domain.Registration;
import com.data.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;

    public void saveRegistration(Registration registration) {
        registrationRepository.save(registration);
    }

    public Iterable<Registration> findAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Optional<Registration> findRegistrationById(long id) {
        return registrationRepository.findById(id);
    }
}
