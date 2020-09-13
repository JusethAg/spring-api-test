package com.jusethag.market.persistence;

import com.jusethag.market.domain.Purchase;
import com.jusethag.market.persistence.crud.PurchaseCrudRepository;
import com.jusethag.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseRepository implements com.jusethag.market.domain.repository.PurchaseRepository {

    @Autowired
    private PurchaseCrudRepository purchaseCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases(
                (List<com.jusethag.market.persistence.entity.Purchase>) purchaseCrudRepository.findAll()
        );
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return purchaseCrudRepository.findByClientId(clientId).map(purchases -> mapper.toPurchases(purchases));
    }

    @Override
    public Purchase save(Purchase purchase) {
        com.jusethag.market.persistence.entity.Purchase entityPurchase = mapper.toPurchase(purchase);
        entityPurchase.getProducts().forEach(product -> product.setPurchase(entityPurchase));

        return mapper.toPurchase(purchaseCrudRepository.save(entityPurchase));
    }
}
