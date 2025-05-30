package com.systemlab.ecommerce_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systemlab.ecommerce_application.model.CartItem;
import com.systemlab.ecommerce_application.model.Product;
import com.systemlab.ecommerce_application.model.User;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);

}
