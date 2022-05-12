import { observer } from "mobx-react";
import { updateSelectedCell } from '../states';
import './Board.css';

const Cell = observer(
  ({ index, number, isSelected }) => (
    <svg className="cell" viewBox="0 0 100 100" preserveAspectRatio="none" onClick={() => updateSelectedCell(index)}>
      <rect width="100%" height="100%" fill={isSelected ? "pink" : "white"} />
      <text x="50" y="65" textAnchor="middle" fontSize="50" fill="black">{number}</text>
    </svg>
  )
);

const BoxRow = observer(
  ({ index, numbers, selectedCell }) => (
    <div className="box-row">
      {
        Array(3).fill().map(
          (e, i) => (
            <Cell
              key={i}
              index={[index[0], index[1] * 3 + i]}
              number={numbers[i]}
              isSelected={selectedCell.isSelected && selectedCell.at === i}
            />
          )
        )
      }
    </div>
  )
);

const Box = observer(
  ({ index, numbers, selectedCell }) => (
    <div className="box">
      {
        Array(3).fill().map(
          (e, i) => (
            <BoxRow
              key={i}
              index={[index, i]}
              numbers={numbers.slice(i * 3, (i + 1) * 3)}
              selectedCell={{
                isSelected: selectedCell.isSelected && Math.floor(selectedCell.at / 3) === i,
                at: selectedCell.at % 3,
              }}
            />
          )
        )
      }
    </div>
  )
);

const BoardRow = observer(
  ({ index, numbers, selectedCell }) => (
    <div className="board-row">
      {
        Array(3).fill().map(
          (e, i) => (
            <Box
              key={i}
              index={index * 3 + i}
              numbers={numbers[i]}
              selectedCell={{
                isSelected: selectedCell.isSelected && selectedCell.at[0] === i,
                at: selectedCell.at[1],
              }}
            />
          )
        )
      }
    </div>
  )
);

const Board = observer(
  ({ numbers, selectedCell }) => (
    <div className="board-background">
      {
        Array(3).fill().map(
          (e, i) => (
            <BoardRow
              key={i}
              index={i}
              numbers={numbers.slice(i * 3, (i + 1) * 3)}
              selectedCell={{
                isSelected: Math.floor(selectedCell[0] / 3) === i,
                at: [selectedCell[0] % 3, selectedCell[1]],
              }}
            />
          )
        )
      }
    </div>
  )
);

export default Board;