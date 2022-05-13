import { updateNumbers, selectedCell } from '../states';
import './NumberPad.css';

function NumberPad() {
  const clickHandler = (i) => () => updateNumbers(selectedCell, "" + (i + 1));
  const deleteHandler = () => updateNumbers(selectedCell, "");

  return (
    <svg className="sudoku-numberpad" viewBox="0 0 1055 110" preserveAspectRatio="none">
      <rect width="1055" height="110" fill="black" />
      {Array(9).fill().map((e, i) => <rect key={i} className="button" x={5 + 105 * i} y="5" width="100" height="100" fill="white" onClick={clickHandler(i)} />)}
      {Array(9).fill().map((e, i) => <text key={i} x={55 + 105 * i} y="75" textAnchor="middle" fontSize="58" fill="blue" onClick={clickHandler(i)}>{i + 1}</text>)}
      <rect className="button" x="950" y="5" width="100" height="100" fill="white" onClick={deleteHandler} />
      <text x="1000" y="75" textAnchor="middle" fontSize="58" fill="red" onClick={deleteHandler}>&times;</text>
    </svg>
  );
}

export default NumberPad;