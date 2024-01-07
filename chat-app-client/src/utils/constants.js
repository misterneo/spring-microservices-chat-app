// export const API_URL = "http://localhost/api";
// export const API_WS_URL = "ws://localhost/api";

// export const API_URL = "http://192.168.11.100:8080/api";
// export const API_WS_URL = "ws://192.168.11.100:8080/api";

export const API_URL = `${window.location.protocol}//${window.location.hostname}:${window.location.port}/api`;
export const API_WS_URL = `${
  window.location.protocol === "https:" ? "wss" : "ws"
}://${window.location.hostname}:${window.location.port}/api`;
