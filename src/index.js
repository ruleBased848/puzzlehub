import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

class Cell extends React.Component {
  render() {
    return <div className="cell" />;
  }
}

function Box() {
  return (
    <div className="box">
      <div className="box-row">
        <Cell />
        <Cell />
        <Cell />
      </div>
      <div className="box-row">
        <Cell />
        <Cell />
        <Cell />
      </div>
      <div className="box-row">
        <Cell />
        <Cell />
        <Cell />
      </div>
    </div>
  );
}

function BoardRow() {
  return (
    <div className="board-row">
      <Box />
      <Box />
      <Box />
    </div>
  );
}

class Board extends React.Component {
  render() {
    return (
      <div className="board-background">
        <BoardRow />
        <BoardRow />
        <BoardRow />
      </div>
    );
  }
}

class Game extends React.Component {
  render() {
    return (
      <div className="game">
        <div className="game-board">
          <Board />
        </div>
      </div>
    );
  }
}

// ========================================

ReactDOM.render(
  <Game />,
  document.getElementById('root')
);