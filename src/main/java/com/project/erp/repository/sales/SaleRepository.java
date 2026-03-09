package com.project.erp.repository.sales;

import com.project.erp.entity.sales.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
