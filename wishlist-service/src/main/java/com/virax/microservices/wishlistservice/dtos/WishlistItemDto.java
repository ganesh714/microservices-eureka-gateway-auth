package com.virax.microservices.wishlistservice.dtos;

import lombok.Data;

@Data
public class WishlistItemDto {
	private Long id;
	private Long userId;
	private Long productId;
	private String productName;
	private Double productPrice;
}
