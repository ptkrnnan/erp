package com.project.erp.service.sales;

import com.project.erp.entity.Client;
import com.project.erp.entity.Product;
import com.project.erp.entity.sales.Sale;
import com.project.erp.entity.sales.SaleItem;
import com.project.erp.entity.sales.SaleStatus;
import com.project.erp.repository.ClientRepository;
import com.project.erp.repository.ProductRepository;
import com.project.erp.repository.sales.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public SaleService(
            SaleRepository saleRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository
    ) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Sale createSale(Long clientId, List<SaleItemDTO> itemsDTO) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Sale sale = new Sale();
        sale.setClient(client);
        sale.setStatus(SaleStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for (SaleItemDTO dto : itemsDTO) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // verificar estoque
            if (product.getStock() < dto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // diminuir estoque
            product.setStock(product.getStock() - dto.getQuantity());

            SaleItem item = new SaleItem();
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            item.setPrice(product.getPrice());
            item.setSale(sale);

            sale.getItems().add(item);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
        }

        sale.setTotal(total);
        return saleRepository.save(sale);
    }

    public Sale getSale(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Transactional
    public Sale updateStatus(Long id, SaleStatus status) {
        Sale sale = saleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Sale not found"));

        if (sale.getStatus() == SaleStatus.COMPLETED || sale.getStatus() == SaleStatus.CANCELED) {
            throw new RuntimeException("Sale cannot be modified because it is already finalized");
        }

        if (status == SaleStatus.CANCELED) {
            for (SaleItem item: sale.getItems()) {
                Product product = item.getProduct();

                product.setStock(product.getStock() + item.getQuantity());
            }
        }

        sale.setStatus(status);
        return saleRepository.save(sale);
    }
}
