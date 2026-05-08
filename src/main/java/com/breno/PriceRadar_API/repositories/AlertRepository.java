package com.breno.PriceRadar_API.repositories;

import com.breno.PriceRadar_API.models.PriceAlert;
import com.breno.PriceRadar_API.models.TrackedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<PriceAlert, Long> {

    List<PriceAlert> findByItemOrderByTriggeredAtDesc(TrackedItem item);
}
