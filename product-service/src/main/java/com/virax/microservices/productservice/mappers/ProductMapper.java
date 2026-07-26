package com.virax.microservices.productservice.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.virax.microservices.productservice.dtos.ProductDto;
import com.virax.microservices.productservice.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	public ProductDto toProductDto(Product product);
	
	public Product toProduct(ProductDto productDto);
	
	public List<Product> toProducts(List<ProductDto> productDtos);
	
	public List<ProductDto> toProductDtos(List<Product> products);
}
