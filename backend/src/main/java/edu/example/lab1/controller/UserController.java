package edu.example.lab1.controller;

import edu.example.lab1.model.User;
import edu.example.lab1.repository.UserRepository;
import edu.example.lab1.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", maxAge = 3600)
public class UserController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, @Value("${jwt.secret}") String jwtSecret) {
        this.userRepository = userRepository;
        this.jwtUtil = new JwtUtil(jwtSecret);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));
        String token = auth.substring(7);
        try {
            Claims claims = jwtUtil.parseToken(token);
            Number idn = (Number) claims.get("id");
            if (idn == null) return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
            Long id = idn.longValue();
            var opt = userRepository.findById(id);
            if (opt.isEmpty()) return ResponseEntity.status(404).body(Map.of("message", "User not found"));
            User u = opt.get();
            Map<String, Object> out = Map.of(
                "id", u.getId(), 
                "firstName", u.getFirstName(), 
                "lastName", u.getLastName(), 
                "email", u.getEmail(), 
                "phone", u.getPhone() != null ? u.getPhone() : "",
                "address", u.getAddress() != null ? u.getAddress() : "",
                "city", u.getCity() != null ? u.getCity() : "",
                "country", u.getCountry() != null ? u.getCountry() : "",
                "created_at", u.getCreatedAt()
            );
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }
    }

    @PutMapping("/profile")
    @SuppressWarnings("null")
    public ResponseEntity<?> updateProfile(@RequestHeader(value = "Authorization", required = false) String auth, @RequestBody Map<String, String> body) {
        if (auth == null || !auth.startsWith("Bearer ")) return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));
        String token = auth.substring(7);
        try {
            Claims claims = jwtUtil.parseToken(token);
            Number idn = (Number) claims.get("id");
            if (idn == null) return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
            Long id = idn.longValue();
            var opt = userRepository.findById(id);
            if (opt.isEmpty()) return ResponseEntity.status(404).body(Map.of("message", "User not found"));
            
            User u = opt.get();
            if (body.containsKey("firstName")) u.setFirstName(body.get("firstName"));
            if (body.containsKey("lastName")) u.setLastName(body.get("lastName"));
            if (body.containsKey("email")) u.setEmail(body.get("email"));
            if (body.containsKey("phone")) u.setPhone(body.get("phone"));
            if (body.containsKey("address")) u.setAddress(body.get("address"));
            if (body.containsKey("city")) u.setCity(body.get("city"));
            if (body.containsKey("country")) u.setCountry(body.get("country"));
            
            userRepository.save(u);
            
            Map<String, Object> out = Map.of(
                "id", u.getId(), 
                "firstName", u.getFirstName(), 
                "lastName", u.getLastName(), 
                "email", u.getEmail(), 
                "phone", u.getPhone() != null ? u.getPhone() : "",
                "address", u.getAddress() != null ? u.getAddress() : "",
                "city", u.getCity() != null ? u.getCity() : "",
                "country", u.getCountry() != null ? u.getCountry() : "",
                "created_at", u.getCreatedAt()
            );
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }
    }
}
