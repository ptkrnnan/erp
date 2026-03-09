package com.project.erp.repository.sales;

import com.project.erp.entity.sales.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
