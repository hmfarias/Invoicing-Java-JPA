package edu.coderhouse.invoicing.dto;

import edu.coderhouse.invoicing.entity.InvoiceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceSummaryDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private String clientLastname;

    // Constructor
    public InvoiceSummaryDTO(InvoiceEntity invoice) {
        this.id = invoice.getId();
        this.clientId = invoice.getClient().getId();
        this.clientName = invoice.getClient().getName();
        this.clientLastname = invoice.getClient().getLastName();
    }
}
