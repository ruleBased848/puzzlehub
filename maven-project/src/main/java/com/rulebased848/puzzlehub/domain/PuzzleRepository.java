package com.rulebased848.puzzlehub.domain;

import org.springframework.data.repository.*;
import org.springframework.data.jpa.repository.*;

public interface PuzzleRepository extends CrudRepository<Puzzle, Integer> {
    @Query("select count(p) from Puzzle p")
    long getCountOfPuzzles();
}