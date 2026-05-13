package com.breno.PriceRadar_API.mappers;

import com.breno.PriceRadar_API.DTOs.AlertResponseDTO;
import com.breno.PriceRadar_API.models.PriceAlert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlertMapper {

    @Mapping(source = "timestamp", target = "triggeredAt")
    @Mapping(source = "read", target = "isRead") // Liga o 'read' da entidade ao 'isRead' do DTO
    AlertResponseDTO toDTO(PriceAlert alert);

}