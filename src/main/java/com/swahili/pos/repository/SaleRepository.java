package com.swahili.pos.repository;

import com.swahili.pos.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findBySaleDateBetweenOrderBySaleDateDesc(LocalDate from, LocalDate to);

    @Query("SELECT COALESCE(SUM(s.amount), 0) FROM Sale s WHERE s.saleDate = :date")
    BigDecimal sumByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(s.amount), 0) FROM Sale s WHERE s.saleDate BETWEEN :from AND :to")
    BigDecimal sumByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("""
        SELECT s.service.name AS service,
               s.service.color AS color,
               s.service.emoji AS emoji,
               COALESCE(SUM(s.amount), 0) AS total
        FROM Sale s
        WHERE s.saleDate BETWEEN :from AND :to
        GROUP BY s.service.name, s.service.color, s.service.emoji
        ORDER BY total DESC
    """)
    List<Map<String, Object>> revenueByService(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("""
        SELECT CAST(s.saleDate AS string) AS date,
               COALESCE(SUM(s.amount), 0) AS total
        FROM Sale s
        WHERE s.saleDate BETWEEN :from AND :to
        GROUP BY s.saleDate
        ORDER BY s.saleDate
    """)
    List<Map<String, Object>> dailyTotals(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
