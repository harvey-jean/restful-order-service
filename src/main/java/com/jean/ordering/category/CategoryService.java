package com.jean.ordering.category;

import com.jean.ordering.shared.exceptions.ResourceAlreadyExistsException;
import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id[%s] does not exist"
                        .formatted(id)));
    }

    public Category getCategoryByName(final String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name[%s] does not exist"
                        .formatted(name)));
    }

    public void addCategory(final Category category) {
        if(categoryRepository.findByName(category.getName()) != null){
            throw new ResourceAlreadyExistsException("Category:[%s] is already exist"
                    .formatted(category.getName()));
        }
        category.setCreatedAt(LocalDateTime.now());
        categoryRepository.save(category);

    }

    public Category updateCategory(final Long id, final Category updatedCategory) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setCreatedAt(existingCategory.getCreatedAt());

        return categoryRepository.save(existingCategory);
    }

    public Category partialUpdateCategory(final Long id, final Category updatedCategory) {
        Category existingCategory = getCategoryById(id);

        if (updatedCategory.getName() != null) {
            existingCategory.setName(updatedCategory.getName());
        }

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(final Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Category with id:[%s] does not exist"
                    .formatted(id));
        }
    }

}
