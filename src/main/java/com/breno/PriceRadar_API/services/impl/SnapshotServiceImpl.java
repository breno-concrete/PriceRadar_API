package com.breno.PriceRadar_API.services.impl;

import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.mappers.SnapshotMapper;
import com.breno.PriceRadar_API.models.PriceSnapshot;
import com.breno.PriceRadar_API.models.TrackedItem;
import com.breno.PriceRadar_API.repositories.ItemRepository;
import com.breno.PriceRadar_API.repositories.SnapshotRepository;
import com.breno.PriceRadar_API.services.SnapshotService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SnapshotServiceImpl implements SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final ItemRepository itemRepository;
    private final SnapshotMapper snapshotMapper;

    @Override
    @Transactional
    public SnapshotHistoryResponseDTO createSnapshot(Long itemId, SnapshotHistoryResponseDTO request) {
        TrackedItem item = findTrackedItemById(itemId);

        PriceSnapshot snapshot = new PriceSnapshot();
        snapshot.setItem(item);
        snapshot.setCurrentPrice(request.currentPrice());
        snapshot.setTimestamp(LocalDateTime.now());

        snapshot = snapshotRepository.save(snapshot);

        return snapshotMapper.toDTO(snapshot);
    }

    @Override
    @Transactional
    public List<SnapshotHistoryResponseDTO> getSnapshotsByItem(Long itemId) {
        TrackedItem item = findTrackedItemById(itemId);

        return snapshotRepository.findByItemOrderByTimeStamp(item).stream()
                .map(snapshotMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public SnapshotHistoryResponseDTO getSnapshotById(Long snapshotId) {
        PriceSnapshot snapshot = findSnapshotById(snapshotId);
        return snapshotMapper.toDTO(snapshot);
    }

    @Override
    @Transactional
    public void deleteSnapshot(Long snapshotId) {
        PriceSnapshot snapshot = findSnapshotById(snapshotId);
        snapshotRepository.delete(snapshot);
    }

    private TrackedItem findTrackedItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Item com id " + itemId + " não existe."
                ));
    }

    private PriceSnapshot findSnapshotById(Long snapshotId) {
        return snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Snapshot com id " + snapshotId + " não existe."
                ));
    }
}

