package com.swahili.pos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swahili.pos.model.Sale;
import com.swahili.pos.service.SaleService;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin
public class SaleController {

    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sale> getSales() {
        return service.getAll();
    }

    @PostMapping
    public Sale addSale(@RequestBody Sale sale) {
        return service.save(sale);
    }
}