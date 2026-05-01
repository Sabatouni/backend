package com.yourapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SaleService {

    private final SaleRepository repo;

    public SaleService(SaleRepository repo) {
        this.repo = repo;
    }

    public List<Sale> getAll() {
        return repo.findAll();
    }

    public Sale save(Sale sale) {
        return repo.save(sale);
    }
}