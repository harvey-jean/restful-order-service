package com.jean.restful.customer;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Created by Harvey's on 7/8/2023.
 */
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int age;
    private Gender gender;
    private LocalDate createdAt;

}
