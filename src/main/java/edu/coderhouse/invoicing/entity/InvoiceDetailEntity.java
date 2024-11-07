package edu.coderhouse.invoicing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*Constructors, Geters y Seters ---------------------------------------------------*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

/*END Geters y Seters ------------------------------------------------*/

@Entity
@Table(name = "invoice_detail")
@Schema(description = "Represents the details of an invoice, including the product, amount, and price.")
public class InvoiceDetailEntity {
    /*Constructors  ----------------------------------------------------*/
    public InvoiceDetailEntity(InvoiceEntity invoice, ProductEntity product, int amount, double price) {
        this.invoice = invoice;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }
    /*END Constructors  ------------------------------------------------*/

    /*Fields -----------------------------------------------------------*/
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "IDENTITY self-generated", example = "1")
    private Long invoiceDetailId;

    @Column(nullable = false)
    @Schema(description = "The amount of products in the invoice detail", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private int amount;

    @Column(nullable = false)
    @Schema(description = "The price of the product in the invoice detail", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.0")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    @Schema(description = "The invoice to which this detail belongs", example = "InvoiceEntity{id=1}")
    private InvoiceEntity invoice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("invoiceDetails")
    @Schema(description = "The product associated with the invoice detail", example = "ProductEntity{id=1, description=\"Product A\", code=\"P001\"}")
    private ProductEntity product;

    /*END Fields -----------------------------------------------------------*/


}
