import React from 'react';
import ReactDOM from 'react-dom';
import { action, observable } from 'mobx';
import { observer } from "mobx-react";
import './index.css';

const selectedCell = observable([0, 0]);
const updateSelectedCell = action((index) => {
  selectedCell[0] = index[0];
  selectedCell[1] = index[1];
});

function Cell({ index, isSelected }) {
  return <div className={isSelected ? "selected-cell" : "cell"} onClick={() => updateSelectedCell(index)} />;
}

function BoxRow({ index, selectedCell }) {
  return (
    <div className="box-row">
      {
        Array(3).fill().map(
          (e, i) => (
            <Cell
              key={i}
              index={[index[0], index[1] * 3 + i]}
              isSelected={selectedCell.isSelected && selectedCell.at === i}
            />
          )
        )
      }
    </div>
  );
}

function Box({ index, selectedCell }) {
  return (
    <div className="box">
      {
        Array(3).fill().map(
          (e, i) => (
            <BoxRow
              key={i}
              index={[index, i]}
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

function BoardRow({ index, selectedCell }) {
  return (
    <div className="board-row">
      {
        Array(3).fill().map(
          (e, i) => (
            <Box
              key={i}
              index={index * 3 + i}
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

const Board = observer(
  ({ selectedCell }) => {
    return (
      <div className="board-background">
        {
          Array(3).fill().map(
            (e, i) => (
              <BoardRow
                key={i}
                index={i}
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

function Game() {
  return (
    <div className="game">
      <div className="game-board">
        <Board selectedCell={selectedCell} />
      </div>
    </div>
  );
}

// ========================================

ReactDOM.render(
  <Game />,
  document.getElementById('root')
);