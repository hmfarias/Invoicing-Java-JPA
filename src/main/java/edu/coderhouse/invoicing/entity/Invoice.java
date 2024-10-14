package edu.coderhouse.invoicing.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {
    /*Constructors  ----------------------------------------------------*/
    public Invoice(){}

    public Invoice(Client client, LocalDateTime createdAt, double total) {
        this.client = client;
        this.createdAt = createdAt;
        this.total = total;
    }

    public Invoice(Client client, LocalDateTime createdAt, double total, List<InvoiceDetail> invoiceDetails) {
        this.client = client;
        this.createdAt = createdAt;
        this.total = total;
        this.invoiceDetails = invoiceDetails;
    }
    /*END Constructors  ------------------------------------------------*/

    /*Fields -----------------------------------------------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "total")
    private double total;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;
    /*END Fields -----------------------------------------------------------*/

    /*Geters y Seters ---------------------------------------------------*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
    /*END Geters y Seters ---------------------------------------------------*/



}
