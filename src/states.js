import { action, observable } from 'mobx';

export const selectedCell = observable([0, 0]);
export const updateSelectedCell = action((index) => {
  selectedCell[0] = index[0];
  selectedCell[1] = index[1];
});