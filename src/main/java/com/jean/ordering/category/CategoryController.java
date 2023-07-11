package com.jean.ordering.category;

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
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
            return categoryService.getCategoryById(id);
    }

    @GetMapping("/search")
    public List<CategoryDTO> getCategoryByNameOrDescription(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        return categoryService.getCategoryBySearchingParams(name, description);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody Category category) {
        return new ResponseEntity(categoryService.addCategory(category),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory);
    }

    @PatchMapping("/{id}")
    public CategoryDTO partialUpdateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryService.partialUpdateCategory(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}
