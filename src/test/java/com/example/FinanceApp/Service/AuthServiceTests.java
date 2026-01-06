package com.example.FinanceApp.Service;

import com.example.FinanceApp.dto.auth.LoginRequest;
import com.example.FinanceApp.dto.auth.RegisterRequest;
import com.example.FinanceApp.entity.User;
import com.example.FinanceApp.repository.UserRepository;
import com.example.FinanceApp.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.mockito.Mockito.*;
public class AuthServiceTests {
    UserRepository userRepository = mock(UserRepository.class);
    AuthService authService = new AuthService(userRepository);

    @Test
    void register_success() {
        RegisterRequest req = new RegisterRequest();
        req.username = "test@gmail.com";
        req.password = "123";
        req.fullName = "Test";
        req.phoneNumber = "999";

        when(userRepository.findUserByUsername(req.username))
                .thenReturn(Optional.empty());

        authService.register(req);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void login_success() {
        User user = new User();
        user.setId(1L);
        user.setUsername("test@gmail.com");
        user.setPassword("123");

        LoginRequest req = new LoginRequest();
        req.username = "test@gmail.com";
        req.password = "123";

        HttpSession session = mock(HttpSession.class);

        when(userRepository.findUserByUsername(req.username))
                .thenReturn(Optional.of(user));

        authService.login(req, session);

        verify(session).setAttribute("USER_ID", 1L);
    }
}
