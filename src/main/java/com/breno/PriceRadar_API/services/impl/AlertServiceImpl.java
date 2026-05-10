package com.breno.PriceRadar_API.services.impl;

import com.breno.PriceRadar_API.DTOs.AlertResponseDTO;
import com.breno.PriceRadar_API.mappers.AlertMapper;
import com.breno.PriceRadar_API.models.PriceAlert;
import com.breno.PriceRadar_API.models.TrackedItem;
import com.breno.PriceRadar_API.repositories.AlertRepository;
import com.breno.PriceRadar_API.repositories.ItemRepository;
import com.breno.PriceRadar_API.services.AlertService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final ItemRepository itemRepository;
    private final AlertMapper alertMapper;

    @Override
    @Transactional
    public AlertResponseDTO createAlert(Long itemId, BigDecimal triggeredPrice) {
        validatePriceNotNull(triggeredPrice);
        TrackedItem item = findTrackedItemById(itemId);

        PriceAlert alert = new PriceAlert();
        alert.setItem(item);
        alert.setTriggeredPrice(triggeredPrice);
        alert.setTimestamp(LocalDateTime.now());

        alert = alertRepository.save(alert);

        return alertMapper.toDTO(alert);
    }

    @Override
    @Transactional
    public List<AlertResponseDTO> getAlertsByItem(Long itemId) {
        TrackedItem item = findTrackedItemById(itemId);

        return alertRepository.findByItemOrderByTriggeredAtDesc(item).stream()
                .map(alertMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public AlertResponseDTO getAlertById(Long alertId) {
        PriceAlert alert = findAlertById(alertId);
        return alertMapper.toDTO(alert);
    }

    @Override
    @Transactional
    public void deleteAlert(Long alertId) {
        PriceAlert alert = findAlertById(alertId);
        alertRepository.delete(alert);
    }

    private TrackedItem findTrackedItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Item com id " + itemId + " não existe."
                ));
    }

    private PriceAlert findAlertById(Long alertId) {
        return alertRepository.findById(alertId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Alerta com id " + alertId + " não existe."
                ));
    }

    private void validatePriceNotNull(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("O preço é obrigatório.");
        }
    }
}

