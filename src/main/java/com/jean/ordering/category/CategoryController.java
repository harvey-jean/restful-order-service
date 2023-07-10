package com.jean.ordering.category;

import com.jean.ordering.customer.CustomerDTO;
import com.jean.ordering.product.Product;
import com.jean.ordering.shared.exceptions.ExceptionBodyResponse;
import com.jean.ordering.shared.exceptions.ResourceAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@RestController
@RequestMapping(path = "/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getCategoryProducts(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return category.getProducts();
    }

    @GetMapping("/search")
    public Category getCategoryByName(
            @RequestParam(required = false) String name) {
        return categoryService.getCategoryByName(name);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try{
            categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Category[%s] has been created successfullly"
                            .formatted(category.getName()));
        }catch (ResourceAlreadyExistsException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ExceptionBodyResponse(HttpStatus.CONFLICT.value(), exception.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        updatedCategory.setId(id);
        return categoryService.updateCategory(id, updatedCategory);
    }

    @PatchMapping("/{id}")
    public Category partialUpdateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        updatedCategory.setId(id);
        return categoryService.partialUpdateCategory(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}
