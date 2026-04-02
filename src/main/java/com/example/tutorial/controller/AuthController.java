package com.example.tutorial.controller;

import com.example.tutorial.advice.ApiResponse;
import com.example.tutorial.dto.LoginDTO;
import com.example.tutorial.dto.SignUpDTO;
import com.example.tutorial.dto.UserDTO;
import com.example.tutorial.service.AuthService;
import com.example.tutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(new ApiResponse<>(authService.login(loginDTO)));
    }
}
