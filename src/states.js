import { action, observable } from 'mobx';

export const numbers = observable(Array(9).fill().map((e) => Array(9).fill("")));
export const updateNumbers = action((index, num) => {
  numbers[index[0]][index[1]] = num;
});

export const selectedCell = observable([0, 0]);
export const updateSelectedCell = action((index) => {
  selectedCell[0] = index[0];
  selectedCell[1] = index[1];
});

export function resetSudoku() {
  for (let i = 0; i < 9; ++i)
  {
    for (let j = 0; j < 9; ++j)
    {
      updateNumbers([i, j], "");
    }
  }
  updateSelectedCell([0, 0]);
}

export const signIn = observable.box(false);

export const page = observable.box(1);