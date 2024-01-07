import {
  ArrowButton,
  Avatar,
  ChatContainer,
  ConversationHeader,
  MainContainer,
  Message,
  MessageInput,
  MessageList,
} from "@chatscope/chat-ui-kit-react";
import "@chatscope/chat-ui-kit-styles/dist/default/styles.min.css";
import { useSubscription } from "react-stomp-hooks";
import { createAvatar, formatUsername } from "../utils/helpers";
import { useChat } from "../hooks/useChat";

const Chat = ({ user, logout }) => {
  const { data, sendMessage, onMessageReceived } = useChat(user);
  useSubscription("/topic/newMessage", onMessageReceived);

  return (
    <div
      style={{
        height: "100dvh",
      }}
    >
      <MainContainer>
        <ChatContainer
          style={{
            paddingTop: "10px",
          }}
        >
          <ConversationHeader>
            <Avatar src={createAvatar(user?.id)} />
            <ConversationHeader.Content
              userName={formatUsername(user?.fullName) || ""}
            />
            <ConversationHeader.Actions>
              <ArrowButton onClick={() => logout()} />
            </ConversationHeader.Actions>
          </ConversationHeader>
          <MessageList loading={!data}>
            {data && data.messages.length === 0 && (
              <MessageList.Content
                style={{
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "center",
                  height: "100%",
                  textAlign: "center",
                  fontSize: "1.2em",
                }}
              >
                <p>No messages yet</p>
              </MessageList.Content>
            )}
            {data &&
              data.messages.map((message, index) => {
                let isMe = message.user.id === user.id.toString();
                let prevMessage = data.messages[index - 1];
                let isFirst =
                  !prevMessage || prevMessage.user.id !== message.user.id;
                let isLast =
                  data.messages[index + 1]?.user.id !== message.user.id;
                let isSingle = isFirst && isLast;

                if (isMe) {
                  return (
                    <Message
                      key={index}
                      model={{
                        message: message.content,
                        sender: message.user.name,
                        direction: "outgoing",
                        position: isLast
                          ? "last"
                          : isFirst
                          ? "first"
                          : "normal",
                      }}
                    />
                  );
                } else {
                  return (
                    <Message
                      key={index}
                      model={{
                        message: message.content,
                        direction: "incoming",
                        position: isLast
                          ? "last"
                          : isFirst
                          ? "first"
                          : "normal",
                      }}
                      avatarSpacer={
                        (!isFirst && !isLast) || (!isSingle && !isLast)
                      }
                      avatarPosition="tl"
                    >
                      {(isSingle || isLast) && (
                        <Avatar
                          status={message.user.isOnline ? "available" : "dnd"}
                          src={message.user.avatar}
                          name={message.user.name}
                        />
                      )}
                      {(isSingle || isLast) && (
                        <Message.HtmlContent
                          html={`<strong style='display:flex; padding-bottom:5px'>${
                            message.user.name
                          }</strong>${
                            message.content
                          }  <small style='display: inline-block; margin-right:20px; width: 100%; text-align:right'>${new Date(
                            message.createdAt
                          ).toLocaleString("en-US", {
                            hour: "numeric",
                            minute: "numeric",
                          })}</small>`}
                        />
                      )}
                    </Message>
                  );
                }
              })}
          </MessageList>

          <MessageInput
            placeholder="Type your message here..."
            onSend={sendMessage}
            attachButton={false}
          />
        </ChatContainer>
      </MainContainer>
    </div>
  );
};

export default Chat;
