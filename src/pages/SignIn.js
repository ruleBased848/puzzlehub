import { signIn } from "../states";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./SignIn.module.css";

function SignIn() {
  const username = useRef(null);
  const password = useRef(null);
  const remember = useRef(null);
  const navigate = useNavigate();

  const clickHandler = () => {
    if (username.current.value === "") {
      alert("The username is required.");
    } else if (password.current.value === "") {
      alert("The password is required.");
    } else {
      fetch("/authentication", {
        method: "POST",
        body: JSON.stringify({
          username: username.current.value,
          password: password.current.value,
          remember: remember.current.checked,
        }),
      })
        .then((response) => response.json())
        .then((json) => {
          if (json.ok) {
            signIn.set(true);
            navigate("/");
          } else {
            alert(`Authentication failed.`);
          }
        });
    }
  };

  return (
    <div>
      <form className={styles.signin}>
        <div className={styles.title}>Sign in</div>
        <div>
          <label className={styles.mainlabel} htmlFor="username">User name</label>
          <input type="text" id="username" className={styles.maininput} ref={username} />
        </div>
        <div>
          <label className={styles.mainlabel} htmlFor="password">Password</label>
          <input type="password" id="password" className={styles.maininput} ref={password} />
        </div>
        <div className={styles.check}>
          <input type="checkbox" id="remember" className={styles.checkboxinput} ref={remember}/>
          <label className={styles.checkboxlabel} htmlFor="remember">Remember me</label>
        </div>
        <div>
          <button className={styles.button} type="button" onClick={clickHandler}>Sign In</button>
        </div>
      </form>
    </div>
  );
}

export default SignIn;