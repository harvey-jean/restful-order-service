package com.jean.ordering.order;

import com.jean.ordering.product.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Harvey's on 7/20/2023.
 */
@Service
public class OrderDTOMapper implements Function<Order, OrderDTO> {
    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getQuantity(),
                order.getPrice(),
                order.getOrderDate(),
                order.getCustomer().getId(),
                order.getProducts().stream().map(Product::getId).collect(Collectors.toList())
        );
    }
}
