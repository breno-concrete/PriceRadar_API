package com.breno.PriceRadar_API.services.impl;

import com.breno.PriceRadar_API.DTOs.ItemResponseDTO;
import com.breno.PriceRadar_API.DTOs.TrackedItemRequestDTO;
import com.breno.PriceRadar_API.models.TrackedItem;
import com.breno.PriceRadar_API.repositories.AlertRepository;
import com.breno.PriceRadar_API.repositories.ItemRepository;
import com.breno.PriceRadar_API.repositories.SnapshotRepository;
import com.breno.PriceRadar_API.services.TrackedItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements TrackedItemService {

    private final ItemRepository itemRepository;
    private final SnapshotRepository snapshotRepository;
    private final AlertRepository alertRepository;

    @Override
    @Transactional
    public ItemResponseDTO createTrackedItem(TrackedItemRequestDTO request) throws IllegalArgumentException {
        if (itemRepository.existsByUrl(request.url())) {
            throw new IllegalArgumentException("An item with the same URL already exists.");

        TrackedItem item = new TrackedItem();
        item.setName(request.name());
        item.setUrl(request.url());
        item.setTargetPrice(request.targetPrice());

        item = itemRepository.save(item);
        return new ItemResponseDTO(item.getId(), item.getName(), item.getUrl(), item.getTargetPrice(), item.getCreatedAt());
    }

    @Override
    @Transactional
    public TrackedItem findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));
    }

}
