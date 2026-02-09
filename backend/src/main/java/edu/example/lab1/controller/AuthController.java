package edu.example.lab1.controller;

import edu.example.lab1.dto.AuthRequests;
import edu.example.lab1.model.User;
import edu.example.lab1.repository.UserRepository;
import edu.example.lab1.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", maxAge = 3600)
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, @Value("${jwt.secret}") String jwtSecret) {
        this.userRepository = userRepository;
        this.jwtUtil = new JwtUtil(jwtSecret);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequests.Register req) {
        if (req.email == null || req.password == null) return ResponseEntity.badRequest().body(Map.of("message", "Email and password required"));
        if (req.password.length() > 72) return ResponseEntity.badRequest().body(Map.of("message", "Password must be 72 characters or fewer"));
        if (userRepository.findByEmail(req.email).isPresent()) return ResponseEntity.badRequest().body(Map.of("message", "Email already registered"));

        User u = new User();
        u.setFirstName(req.firstName == null ? "" : req.firstName);
        u.setLastName(req.lastName == null ? "" : req.lastName);
        u.setEmail(req.email);
        u.setPassword(passwordEncoder.encode(req.password));

        User saved = userRepository.save(u);
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail());

        Map<String, Object> resp = new HashMap<>();
        resp.put("user", Map.of("id", saved.getId(), "firstName", saved.getFirstName(), "lastName", saved.getLastName(), "email", saved.getEmail()));
        resp.put("token", token);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequests.Login req) {
        if (req.email == null || req.password == null) return ResponseEntity.badRequest().body(Map.of("message", "Email and password required"));
        if (req.password.length() > 72) return ResponseEntity.badRequest().body(Map.of("message", "Password must be 72 characters or fewer"));
        var opt = userRepository.findByEmail(req.email);
        if (opt.isEmpty()) return ResponseEntity.status(400).body(Map.of("message", "Invalid credentials"));
        User u = opt.get();
        if (!passwordEncoder.matches(req.password, u.getPassword())) return ResponseEntity.status(400).body(Map.of("message", "Invalid credentials"));

        String token = jwtUtil.generateToken(u.getId(), u.getEmail());
        Map<String, Object> resp = new HashMap<>();
        resp.put("user", Map.of("id", u.getId(), "firstName", u.getFirstName(), "lastName", u.getLastName(), "email", u.getEmail()));
        resp.put("token", token);
        return ResponseEntity.ok(resp);
    }
}
