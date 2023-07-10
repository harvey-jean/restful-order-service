package com.jean.ordering.product;

import com.jean.ordering.customer.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Created by Harvey's on 7/10/2023.
 */
@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getCategory().getId()
        );
    }
}
