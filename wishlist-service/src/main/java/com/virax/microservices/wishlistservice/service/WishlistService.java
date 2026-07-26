package com.virax.microservices.wishlistservice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.virax.microservices.wishlistservice.client.ProductClient;
import com.virax.microservices.wishlistservice.dtos.ProductDto;
import com.virax.microservices.wishlistservice.dtos.WishlistItemDto;
import com.virax.microservices.wishlistservice.mappers.WishlistMapper;
import com.virax.microservices.wishlistservice.model.WishlistItem;
import com.virax.microservices.wishlistservice.repository.WishlistRepository;

@Service
public class WishlistService {
	
	@Autowired
	WishlistRepository wishlistRepository;
	@Autowired
	WishlistMapper wishlistMapper;
	@Autowired
	ProductClient productClient;
	
	Logger logger = LogManager.getLogger(WishlistService.class);
	
	public WishlistItemDto addToWishlist(Long userId, Long productId) {
		logger.info("Fetching product details from Product Service via Feign Client for productId: " + productId);
		ProductDto productDto;
		try {
			productDto = productClient.getProductById(productId);
		} catch (Exception e) {
			logger.error("Failed to fetch product with id " + productId + " from Product Service", e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or Product Service unavailable for id: " + productId);
		}
		
		WishlistItem wishlistItem = new WishlistItem();
		wishlistItem.setUserId(userId);
		wishlistItem.setProductId(productId);
		wishlistItem.setProductName(productDto.getName());
		wishlistItem.setProductPrice(productDto.getPrice());
		
		WishlistItem savedItem = wishlistRepository.save(wishlistItem);
		logger.info("Added item to wishlist with id: " + savedItem.getId() + " for userId: " + userId);
		return wishlistMapper.toWishlistItemDto(savedItem);
	}
	
	public List<WishlistItemDto> getWishlistByUserId(Long userId) {
		List<WishlistItem> items = wishlistRepository.findByUserId(userId);
		return wishlistMapper.toWishlistItemDtos(items);
	}
	
	public List<WishlistItemDto> getAllWishlistItems() {
		return wishlistMapper.toWishlistItemDtos(wishlistRepository.findAll());
	}
	
	public void deleteWishlistItem(Long id) {
		if (!wishlistRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found with id: " + id);
		}
		wishlistRepository.deleteById(id);
		logger.info("Deleted wishlist item with id: " + id);
	}
}
