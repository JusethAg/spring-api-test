package com.jusethag.market.domain.service;

import com.jusethag.market.domain.Cart;
import com.jusethag.market.domain.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAll(){
        return cartRepository.getAll();
    }

    public Optional<List<Cart>> getByClient(String clientId) {
        return cartRepository.getByClient(clientId);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
}
