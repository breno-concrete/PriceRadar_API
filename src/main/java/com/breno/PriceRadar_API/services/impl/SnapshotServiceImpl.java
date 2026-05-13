package com.breno.PriceRadar_API.services.impl;

import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotRequestDTO;
import com.breno.PriceRadar_API.exceptions.EntityNotFoundException;
import com.breno.PriceRadar_API.mappers.SnapshotMapper;
import com.breno.PriceRadar_API.models.PriceSnapshot;
import com.breno.PriceRadar_API.models.TrackedItem;
import com.breno.PriceRadar_API.repositories.ItemRepository;
import com.breno.PriceRadar_API.repositories.SnapshotRepository;
import com.breno.PriceRadar_API.services.AlertService;
import com.breno.PriceRadar_API.services.SnapshotService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SnapshotServiceImpl implements SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final ItemRepository itemRepository;
    private final SnapshotMapper snapshotMapper;
    private final AlertService alertService;

    @Override
    @Transactional
    public SnapshotHistoryResponseDTO createSnapshot(Long itemId, SnapshotRequestDTO request) {
        TrackedItem item = findTrackedItemById(itemId);

        // 1. Busca o preço anterior ANTES de salvar o novo
        Optional<PriceSnapshot> previousSnapshot = snapshotRepository.findTopByItemOrderByTimestampDesc(item);

        // 2. Prepara e salva o novo snapshot
        PriceSnapshot snapshot = new PriceSnapshot();
        snapshot.setItem(item);
        snapshot.setCurrentPrice(request.currentPrice());
        snapshot.setTimestamp(LocalDateTime.now());

        snapshot = snapshotRepository.save(snapshot);

        // 3. Cria alerta automaticamente se o preço atual for menor ou igual ao targetPrice
        if (item.getTargetPrice().compareTo(snapshot.getCurrentPrice()) >= 0) {
            try {
                alertService.createAlert(itemId, snapshot.getCurrentPrice());
                log.info("Alerta criado automaticamente para item {}", itemId);
            } catch (Exception e) {
                log.error("Erro ao criar alerta automático para item {}: {}", itemId, e.getMessage(), e);
                // Não interrompe o fluxo - o snapshot será criado mesmo se o alerta falhar
            }
        }

        // 4. Calcula a variação percentual
        String percentageChange = "N/A";
        if (previousSnapshot.isPresent()) {
            percentageChange = calculatePercentageChange(
                    snapshot.getCurrentPrice(),
                    previousSnapshot.get().getCurrentPrice()
            );
        }

        // 5. Passa os dois argumentos para o mapper
        return snapshotMapper.toDTO(snapshot, percentageChange);
    }

    @Override
    @Transactional // Melhoria de performance para leituras
    public List<SnapshotHistoryResponseDTO> getSnapshotsByItem(Long itemId) {
        TrackedItem item = findTrackedItemById(itemId);

        // Lista já vem ordenada do mais antigo para o mais novo
        List<PriceSnapshot> snapshots = snapshotRepository.findByItemOrderByTimestamp(item);
        List<SnapshotHistoryResponseDTO> dtos = new ArrayList<>();

        for (int i = 0; i < snapshots.size(); i++) {
            PriceSnapshot currentSnapshot = snapshots.get(i);
            String percentageChange = "N/A";

            // Se não for o primeiro elemento, pega o preço do elemento anterior da própria lista
            if (i > 0) {
                BigDecimal previousPrice = snapshots.get(i - 1).getCurrentPrice();
                percentageChange = calculatePercentageChange(currentSnapshot.getCurrentPrice(), previousPrice);
            }

            dtos.add(snapshotMapper.toDTO(currentSnapshot, percentageChange));
        }

        return dtos;
    }

    @Override
    @Transactional
    public SnapshotHistoryResponseDTO getSnapshotById(Long snapshotId) {
        PriceSnapshot snapshot = findSnapshotById(snapshotId);

        String percentageChange = "N/A";

        // Busca o snapshot imediatamente anterior a este
        Optional<PriceSnapshot> previousSnapshot = snapshotRepository
                .findTopByItemAndTimestampBeforeOrderByTimestampDesc(snapshot.getItem(), snapshot.getTimestamp());

        if (previousSnapshot.isPresent()) {
            percentageChange = calculatePercentageChange(
                    snapshot.getCurrentPrice(),
                    previousSnapshot.get().getCurrentPrice()
            );
        }

        // Passa o snapshot e a variação calculada para o Mapper
        return snapshotMapper.toDTO(snapshot, percentageChange);
    }

    @Override
    @Transactional
    public void deleteSnapshot(Long snapshotId) {
        PriceSnapshot snapshot = findSnapshotById(snapshotId);
        snapshotRepository.delete(snapshot);
    }

    private TrackedItem findTrackedItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Item com id " + itemId + " não existe."
                ));
    }

    private PriceSnapshot findSnapshotById(Long snapshotId) {
        return snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Snapshot com id " + snapshotId + " não existe."
                ));
    }

    private String calculatePercentageChange(BigDecimal currentPrice, BigDecimal previousPrice) {
        if (previousPrice.compareTo(BigDecimal.ZERO) == 0) {
            return "N/A";
        }

        BigDecimal difference = currentPrice.subtract(previousPrice);
        BigDecimal percentage = difference
                .divide(previousPrice, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);

        String sign = percentage.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "";
        return sign + percentage + "%";
    }
}

