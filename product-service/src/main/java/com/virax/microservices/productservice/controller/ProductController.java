package com.virax.microservices.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virax.microservices.productservice.dtos.ProductDto;
import com.virax.microservices.productservice.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Operation(
			summary = "Add a new product",
			description = "Requires fields: name, price, and description"
	)
	@PostMapping
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
		return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Add multiple products at once",
			description = "Accepts a list of ProductDto objects"
	)
	@PostMapping("/batch")
	public ResponseEntity<List<ProductDto>> addMultipleProducts(@RequestBody List<ProductDto> productDtos) {
		return new ResponseEntity<>(productService.addMultipleProducts(productDtos), HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Get a product by ID",
			description = "Returns product details for the given ID"
	)
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Get all products",
			description = "Returns a list of all available products"
	)
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Update an existing product",
			description = "Updates product details for the given ID"
	)
	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
		return new ResponseEntity<>(productService.updateProduct(id, productDto), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete a product by ID",
			description = "Removes the product with the specified ID"
	)
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteProductById(id);
		return new ResponseEntity<>("Product deleted successfully with id: " + id, HttpStatus.OK);
	}
}
