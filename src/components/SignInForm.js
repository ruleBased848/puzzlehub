import { signIn } from '../states';
import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import './SignInForm.css';

function SignInForm() {
  const username = useRef(null);
  const password = useRef(null);
  const remember = useRef(null);
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
          remember: remember.current.checked,
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
    <form className="signin">
      <div className="title">Sign in</div>
      <div>
        <label htmlFor="username">User name</label>
        <input type="text" id="username" ref={username} />
      </div>
      <div>
        <label htmlFor="password">Password</label>
        <input type="password" id="password" ref={password} />
      </div>
      <div className="check">
        <input type="checkbox" id="remember" ref={remember}/>
        <label htmlFor="remember">Remember me</label>
      </div>
      <div>
        <button type="button" onClick={clickHandler}>Sign In</button>
      </div>
    </form>
  );
}

export default SignInForm;