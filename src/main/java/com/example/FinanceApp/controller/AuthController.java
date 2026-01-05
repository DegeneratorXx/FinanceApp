package com.example.FinanceApp.controller;

import com.example.FinanceApp.dto.auth.AuthResponse;
import com.example.FinanceApp.dto.auth.LoginRequest;
import com.example.FinanceApp.dto.auth.RegisterRequest;
import com.example.FinanceApp.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        authService.register(request);
        return new ResponseEntity<>(
                new AuthResponse("User registered successfully"),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpSession session){
        authService.login(request,session);
        return new ResponseEntity<>(new AuthResponse("Login Successful"),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpSession session){
        authService.logout(session);
        return new ResponseEntity<>(new AuthResponse("Logout Successfull"),HttpStatus.OK);
    }
}
