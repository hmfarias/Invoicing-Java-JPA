package edu.coderhouse.invoicing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/*Constructor, Geters y Seters ---------------------------------------------------*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*End Geters y Seters -----------------------------------------------*/

@Entity
@Table(name = "product")
public class ProductEntity {

    /*Constructors  ----------------------------------------------------*/
    public ProductEntity(String description, String code, int stock, double price) {
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }

    // Constructor completo (cuando quieres inicializar para pruebas)
//    public ProductEntity(String description, String code, int stock, double price, List<InvoiceDetailEntity> invoiceDetails) {
//        this.description = description;
//        this.code = code;
//        this.stock = stock;
//        this.price = price;
//        this.invoiceDetails = invoiceDetails;
//    }
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
    @JsonIgnoreProperties("product")
    //@JsonBackReference
    private List<InvoiceDetailEntity> invoiceDetails;
    /*END Fields -----------------------------------------------------------*/

    /*END Geters y Seters ---------------------------------------------------*/
}
