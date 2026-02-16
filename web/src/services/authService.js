const API_BASE_URL = "http://localhost:8081/api";

export const authService = {
  register: async (firstName, lastName, email, password) => {
    try {
      console.log("Registering user:", { firstName, lastName, email });
      const response = await fetch(`${API_BASE_URL}/auth/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          firstName,
          lastName,
          email,
          password,
        }),
      });

      const text = await response.text();
      console.log("Registration response status:", response.status);
      console.log("Registration response text:", text);
      let wrapper;
      try {
        wrapper = JSON.parse(text);
      } catch (e) {
        throw new Error("Server returned invalid JSON response");
      }

      if (!response.ok || wrapper.status !== "success") {
        throw new Error(wrapper.message || "Registration failed");
      }

      // Backend wraps payload under `data`
      return wrapper.data;
    } catch (error) {
      console.error("Registration error:", error);
      throw error;
    }
  },

  login: async (email, password) => {
    try {
      console.log("Logging in user:", email);
      const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          password,
        }),
      });

      const text = await response.text();
      console.log("Login response status:", response.status);
      console.log("Login response text:", text);
      let wrapper;
      try {
        wrapper = JSON.parse(text);
      } catch (e) {
        throw new Error("Server returned invalid JSON response");
      }

      if (!response.ok || wrapper.status !== "success") {
        throw new Error(wrapper.message || "Login failed");
      }

      const payload = wrapper.data || {};
      if (payload.token && payload.user) {
        localStorage.setItem("authToken", payload.token);
        localStorage.setItem("user", JSON.stringify(payload.user));
      }

      // Return payload directly for component consumption
      return payload;
    } catch (error) {
      console.error("Login error:", error);
      throw error;
    }
  },

  getUser: async (token) => {
    try {
      console.log("Fetching user with token");
      const response = await fetch(`${API_BASE_URL}/user/me`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });

      const text = await response.text();
      console.log("GetUser response status:", response.status);
      console.log("GetUser response text:", text);
      let wrapper;
      try {
        wrapper = JSON.parse(text);
      } catch (e) {
        throw new Error("Server returned invalid JSON response");
      }

      if (!response.ok || wrapper.status !== "success") {
        throw new Error(wrapper.message || "Failed to fetch user");
      }

      return wrapper.data;
    } catch (error) {
      console.error("GetUser error:", error);
      throw error;
    }
  },

  updateProfile: async (profileData) => {
    try {
      const token = localStorage.getItem("authToken");
      if (!token) {
        throw new Error("No authentication token found");
      }

      console.log("Updating profile:", profileData);
      const response = await fetch(`${API_BASE_URL}/user/profile`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(profileData),
      });

      const text = await response.text();
      console.log("Update profile response status:", response.status);
      console.log("Update profile response text:", text);
      let wrapper;
      try {
        wrapper = JSON.parse(text);
      } catch (e) {
        throw new Error("Server returned invalid JSON response");
      }

      if (!response.ok || wrapper.status !== "success") {
        throw new Error(wrapper.message || "Failed to update profile");
      }

      const user = wrapper.data;
      // Update local storage with new user data
      localStorage.setItem("user", JSON.stringify(user));

      return user;
    } catch (error) {
      console.error("Update profile error:", error);
      throw error;
    }
  },

  logout: () => {
    localStorage.removeItem("authToken");
    localStorage.removeItem("user");
  },
};

export default authService;
