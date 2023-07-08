package com.jean.restful.product;

import com.jean.restful.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    List<Product> findByDescriptionContaining(String descriptionKeyWord);

    List<Product> findByNameAndDescriptionContaining(String name, String descriptionKeyWord);
}
