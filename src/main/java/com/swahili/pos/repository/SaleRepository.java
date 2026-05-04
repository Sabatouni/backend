package com.swahili.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swahili.pos.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}