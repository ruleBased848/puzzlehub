function Submit() {
  const clickHandler = () => {
    fetch("/main")
      .then((response) => response.text())
      .then((text) => alert(text));
  };

  return <div className="submit-background" onClick={clickHandler}>Submit</div>;
}

export default Submit;