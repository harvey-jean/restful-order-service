package com.jean.restful.customer;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
}
