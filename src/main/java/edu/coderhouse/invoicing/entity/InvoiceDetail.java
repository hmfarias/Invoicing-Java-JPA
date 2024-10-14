package edu.coderhouse.invoicing.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetail {
    /*Constructors  ----------------------------------------------------*/
    public InvoiceDetail(){}

    public InvoiceDetail(Invoice invoice, Product product, int amount, double price) {
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

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private double price;
    /*END Fields -----------------------------------------------------------*/

    /*Geters y Seters ---------------------------------------------------*/

    public int getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(int invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    /*END Geters y Seters ---------------------------------------------------*/
}
