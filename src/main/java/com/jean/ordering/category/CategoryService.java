package com.jean.ordering.category;

import com.jean.ordering.shared.exceptions.ResourceAlreadyExistsException;
import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryDTOMapper)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(final Long id) {
        return categoryRepository.findById(id)
                .map(categoryDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id[%s] does not exist"
                        .formatted(id)));
    }

    public Category getCategoryProdById(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id[%s] does not exist"
                        .formatted(id)));
    }

    public CategoryDTO getCategoryByName(final String name) {
        return categoryRepository.findByName(name)
                .map(categoryDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name[%s] does not exist"
                        .formatted(name)));
    }

    public List<CategoryDTO> getCategoryByDescriptionKeyWord(final String descriptionKeyWord) {
        final List<CategoryDTO> categoriesList = categoryRepository
                .findByDescription(descriptionKeyWord)
                .stream()
                .map(categoryDTOMapper)
                .collect(Collectors.toList());
        if (categoriesList.isEmpty()) {
            throw new ResourceNotFoundException("Category with description key word [%s] does not exist"
                    .formatted(descriptionKeyWord));
        }

        return categoriesList;
    }

    public List<CategoryDTO> getCategoryBySearchingParams(final String name,
                                                       final String description) {
        if (name != null) {
            return List.of(getCategoryByName(name));
        } else if (description != null) {
            return getCategoryByDescriptionKeyWord(description);
        } else {
            return getAllCategories();
        }
    }

    public CategoryDTO addCategory(final Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new ResourceAlreadyExistsException("Category:[%s] is already exist"
                    .formatted(category.getName()));
        }
        category.setCreatedAt(LocalDateTime.now());
        categoryRepository.save(category);

        return categoryDTOMapper.apply(category);
    }

    public CategoryDTO updateCategory(final Long id, final Category updatedCategory) {
        updatedCategory.setId(id);
        final CategoryDTO existingCategory = getCategoryById(id);
        updatedCategory.setCreatedAt(existingCategory.getCreatedAt());
        categoryRepository.save(updatedCategory);

        return categoryDTOMapper.apply(updatedCategory);
    }

    public CategoryDTO partialUpdateCategory(final Long id, final Category updatedCategory) {
        updatedCategory.setId(id);
        final Optional<Category> existingCategory = categoryRepository.findById(id);

        if(existingCategory.isPresent()) {
            if (updatedCategory.getName() != null) {
                existingCategory.get().setName(updatedCategory.getName());
            }

            if (updatedCategory.getDescription() != null) {
                existingCategory.get().setDescription(updatedCategory.getDescription());
            }

            categoryRepository.save(existingCategory.get());
            return categoryDTOMapper.apply(existingCategory.get());
        }else{
            throw new ResourceNotFoundException("Product with id:[%s] does not exist"
                    .formatted(id));
        }
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
