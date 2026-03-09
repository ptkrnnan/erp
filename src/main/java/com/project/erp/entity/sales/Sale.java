package com.project.erp.entity.sales;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.erp.entity.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SaleItem> items = new ArrayList<>();

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    private LocalDateTime date = LocalDateTime.now();
}
