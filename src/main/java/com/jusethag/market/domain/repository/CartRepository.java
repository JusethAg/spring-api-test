package com.jusethag.market.domain.repository;

import com.jusethag.market.domain.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    List<Cart> getAll();
    Optional<List<Cart>> getByClient(String clientId);
    Cart save(Cart cart);

}
