import { observer } from "mobx-react";
import { sudokuState, updateNumbers, updateSelectedCell, resetSudoku, signIn } from '../states';
import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import Board from '../components/Board';
import NumberPad from '../components/NumberPad';
import styles from './Upload.module.css';

const Upload = observer(
  () => {
    const username = useRef(null);
    const password = useRef(null);
    const remember = useRef(null);
    const navigate = useNavigate();

    const sudokuClickHandler = (i) => () => updateSelectedCell([Math.floor(i / 9), i % 9]);

    const clickHandler = (i) => () => updateNumbers(sudokuState.selectedCell, "" + (i + 1));

    const deleteHandler = () => updateNumbers(sudokuState.selectedCell, "");

    const submitHandler = () => {
      fetch("/members/main", {
        method: "POST",
        body: JSON.stringify(sudokuState.numbers),
      })
        .then((response) => response.json())
        .then((json) => {
          if (json.ok)
          {
            resetSudoku();
            navigate("/");
          }
          else if (json.reason === "invalid puzzle")
          {
            alert(`Only puzzles with one answer can be submitted.\nThere are ${json.number === 1000 ? "999+" : json.number} answers to this puzzle.`);
          }
          else if (json.reason === "not authenticated")
          {
            signIn.set(false);
          }
        });
    };

    const cancelHandler = () => {
      resetSudoku();
      navigate("/");
    };

    const signinHandler = () => {
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
            }
            else
            {
              alert(`Authentication failed.`);
            }
          });
      }
    };

    return signIn.get() ? (
      <div className={styles.background}>
        <div className={styles.container}>
          <div>
            <Board numbers={sudokuState.numbers} selectedCell={sudokuState.selectedCell} onClick={sudokuClickHandler} />
          </div>
          <div>
            <NumberPad clickHandler={clickHandler} deleteHandler={deleteHandler} />
          </div>
          <button className={styles.submitbutton} type="button" onClick={submitHandler}>Submit</button>
          <button className={styles.cancelbutton} type="button" onClick={cancelHandler}>Cancel</button>
        </div>
      </div>
    ) : (
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
            <button className={styles.signinbutton} type="button" onClick={signinHandler}>Sign In</button>
          </div>
        </form>
      </div>
    );
  }
);

export default Upload;