package com.rulebased848.puzzlehub.domain;

import org.springframework.data.repository.*;

public interface UserRepository extends CrudRepository<User, String> {
}