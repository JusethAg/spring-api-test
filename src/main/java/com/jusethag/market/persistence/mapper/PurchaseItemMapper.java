package com.jusethag.market.persistence.mapper;

import com.jusethag.market.domain.CartItem;
import com.jusethag.market.persistence.entity.PurchasesProduct;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {

    @Mappings({
            @Mapping(source = "id.productId", target = "purchaseId"),
            @Mapping(source = "amount", target = "quantity"),
            @Mapping(source = "status", target = "active")
    })
    CartItem toPurchase(PurchasesProduct purchasesProduct);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "purchase", ignore = true),
            @Mapping(target = "product", ignore = true),
            @Mapping(target = "id.purchaseId", ignore = true)
    })
    PurchasesProduct toPurchasesProduct(CartItem cartItem);
}
