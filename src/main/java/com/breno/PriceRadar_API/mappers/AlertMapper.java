package com.breno.PriceRadar_API.mappers;

import com.breno.PriceRadar_API.DTOs.AlertResponseDTO;
import com.breno.PriceRadar_API.models.PriceAlert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlertMapper {

    @Mapping(source = "timestamp", target = "triggeredAt")
    AlertResponseDTO toDTO(PriceAlert alert);
}

