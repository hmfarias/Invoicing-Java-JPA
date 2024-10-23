package edu.coderhouse.invoicing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceDetailId;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private InvoiceEntity invoice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    //@JsonManagedReference
    @JsonIgnoreProperties("invoiceDetails")
    private ProductEntity product;
    /*END Fields -----------------------------------------------------------*/


}
