package edu.coderhouse.invoicing.repository;

import edu.coderhouse.invoicing.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
}