package com.breno.PriceRadar_API.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(description = "Solicitação para criar um novo item rastreado")
public record TrackedItemRequestDTO(

        @NotBlank(message = "O nome do item é obrigatório")
        @Schema(description = "Nome do produto a ser rastreado", example = "Monitor LG 27 polegadas")
        String name,

        @NotBlank(message = "A URL do item é obrigatória")
        @Schema(description = "URL do produto", example = "https://example.com/product/monitor")
        String url,

        @NotNull(message = "O preço alvo é obrigatório")
        @Positive(message = "O preço alvo deve ser maior que zero")
        @Schema(description = "Preço alvo para disparar o alerta", example = "1299.90")
        BigDecimal targetPrice
) {
}
