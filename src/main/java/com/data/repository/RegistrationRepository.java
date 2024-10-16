package com.data.repository;

import com.data.domain.Registration;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<Registration, Long> {
    boolean existsByEventId(long id);
}
