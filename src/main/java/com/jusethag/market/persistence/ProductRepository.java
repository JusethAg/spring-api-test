package com.jusethag.market.persistence;

import com.jusethag.market.persistence.crud.ProductCrudRepository;
import com.jusethag.market.persistence.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private ProductCrudRepository productCrudRepository;

    public List<Product> getAll() {
        return (List<Product>) this.productCrudRepository.findAll();
    }

    public List<Product> getByCategoryId(int categoryId) {
        return this.productCrudRepository.findByCategoryIdOrderByNameAsc(categoryId);
    }

    public Optional<List<Product>> getScarce(int amount) {
        return this.productCrudRepository.findByStockAmountLessThanAndStatus(amount, true);
    }

    public Optional<Product> getProductById(int productId) {
        return this.productCrudRepository.findById(productId);
    }

    public Product save(Product product) {
        return this.productCrudRepository.save(product);
    }

    public void delete(int productId) {
        this.productCrudRepository.deleteById(productId);
    }
}
