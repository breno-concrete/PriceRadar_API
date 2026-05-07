package com.breno.PriceRadar_API.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemResponseDTO(
        Long id,
        String name,
        String url,
        BigDecimal targetPrice,
        LocalDateTime createdAt
) {
}
