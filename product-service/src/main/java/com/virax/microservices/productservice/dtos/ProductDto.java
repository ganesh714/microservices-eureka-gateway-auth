package com.virax.microservices.productservice.dtos;

import lombok.Data;

@Data
public class ProductDto {
	private Long id;
	private String name;
	private Double price;
	private String description;
}
