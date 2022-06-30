package com.rulebased848.puzzlehub.domain;

import org.springframework.data.repository.*;

public interface PuzzleRepository extends CrudRepository<Puzzle, Integer> {
}