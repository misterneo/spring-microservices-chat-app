import { useState } from "react";
import api from "../api";

export const useAuth = (currentUser, setCurrentUser) => {
  const [register, setRegister] = useState(false);
  const [loading, setLoading] = useState(false);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const getCurrentUser = async () => {
    try {
      setLoading(true);
      const response = await api.get("/users/me");
      setLoading(false);
      setCurrentUser(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newEmail = email.trim();
    if (!newEmail) return;

    try {
      setLoading(true);
      let response;
      if (register) {
        response = await api.post("/auth/signup", {
          fullName: username,
          email: newEmail,
          password,
        });
      } else {
        response = await api.post("/auth/login", {
          email: newEmail,
          password,
        });
      }

      const data = response.data;
      const token = data.token;

      localStorage.setItem("token", token);
      localStorage.setItem("expiresIn", data.expiresIn);

      const user = await api.get("/users/me");
      localStorage.setItem("user", JSON.stringify(user.data));

      setLoading(false);
      setEmail("");
      setPassword("");
      setCurrentUser(user.data);
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("expiresIn");
    localStorage.removeItem("user");
    setCurrentUser(null);
  };

  return {
    loading,
    email,
    password,
    setEmail,
    setPassword,
    handleSubmit,
    handleLogout,
    getCurrentUser,
    currentUser,
    username,
    setUsername,
    register,
    setRegister,
  };
};
