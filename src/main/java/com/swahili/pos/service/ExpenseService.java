package com.swahili.pos.service;

import com.swahili.pos.dto.ExpenseRequest;
import com.swahili.pos.dto.ExpenseResponse;
import com.swahili.pos.model.Expense;
import com.swahili.pos.model.ExpenseCategory;
import com.swahili.pos.model.User;
import com.swahili.pos.repository.ExpenseCategoryRepository;
import com.swahili.pos.repository.ExpenseRepository;
import com.swahili.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepo;
    private final ExpenseCategoryRepository categoryRepo;
    private final UserRepository userRepo;

    public ExpenseResponse create(ExpenseRequest req, String username) {
        ExpenseCategory category = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + req.getCategoryId()));

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Expense expense = Expense.builder()
                .category(category)
                .itemName(req.getItemName())
                .cost(req.getCost())
                .expenseDate(req.getExpenseDate())
                .createdBy(user)
                .build();

        return toResponse(expenseRepo.save(expense));
    }

    public List<ExpenseResponse> findByRange(LocalDate from, LocalDate to) {
        return expenseRepo.findByExpenseDateBetweenOrderByExpenseDateDesc(from, to)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ExpenseResponse toResponse(Expense e) {
        return ExpenseResponse.builder()
                .id(e.getId())
                .category(e.getCategory().getName())
                .itemName(e.getItemName())
                .cost(e.getCost())
                .expenseDate(e.getExpenseDate())
                .createdBy(e.getCreatedBy().getUsername())
                .createdAt(e.getCreatedAt())
                .build();
    }
}
