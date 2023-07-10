package com.jean.ordering.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE LOWER(p.name) = LOWER(?1)")
    List<Product> findByName(String name);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE LOWER(p.description) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<Product> findByDescriptionContaining(String descriptionKeyWord);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE LOWER(p.name) = LOWER(?1) AND LOWER(p.description) LIKE LOWER(CONCAT('%',?2,'%'))")
    List<Product> findByNameAndDescriptionContaining(String name, String descriptionKeyWord);

    @Modifying
    @Query(value = "DELETE FROM products WHERE id=:productId", nativeQuery = true)
    void deleteProductById(@Param("productId") Long productId);
}
