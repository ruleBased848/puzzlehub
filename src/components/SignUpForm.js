import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import './SignUpForm.css';

function SignUpForm() {
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
    <form>
      <ul>
        <li>
          <label htmlFor="username">Username (Required)</label>
          <input type="text" id="username" ref={username} />
        </li>
        <li>
          <label htmlFor="password">Password (Required)</label>
          <input type="password" id="password" ref={password} />
        </li>
        <li>
          <label htmlFor="password-confirm">Password Confirm (Required)</label>
          <input type="password" id="password-confirm" ref={passwordConfirm} />
        </li>
        <li className="button">
          <button type="button" onClick={clickHandler}>Sign Up</button>
        </li>
      </ul>
    </form>
  );
}

export default SignUpForm;