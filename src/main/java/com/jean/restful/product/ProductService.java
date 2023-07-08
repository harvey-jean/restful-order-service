package com.jean.restful.product;

import com.jean.restful.category.Category;
import com.jean.restful.shared.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id:"+ id + " does not exist"));
    }

    public List<Product> getProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product with name:"+ name + " does not exist");
        }

        return products;
    }

    public List<Product> getProductsByDescriptionKeyword(String descriptionKeyWord) {
        List<Product> products = productRepository.findByDescriptionContaining(descriptionKeyWord);
        if(products.isEmpty()){
            throw new ResourceNotFoundException(
                    "Product with description keyword "+ descriptionKeyWord +" does not exist"
            );
        }

        return products;
    }

    public List<Product> getProductsByNameAndDescription(String name, String descriptionKeyWord) {
        List<Product> products = productRepository.findByNameAndDescriptionContaining(name, descriptionKeyWord);
        if(products.isEmpty()){
            throw new ResourceNotFoundException(
                    "Product with name:"+ name + " with description keyword "+ descriptionKeyWord +" does not exist"
            );
        }

        return products;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setDateManufacture(updatedProduct.getDateManufacture());
        existingProduct.setCategory(updatedProduct.getCategory());

        return productRepository.save(existingProduct);
    }

    public Product partialUpdateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);

        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }

        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }

        if(updatedProduct.getDateManufacture() != null){
            existingProduct.setDateManufacture(updatedProduct.getDateManufacture());
        }

        if (updatedProduct.getCategory() != null) {
            existingProduct.setCategory(updatedProduct.getCategory());
        }

        return productRepository.save(existingProduct);
    }


    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product with id:"+ id + " does not exist");
        }
    }

}
