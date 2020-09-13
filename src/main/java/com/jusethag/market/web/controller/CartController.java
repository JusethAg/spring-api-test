package com.jusethag.market.web.controller;

import com.jusethag.market.domain.Cart;
import com.jusethag.market.domain.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getAll() {
        return new ResponseEntity<>(cartService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<Cart>> getByClient(@PathVariable("clientId") String clientId) {
        return cartService.getByClient(clientId)
                .map(carts -> new ResponseEntity<>(carts, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Cart> save(@RequestBody Cart cart) {
        return new ResponseEntity<>(cartService.save(cart), HttpStatus.CREATED);
    }

}
