package com.breno.PriceRadar_API.repositories;

import com.breno.PriceRadar_API.models.PriceSnapshot;
import com.breno.PriceRadar_API.models.TrackedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnapshotRepository extends JpaRepository<PriceSnapshot, Long> {

    List<PriceSnapshot> findByItemOrderByTimeStamp(TrackedItem item);
}
