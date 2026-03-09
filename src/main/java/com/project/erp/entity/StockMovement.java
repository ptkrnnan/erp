package com.project.erp.entity;

import com.project.erp.enums.StockMovementType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private StockMovementType type;

    private LocalDateTime date = LocalDateTime.now();
}
