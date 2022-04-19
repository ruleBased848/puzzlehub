import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

function Cell() {
  return <div className="cell" />;
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