package com.breno.PriceRadar_API.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Resposta de um alerta de preço")
public record AlertResponseDTO(
        @Schema(description = "Identificador único do alerta", example = "1")
        Long id,

        @Schema(description = "Preço que disparou o alerta", example = "99.90")
        BigDecimal triggeredPrice,

        @Schema(description = "Data e hora que o alerta foi disparado")
        LocalDateTime triggeredAt,

        @Schema(description = "Indica se o alerta foi lido", example = "false")
        boolean isRead
) {}
