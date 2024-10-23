package edu.coderhouse.invoicing.repository;

import edu.coderhouse.invoicing.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
