package com.virax.microservices.wishlistservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virax.microservices.wishlistservice.model.WishlistItem;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
	
	public List<WishlistItem> findByUserId(Long userId);
}
