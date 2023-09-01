package com.barreto.stockmanagement.infra.DTOs.Mappers;

import com.barreto.stockmanagement.domains.Product;
import com.barreto.stockmanagement.infra.DTOs.ProductPostRequestBody;
import com.barreto.stockmanagement.infra.DTOs.ProductPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    public abstract Product toProduct(ProductPostRequestBody productPostRequestBody);
    public abstract Product toProduct(ProductPutRequestBody productPutRequestBody);
}
