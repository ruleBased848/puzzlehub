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

class Board extends React.Component {
  render() {
    return (
      <div className="board-background">
        <div className="board-row">
          <Box />
          <Box />
          <Box />
        </div>
        <div className="board-row">
          <Box />
          <Box />
          <Box />
        </div>
        <div className="board-row">
          <Box />
          <Box />
          <Box />
        </div>
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