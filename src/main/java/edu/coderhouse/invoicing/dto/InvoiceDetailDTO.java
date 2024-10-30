
package edu.coderhouse.invoicing.dto;

import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
import edu.coderhouse.invoicing.entity.ProductEntity;
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

    //  para convertir el DTO a una entidad
    public InvoiceDetailEntity toEntity() {
        InvoiceDetailEntity entity = new InvoiceDetailEntity();
        entity.setInvoiceDetailId(this.invoiceDetailId);
        entity.setAmount(this.amount);
        entity.setPrice(this.price);
        if (this.product != null) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(this.product.getId());
            // Otros atributos de producto pueden ser configurados aquí según sea necesario
            entity.setProduct(productEntity);
        }
        // No se establece `invoice` aquí, ya que se maneja por separado
        return entity;
    }
}