package edu.coderhouse.invoicing.dto;

import edu.coderhouse.invoicing.entity.InvoiceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InvoiceDTO {
    private Long id;
    private LocalDateTime createdAt;
    private Double total;
    private ClientDTO client; // DTO anidado para el cliente

    // Constructor
    public InvoiceDTO(InvoiceEntity invoice) {
        this.id = invoice.getId();
        this.createdAt = invoice.getCreatedAt();
        this.total = invoice.getTotal();
        this.client = new ClientDTO(invoice.getClient()); // Solo incluye datos necesarios del cliente
    }

}
