package com.jusethag.market.persistence.mapper;

import com.jusethag.market.domain.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "sellPrice", target = "price"),
            @Mapping(source = "stockAmount", target = "stock"),
            @Mapping(source = "status", target = "active")
    })
    Product toProduct(com.jusethag.market.persistence.entity.Product product);

    List<Product> toProducts(List<com.jusethag.market.persistence.entity.Product> products)

    @InheritInverseConfiguration
    @Mapping(target = "barcode", ignore = true)
    com.jusethag.market.persistence.entity.Product toProduct(Product product);
}
