import { selectedCell } from './states';
import Board from './Board';

function Game() {
  return (
    <div className="game">
      <div className="game-board">
        <Board selectedCell={selectedCell} />
      </div>
    </div>
  );
}

export default Game;