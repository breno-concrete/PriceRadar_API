package com.breno.PriceRadar_API.repositories;

import com.breno.PriceRadar_API.models.PriceSnapshot;
import com.breno.PriceRadar_API.models.TrackedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SnapshotRepository extends JpaRepository<PriceSnapshot, Long> {

    List<PriceSnapshot> findByItemOrderByTimestamp(TrackedItem item);

    // Busca o primeiro snapshot do mesmo item, com data menor que a do snapshot atual, ordenado do mais recente pro mais antigo
    Optional<PriceSnapshot> findTopByItemAndTimestampBeforeOrderByTimestampDesc(TrackedItem item, LocalDateTime timestamp);

    Optional<PriceSnapshot> findTopByItemOrderByTimestampDesc(TrackedItem item);
}
