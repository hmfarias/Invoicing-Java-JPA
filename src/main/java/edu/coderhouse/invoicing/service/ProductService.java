package edu.coderhouse.invoicing.service;

import edu.coderhouse.invoicing.entity.ProductEntity;
import edu.coderhouse.invoicing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired //Indico que la propiedad será inyectada
    private ProductRepository productRepository;

    //Creo el constructor

    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    //INSERTAR UN PRODUCTO
    public ProductEntity save(ProductEntity product){
        return productRepository.save(product);
    }

    //OBTENER TODOS LOS PRODUCTOS
    public List<ProductEntity> getProducts(){
        return productRepository.findAll();
    }

    //OBTENER UN PRODUCTO POR ID
    //uso Optional porque puede que el producto que busque esté o no esté.
    //si no está, Optional resuelve
    public Optional<ProductEntity> getById(long id){
        return productRepository.findById(id);
    }

    //ACTUALIZAR UN PRODUCTO
    public Optional<ProductEntity> update(long id, ProductEntity newProductData) {
        return productRepository.findById(id).map(product -> {
            product.setDescription(newProductData.getDescription());
            product.setCode(newProductData.getCode());
            product.setStock(newProductData.getStock());
            product.setPrice(newProductData.getPrice());
            return productRepository.save(product);
        });
    }

    //ELIMINAR UN PRODUCTO
    public boolean delete(long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //ACTUALIZAR STOCK DE PRODUCTO
    public ProductEntity updateProductStock(ProductEntity product) {
        return productRepository.save(product); // Actualiza el producto en la base de datos
    }

}
