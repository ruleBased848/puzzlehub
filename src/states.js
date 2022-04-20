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