package com.jean.ordering.product;

import com.jean.ordering.category.Category;
import com.jean.ordering.rating.Rating;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Harvey's on 7/10/2023.
 */
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Long categoryId;

}
