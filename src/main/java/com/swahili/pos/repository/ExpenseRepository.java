package com.swahili.pos.repository;

import com.swahili.pos.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByExpenseDateBetweenOrderByExpenseDateDesc(LocalDate from, LocalDate to);

    @Query("SELECT COALESCE(SUM(e.cost), 0) FROM Expense e WHERE e.expenseDate = :date")
    BigDecimal sumByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(e.cost), 0) FROM Expense e WHERE e.expenseDate BETWEEN :from AND :to")
    BigDecimal sumByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("""
        SELECT CAST(e.expenseDate AS string) AS date,
               COALESCE(SUM(e.cost), 0) AS total
        FROM Expense e
        WHERE e.expenseDate BETWEEN :from AND :to
        GROUP BY e.expenseDate
        ORDER BY e.expenseDate
    """)
    List<Map<String, Object>> dailyTotals(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
