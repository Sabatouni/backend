package com.swahili.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swahili.pos.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}