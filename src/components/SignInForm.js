import { useRef } from 'react';
import './SignInForm.css';

function SignInForm() {
  const username = useRef(null);
  const password = useRef(null);

  const clickHandler = () => {
    if (username.current.value === "")
    {
      alert("The username is required.");
    }
    else if (password.current.value === "")
    {
      alert("The password is required.");
    }
    else
    {
      fetch("/authentication", {
        method: "POST",
        body: JSON.stringify({
          username: username.current.value,
          password: password.current.value,
        }),
      });
    }
  };

  return (
    <form>
      <ul>
        <li>
          <label htmlFor="username">Username</label>
          <input type="text" id="username" ref={username} />
        </li>
        <li>
          <label htmlFor="password">Password</label>
          <input type="password" id="password" ref={password} />
        </li>
        <li className="button">
          <button type="button" onClick={clickHandler}>Sign In</button>
        </li>
      </ul>
    </form>
  );
}

export default SignInForm;