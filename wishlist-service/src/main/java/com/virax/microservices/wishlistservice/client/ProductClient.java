package com.virax.microservices.wishlistservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.virax.microservices.wishlistservice.dtos.ProductDto;

@FeignClient(name = "product-service")
public interface ProductClient {

	@GetMapping("/products/{id}")
	public ProductDto getProductById(@PathVariable("id") Long id);
}
