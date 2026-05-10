package com.breno.PriceRadar_API.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price_snapshots")
public class PriceSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tracked_item_id", nullable = false)
    private TrackedItem item;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal currentPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();


}
