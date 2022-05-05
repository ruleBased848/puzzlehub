import { numbers, selectedCell } from '../states';
import { Link } from 'react-router-dom';
import Board from '../components/Board';
import NumberPad from '../components/NumberPad';
import Submit from '../components/Submit';

function Game() {
  return (
    <div>
      <div>
        <Link to="/signup">Sign up</Link>
        <Link to="/signin">Sign in</Link>
      </div>
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

export default Game;