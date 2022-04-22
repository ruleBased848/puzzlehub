import { numbers } from '../states';

function Submit() {
  const clickHandler = () => {
    fetch("/main", {
      method: "POST",
      body: JSON.stringify(numbers),
    })
      .then((response) => response.text())
      .then((text) => alert(text));
  };

  return <div className="submit-background" onClick={clickHandler}>Submit</div>;
}

export default Submit;