package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.entity.ProductEntity;
import edu.coderhouse.invoicing.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    //Constructor
    public ProductController(ProductService service) {
        this.productService = service;
    }

    // PARA TRAER TODOS LOS PRODUCTOS
    @Operation(summary = "Gets all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductEntity.class))),
            @ApiResponse(responseCode = "204", description = "No products found"),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ProductEntity>> getAll() {
        List<ProductEntity> products = productService.getProducts();

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay productos
        }

        return ResponseEntity.ok(products); // 200 OK si se encuentran productos
    }

    // PARA TRAER UN PRODUCTO POR ID
    @Operation(summary = "Gets a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductEntity.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductEntity> getById(@PathVariable long id) {
        Optional<ProductEntity> product = productService.getById(id);

        // Verifico si el producto con ese id existe
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Add a new product", description = "Creates a new product and saves it in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ProductEntity> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product data to be created", required = true, content = @Content(schema = @Schema(implementation = ProductEntity.class)))
            @Valid @RequestBody ProductEntity product) {
        try {
            ProductEntity newProduct = productService.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct); // 201 Created
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    //PARA ACTUALIZAR UN PRODUCTO
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update an existing product", description = "Updates the details of an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductEntity.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ProductEntity> update(
            @PathVariable long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product data to update", required = true, content = @Content(schema = @Schema(implementation = ProductEntity.class)))
            @RequestBody ProductEntity product) {

        Optional<ProductEntity> updatedProduct = productService.update(id, product);

        // Verifico si el producto fue encontrado y actualizado
        return updatedProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //PARA ELIMINAR UN PRODUCTO
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = productService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
