package com.jusethag.market.persistence;

import com.jusethag.market.domain.Product;
import com.jusethag.market.persistence.crud.ProductCrudRepository;
import com.jusethag.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository implements com.jusethag.market.domain.repository.ProductRepository {

    @Autowired
    private ProductCrudRepository productCrudRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll() {
        List<com.jusethag.market.persistence.entity.Product> products =
                (List<com.jusethag.market.persistence.entity.Product>) this.productCrudRepository.findAll();
        return mapper.toProducts(products);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<com.jusethag.market.persistence.entity.Product> products =
                        this.productCrudRepository.findByCategoryIdOrderByNameAsc(categoryId);

        return Optional.of(mapper.toProducts(products));
    }

    @Override
    public Optional<List<Product>> getScarceProducts(int quantity) {
        Optional<List<com.jusethag.market.persistence.entity.Product>> products =
                this.productCrudRepository.findByStockAmountLessThanAndStatus(quantity, true);

        return products.map(prods -> mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return this.productCrudRepository.findById(productId)
                .map(product -> mapper.toProduct(product));
    }

    @Override
    public Product save(Product product) {
        com.jusethag.market.persistence.entity.Product entityProduct =
                mapper.toProduct(product);
        return mapper.toProduct(this.productCrudRepository.save(entityProduct));
    }

    public void delete(int productId) {
        this.productCrudRepository.deleteById(productId);
    }
}
