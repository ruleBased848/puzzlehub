import { numbers, selectedCell } from './states';
import Board from './components/Board';
import NumberPad from './components/NumberPad';
import Submit from './components/Submit';

function Game() {
  return (
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
  );
}

export default Game;