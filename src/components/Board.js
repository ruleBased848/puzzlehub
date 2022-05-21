import { observer } from "mobx-react";
import { updateSelectedCell } from '../states';
import styles from './Board.module.css';

const Board = observer(
  ({ numbers, selectedCell }) => {
    const box = (i) => Math.floor(i / 9);
    const cell = (i) => i % 9;
    const x = (i) => 5 + 265 * (box(i) % 3) + 87 * (cell(i) % 3);
    const y = (i) => 5 + 265 * Math.floor(box(i) / 3) + 87 * Math.floor(cell(i) / 3);
    const fill = (i) => 9 * selectedCell[0] + selectedCell[1] === i ? "pink" : "white";
    const clickHandler = (i) => () => updateSelectedCell([box(i), cell(i)]);

    return (
      <svg className={styles.sudoku} viewBox="0 0 800 800" preserveAspectRatio="none">
        <rect width="800" height="800" fill="black" />
        {
          Array(81).fill().map(
            (e, i) => <rect key={i} className={styles.cell} x={x(i)} y={y(i)} width="86" height="86" fill={fill(i)} onClick={clickHandler(i)} />
          )
        }
        {
          Array(81).fill().map(
            (e, i) => (
              <text key={i} className={styles.text} x={x(i) + 43} y={y(i) + 60} textAnchor="middle" fontSize="50" fill="black" onClick={clickHandler(i)}>
                {numbers[box(i)][cell(i)]}
              </text>
            )
          )
        }
      </svg>
    );
  }
);

export default Board;