//package edu.coderhouse.invoicing.dto;
//
//import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class InvoiceDetailDTO {
//    private Long invoiceDetailId;
//    private int amount;
//    private Double price;
//    private ProductDTO product; // DTO anidado para el producto
//
//    // Constructor
//    public InvoiceDetailDTO(InvoiceDetailEntity detail) {
//        this.invoiceDetailId = detail.getInvoiceDetailId();
//        this.amount = detail.getAmount();
//        this.price = detail.getPrice();
//        this.product = new ProductDTO(detail.getProduct());
//    }
//}
package edu.coderhouse.invoicing.dto;

import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
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
    private int amount;
    private Double price;
    private ProductDTO product; // DTO anidado para el producto
    private InvoiceSummaryDTO invoice; // DTO de resumen para evitar referencia cíclica

    // Constructor
    public InvoiceDetailDTO(InvoiceDetailEntity detail) {
        this.invoiceDetailId = detail.getInvoiceDetailId();
        this.amount = detail.getAmount();
        this.price = detail.getPrice();
        this.product = new ProductDTO(detail.getProduct());
        //this.invoice = new InvoiceSummaryDTO(detail.getInvoice()); // Asignación al DTO resumen
        // Si la factura está presente, asignarla al DTO de resumen
        if (detail.getInvoice() != null) {
            this.invoice = new InvoiceSummaryDTO(detail.getInvoice());
        }
    }
}