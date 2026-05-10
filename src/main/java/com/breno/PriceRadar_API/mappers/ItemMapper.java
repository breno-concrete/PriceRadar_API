package com.breno.PriceRadar_API.mappers;

import com.breno.PriceRadar_API.DTOs.ItemResponseDTO;
import com.breno.PriceRadar_API.DTOs.TrackedItemRequestDTO;
import com.breno.PriceRadar_API.models.TrackedItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {


    ItemResponseDTO toDTO(TrackedItem item);

    TrackedItem toEntity(TrackedItemRequestDTO request);
}
