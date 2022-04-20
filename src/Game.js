import { selectedCell } from './states';
import Board from './components/Board';
import NumberPad from './components/NumberPad';

function Game() {
  return (
    <div className="game">
      <div className="game-board">
        <Board selectedCell={selectedCell} />
      </div>
      <div className="numberpad">
        <NumberPad />
      </div>
    </div>
  );
}

export default Game;