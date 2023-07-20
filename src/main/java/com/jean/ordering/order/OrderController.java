package com.jean.ordering.order;

import com.jean.ordering.customer.Customer;
import com.jean.ordering.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addProduct(@RequestBody Order order) {

        return new ResponseEntity(orderService.addOrder(order), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}
