package com.breno.PriceRadar_API.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TrackedItemRequestDTO(

        @NotBlank(message = "O nome do item é obrigatório")
        String name,

        @NotBlank(message = "A URL do item é obrigatória")
        String url,

        @NotNull(message = "O preço alvo é obrigatório")
        @Positive(message = "O preço alvo deve ser maior que zero")
        BigDecimal targetPrice
) {
}
