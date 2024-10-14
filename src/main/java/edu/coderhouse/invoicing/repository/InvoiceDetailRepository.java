package edu.coderhouse.invoicing.repository;

import edu.coderhouse.invoicing.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
}
