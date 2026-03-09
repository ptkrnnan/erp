package com.project.erp.controller;

import com.project.erp.service.StockService;
import com.project.erp.dto.StockEntryDTO;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {

    private final StockService stockService;
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/stock/entry")
    public void addStock(@RequestBody StockEntryDTO dto) {
        stockService.addStock(dto);
    }
}
