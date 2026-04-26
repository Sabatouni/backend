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
public class SaleResponse {
    private Long id;
    private String service;
    private String serviceColor;
    private String serviceEmoji;
    private BigDecimal amount;
    private String notes;
    private LocalDate saleDate;
    private String createdBy;
    private LocalDateTime createdAt;
}
