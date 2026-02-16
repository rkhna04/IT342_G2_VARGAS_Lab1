package edu.example.lab1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.example.lab1.dto.AuthRequests;
import edu.example.lab1.model.User;
import edu.example.lab1.repository.UserRepository;
import edu.example.lab1.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true", maxAge = 3600)
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, @Value("${jwt.secret}") String jwtSecret) {
        this.userRepository = userRepository;
        this.jwtUtil = new JwtUtil(jwtSecret);
    }

    private Map<String, Object> buildApiResponse(String status, String message, Object data) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", status);
        resp.put("message", message);
        if (data != null) resp.put("data", data);
        return resp;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequests.Register req) {
        // Validation
        if (req.email == null || req.email.isBlank()) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Email is required", null));
        }
        if (req.password == null || req.password.isBlank()) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Password is required", null));
        }
        if (req.password.length() < 6) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Password must be at least 6 characters", null));
        }
        if (req.password.length() > 72) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Password must be 72 characters or fewer", null));
        }
        if (!req.email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Invalid email format", null));
        }
        if (userRepository.findByEmail(req.email).isPresent()) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Email already registered", null));
        }

        // Create user
        User u = new User();
        u.setFirstName(req.firstName == null ? "" : req.firstName.trim());
        u.setLastName(req.lastName == null ? "" : req.lastName.trim());
        u.setEmail(req.email.toLowerCase().trim());
        u.setPassword(passwordEncoder.encode(req.password));

        User saved = userRepository.save(u);
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail());

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", saved.getId());
        userData.put("firstName", saved.getFirstName());
        userData.put("lastName", saved.getLastName());
        userData.put("email", saved.getEmail());

        Map<String, Object> respData = new HashMap<>();
        respData.put("user", userData);
        respData.put("token", token);

        return ResponseEntity.ok(buildApiResponse("success", "Registration successful", respData));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequests.Login req) {
        // Validation
        if (req.email == null || req.email.isBlank()) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Email is required", null));
        }
        if (req.password == null || req.password.isBlank()) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Password is required", null));
        }
        if (req.password.length() > 72) {
            return ResponseEntity.badRequest().body(buildApiResponse("error", "Invalid credentials", null));
        }

        var opt = userRepository.findByEmail(req.email.toLowerCase().trim());
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body(buildApiResponse("error", "Invalid credentials", null));
        }

        User u = opt.get();
        if (!passwordEncoder.matches(req.password, u.getPassword())) {
            return ResponseEntity.status(401).body(buildApiResponse("error", "Invalid credentials", null));
        }

        String token = jwtUtil.generateToken(u.getId(), u.getEmail());
        
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", u.getId());
        userData.put("firstName", u.getFirstName());
        userData.put("lastName", u.getLastName());
        userData.put("email", u.getEmail());

        Map<String, Object> respData = new HashMap<>();
        respData.put("user", userData);
        respData.put("token", token);

        return ResponseEntity.ok(buildApiResponse("success", "Login successful", respData));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT logout is stateless - client simply discards the token
        // This endpoint confirms logout on the server side
        return ResponseEntity.ok(buildApiResponse("success", "Logout successful", null));
    }
}
