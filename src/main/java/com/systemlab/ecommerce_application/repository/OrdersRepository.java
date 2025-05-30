package com.systemlab.ecommerce_application.repository;

import com.systemlab.ecommerce_application.model.OderStatus;
import com.systemlab.ecommerce_application.model.Order;
import com.systemlab.ecommerce_application.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(OderStatus status);

}