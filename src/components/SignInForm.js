import { signIn } from '../states';
import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import './SignInForm.css';

function SignInForm() {
  const username = useRef(null);
  const password = useRef(null);
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
    else
    {
      fetch("/authentication", {
        method: "POST",
        body: JSON.stringify({
          username: username.current.value,
          password: password.current.value,
        }),
      })
        .then((response) => response.json())
        .then((json) => {
          if (json.ok)
          {
            signIn.set(true);
            navigate("/");
          }
          else
          {
            alert(`Authentication failed.`);
          }
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