package com.breno.PriceRadar_API.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(description = "Solicitação para criar um alerta de preço")
public record PriceAlertRequestDTO(
        @NotNull(message = "O preço de disparo é obrigatório")
        @Positive(message = "O preço de disparo deve ser maior que zero")
        @Schema(description = "Preço de disparo do alerta", example = "99.90")
        BigDecimal triggeredPrice
) {
}
