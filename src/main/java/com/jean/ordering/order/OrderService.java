package com.jean.ordering.order;

import com.jean.ordering.product.Product;
import com.jean.ordering.product.ProductRepository;
import com.jean.ordering.shared.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Harvey's on 7/6/2023.
 */
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDTOMapper orderDTOMapper;

    public List<OrderDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream().map(orderDTOMapper).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(final Long id) {
        return orderRepository.findById(id).map(orderDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Order id:[%s] does not exist"
                        .formatted(id)));
    }

    public String addOrder(final Order order) {
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);

        return "Order created successfully";
    }

    public OrderDTO updateOrderedProducts(final Long id, final Order orderUpdate){
        orderUpdate.setId(id);
        OrderDTO orderExisting = getOrderById(orderUpdate.getId());
        orderUpdate.setOrderDate(orderExisting.getOrderDate());
        orderRepository.save(orderUpdate);

        return orderDTOMapper.apply(orderUpdate);
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
