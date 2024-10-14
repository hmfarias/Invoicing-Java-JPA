package edu.coderhouse.invoicing.repository;

import edu.coderhouse.invoicing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
