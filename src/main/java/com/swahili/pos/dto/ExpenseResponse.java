package com.swahili.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String category;
    private String itemName;
    private BigDecimal cost;
    private LocalDate expenseDate;
    private String createdBy;
    private LocalDateTime createdAt;
}
