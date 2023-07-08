package com.jean.restful.rating;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Harvey's on 7/6/2023.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
