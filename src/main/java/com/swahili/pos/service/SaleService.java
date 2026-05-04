package com.swahili.pos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swahili.pos.model.Sale;
import com.swahili.pos.repository.SaleRepository;

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