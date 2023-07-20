package com.jean.ordering.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jean.ordering.category.Category;
import com.jean.ordering.order.Order;
import com.jean.ordering.rating.Rating;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;

    private String description;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
