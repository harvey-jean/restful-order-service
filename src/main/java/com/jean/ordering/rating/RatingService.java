package com.jean.ordering.rating;

import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating getRatingById(final Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating id:[%s] does not exist"
                        .formatted(id)));
    }

    public Rating addRating(final Rating rating) {
        return ratingRepository.save(rating);
    }

    public void deleteRating(final Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Rating id:[%s] does not exist"
                    .formatted(id));
        }
    }

}
