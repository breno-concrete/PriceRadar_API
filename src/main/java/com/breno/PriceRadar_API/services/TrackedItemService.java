package com.breno.PriceRadar_API.services;

import com.breno.PriceRadar_API.DTOs.ItemResponseDTO;
import com.breno.PriceRadar_API.DTOs.PriceSnapshotResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.DTOs.TrackedItemRequestDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TrackedItemService {


    ItemResponseDTO createTrackedItem(TrackedItemRequestDTO request);

    List<PriceSnapshotResponseDTO> getPriceHistoryForItem(Long id);

    List<ItemResponseDTO> getAllTrackedItems();

    ItemResponseDTO getTrackedItemById(Long id);

    ItemResponseDTO updateTrackedItem(Long id, BigDecimal newTargetPrice);

    void deleteItem(Long id);



}