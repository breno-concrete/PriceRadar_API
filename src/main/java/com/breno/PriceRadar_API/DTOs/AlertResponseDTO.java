package com.breno.PriceRadar_API.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AlertResponseDTO(
        Long id,
        BigDecimal triggeredPrice,
        LocalDateTime triggeredAt,
        boolean isRead
) {}
