package com.example.FinanceApp.service;

import com.example.FinanceApp.dto.auth.LoginRequest;
import com.example.FinanceApp.dto.auth.RegisterRequest;
import com.example.FinanceApp.entity.User;
import com.example.FinanceApp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void register(RegisterRequest request){
        if(userRepository.findUserByUsername(request.username).isPresent()) {
            throw new RuntimeException("User Already Exists");
        }

        User user=User.builder()
                .username(request.username)
                .password(request.password)
                .fullName(request.fullName)
                .phoneNumber(request.phoneNumber)
                .build();

        userRepository.save(user);
    }

    public void login(LoginRequest request, HttpSession session){
        User user = userRepository.findUserByUsername(request.username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!user.getPassword().equals(request.password))
            throw new RuntimeException("Invalid Credentials!");

        session.setAttribute("USER_ID",user.getId());
    }

    public void logout(HttpSession session){
        session.invalidate();
    }

}
