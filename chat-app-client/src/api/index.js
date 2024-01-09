import Axios from "axios";
import { API_URL } from "../utils/constants";

const api = Axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(async (config) => {
  const token = localStorage.getItem("token");
  const needsToken = !config.url.includes("/auth");
  if (token && needsToken) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
