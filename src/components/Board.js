import { observer } from "mobx-react";
import { updateSelectedCell } from '../states';

const Cell = observer(
  ({ index, number, isSelected }) => {
    return <div className={isSelected ? "selected-cell" : "cell"} onClick={() => updateSelectedCell(index)}>{number}</div>;
  }
);

const BoxRow = observer(
  ({ index, numbers, selectedCell }) => {
    return (
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
    );
  }
);

const Box = observer(
  ({ index, numbers, selectedCell }) => {
    return (
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
    );
  }
);

const BoardRow = observer(
  ({ index, numbers, selectedCell }) => {
    return (
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
    );
  }
);

const Board = observer(
  ({ numbers, selectedCell }) => {
    return (
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
    );
  }
);

export default Board;