package com.project.erp.service;

import com.project.erp.entity.Product;
import com.project.erp.entity.StockMovement;
import com.project.erp.repository.StockMovementRepository;
import com.project.erp.repository.ProductRepository;
import com.project.erp.dto.StockEntryDTO;
import com.project.erp.enums.StockMovementType;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class StockService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    public StockService(StockMovementRepository stockMovementRepository, ProductRepository productRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addStock(StockEntryDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStock(product.getStock() + dto.getQuantity());
        registerMovement(product, dto.getQuantity(), StockMovementType.IN);
    }

    public void registerMovement(Product product, int quantity, StockMovementType type) {
        StockMovement movement = new StockMovement();

        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setType(type);

        stockMovementRepository.save(movement);
    }
}
