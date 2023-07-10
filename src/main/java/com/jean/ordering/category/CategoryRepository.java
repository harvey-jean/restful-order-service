package com.jean.ordering.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT cat " +
            "FROM Category cat " +
            "WHERE LOWER(cat.name) = LOWER(?1)")
    Optional<Category> findByName(String name);

    @Query("SELECT cat " +
            "FROM Category cat " +
            "WHERE LOWER(cat.description) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<Category> findByDescription(String description);
}
