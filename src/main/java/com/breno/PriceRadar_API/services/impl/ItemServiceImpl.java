package com.breno.PriceRadar_API.services.impl;

import com.breno.PriceRadar_API.DTOs.ItemResponseDTO;
import com.breno.PriceRadar_API.DTOs.PriceSnapshotResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.DTOs.TrackedItemRequestDTO;
import com.breno.PriceRadar_API.exceptions.DuplicateItemException;
import com.breno.PriceRadar_API.exceptions.EntityNotFoundException;
import com.breno.PriceRadar_API.mappers.ItemMapper;
import com.breno.PriceRadar_API.models.TrackedItem;
import com.breno.PriceRadar_API.repositories.ItemRepository;
import com.breno.PriceRadar_API.services.TrackedItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements TrackedItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    //CREATE

    @Override
    @Transactional
    public ItemResponseDTO createTrackedItem(TrackedItemRequestDTO request) {
        if (itemRepository.existsByUrl(request.url())) {
            throw new DuplicateItemException("Um item com esta URL já existe.");
        }

        TrackedItem item = new TrackedItem();
        item.setName(request.name());
        item.setUrl(request.url());
        item.setTargetPrice(request.targetPrice());

        item = itemRepository.save(item);

        return itemMapper.toDTO(item);
    }


    //DELETE

    @Override
    @Transactional
    public void deleteItem(Long id) {
        TrackedItem item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com id " + id + " não existe."));

        itemRepository.delete(item);
    }


    //UPDATE
    @Override
    @Transactional
    public ItemResponseDTO updateTrackedItem(Long id, BigDecimal newTargetPrice){
        TrackedItem item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com id " + id + " não existe."));

        item.setTargetPrice(newTargetPrice);
        item = itemRepository.save(item);

        return itemMapper.toDTO(item);
    }

    @Override
    @Transactional
    public ItemResponseDTO getTrackedItemById(Long id) {
        TrackedItem item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com id " + id + " não existe."));

        return itemMapper.toDTO(item);
    }

    @Override
    @Transactional
    public List<ItemResponseDTO> getAllTrackedItems() {
        List<TrackedItem> items = itemRepository.findAll();

        return items.stream()
                .map(item -> itemMapper.toDTO(item))
                .toList();
    }

    @Override
    @Transactional
    public List<PriceSnapshotResponseDTO> getPriceHistoryForItem(Long id) {
        TrackedItem item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com id " + id + " não existe."));

        return item.getPriceSnapshots().stream()
                .map(snapshot -> new PriceSnapshotResponseDTO(
                        snapshot.getCurrentPrice()
                ))
                .toList();
    }
}