package com.breno.PriceRadar_API.repositories;

import com.breno.PriceRadar_API.models.TrackedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<TrackedItem, Long> {
}
