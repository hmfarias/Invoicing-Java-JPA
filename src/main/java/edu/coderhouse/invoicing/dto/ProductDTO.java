package edu.coderhouse.invoicing.dto;

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
    private Integer stock;
    private Double price;
}