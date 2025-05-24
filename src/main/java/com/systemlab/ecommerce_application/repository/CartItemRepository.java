package com.systemlab.ecommerce_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systemlab.ecommerce_application.model.CartItem;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem, Long> {

}
