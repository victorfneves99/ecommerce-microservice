package com.systemlab.ecommerce_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systemlab.ecommerce_application.dto.CartItemRequest;
import com.systemlab.ecommerce_application.model.CartItem;
import com.systemlab.ecommerce_application.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // @GetMapping
    // public List<Cart> findAll() {
    // return cartService.findAll();
    // }

    // @GetMapping("/{id}")
    // public Cart findById(@PathVariable Long id) {
    // return cartService.findById(id);
    // }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-Id") String userId,
            @RequestBody CartItemRequest cartItemRequest) {
        if (cartService.saveToCart(userId, cartItemRequest)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.badRequest().body("Product Out of Stock or product not found");
        }
    }

    // @PutMapping("/{id}")
    // public Cart update(@PathVariable Long id, @RequestBody Cart cart) {
    // cart.setId(id);
    // return cartService.save(cart);
    // }

    @GetMapping
    public ResponseEntity<List<CartItem>> findAllItemsByUser(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok().body(cartService.findAllItemsByUser(userId));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-Id") String userId,
            @PathVariable Long productId) {
        boolean removed = cartService.deleteItemFromCart(userId, productId);
        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.badRequest().body("Product not found in cart or removal failed");
        }
    }

}
