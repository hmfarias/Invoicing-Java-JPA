
package edu.coderhouse.invoicing.dto;

import edu.coderhouse.invoicing.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String description;
    private String code;
    private Double price;

    // Constructor
    public ProductDTO(ProductEntity product) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.code = product.getCode();
        this.price = product.getPrice();
    }
}