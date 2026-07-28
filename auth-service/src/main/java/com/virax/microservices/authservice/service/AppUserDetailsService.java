package com.virax.microservices.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.virax.microservices.authservice.model.AppUser;
import com.virax.microservices.authservice.model.AppUserDetails;
import com.virax.microservices.authservice.repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new AppUserDetails(appUser);
    }

}
