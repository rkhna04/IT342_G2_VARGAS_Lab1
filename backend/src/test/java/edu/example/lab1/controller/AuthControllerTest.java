package edu.example.lab1.controller;

import edu.example.lab1.dto.AuthRequests;
import edu.example.lab1.model.User;
import edu.example.lab1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    private UserRepository userRepository;
    private AuthController controller;

    @BeforeEach
    void setUp() {
        this.userRepository = Mockito.mock(UserRepository.class);
        // Provide deterministic secret for token creation
        this.controller = new AuthController(userRepository, "test-secret");
    }

    @Test
    @DisplayName("Should return 400 when email or password is null on register")
    @SuppressWarnings("null")
    void register_missingFields_returnsBadRequest() {
        AuthRequests.Register r = new AuthRequests.Register();
        r.email = null; r.password = null;

        ResponseEntity<?> resp = controller.register(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(400);
        assertThat(resp.getBody()).isNotNull();
        Object body = resp.getBody();
        assertThat(((Map<?, ?>) body).get("message")).isEqualTo("Email and password required");
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("Should return 400 when password length > 72 on register")
    @SuppressWarnings("null")
    void register_tooLongPassword_returnsBadRequest() {
        AuthRequests.Register r = new AuthRequests.Register();
        r.email = "a@test.com";
        r.password = "x".repeat(73);

        ResponseEntity<?> resp = controller.register(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(400);
        assertThat(resp.getBody()).isNotNull();
        Object body = resp.getBody();
        assertThat(((Map<?, ?>) body).get("message")).isEqualTo("Password must be 72 characters or fewer");
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("Should return 400 when email already registered on register")
    @SuppressWarnings("null")
    void register_existingEmail_returnsBadRequest() {
        when(userRepository.findByEmail("dup@test.com")).thenReturn(Optional.of(new User()));
        AuthRequests.Register r = new AuthRequests.Register();
        r.email = "dup@test.com";
        r.password = "Secret1!";

        ResponseEntity<?> resp = controller.register(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(400);
        assertThat(resp.getBody()).isNotNull();
        Object body = resp.getBody();
        assertThat(((Map<?, ?>) body).get("message")).isEqualTo("Email already registered");
        verify(userRepository).findByEmail("dup@test.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should save user with hashed password and return token on register")
    @SuppressWarnings("null")
    void register_success_persistsHashedPassword_andReturnsToken() {
        when(userRepository.findByEmail("new@test.com")).thenReturn(Optional.empty());
        // capture saved user and simulate DB assigning id
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            u.setId(123L);
            return u;
        });

        AuthRequests.Register r = new AuthRequests.Register();
        r.firstName = "John";
        r.lastName = "Doe";
        r.email = "new@test.com";
        r.password = "Password1!";

        ResponseEntity<?> resp = controller.register(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(200);
        assertThat(resp.getBody()).isNotNull();
        Map<?, ?> body = (Map<?, ?>) resp.getBody();
        assertThat(body.get("user")).isNotNull();
        assertThat(body.get("token")).isNotNull();
        Map<?, ?> user = (Map<?, ?>) body.get("user");
        assertThat(user.get("id")).isEqualTo(123L);
        assertThat(user.get("email")).isEqualTo("new@test.com");
        // verify password encoding (not equal to raw)
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertThat(saved.getPassword()).isNotEqualTo("Password1!");
        assertThat(((String) body.get("token")).length()).isGreaterThan(10);
    }

    @Test
    @DisplayName("Should return 400 when invalid credentials on login (email not found)")
    @SuppressWarnings("null")
    void login_emailNotFound_returnsBadRequest() {
        when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());
        AuthRequests.Login r = new AuthRequests.Login();
        r.email = "missing@test.com";
        r.password = "irrelevant";

        ResponseEntity<?> resp = controller.login(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(400);
        assertThat(resp.getBody()).isNotNull();
        Object body = resp.getBody();
        assertThat(((Map<?, ?>) body).get("message")).isEqualTo("Invalid credentials");
    }

    @Test
    @DisplayName("Should return 400 when invalid credentials on login (wrong password)")
    @SuppressWarnings("null")
    void login_wrongPassword_returnsBadRequest() {
        User existing = new User();
        existing.setId(5L);
        existing.setEmail("user@test.com");
        // bcrypt hash for "CorrectPass1!" using default strength 10
        existing.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("CorrectPass1!"));
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(existing));

        AuthRequests.Login r = new AuthRequests.Login();
        r.email = "user@test.com";
        r.password = "WrongPass";

        ResponseEntity<?> resp = controller.login(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(400);
        assertThat(resp.getBody()).isNotNull();
        Object body = resp.getBody();
        assertThat(((Map<?, ?>) body).get("message")).isEqualTo("Invalid credentials");
    }

    @Test
    @DisplayName("Should return user and token on successful login")
    @SuppressWarnings("null")
    void login_success_returnsTokenAndUser() {
        User existing = new User();
        existing.setId(9L);
        existing.setFirstName("Alice");
        existing.setLastName("Smith");
        existing.setEmail("alice@test.com");
        existing.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("GoodPass!1"));
        when(userRepository.findByEmail("alice@test.com")).thenReturn(Optional.of(existing));

        AuthRequests.Login r = new AuthRequests.Login();
        r.email = "alice@test.com";
        r.password = "GoodPass!1";

        ResponseEntity<?> resp = controller.login(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(200);
        assertThat(resp.getBody()).isNotNull();
        Map<?, ?> body = (Map<?, ?>) resp.getBody();
        assertThat(body.get("user")).isNotNull();
        assertThat(body.get("token")).isNotNull();
        Map<?, ?> user = (Map<?, ?>) body.get("user");
        assertThat(user.get("id")).isEqualTo(9L);
        assertThat(user.get("firstName")).isEqualTo("Alice");
        assertThat(((String) body.get("token")).length()).isGreaterThan(10);
    }

    @Test
    @DisplayName("Should return 400 when password length > 72 on login")
    @SuppressWarnings("null")
    void login_tooLongPassword_returnsBadRequest() {
        AuthRequests.Login r = new AuthRequests.Login();
        r.email = "x@test.com";
        r.password = "y".repeat(73);

        ResponseEntity<?> resp = controller.login(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(400);
        assertThat(resp.getBody()).isNotNull();
        Object body = resp.getBody();
        assertThat(((Map<?, ?>) body).get("message")).isEqualTo("Password must be 72 characters or fewer");
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("Should default null first/last names to empty on register")
    @SuppressWarnings("null")
    void register_nullNames_defaultsToEmptyStrings() {
        when(userRepository.findByEmail("nn@test.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(inv -> { User u = inv.getArgument(0); u.setId(1L); return u; });

        AuthRequests.Register r = new AuthRequests.Register();
        r.email = "nn@test.com";
        r.password = "Password1!";
        r.firstName = null; r.lastName = null;

        ResponseEntity<?> resp = controller.register(r);

        assertThat(resp.getStatusCodeValue()).isEqualTo(200);
        verify(userRepository).save(argThat((User u) -> "".equals(u.getFirstName()) && "".equals(u.getLastName())));
    }
}
