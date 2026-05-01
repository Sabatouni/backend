package com.yourapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;
    private Double cost;
    private LocalDateTime date = LocalDateTime.now();

    public Long getId() { return id; }
    public String getItem() { return item; }
    public Double getCost() { return cost; }
    public LocalDateTime getDate() { return date; }

    public void setItem(String item) { this.item = item; }
    public void setCost(Double cost) { this.cost = cost; }
}