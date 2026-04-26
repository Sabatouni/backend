package com.swahili.pos.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardStats {
    private BigDecimal todaySales;
    private BigDecimal todayExpenses;
    private BigDecimal monthSales;
    private BigDecimal monthExpenses;
    private BigDecimal netProfit;
    private List<Map<String, Object>> salesByService;
    private List<Map<String, Object>> last7DaysTrend;
}
