package com.yourapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public List<Expense> getAll() {
        return repo.findAll();
    }

    public Expense save(Expense expense) {
        return repo.save(expense);
    }
}