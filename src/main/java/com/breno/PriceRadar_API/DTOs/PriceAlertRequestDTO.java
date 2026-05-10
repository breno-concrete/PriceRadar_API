package com.breno.PriceRadar_API.DTOs;

import java.math.BigDecimal;

public record PriceAlertRequestDTO(
        BigDecimal triggeredPrice
) {
}
