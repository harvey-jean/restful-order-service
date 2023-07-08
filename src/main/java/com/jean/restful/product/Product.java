package com.jean.restful.product;

import com.jean.restful.category.Category;
import com.jean.restful.rating.Rating;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    private String name;

    private String description;

    private LocalDate dateManufacture;

    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
