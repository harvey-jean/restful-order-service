package com.jean.ordering.order;

import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(final Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order id:[%s] does not exist"
                        .formatted(id)));
    }

    public Order addOrder(final Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(final Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Order id:[%id] does not exist"
                    .formatted(id));
        }
    }

}
