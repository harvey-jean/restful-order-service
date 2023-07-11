package com.jean.ordering.product;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@RestController
@RequestMapping(path = "/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public List<ProductDTO> getProductsByNameOrDescription(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
            return productService.getProductBySearchingParams(name, description);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product) {
            return new ResponseEntity(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
            return productService.updateProduct(id, updatedProduct);
    }

    @PatchMapping("/{id}")
    public ProductDTO partialUpdateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
            return productService.partialUpdateProduct(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
            productService.deleteProduct(id);
    }

}
