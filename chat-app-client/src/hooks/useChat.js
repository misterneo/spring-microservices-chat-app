import { useEffect, useState } from "react";
import { useStompClient } from "react-stomp-hooks";
import { messageMapper } from "../utils/helpers";
import api from "../api";

export const useChat = (user) => {
  const [data, setData] = useState({
    messages: [],
  });
  const stompClient = useStompClient();

  const onMessageReceived = (msg) => {
    const response = JSON.parse(msg.body);
    const message = response.body;

    setData((prev) => {
      return {
        messages: [...prev.messages, messageMapper(message)],
      };
    });
  };

  const sendMessage = (message) => {
    if (stompClient && message) {
      const token = localStorage.getItem("token");
      stompClient.publish({
        destination: "/app/create",
        headers: {
          token: token,
        },
        body: JSON.stringify({
          userId: user.id,
          content: message,
        }),
      });
    }
  };

  const getMessages = async () => {
    const response = await api.get("/chat/messages");
    const data = response.data;

    const messages = data.map(messageMapper);

    setData({ messages });
  };

  useEffect(() => {
    getMessages();
  }, []);

  return {
    data,
    sendMessage,
    onMessageReceived,
  };
};
