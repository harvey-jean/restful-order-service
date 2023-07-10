package com.jean.ordering.order;

import com.jean.ordering.customer.Customer;
import com.jean.ordering.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Harvey's on 7/6/2023.
 */
@RestController
@RequestMapping(path = "/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/{id}/customer")
    public Customer getOrderCustomer(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order.getCustomer();
    }

    @GetMapping("/{id}/products")
    public List<Product> getOrderProducts(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order.getProducts();
    }

    @PostMapping
    public Order addProduct(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}
