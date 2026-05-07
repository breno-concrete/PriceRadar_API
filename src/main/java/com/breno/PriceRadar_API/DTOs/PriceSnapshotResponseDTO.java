package com.breno.PriceRadar_API.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PriceSnapshotResponseDTO(

        @NotNull(message = "O preço atual é obrigatório")
        @Positive(message = "O preço atual deve ser maior que zero")
        BigDecimal currentPrice
) {
}
