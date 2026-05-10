package com.breno.PriceRadar_API.services;

import com.breno.PriceRadar_API.DTOs.AlertResponseDTO;
import java.math.BigDecimal;
import java.util.List;

public interface AlertService {

    AlertResponseDTO createAlert(Long itemId, BigDecimal triggeredPrice);

    List<AlertResponseDTO> getAlertsByItem(Long itemId);

    AlertResponseDTO getAlertById(Long alertId);

    void deleteAlert(Long alertId);
}
