package com.breno.PriceRadar_API.services;

import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotRequestDTO;

import java.util.List;

public interface SnapshotService {

    SnapshotHistoryResponseDTO createSnapshot(Long itemId, SnapshotRequestDTO request);

    List<SnapshotHistoryResponseDTO> getSnapshotsByItem(Long itemId);

    SnapshotHistoryResponseDTO getSnapshotById(Long snapshotId);

    void deleteSnapshot(Long snapshotId);
}
