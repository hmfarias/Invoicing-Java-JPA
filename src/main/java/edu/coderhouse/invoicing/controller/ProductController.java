package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.entity.ProductEntity;
import edu.coderhouse.invoicing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    //Constructor
    public ProductController(ProductService service) {
        this.service = service;
    }

    //PARA TRAER TODOS LOS PRODUCTOS
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<ProductEntity>> getAll(){
        Iterable<ProductEntity> products = service.getProducts();
        return ResponseEntity.ok(products);
    }

    //PARA TRAER UN PRODUCTO POR ID
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
        public ResponseEntity<Optional<ProductEntity>> getById(@PathVariable long id){
        Optional<ProductEntity> product = service.getById(id);

        //Verifico si el producto con ese id existe
        if (product.isPresent()){
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA AGREGAR UN NUEVO PRODUCTO
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductEntity> create(@RequestBody ProductEntity product) {
        try {
            ProductEntity newProduct = service.save(product);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //PARA ACTUALIZAR UN PRODUCTO
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductEntity> update(@PathVariable long id, @RequestBody ProductEntity product) {
        Optional<ProductEntity> updatedProduct = service.update(id, product);

        // Verifico si el producto fue encontrado y actualizado
        if (updatedProduct.isPresent()) {
            return ResponseEntity.ok(updatedProduct.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA ELIMINAR UN PRODUCTO
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = service.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
