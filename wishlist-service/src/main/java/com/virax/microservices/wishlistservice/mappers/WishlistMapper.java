package com.virax.microservices.wishlistservice.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.virax.microservices.wishlistservice.dtos.WishlistItemDto;
import com.virax.microservices.wishlistservice.model.WishlistItem;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
	
	public WishlistItemDto toWishlistItemDto(WishlistItem wishlistItem);
	
	public WishlistItem toWishlistItem(WishlistItemDto wishlistItemDto);
	
	public List<WishlistItem> toWishlistItems(List<WishlistItemDto> wishlistItemDtos);
	
	public List<WishlistItemDto> toWishlistItemDtos(List<WishlistItem> wishlistItems);
}
