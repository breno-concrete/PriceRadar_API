package com.breno.PriceRadar_API.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Resposta de um item rastreado")
public record ItemResponseDTO(
        @Schema(description = "Identificador único do item", example = "1")
        Long id,

        @Schema(description = "Nome do produto", example = "Monitor LG 27 polegadas")
        String name,

        @Schema(description = "URL do produto", example = "https://example.com/product/monitor")
        String url,

        @Schema(description = "Preço alvo para disparar o alerta", example = "1299.90")
        BigDecimal targetPrice,

        @Schema(description = "Data e hora da criação do item")
        LocalDateTime createdAt
) {
}
