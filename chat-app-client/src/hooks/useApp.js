import { useEffect, useState } from "react";
import api from "../api";
import { useAuth } from "./useAuth";

export const useApp = () => {
  const [appLoading, setAppLoading] = useState(true);
  const [currentUser, setCurrentUser] = useState(null);
  const { getCurrentUser, handleLogout } = useAuth(currentUser, setCurrentUser);
  const [ticket, setTicket] = useState(null);

  const getTicket = async () => {
    try {
      const response = await api.get("/chat/ws-ticket");
      const ticket = response.data;
      setTicket(ticket);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getCurrentUser().then(() => {
      setAppLoading(false);
    });
  }, []);

  useEffect(() => {
    if (currentUser) {
      getTicket();
    }
  }, [currentUser]);

  return {
    appLoading,
    currentUser,
    ticket,
    handleLogout,
    setCurrentUser,
  };
};
