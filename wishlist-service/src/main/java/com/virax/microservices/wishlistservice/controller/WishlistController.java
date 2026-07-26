package com.virax.microservices.wishlistservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virax.microservices.wishlistservice.dtos.WishlistItemDto;
import com.virax.microservices.wishlistservice.service.WishlistService;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wishlist")
public class WishlistController {
	
	@Autowired
	WishlistService wishlistService;
	
	@Operation(
			summary = "Add a product to user's wishlist",
			description = "Calls Product Service via OpenFeign to fetch and cache product details"
	)
	@PostMapping("/add")
	public ResponseEntity<WishlistItemDto> addToWishlist(@RequestParam Long userId, @RequestParam Long productId) {
		return new ResponseEntity<>(wishlistService.addToWishlist(userId, productId), HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Get wishlist items by user ID",
			description = "Returns all wishlist items for a specific user"
	)
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<WishlistItemDto>> getWishlistByUser(@PathVariable Long userId) {
		return new ResponseEntity<>(wishlistService.getWishlistByUserId(userId), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Get all wishlist items",
			description = "Returns all wishlist items in the system"
	)
	@GetMapping
	public ResponseEntity<List<WishlistItemDto>> getAllWishlistItems() {
		return new ResponseEntity<>(wishlistService.getAllWishlistItems(), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete a wishlist item by ID",
			description = "Removes a specific wishlist item"
	)
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteWishlistItem(@PathVariable Long id) {
		wishlistService.deleteWishlistItem(id);
		return new ResponseEntity<>("Wishlist item deleted successfully with id: " + id, HttpStatus.OK);
	}
}
