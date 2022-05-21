import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './SignUp.module.css';

function SignUp() {
  const username = useRef(null);
  const password = useRef(null);
  const passwordConfirm = useRef(null);
  const navigate = useNavigate();

  const clickHandler = () => {
    if (username.current.value === "")
    {
      alert("The username is required.");
    }
    else if (password.current.value === "")
    {
      alert("The password is required.");
    }
    else if (passwordConfirm.current.value === "")
    {
      alert("Please fill the password confirm field.");
    }
    else if (password.current.value !== passwordConfirm.current.value)
    {
      alert("Please check your password.");
    }
    else
    {
      fetch("/registration", {
        method: "POST",
        body: JSON.stringify({
          username: username.current.value,
          password: password.current.value,
        }),
      })
        .then((response) => response.json())
        .then((json) => {
          alert(`Registration ${json.ok ? "succeeded" : "failed"}.`);
          if (json.ok)
          {
            navigate("/signin");
          }
        });
    }
  };

  return (
    <div>
      <form className={styles.signup}>
        <div className={styles.title}>Sign up</div>
        <div>
          <label className={styles.label} htmlFor="username">User name (Required)</label>
          <input type="text" id="username" className={styles.input} ref={username} />
        </div>
        <div>
          <label className={styles.label} htmlFor="password">Password (Required)</label>
          <input type="password" id="password" className={styles.input} ref={password} />
        </div>
        <div>
          <label className={styles.label} htmlFor="password-confirm">Password Confirm (Required)</label>
          <input type="password" id="password-confirm" className={styles.input} ref={passwordConfirm} />
        </div>
        <div>
          <button className={styles.button} type="button" onClick={clickHandler}>Sign Up</button>
        </div>
      </form>
    </div>
  );
}

export default SignUp;