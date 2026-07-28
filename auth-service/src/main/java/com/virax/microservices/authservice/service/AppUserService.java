package com.virax.microservices.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.virax.microservices.authservice.dto.LoginUserDto;
import com.virax.microservices.authservice.dto.RegisterUserDto;
import com.virax.microservices.authservice.model.AppUser;
import com.virax.microservices.authservice.model.AppUserDetails;
import com.virax.microservices.authservice.repository.AppUserRepository;

@Service
public class AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    public Optional<AppUser> findByUsername(String username) {
        return Optional.ofNullable(appUserRepository.findByUsername(username));
    }

    public AppUser saveUser(RegisterUserDto registerUserDto) {

        if (appUserRepository.findByUsername(registerUserDto.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exists");
        }
        AppUser user = new AppUser();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setName(registerUserDto.getName());
        return appUserRepository.save(user);
    }

    public String generateToken(LoginUserDto loginUserDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()));

        if (authentication.isAuthenticated()) {
            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
            AppUser appUser = userDetails.getAppUser();
            return jwtService.generateToken(appUser);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad credentials");
    }
}
