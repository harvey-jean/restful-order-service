package com.jean.restful.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
