package com.swahili.pos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SaleRequest {

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    private String notes;

    @NotNull(message = "Sale date is required")
    private LocalDate saleDate;
}
