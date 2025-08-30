package com.namekart.NotesApp.controller;

import com.namekart.NotesApp.entity.User;
import com.namekart.NotesApp.repository.UserRepository;
import com.namekart.NotesApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        User user = new User(username, passwordEncoder.encode(password));
        userRepository.save(user);

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token, "user", Map.of("id", user.getId(), "username", username)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token, "user", Map.of("id", user.getId(), "username", username)));
    }
}
