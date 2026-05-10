package com.breno.PriceRadar_API.controllers;

import com.breno.PriceRadar_API.DTOs.AlertResponseDTO;
import com.breno.PriceRadar_API.DTOs.PriceAlertRequestDTO;
import com.breno.PriceRadar_API.services.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items/{itemId}/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;


    @PostMapping
    public ResponseEntity<AlertResponseDTO> createAlert(
            @PathVariable Long itemId,
            @RequestBody PriceAlertRequestDTO request) {
        AlertResponseDTO alertDTO = alertService.createAlert(itemId, request.triggeredPrice());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{alertId}")
                .buildAndExpand(alertDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(alertDTO);
    }

    @GetMapping
    public ResponseEntity<List<AlertResponseDTO>> getAlertsByItem(@PathVariable Long itemId) {
        List<AlertResponseDTO> alerts = alertService.getAlertsByItem(itemId);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<AlertResponseDTO> getAlertById(
            @PathVariable Long itemId,
            @PathVariable Long alertId) {
        AlertResponseDTO alert = alertService.getAlertById(alertId);
        return ResponseEntity.ok(alert);
    }

    @DeleteMapping("/{alertId}")
    public ResponseEntity<Void> deleteAlert(
            @PathVariable Long itemId,
            @PathVariable Long alertId) {
        alertService.deleteAlert(alertId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{alertId}/read")
    public ResponseEntity<AlertResponseDTO> markAsRead(
            @PathVariable Long itemId,
            @PathVariable Long alertId) {
        alertService.markAsRead(alertId);
        AlertResponseDTO alert = alertService.getAlertById(alertId);
        return ResponseEntity.ok(alert);
    }
}
