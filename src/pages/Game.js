import { observer } from "mobx-react";
import { signIn, pageControlState, changeSearchState, changeHamburgerState, closeAll } from '../states';
import { useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import Async from "react-async";
import Board from '../components/Board';
import styles from './Game.module.css';

const Header = observer(() => {
  const search = useRef(null);

  useEffect(() => {
    if (pageControlState.searchOpen)
    {
      search.current.focus();
    }
  },
  // eslint-disable-next-line
  [pageControlState.searchOpen]);

  const searchClickHandler = (e) => {
    e.stopPropagation();
    changeSearchState();
  };

  const hamburgerClickHandler = (e) => {
    e.stopPropagation();
    changeHamburgerState();
  };

  return (
    <header className={styles.header}>
      <div className={styles.logocontainer}>
        <img className={styles.image} src="/logo40.png" alt="Puzzle hub" />
      </div>
      <div className={styles.searchcontainer}>
        <button className={styles.button} type="button" onClick={searchClickHandler}>
          <img className={styles.image} src="/assets/search.png" alt="search" />
        </button>
        <input type="search" className={`${styles.search} ${pageControlState.searchOpen ? styles.visible : styles.invisible}`} ref={search} onClick={(e) => e.stopPropagation()} />
      </div>
      <div className={styles.hamburgercontainer}>
        <button className={styles.button} type="button" onClick={hamburgerClickHandler}>
          <img className={styles.image} src="/assets/hamburger.png" alt="menu" />
        </button>
        <nav className={`${styles.nav} ${pageControlState.hamburgerOpen ? styles.visible : styles.invisible}`} onClick={(e) => e.stopPropagation()}>
          {signIn.get() ? <ul className={styles.ul} /> : (
            <ul className={styles.ul}>
              <li>
                <Link to="/signup">Sign up</Link>
              </li>
              <li>
                <Link to="/signin">Sign in</Link>
              </li>
            </ul>
          )}
        </nav>
      </div>
    </header>
  );
});

const itemNum = 10;

const Main = observer(() => {
  const fetchPuzzles = async ({ itemNum, page }) => {
    const res = await fetch("/search", {
      method: "POST",
      body: JSON.stringify({ itemNum, page }),
    });
    return res.json();
  };

  return (
    <main className={styles.main}>
      <Async promiseFn={fetchPuzzles} itemNum={itemNum} page={pageControlState.page}>
        <Async.Pending>Loading...</Async.Pending>
        <Async.Fulfilled>
          {(data) => (
            <div className={styles.puzzledisplay}>
              <div className={styles.puzzlelist}>
                {data.puzzles.map((e, i) => (
                  <div key={i} className={styles.puzzleinfo}>
                    <div className={styles.boardcontainer}>
                      <Board numbers={[e.content]} selectedCell={[0, -1]} />
                      <div className={styles.meta}>
                        {e.username}<br />{e.createdAt}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </Async.Fulfilled>
      </Async>
    </main>
  );
});

function Game() {
  return (
    <div className={styles.background} onClick={closeAll}>
      <Header />
      <Main />
    </div>
  );
}

export default Game;