package com.breno.PriceRadar_API.mappers;

import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.models.PriceSnapshot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SnapshotMapper {

    SnapshotHistoryResponseDTO toDTO(PriceSnapshot snapshot);
    
    PriceSnapshot toEntity(SnapshotHistoryResponseDTO dto);
}

