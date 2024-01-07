import { StompSessionProvider } from "react-stomp-hooks";
import "./App.css";
import { useApp } from "./hooks/useApp";
import Chat from "./pages/chat";
import Loading from "./pages/loading";
import Auth from "./pages/auth";
import { API_WS_URL } from "./utils/constants";

function App() {
  const { appLoading, currentUser, setCurrentUser, ticket, handleLogout } =
    useApp();

  if (appLoading) {
    return <Loading />;
  }

  if (!currentUser) {
    return <Auth setCurrentUser={setCurrentUser} />;
  }

  if (!ticket) {
    return <Loading />;
  }

  return (
    <>
      <StompSessionProvider brokerURL={`${API_WS_URL}/chat/ws/${ticket}`}>
        <Chat user={currentUser} logout={handleLogout} />
      </StompSessionProvider>
    </>
  );
}

export default App;
