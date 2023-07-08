package com.jean.restful.category;

import com.jean.restful.product.Product;
import com.jean.restful.shared.ExceptionBodyResponse;
import com.jean.restful.shared.ResourceAlreadyExistsException;
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

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        try{
            Category createCategory = categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createCategory);
        }catch (ResourceAlreadyExistsException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ExceptionBodyResponse(HttpStatus.CONFLICT.value(), exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}
