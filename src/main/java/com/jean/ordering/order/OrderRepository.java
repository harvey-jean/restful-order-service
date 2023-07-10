package com.jean.ordering.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Harvey's on 7/6/2023.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
