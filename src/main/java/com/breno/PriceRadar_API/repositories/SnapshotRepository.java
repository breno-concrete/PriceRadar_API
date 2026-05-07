package com.breno.PriceRadar_API.repositories;

import com.breno.PriceRadar_API.models.PriceSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotRepository extends JpaRepository<PriceSnapshot, Long> {
}
