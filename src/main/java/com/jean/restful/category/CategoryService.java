package com.jean.restful.category;

import com.jean.restful.shared.ResourceAlreadyExistsException;
import com.jean.restful.shared.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id:"+id + " does not exist"));
    }

    public Category addCategory(Category category) {
        if(categoryRepository.findByName(category.getName()) != null){
            throw new ResourceAlreadyExistsException("Category: "+ category.getName() +" is already exist");
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Category with id:"+id + " does not exist");
        }
    }

}
