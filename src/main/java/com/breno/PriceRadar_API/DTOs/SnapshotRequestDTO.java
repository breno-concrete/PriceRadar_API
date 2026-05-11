package com.breno.PriceRadar_API.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(description = "Solicitação para criar um snapshot de preço")
public record SnapshotRequestDTO(
        @NotNull(message = "O preço atual é obrigatório")
        @Positive(message = "O preço atual deve ser maior que zero")
        @Schema(description = "Preço atual do produto", example = "1199.90")
        BigDecimal currentPrice
){ }
