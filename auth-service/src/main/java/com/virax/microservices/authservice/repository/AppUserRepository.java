package com.virax.microservices.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virax.microservices.authservice.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	public AppUser findByUsername(String userName);
}
