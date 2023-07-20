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
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    /*@GetMapping("/{id}/customer")
    public Customer getOrderCustomer(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order.getCustomer();
    }*/

    /*@GetMapping("/{id}/products")
    public List<Product> getOrderProducts(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order.getProducts();
    }*/

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Order order) {

        return new ResponseEntity(orderService.addOrder(order), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrderedProducts(@PathVariable Long id, Order order) {
        return orderService.updateOrderedProducts(id, order);
    }

    /*@PutMapping("/{id}/products/{product_id}")
    public List<Product> updateOrderedProducts(@PathVariable Long id,
                                               @PathVariable Long product_id) {
        Order order = orderService.getOrderById(id);
        return order.getProducts();
    }*/

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}
