package com.breno.PriceRadar_API.services;

import com.breno.PriceRadar_API.DTOs.ItemResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.DTOs.TrackedItemRequestDTO;

import java.util.List;

public interface TrackedItemService {


    ItemResponseDTO createTrackedItem(TrackedItemRequestDTO request);

    void processNewSnapshot(Long itemId, SnapshotHistoryResponseDTO request);

    List<SnapshotHistoryResponseDTO> getItemHistoryWithVariations(Long itemId);


}