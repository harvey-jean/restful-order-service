package com.jean.ordering.order;

import com.jean.ordering.customer.Customer;
import com.jean.ordering.product.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Harvey's on 7/20/2023.
 */
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {

    private Long id;
    private int quantity;
    private BigDecimal price;
    private LocalDate orderDate;
    private Long customerId;
    private List<Long> products;

}
