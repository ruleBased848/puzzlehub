import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

function Cell() {
  return <div className="cell" />;
}

function BoxRow() {
  return (
    <div className="box-row">
      {Array(3).fill().map((e, i) => <Cell key={i} />)}
    </div>
  );
}

function Box() {
  return (
    <div className="box">
      <BoxRow />
      <BoxRow />
      <BoxRow />
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

function Board() {
  return (
    <div className="board-background">
      <BoardRow />
      <BoardRow />
      <BoardRow />
    </div>
  );
}

function Game() {
  return (
    <div className="game">
      <div className="game-board">
        <Board />
      </div>
    </div>
  );
}

// ========================================

ReactDOM.render(
  <Game />,
  document.getElementById('root')
);