package com.jusethag.market.web.controller;

import com.jusethag.market.domain.Cart;
import com.jusethag.market.domain.service.CartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation("Get all carts on the market")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<List<Cart>> getAll() {
        return new ResponseEntity<>(cartService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    @ApiOperation("Search carts by client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Carts not found")
    })
    public ResponseEntity<List<Cart>> getByClient(
            @ApiParam(value = "Client ID", required = true, example = "4546221")
            @PathVariable("clientId") String clientId) {
        return cartService.getByClient(clientId)
                .map(carts -> new ResponseEntity<>(carts, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation("Create a cart")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cart created")
    })
    public ResponseEntity<Cart> save(@RequestBody Cart cart) {
        return new ResponseEntity<>(cartService.save(cart), HttpStatus.CREATED);
    }

}
