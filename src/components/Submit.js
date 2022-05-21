import { numbers } from '../states';
import styles from './Submit.module.css';

function Submit() {
  const clickHandler = () => {
    fetch("/members/main", {
      method: "POST",
      body: JSON.stringify(numbers),
    })
      .then((response) => response.text())
      .then((text) => alert(text));
  };

  return <div className={styles.background} onClick={clickHandler}>Submit</div>;
}

export default Submit;