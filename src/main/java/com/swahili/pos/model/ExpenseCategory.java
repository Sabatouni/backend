package com.swahili.pos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_categories")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private boolean active = true;
}
