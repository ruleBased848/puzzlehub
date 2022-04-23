import { updateNumbers, selectedCell } from '../states';
import './NumberPad.css';

function NumberPadRow({ labels }) {
  return (
    <div className="numberpad-row">
      {Array(3).fill().map((e, i) => <div key={i} className="numberpad-cell" onClick={() => updateNumbers(selectedCell, labels[i])}>{labels[i]}</div>)}
    </div>
  );
}

function NumberPadDelete() {
  return <div className="numberpad-delete" onClick={() => updateNumbers(selectedCell, "")}>X</div>;
}

function NumberPad() {
  return (
    <div className="numberpad-background">
      <NumberPadRow labels={["1", "2", "3"]} />
      <NumberPadRow labels={["4", "5", "6"]} />
      <NumberPadRow labels={["7", "8", "9"]} />
      <NumberPadDelete />
    </div>
  );
}

export default NumberPad;