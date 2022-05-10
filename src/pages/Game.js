import { observer } from "mobx-react";
import { numbers, selectedCell, signIn } from '../states';
import { Link } from 'react-router-dom';
import Board from '../components/Board';
import NumberPad from '../components/NumberPad';
import Submit from '../components/Submit';

const Game = observer(
  () => {
    const linksSignInFalse = (
      <div>
        <Link to="/signup">Sign up</Link>
        <Link to="/signin">Sign in</Link>
      </div>
    );

    return (
      <div>
        {signIn.get() ? <div /> : linksSignInFalse}
        <div className="game">
          <div className="game-board">
            <Board numbers={numbers} selectedCell={selectedCell} />
          </div>
          <div className="numberpad">
            <NumberPad />
          </div>
          <div className="submit">
            <Submit />
          </div>
        </div>
      </div>
    );
  }
);

export default Game;