package com.breno.PriceRadar_API.mappers;

import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.models.PriceSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SnapshotMapper {

    // O MapStruct vai usar o parâmetro percentageChange para preencher o DTO
    @Mapping(target = "percentageChange", source = "percentageChange")
    SnapshotHistoryResponseDTO toDTO(PriceSnapshot snapshot, String percentageChange);

    PriceSnapshot toEntity(SnapshotHistoryResponseDTO dto);
}