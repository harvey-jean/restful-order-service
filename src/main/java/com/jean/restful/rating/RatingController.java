package com.jean.restful.rating;

import com.jean.restful.customer.Customer;
import com.jean.restful.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@RestController
@RequestMapping(path = "/api/v1/ratings")
@AllArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id);
    }

    @GetMapping("/{id}/customer")
    public Customer getRatingCustomer(@PathVariable Long id) {
        Rating rating = ratingService.getRatingById(id);
        return rating.getCustomer();
    }

    @GetMapping("/{id}/product")
    public Product getRatingProduct(@PathVariable Long id) {
        Rating rating = ratingService.getRatingById(id);
        return rating.getProduct();
    }

    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        return ratingService.addRating(rating);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
    }

}
