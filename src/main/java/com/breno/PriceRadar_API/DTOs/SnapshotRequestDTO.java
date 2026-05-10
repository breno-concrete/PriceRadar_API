package com.breno.PriceRadar_API.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SnapshotRequestDTO(
        BigDecimal currentPrice
){ }
