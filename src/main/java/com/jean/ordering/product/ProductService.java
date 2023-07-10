package com.jean.ordering.product;

import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Product getProductById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id:[%s] does not exist"
                        .formatted(id)));
    }

    public List<Product> getProductsByName(final String name) {
        List<Product> products = productRepository.findByName(name);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product with name:[%s] does not exist"
                    .formatted(name));
        }

        return products;
    }

    public List<Product> getProductsByDescriptionKeyword(final String descriptionKeyWord) {
        List<Product> products = productRepository.findByDescriptionContaining(descriptionKeyWord);
        if(products.isEmpty()){
            throw new ResourceNotFoundException(
                    "Product with description keyword [%s] does not exist"
                            .formatted(descriptionKeyWord)
            );
        }

        return products;
    }

    public List<Product> getProductsByNameAndDescription(final String name, final String descriptionKeyWord) {
        List<Product> products = productRepository.findByNameAndDescriptionContaining(name, descriptionKeyWord);
        if(products.isEmpty()){
            throw new ResourceNotFoundException(
                    "Product with name:[%s] with description keyword [%s] does not exist"
                            .formatted(name, descriptionKeyWord)
            );
        }

        return products;
    }

    public Product addProduct(final Product product) {
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateProduct(final Long id, final Product updatedProduct) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCreatedAt(existingProduct.getCreatedAt());
        existingProduct.setCategory(updatedProduct.getCategory());

        return productRepository.save(existingProduct);
    }

    public Product partialUpdateProduct(final Long id, final Product updatedProduct) {
        Product existingProduct = getProductById(id);

        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }

        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }

        if (updatedProduct.getCategory() != null) {
            existingProduct.setCategory(updatedProduct.getCategory());
        }

        return productRepository.save(existingProduct);
    }


    public void deleteProduct(final Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product with id:[%s] does not exist"
                    .formatted(id));
        }
    }

}
