package com.swahili.pos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 7)
    private String color = "#3D405B";

    @Column(length = 10)
    private String emoji = "🎪";

    @Column(nullable = false)
    private boolean active = true;
}
