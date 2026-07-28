package com.virax.microservices.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virax.microservices.authservice.dto.LoginUserDto;
import com.virax.microservices.authservice.dto.RegisterUserDto;
import com.virax.microservices.authservice.model.AppUser;
import com.virax.microservices.authservice.service.AppUserService;
import com.virax.microservices.authservice.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(appUserService.saveUser(registerUserDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto loginUserDto) {
        return new ResponseEntity<>(appUserService.generateToken(loginUserDto), HttpStatus.OK);
    }
    
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        return new ResponseEntity<>(jwtService.validateToken(token), HttpStatus.OK);
    }
}
