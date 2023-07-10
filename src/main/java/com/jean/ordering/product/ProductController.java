package com.jean.ordering.product;

import com.jean.ordering.customer.CustomerDTO;
import com.jean.ordering.rating.Rating;
import com.jean.ordering.shared.exceptions.BadRequestException;
import com.jean.ordering.shared.exceptions.ResourceAlreadyExistsException;
import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import com.jean.ordering.shared.responses.MessageResponse;
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
    public ResponseEntity<?> getProductsByNameOrDescription(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {

        try{
            List<ProductDTO> productDTOS = productService
                    .getProductBySearchingParams(name, description);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productDTOS);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
        }
    }

    /*@GetMapping("/{id}/ratings")
    public List<Rating> getProductRatings(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product.getRatings();
    }*/

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try{
            productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse(HttpStatus.CREATED.value(),
                            "Product [%s] has been created successfullly".formatted(product.getName())));
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            updatedProduct.setId(id);
            ProductDTO productDTOUpdate = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(productDTOUpdate);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {

        try {
            ProductDTO productDTOPartialUpdate = productService.partialUpdateProduct(id, updatedProduct);
            return ResponseEntity.ok(productDTOPartialUpdate);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MessageResponse(HttpStatus.OK.value(),
                            "Product ID [%s] has been deleted successfullly".formatted(id)));
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
        }
    }

}
