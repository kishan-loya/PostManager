package com.example.tutorial.controller;

import com.example.tutorial.advice.ApiResponse;
import com.example.tutorial.dto.LoginDTO;
import com.example.tutorial.dto.LoginResponseDTO;
import com.example.tutorial.dto.SignUpDTO;
import com.example.tutorial.dto.UserDTO;
import com.example.tutorial.service.AuthService;
import com.example.tutorial.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.environment}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        Cookie cookie  = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);
        return ResponseEntity.ok(new ApiResponse<>(loginResponseDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new AuthenticationServiceException("Refresh token not found");
        }
        String refreshToken = Arrays.stream(request.getCookies())
                                .filter((cookie) -> "refreshToken".equals(cookie.getName()))
                                .findFirst()
                                .map(cookie -> cookie.getValue())
                                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found"));
        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);
        Cookie cookie  = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);
        return ResponseEntity.ok(new ApiResponse<>(loginResponseDTO));
    }
}
