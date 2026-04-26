package com.swahili.pos.controller;

import com.swahili.pos.dto.ServiceRequest;
import com.swahili.pos.model.ExpenseCategory;
import com.swahili.pos.model.Service;
import com.swahili.pos.repository.ExpenseCategoryRepository;
import com.swahili.pos.repository.ServiceRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReferenceController {

    private final ServiceRepository serviceRepo;
    private final ExpenseCategoryRepository categoryRepo;

    @GetMapping("/services")
    public ResponseEntity<List<Service>> getServices() {
        return ResponseEntity.ok(serviceRepo.findByActiveTrueOrderByName());
    }

    // Only owners can add new services
    @PostMapping("/services")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Service> addService(@Valid @RequestBody ServiceRequest req) {
        // Prevent duplicates
        serviceRepo.findByNameIgnoreCase(req.getName()).ifPresent(s -> {
            throw new IllegalArgumentException("Service already exists: " + req.getName());
        });

        Service service = Service.builder()
                .name(req.getName())
                .color(req.getColor())
                .emoji(req.getEmoji())
                .active(true)
                .build();

        return ResponseEntity.ok(serviceRepo.save(service));
    }

    @GetMapping("/expense-categories")
    public ResponseEntity<List<ExpenseCategory>> getCategories() {
        return ResponseEntity.ok(categoryRepo.findByActiveTrueOrderByName());
    }
}
