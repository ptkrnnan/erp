package com.project.erp.controller;

import com.project.erp.entity.sales.Sale;
import com.project.erp.entity.sales.SaleStatus;
import com.project.erp.service.sales.SaleItemDTO;
import com.project.erp.service.sales.SaleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public Sale createSale(@RequestParam Long clientId, @RequestBody List<SaleItemDTO> items) {
        return saleService.createSale(clientId, items);
    }

    @GetMapping("/{id}")
    public Sale getSale(@PathVariable Long id) {
        return saleService.getSale(id);
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @PutMapping("/{id}/status")
    public Sale updateStatus(@PathVariable Long id, @RequestParam SaleStatus status) {
        return saleService.updateStatus(id, status);
    }
}
