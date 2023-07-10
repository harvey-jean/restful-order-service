package com.jean.ordering.product;

import com.jean.ordering.rating.Rating;
import lombok.AllArgsConstructor;
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
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/{name}")
    public List<Product> getProductByName(@PathVariable String name) {
        return productService.getProductsByName(name);
    }

    @GetMapping("/search")
    public List<Product> getProductsByNameAndDescription(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String descriptionKeyWord) {
        if (name != null && descriptionKeyWord != null) {
            return productService.getProductsByNameAndDescription(name, descriptionKeyWord);
        } else if (name != null) {
            return productService.getProductsByName(name);
        } else if (descriptionKeyWord != null) {
            return productService.getProductsByDescriptionKeyword(descriptionKeyWord);
        } else {
            return productService.getAllProducts();
        }
    }

    @GetMapping("/{id}/ratings")
    public List<Rating> getProductRatings(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product.getRatings();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        updatedProduct.setId(id);
        return productService.updateProduct(id, updatedProduct);
    }

    @PatchMapping("/{id}")
    public Product partialUpdateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        updatedProduct.setId(id);
        return productService.partialUpdateProduct(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
