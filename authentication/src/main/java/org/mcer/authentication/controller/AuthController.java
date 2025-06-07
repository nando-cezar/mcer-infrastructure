package org.mcer.authentication.controller;

import jakarta.validation.Valid;
import org.mcer.authentication.entities.dto.AuthRequest;
import org.mcer.authentication.entities.dto.AuthResponse;
import org.mcer.authentication.entities.dto.RegisterResponse;
import org.mcer.authentication.entities.User;
import org.mcer.authentication.repository.UserRepository;
import org.mcer.authentication.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
                          UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username()).orElseThrow();
        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole())))
        );

        return ResponseEntity.ok(new AuthResponse(HttpStatus.OK.value(), "Autenticação bem-sucedida", token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid AuthRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new RegisterResponse(HttpStatus.CONFLICT.value(), "Usuário já existe"));
        }

        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole("USER");

        userRepository.save(newUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterResponse(HttpStatus.CREATED.value(), "Usuário cadastrado com sucesso"));
    }
}
