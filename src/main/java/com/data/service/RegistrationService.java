package com.data.service;

import com.data.domain.Registration;

import java.util.Optional;

public interface RegistrationService {
    public void saveRegistration(Registration registration);
    public Iterable<Registration> findAllRegistrations();
    public Optional<Registration> findRegistrationById(long id);
}
