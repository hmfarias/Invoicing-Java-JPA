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
    private ProductRepository repository;

    //Creo el constructor

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    //INSERTAR UN PRODUCTO
    public ProductEntity save(ProductEntity product){
        return repository.save(product);
    }

    //OBTENER TODOS LOS PRODUCTOS
    public List<ProductEntity> getProducts(){
        return repository.findAll();
    }

    //OBTENER UN PRODUCTO POR ID
    //uso Optional porque puede que el producto que busque esté o no esté.
    //si no está, Optional resuelve
    public Optional<ProductEntity> getById(long id){
        return repository.findById(id);
    }

    //ACTUALIZAR UN PRODUCTO
    public Optional<ProductEntity> update(long id, ProductEntity newProductData) {
        return repository.findById(id).map(product -> {
            product.setDescription(newProductData.getDescription());
            product.setCode(newProductData.getCode());
            product.setStock(newProductData.getStock());
            product.setPrice(newProductData.getPrice());
            return repository.save(product);
        });
    }

    //ELIMINAR UN PRODUCTO
    public boolean delete(long id) {
        Optional<ProductEntity> product = repository.findById(id);
        if (product.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
