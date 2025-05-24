package com.systemlab.ecommerce_application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.systemlab.ecommerce_application.dto.CartItemRequest;
import com.systemlab.ecommerce_application.model.CartItem;
import com.systemlab.ecommerce_application.repository.CartItemRepository;
import com.systemlab.ecommerce_application.repository.ProductRepository;
import com.systemlab.ecommerce_application.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public boolean saveToCart(String userId, CartItemRequest cartItemRequest) {

        // look for product

        var productOptional = productRepository.findById(cartItemRequest.getProductId());

        if (productOptional.isEmpty())
            return false;

        var product = productOptional.get();

        if (product.getStockQuantity() < cartItemRequest.getQuantity())
            return false;

        var userOptional = userRepository.findById(Long.valueOf(userId));

        if (userOptional.isEmpty())
            return false;

        var user = userOptional.get();

        var existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            var cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice());
            cartItemRepository.save(cartItem);
        }

        return true;
    }

}
