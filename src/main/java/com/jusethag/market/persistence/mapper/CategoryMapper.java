package com.jusethag.market.persistence.mapper;

import com.jusethag.market.domain.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "description", target = "category"),
            @Mapping(source = "active", target = "status")
    })
    Category toCategory(com.jusethag.market.persistence.entity.Category category);

    @InheritInverseConfiguration
    @Mapping(target = "products", ignore = true)
    com.jusethag.market.persistence.entity.Category toCategory(Category category);
}
