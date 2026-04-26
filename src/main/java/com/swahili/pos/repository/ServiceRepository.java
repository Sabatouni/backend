package com.swahili.pos.repository;

import com.swahili.pos.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByActiveTrueOrderByName();
    Optional<Service> findByNameIgnoreCase(String name);
}
