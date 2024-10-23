package edu.coderhouse.invoicing.repository;

import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetailEntity, Long> {
}
