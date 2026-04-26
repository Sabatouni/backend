package com.swahili.pos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceRequest {

    @NotBlank(message = "Service name is required")
    private String name;

    private String color = "#3D405B";

    private String emoji = "🎪";
}
