package edu.example.lab1.dto;

public class AuthRequests {
    public static class Register {
        public String firstName;
        public String lastName;
        public String email;
        public String password;
    }

    public static class Login {
        public String email;
        public String password;
    }
}
