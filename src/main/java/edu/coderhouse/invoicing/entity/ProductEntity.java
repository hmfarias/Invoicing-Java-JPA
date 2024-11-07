package edu.coderhouse.invoicing.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Represents a product in the system, including description, code, stock, price, and associated invoice details.")
public class ProductEntity {

    /*Constructors  ----------------------------------------------------*/
    public ProductEntity(String description, String code, int stock, double price) {
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }


    /*Fields -----------------------------------------------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "IDENTITY self-generated", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long id;

    @Column(name = "description", length = 150)
    @Schema(description = "The description of the product", example = "Product A")
    private String description;

    @Column(name = "code", length = 50)
    @Schema(description = "The product code", example = "P001")
    private String code;

    @Column(name = "stock")
    @Schema(description = "The available stock of the product", example = "100")
    private int stock;

    @Column(name = "price")
    @Schema(description = "The price of the product", example = "50.0")
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "List of invoice details associated with this product", example = "[InvoiceDetailEntity{invoiceDetailId=1, amount=5, price=50.0}, ...]")
    private List<InvoiceDetailEntity> invoiceDetails;
    /*END Fields -----------------------------------------------------------*/

    /*END Geters y Seters ---------------------------------------------------*/
}
