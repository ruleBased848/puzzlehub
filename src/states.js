import { action, observable } from 'mobx';

export const sudokuState = observable({
  numbers: Array(9).fill().map((e) => Array(9).fill("")),
  selectedCell: [0, 0],
});

export const updateNumbers = action((index, num) => {
  sudokuState.numbers[index[0]][index[1]] = num;
});

export const updateSelectedCell = action((index) => {
  sudokuState.selectedCell = index;
});

export const resetSudoku = action(() => {
  sudokuState.numbers = Array(9).fill().map((e) => Array(9).fill(""));
  sudokuState.selectedCell = [0, 0];
});

export const signIn = observable.box(false);

export const page = observable.box(1);