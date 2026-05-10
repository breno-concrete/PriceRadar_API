package com.breno.PriceRadar_API.controllers;

import com.breno.PriceRadar_API.DTOs.ItemResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotRequestDTO;
import com.breno.PriceRadar_API.DTOs.TrackedItemRequestDTO;
import com.breno.PriceRadar_API.services.SnapshotService;
import com.breno.PriceRadar_API.services.TrackedItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final TrackedItemService trackedItemService;
    private final SnapshotService snapshotService;

    @PostMapping
    public ResponseEntity<ItemResponseDTO> create(@Valid @RequestBody TrackedItemRequestDTO request) {
        ItemResponseDTO itemResponseDTO = trackedItemService.createTrackedItem(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itemResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(itemResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(trackedItemService.getAllTrackedItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(trackedItemService.getTrackedItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> update(@PathVariable Long id, String targetPrice) {
        ItemResponseDTO updatedItem = trackedItemService.updateTrackedItem(id, new java.math.BigDecimal(targetPrice));
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trackedItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/snapshots")
    public ResponseEntity<SnapshotHistoryResponseDTO> addPriceSnapshot(@PathVariable Long id, @RequestBody SnapshotRequestDTO request) {
        SnapshotHistoryResponseDTO snapshotResponseDTO = snapshotService.createSnapshot(id, request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{snapshotId}")
                .buildAndExpand(snapshotResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(snapshotResponseDTO);
    }

    @GetMapping("/{id}/snapshots")
    public ResponseEntity<List<SnapshotHistoryResponseDTO>> getSnapshots(@PathVariable Long id) {
        List<SnapshotHistoryResponseDTO> snapshots = snapshotService.getSnapshotsByItem(id);
        return ResponseEntity.ok(snapshots);
    }

    @GetMapping("/{itemId}/snapshots/{snapshotId}")
    public ResponseEntity<SnapshotHistoryResponseDTO> getSnapshotById(@PathVariable Long itemId, @PathVariable Long snapshotId) {
        SnapshotHistoryResponseDTO snapshot = snapshotService.getSnapshotById(snapshotId);
        return ResponseEntity.ok(snapshot);
    }
}
