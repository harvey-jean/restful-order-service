package com.jean.ordering.customer;

import lombok.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

}
