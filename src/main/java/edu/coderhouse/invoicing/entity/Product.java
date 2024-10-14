package edu.coderhouse.invoicing.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    /*Constructors  ----------------------------------------------------*/
    public Product(){}

    public Product(String description, String code, int stock, double price) {
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }

    // Constructor completo (cuando quieres inicializar para pruebas)
    public Product(String description, String code, int stock, double price, List<InvoiceDetail> invoiceDetails) {
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
        this.invoiceDetails = invoiceDetails;
    }
    /*END Constructors  ------------------------------------------------*/

    /*Fields -----------------------------------------------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "stock")
    private int stock;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;
    /*END Fields -----------------------------------------------------------*/

    /*Geters y Seters ---------------------------------------------------*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
    /*END Geters y Seters ---------------------------------------------------*/
}
