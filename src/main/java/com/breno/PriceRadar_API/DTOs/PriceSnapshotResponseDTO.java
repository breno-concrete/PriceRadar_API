package com.breno.PriceRadar_API.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Resposta do Price Snapshot")
public record PriceSnapshotResponseDTO(

        @NotNull(message = "O preço atual é obrigatório")
        @Positive(message = "O preço atual deve ser maior que zero")
        @Schema(description = "Preço atual capturado", example = "1299.90")
        BigDecimal currentPrice
) {
}
