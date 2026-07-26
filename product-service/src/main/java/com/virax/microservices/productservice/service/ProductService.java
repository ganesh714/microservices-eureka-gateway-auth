package com.virax.microservices.productservice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.virax.microservices.productservice.dtos.ProductDto;
import com.virax.microservices.productservice.mappers.ProductMapper;
import com.virax.microservices.productservice.model.Product;
import com.virax.microservices.productservice.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductMapper productMapper;
	
	Logger logger = LogManager.getLogger(ProductService.class);
	
	public ProductDto addProduct(ProductDto productDto) {
		Product savedProduct = productRepository.save(productMapper.toProduct(productDto));
		logger.info("Product created with id: " + savedProduct.getId());
		return productMapper.toProductDto(savedProduct);
	}
	
	public List<ProductDto> addMultipleProducts(List<ProductDto> productDtos) {
		List<Product> savedProducts = productRepository.saveAll(productMapper.toProducts(productDtos));
		logger.info("Multiple products created, count: " + savedProducts.size());
		return productMapper.toProductDtos(savedProducts);
	}
	
	public ProductDto getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
		return productMapper.toProductDto(product);
	}
	
	public List<ProductDto> getAllProducts() {
		return productMapper.toProductDtos(productRepository.findAll());
	}
	
	public ProductDto updateProduct(Long id, ProductDto productDto) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
		
		existingProduct.setName(productDto.getName());
		existingProduct.setPrice(productDto.getPrice());
		existingProduct.setDescription(productDto.getDescription());
		
		Product updatedProduct = productRepository.save(existingProduct);
		logger.info("Product updated with id: " + id);
		return productMapper.toProductDto(updatedProduct);
	}
	
	public void deleteProductById(Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
		}
		productRepository.deleteById(id);
		logger.info("Product deleted with id: " + id);
	}
}
