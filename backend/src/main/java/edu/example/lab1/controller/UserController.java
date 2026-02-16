package edu.example.lab1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.example.lab1.model.User;
import edu.example.lab1.repository.UserRepository;
import edu.example.lab1.util.JwtUtil;
import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true", maxAge = 3600)
public class UserController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, @Value("${jwt.secret}") String jwtSecret) {
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

    private String extractToken(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        return auth.substring(7);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String auth) {
        String token = extractToken(auth);
        if (token == null) {
            return ResponseEntity.status(401).body(buildApiResponse("error", "Unauthorized: Missing or invalid token", null));
        }

        try {
            Claims claims = jwtUtil.parseToken(token);
            Number idn = (Number) claims.get("id");
            if (idn == null) {
                return ResponseEntity.status(401).body(buildApiResponse("error", "Invalid token", null));
            }

            Long id = idn.longValue();
            var opt = userRepository.findById(id);
            if (opt.isEmpty()) {
                return ResponseEntity.status(404).body(buildApiResponse("error", "User not found", null));
            }

            User u = opt.get();
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", u.getId());
            userData.put("firstName", u.getFirstName());
            userData.put("lastName", u.getLastName());
            userData.put("email", u.getEmail());
            userData.put("phone", u.getPhone() != null ? u.getPhone() : "");
            userData.put("gender", u.getGender() != null ? u.getGender() : "");
            userData.put("age", u.getAge() != null ? u.getAge() : null);
            userData.put("createdAt", u.getCreatedAt());

            return ResponseEntity.ok(buildApiResponse("success", "User profile retrieved", userData));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(buildApiResponse("error", "Invalid or expired token", null));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestHeader(value = "Authorization", required = false) String auth, @RequestBody Map<String, String> body) {
        String token = extractToken(auth);
        if (token == null) {
            return ResponseEntity.status(401).body(buildApiResponse("error", "Unauthorized: Missing or invalid token", null));
        }

        try {
            Claims claims = jwtUtil.parseToken(token);
            Number idn = (Number) claims.get("id");
            if (idn == null) {
                return ResponseEntity.status(401).body(buildApiResponse("error", "Invalid token", null));
            }

            Long id = idn.longValue();
            var opt = userRepository.findById(id);
            if (opt.isEmpty()) {
                return ResponseEntity.status(404).body(buildApiResponse("error", "User not found", null));
            }

            User u = opt.get();

            // Validate and update fields
            if (body.containsKey("firstName") && body.get("firstName") != null) {
                u.setFirstName(body.get("firstName").trim());
            }
            if (body.containsKey("lastName") && body.get("lastName") != null) {
                u.setLastName(body.get("lastName").trim());
            }
            if (body.containsKey("email") && body.get("email") != null) {
                String newEmail = body.get("email").toLowerCase().trim();
                if (!newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    return ResponseEntity.badRequest().body(buildApiResponse("error", "Invalid email format", null));
                }
                // Check if email already exists (and is not the same user)
                var existingUser = userRepository.findByEmail(newEmail);
                if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                    return ResponseEntity.badRequest().body(buildApiResponse("error", "Email already in use", null));
                }
                u.setEmail(newEmail);
            }
            if (body.containsKey("phone") && body.get("phone") != null) {
                u.setPhone(body.get("phone").trim());
            }
            // New fields
            if (body.containsKey("gender") && body.get("gender") != null) {
                u.setGender(body.get("gender").trim());
            }
            if (body.containsKey("age") && body.get("age") != null) {
                try {
                    String ageStr = body.get("age").trim();
                    if (!ageStr.isEmpty()) {
                        Integer age = Integer.parseInt(ageStr);
                        if (age < 0 || age > 150) {
                            return ResponseEntity.badRequest().body(buildApiResponse("error", "Invalid age value", null));
                        }
                        u.setAge(age);
                    }
                } catch (NumberFormatException ex) {
                    return ResponseEntity.badRequest().body(buildApiResponse("error", "Age must be a number", null));
                }
            }

            userRepository.save(u);

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", u.getId());
            userData.put("firstName", u.getFirstName());
            userData.put("lastName", u.getLastName());
            userData.put("email", u.getEmail());
            userData.put("phone", u.getPhone() != null ? u.getPhone() : "");
            userData.put("gender", u.getGender() != null ? u.getGender() : "");
            userData.put("age", u.getAge() != null ? u.getAge() : null);
            userData.put("createdAt", u.getCreatedAt());

            return ResponseEntity.ok(buildApiResponse("success", "Profile updated successfully", userData));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(buildApiResponse("error", "Invalid or expired token", null));
        }
    }
}
