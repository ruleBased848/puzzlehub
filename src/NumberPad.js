function NumberPadRow({ labels }) {
  return (
    <div className="numberpad-row">
      {Array(3).fill().map((e, i) => <div key={i} className="numberpad-cell">{labels[i]}</div>)}
    </div>
  );
}

function NumberPad() {
  return (
    <div className="numberpad-background">
      <NumberPadRow labels={["1", "2", "3"]} />
      <NumberPadRow labels={["4", "5", "6"]} />
      <NumberPadRow labels={["7", "8", "9"]} />
      <NumberPadRow labels={["", "X", ""]} />
    </div>
  );
}

export default NumberPad;