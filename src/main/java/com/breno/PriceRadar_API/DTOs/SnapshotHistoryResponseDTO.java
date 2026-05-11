package com.breno.PriceRadar_API.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Resposta do histórico de um snapshot de preço")
public record SnapshotHistoryResponseDTO (
        @Schema(description = "Identificador único do snapshot", example = "1")
        Long id,

        @Schema(description = "Preço capturado no snapshot", example = "1199.90")
        BigDecimal currentPrice,

        @Schema(description = "Data e hora do snapshot")
        LocalDateTime timestamp,

        @Schema(description = "Percentual de variação em relação ao preço anterior", example = "-8.5%")
        String percentageChange
){
}
