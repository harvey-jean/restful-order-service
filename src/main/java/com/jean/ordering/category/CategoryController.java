package com.jean.ordering.category;

import com.jean.ordering.product.Product;
import com.jean.ordering.shared.exceptions.ResourceAlreadyExistsException;
import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import com.jean.ordering.shared.responses.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try{
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.status(HttpStatus.OK).body(category);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(HttpStatus.NOT_FOUND.value(),
                            "Category with id[%s] does not exist".formatted(id)));
        }
    }

    @GetMapping("/{id}/products")
    public List<Product> getCategoryProducts(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return category.getProducts();
    }

    @GetMapping("/search")
    public List<Category> getCategoryByNameOrDescription(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        return categoryService.getCategoryBySearchingParams(name, description);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try{
            categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse(HttpStatus.CREATED.value(),
                            "Category[%s] has been created successfullly".formatted(category.getName())));
        }catch (ResourceAlreadyExistsException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse(HttpStatus.CONFLICT.value(), exception.getMessage()));
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
