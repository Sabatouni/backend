package com.swahili.pos.service;

import com.swahili.pos.dto.DashboardStats;
import com.swahili.pos.repository.ExpenseRepository;
import com.swahili.pos.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final SaleRepository saleRepo;
    private final ExpenseRepository expenseRepo;

    public DashboardStats getStats() {
        LocalDate today      = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate weekAgo    = today.minusDays(6);

        BigDecimal todaySales    = saleRepo.sumByDate(today);
        BigDecimal todayExpenses = expenseRepo.sumByDate(today);
        BigDecimal monthSales    = saleRepo.sumByDateRange(monthStart, today);
        BigDecimal monthExpenses = expenseRepo.sumByDateRange(monthStart, today);

        List<Map<String, Object>> byService = saleRepo.revenueByService(monthStart, today);

        // Build a complete 7-day trend map (fill gaps with zero)
        List<Map<String, Object>> salesDaily   = saleRepo.dailyTotals(weekAgo, today);
        List<Map<String, Object>> expenseDaily = expenseRepo.dailyTotals(weekAgo, today);

        Map<String, Map<String, Object>> trendMap = new LinkedHashMap<>();
        for (LocalDate d = weekAgo; !d.isAfter(today); d = d.plusDays(1)) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("date", d.toString());
            entry.put("sales", BigDecimal.ZERO);
            entry.put("expenses", BigDecimal.ZERO);
            trendMap.put(d.toString(), entry);
        }

        salesDaily.forEach(r -> {
            String date = r.get("date").toString();
            if (trendMap.containsKey(date)) {
                trendMap.get(date).put("sales", r.get("total"));
            }
        });

        expenseDaily.forEach(r -> {
            String date = r.get("date").toString();
            if (trendMap.containsKey(date)) {
                trendMap.get(date).put("expenses", r.get("total"));
            }
        });

        return DashboardStats.builder()
                .todaySales(todaySales)
                .todayExpenses(todayExpenses)
                .monthSales(monthSales)
                .monthExpenses(monthExpenses)
                .netProfit(monthSales.subtract(monthExpenses))
                .salesByService(byService)
                .last7DaysTrend(new ArrayList<>(trendMap.values()))
                .build();
    }
}
