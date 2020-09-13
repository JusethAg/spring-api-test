package com.jusethag.market.persistence;

import com.jusethag.market.domain.Cart;
import com.jusethag.market.domain.repository.CartRepository;
import com.jusethag.market.persistence.crud.PurchaseCrudRepository;
import com.jusethag.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseRepository implements CartRepository {

    @Autowired
    private PurchaseCrudRepository purchaseCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Cart> getAll() {
        return mapper.toPurchases(
                (List<com.jusethag.market.persistence.entity.Purchase>) purchaseCrudRepository.findAll()
        );
    }

    @Override
    public Optional<List<Cart>> getByClient(String clientId) {
        return purchaseCrudRepository.findByClientId(clientId).map(purchases -> mapper.toPurchases(purchases));
    }

    @Override
    public Cart save(Cart cart) {
        com.jusethag.market.persistence.entity.Purchase entityPurchase = mapper.toPurchase(cart);
        entityPurchase.getProducts().forEach(product -> product.setPurchase(entityPurchase));

        return mapper.toPurchase(purchaseCrudRepository.save(entityPurchase));
    }
}
