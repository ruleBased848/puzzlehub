package com.rulebased848.puzzlehub.domain;

import org.springframework.data.repository.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.*;

public interface PuzzleRepository extends CrudRepository<Puzzle, Integer> {
    @Query("select p from Puzzle p")
    Page<Puzzle> getPuzzlesWithPaging(Pageable pageable);

    @Query("select count(p) from Puzzle p")
    long getCountOfPuzzles();
}