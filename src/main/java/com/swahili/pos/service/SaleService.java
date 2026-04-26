package com.swahili.pos.service;

import com.swahili.pos.dto.SaleRequest;
import com.swahili.pos.dto.SaleResponse;
import com.swahili.pos.model.Sale;
import com.swahili.pos.model.Service;
import com.swahili.pos.model.User;
import com.swahili.pos.repository.SaleRepository;
import com.swahili.pos.repository.ServiceRepository;
import com.swahili.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepo;
    private final ServiceRepository serviceRepo;
    private final UserRepository userRepo;

    public SaleResponse create(SaleRequest req, String username) {
        Service service = serviceRepo.findById(req.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + req.getServiceId()));

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Sale sale = Sale.builder()
                .service(service)
                .amount(req.getAmount())
                .notes(req.getNotes())
                .saleDate(req.getSaleDate())
                .createdBy(user)
                .build();

        return toResponse(saleRepo.save(sale));
    }

    public List<SaleResponse> findByRange(LocalDate from, LocalDate to) {
        return saleRepo.findBySaleDateBetweenOrderBySaleDateDesc(from, to)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private SaleResponse toResponse(Sale s) {
        return SaleResponse.builder()
                .id(s.getId())
                .service(s.getService().getName())
                .serviceColor(s.getService().getColor())
                .serviceEmoji(s.getService().getEmoji())
                .amount(s.getAmount())
                .notes(s.getNotes())
                .saleDate(s.getSaleDate())
                .createdBy(s.getCreatedBy().getUsername())
                .createdAt(s.getCreatedAt())
                .build();
    }
}
