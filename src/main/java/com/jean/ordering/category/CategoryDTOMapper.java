package com.jean.ordering.category;

import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Created by Harvey's on 7/11/2023.
 */
@Service
public class CategoryDTOMapper implements Function<Category, CategoryDTO> {
    @Override
    public CategoryDTO apply(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt()
        );
    }
}
