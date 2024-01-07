import { useAuth } from "../hooks/useAuth";
import { authStyles as styles } from "../utils/styles";

function Auth({ setCurrentUser }) {
  const {
    email,
    setEmail,
    password,
    setPassword,
    handleSubmit,
    loading,
    username,
    setUsername,
    register,
    setRegister,
  } = useAuth(null, setCurrentUser);

  return (
    <div style={styles.container}>
      <h1 style={styles.heading}>Tell Us Who You Are!</h1>

      <form style={styles.form} onSubmit={handleSubmit}>
        {register && (
          <input
            type="text"
            name="full-name"
            placeholder="Full Name"
            style={styles.input}
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        )}
        <input
          type="email"
          placeholder="Email"
          style={styles.input}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          style={styles.input}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit" style={styles.button} disabled={loading}>
          {loading ? "Joining Chat..." : "Join Chat"}
        </button>

        <div>
          <p style={styles.noAccount}>
            {register ? "Already" : "Don't"} have an account?{" "}
            <span
              style={styles.noAccountLink}
              onClick={() => setRegister((prev) => !prev)}
            >
              {register ? "Login" : "Register"}
            </span>
          </p>
        </div>
      </form>
    </div>
  );
}

export default Auth;
