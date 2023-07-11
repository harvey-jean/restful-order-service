package com.jean.ordering.category;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by Harvey's on 7/11/2023.
 */
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

}
