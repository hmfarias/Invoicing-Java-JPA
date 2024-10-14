package edu.coderhouse.invoicing.repository;

import edu.coderhouse.invoicing.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}