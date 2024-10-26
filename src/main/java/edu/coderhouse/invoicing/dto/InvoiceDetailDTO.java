package edu.coderhouse.invoicing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InvoiceDetailDTO {
    private Long invoiceDetailId;
    private Integer amount;
    private Double price;
    private ProductDTO product;
}
