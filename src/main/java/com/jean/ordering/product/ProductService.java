package com.jean.ordering.product;

import com.jean.ordering.category.Category;
import com.jean.ordering.category.CategoryService;
import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDTOMapper productDTOMapper;
    private final CategoryService categoryService;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productDTOMapper)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(final Long id) {
        return productRepository.findById(id)
                .map(productDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id:[%s] does not exist"
                        .formatted(id)));
    }

    public List<ProductDTO> getProductsByName(final String name) {
        final List<ProductDTO> products = productRepository.findByName(name)
                .stream()
                .map(productDTOMapper)
                .collect(Collectors.toList());

        if(products.isEmpty()){
            throw new ResourceNotFoundException("Product with name:[%s] does not exist"
                    .formatted(name));
        }

        return products;
    }

    public List<ProductDTO> getProductsByDescriptionKeyword(final String descriptionKeyWord) {
        final List<ProductDTO> products = productRepository.findByDescriptionContaining(descriptionKeyWord)
                .stream()
                .map(productDTOMapper)
                .collect(Collectors.toList());

        if(products.isEmpty()){
            throw new ResourceNotFoundException(
                    "Product with description keyword [%s] does not exist"
                            .formatted(descriptionKeyWord));
        }

        return products;
    }

    public List<ProductDTO> getProductsByNameAndDescription(final String name,
                                                         final String descriptionKeyWord) {
        final List<ProductDTO> products = productRepository
                .findByNameAndDescriptionContaining(name, descriptionKeyWord)
                .stream()
                .map(productDTOMapper)
                .collect(Collectors.toList());

        if(products.isEmpty()){
            throw new ResourceNotFoundException(
                    "Product with name:[%s] with description keyword [%s] does not exist"
                            .formatted(name, descriptionKeyWord));
        }

        return products;
    }

    public List<ProductDTO> getProductBySearchingParams(final String name,
                                                        final String descriptionKeyWord) {
        if (name != null && descriptionKeyWord != null) {
            return getProductsByNameAndDescription(name, descriptionKeyWord);
        } else if (name != null) {
            return getProductsByName(name);
        } else if (descriptionKeyWord != null) {
            return getProductsByDescriptionKeyword(descriptionKeyWord);
        } else {
            return getAllProducts();
        }
    }

    public ProductDTO addProduct(final Product product) {
        final Category category = categoryService.getCategoryProdById(product.getCategory().getId());
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);

        return productDTOMapper.apply(product);
    }

    public ProductDTO updateProduct(final Long id, final Product updatedProduct) {
        updatedProduct.setId(id);
        final ProductDTO existingProduct = getProductById(id);
        final Category category = categoryService.getCategoryProdById(updatedProduct.getCategory().getId());
        updatedProduct.setCategory(category);
        updatedProduct.setCreatedAt(existingProduct.getCreatedAt());
        productRepository.save(updatedProduct);

        return productDTOMapper.apply(updatedProduct);
    }

    public ProductDTO partialUpdateProduct(final Long id, final Product updatedProduct) {
        final Optional<Product> existingProduct = productRepository.findById(id);

        if(existingProduct.isPresent()) {
            if (updatedProduct.getName() != null) {
                existingProduct.get().setName(updatedProduct.getName());
            }

            if (updatedProduct.getDescription() != null) {
                existingProduct.get().setDescription(updatedProduct.getDescription());
            }
            productRepository.save(existingProduct.get());

            return productDTOMapper.apply(existingProduct.get());
        }else{
            throw new ResourceNotFoundException("Product with id:[%s] does not exist"
                    .formatted(id));
        }
    }

    @Transactional
    public void deleteProduct(final Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteProductById(id);
        } else {
            throw new ResourceNotFoundException("Product with id:[%s] does not exist"
                    .formatted(id));
        }
    }

}
